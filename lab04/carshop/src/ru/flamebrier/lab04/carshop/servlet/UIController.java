package ru.flamebrier.lab04.carshop.servlet;

import ru.flamebrier.lab04.carshop.dao.*;
import ru.flamebrier.lab04.carshop.models.*;

import javax.annotation.Resource;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.sql.DataSource;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@WebServlet(name = "UIController")
public class UIController extends HttpServlet {
    @Resource(name = "jdbc/carshop")
    private static DataSource ds;
    private DAO dao;

    public void init() {
        dao = new DAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getServletPath();

        try {
            switch (action) {
                case "/newCar":
                    showNewForm(request, response);
                    break;
                case "/editCar":
                    showEditForm(request, response);
                    break;
                case "/addCar":
                    addCar(request, response);
                    break;
                case "/updateCar":
                    editCar(request, response);
                    break;
                case "/deleteCar":
                    deleteCar(request, response);
                    break;
                default:
                    showCars(request, response);
                    break;
            }
        } catch (DAOException ex) {
            throw new ServletException(ex);
        }
    }

    private void showCars(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException, DAOException {
            List<Car> cars = dao.selectAllCars();
            request.setAttribute("cars", cars);
            RequestDispatcher dispatcher = request.getRequestDispatcher("CarList.jsp");
            dispatcher.forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("CarForm.jsp");
        dispatcher.forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException, DAOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Car existingCar = dao.selectOneCar(id);
        RequestDispatcher dispatcher = request.getRequestDispatcher("CarForm.jsp");
        request.setAttribute("car", existingCar);
        dispatcher.forward(request, response);
    }

    private void addCar(HttpServletRequest request, HttpServletResponse response)
            throws IOException, DAOException {
        Car car = carFromRequest(request);
        dao.insertCar(car);
        response.sendRedirect("list");
    }

    private void editCar(HttpServletRequest request, HttpServletResponse response)
            throws IOException, DAOException {
        Car car = carFromRequest(request);
        int id = Integer.parseInt(request.getParameter("id"));
        car.setId(id);
        dao.updateCar(car);
        response.sendRedirect("list");
    }

    private void deleteCar(HttpServletRequest request, HttpServletResponse response)
            throws IOException, DAOException {
        int id = Integer.parseInt(request.getParameter("id"));
        dao.deleteCar(id);
        response.sendRedirect("list");
    }

    private Car carFromRequest(HttpServletRequest request) {
        Car car = new Car();
        car.setBrand(request.getParameter("brand"));
        car.setModel(request.getParameter("model"));
        car.setColor(request.getParameter("color"));
        car.setIntYear(Integer.parseInt(request.getParameter("year")));
        car.setMileage(Long.parseLong(request.getParameter("mileage")));
        car.setOwner(Integer.parseInt(request.getParameter("owner")));
        car.setPrice(BigDecimal.valueOf(Integer.parseInt(request.getParameter("price"))));
        return car;
    }
}
