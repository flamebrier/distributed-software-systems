package ru.flamebrier.lab05.carshop.servlet;

import ru.flamebrier.lab05.carshop.dao.DAO;
import ru.flamebrier.lab05.carshop.dao.DAOException;
import ru.flamebrier.lab05.carshop.models.Car;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.List;

public class UIController extends HttpServlet {
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
                case "/list":
                    showCars(request, response);
                    break;
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
            }
        } catch (DAOException ex) {
            throw new ServletException(ex);
        }
    }

    private void showCars(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException, DAOException {
        List<Car> cars = dao.selectAllCars();
        request.setAttribute("cars", cars);
        RequestDispatcher dispatcher = request.getRequestDispatcher("Car/CarList.jsp");
        dispatcher.forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("Car/CarForm.jsp");
        dispatcher.forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException, DAOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Car existingCar = dao.selectOneCar(id);
        RequestDispatcher dispatcher = request.getRequestDispatcher("Car/CarForm.jsp");
        request.setAttribute("car", existingCar);
        dispatcher.forward(request, response);
    }

    private void addCar(HttpServletRequest request, HttpServletResponse response)
            throws IOException, DAOException {
        Car car = carFromRequest(request);
        dao.insertCar(car);
        response.sendRedirect("/list");
    }

    private void editCar(HttpServletRequest request, HttpServletResponse response)
            throws IOException, DAOException {
        Car car = carFromRequest(request);
        int id = Integer.parseInt(request.getParameter("id"));
        car.setId(id);
        dao.updateCar(car);
        response.sendRedirect("/list");
    }

    private void deleteCar(HttpServletRequest request, HttpServletResponse response)
            throws IOException, DAOException {
        int id = Integer.parseInt(request.getParameter("id"));
        dao.deleteCar(id);
        response.sendRedirect("/list");
    }

    private Car carFromRequest(HttpServletRequest request) throws UnsupportedEncodingException {
        request.setCharacterEncoding("UTF-8");
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
