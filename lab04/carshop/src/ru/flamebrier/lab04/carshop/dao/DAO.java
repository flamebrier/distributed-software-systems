package ru.flamebrier.lab04.carshop.dao;

import ru.flamebrier.lab04.carshop.models.Car;
import ru.flamebrier.lab04.carshop.models.Owner;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import ru.flamebrier.lab04.carshop.dao.DAOException;

public class DAO {
     /**
     * Установить подключение к базе данных
     */
    private Connection establishConnection() throws DAOException {
        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
            throw new DAOException("Произошла ошибка при загрузке MySQL драйвера " + ex.getMessage());
        }

        try {
            InitialContext ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/carshop");
            conn = ds.getConnection();
            //conn = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
        } catch (SQLException | NamingException ex) {
            throw new DAOException("Произошла ошибка при установке соединения с БД " + ex.getMessage());
        }
        return conn;
    }

    /**
     * Закрытие соединения с БД
     */
    private void closeConnection(Connection conn) throws DAOException {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (Exception ex) {
            throw new DAOException("Произошла ошибка при закрытии соединения с БД " + ex.getMessage());
        }
    }

    public void insertCar(Car car) throws DAOException {
        Connection conn = establishConnection();
        PreparedStatement stmt = null;
        int rows = 0;

        try {
            stmt = conn.prepareStatement(
                    "INSERT INTO Car(Brand, Model, Color, Year, Mileage, Owner, Price)"
                            + "VALUES (?, ?, ?, ?, ?, ?, ?)");
            stmt.setString(1, car.getBrand());
            stmt.setString(2, car.getModel());
            stmt.setString(3, car.getColor());
            stmt.setDate(4, new Date(car.getDateYear().getTime()));
            stmt.setLong(5, car.getMileage());
            stmt.setInt(6, car.getOwner());
            stmt.setBigDecimal(7, car.getPrice());

            rows = stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new DAOException("Произошла ошибка при выполнении " + ex.getMessage());
        } finally {
            try {
                if (stmt != null && !stmt.isClosed()) {
                    stmt.close();
                }
            } catch (Exception e) {
                // do nothing
            }
            closeConnection(conn);
        }
        if (rows < 0) {
            throw new DAOException("Ошибка при добавлении");
        }
    }

    public void updateCar(Car car, int field) throws DAOException {
        Connection conn = establishConnection();
        PreparedStatement stmt = null;
        int rows = 0;

        try {
            switch (field) {
                case 1:
                    stmt = conn.prepareStatement(
                            "UPDATE Car SET Brand=? WHERE CarId=?");
                    stmt.setString(1, car.getBrand());
                    break;
                case 2:
                    stmt = conn.prepareStatement(
                            "UPDATE Car SET Model=? WHERE CarId=?");
                    stmt.setString(1, car.getModel());
                    break;
                case 3:
                    stmt = conn.prepareStatement(
                            "UPDATE Car SET Color=? WHERE CarId=?");
                    stmt.setString(1, car.getColor());
                    break;
                case 4:
                    stmt = conn.prepareStatement(
                            "UPDATE Car SET Year=? WHERE CarId=?");
                    stmt.setDate(1, new Date(car.getDateYear().getTime()));
                    break;
                case 5:
                    stmt = conn.prepareStatement(
                            "UPDATE Car SET Mileage=? WHERE CarId=?");
                    stmt.setLong(1, car.getMileage());
                    break;
                case 6:
                    stmt = conn.prepareStatement(
                            "UPDATE Car SET Owner=? WHERE CarId=?");
                    stmt.setInt(1, car.getOwner());
                    break;
                case 7:
                    stmt = conn.prepareStatement(
                            "UPDATE Car SET Price=? WHERE CarId=?");
                    stmt.setBigDecimal(1, car.getPrice());
                    break;
                default:
                    break;
            }
            stmt.setInt(2, car.getId());
            rows = stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new DAOException("Произошла ошибка при выполнении " + ex.getMessage());
        } finally {
            try {
                if (stmt != null && !stmt.isClosed()) {
                    stmt.close();
                }
            } catch (Exception e) {
                // do nothing
            }
            closeConnection(conn);
        }
        if (rows < 0) {
            throw new DAOException("Ошибка при изменении");
        }
    }

    public void updateCar(Car car) throws DAOException {
        Connection conn = establishConnection();
        PreparedStatement stmt = null;
        int rows = 0;

        try {
            stmt = conn.prepareStatement(
                    "UPDATE Car SET " +
                            "Brand=?, " +
                            "Model=?, " +
                            "Color=?, " +
                            "`Year`=?, " +
                            "Mileage=?, " +
                            "Owner=?, " +
                            "Price=? " +
                            "WHERE CarId=?");
            stmt.setString(1, car.getBrand());
            stmt.setString(2, car.getModel());
            stmt.setString(3, car.getColor());
            stmt.setDate(4, new Date(car.getDateYear().getTime()));
            stmt.setLong(5, car.getMileage());
            stmt.setInt(6, car.getOwner());
            stmt.setBigDecimal(7, car.getPrice());
            stmt.setInt(8, car.getId());
            rows = stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new DAOException("Произошла ошибка при выполнении " + ex.getMessage());
        } finally {
            try {
                if (stmt != null && !stmt.isClosed()) {
                    stmt.close();
                }
            } catch (Exception e) {
                // do nothing
            }
            closeConnection(conn);
        }
        if (rows < 0) {
            throw new DAOException("Ошибка при изменении");
        }
    }


    public void deleteCar(int carId) throws DAOException {
        Connection conn = establishConnection();
        PreparedStatement stmt = null;
        int rows = 0;

        try {
            stmt = conn.prepareStatement(
                    "DELETE FROM Car WHERE CarId=?");
            stmt.setInt(1, carId);
            rows = stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new DAOException("Произошла ошибка при выполнении " + ex.getMessage());
        } finally {
            try {
                if (stmt != null && !stmt.isClosed()) {
                    stmt.close();
                }
            } catch (Exception e) {
                // do nothing
            }
            closeConnection(conn);
        }

        if (rows < 0) {
            throw new DAOException("Ошибка при удалении");
        }
    }

    public void insertOwner(Owner owner) throws DAOException {
        Connection conn = establishConnection();
        PreparedStatement stmt = null;
        int rows = 0;
        int id = -1;

        try {
            stmt = conn.prepareStatement(
                    "INSERT INTO Owner(Name, Lastname, Birthday, Email, Phone)" +
                            " VALUES (?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, owner.getName());
            stmt.setString(2, owner.getLastName());
            stmt.setDate(3, new Date(owner.getBirthday().getTime()));
            stmt.setString(4, owner.getEmail());
            stmt.setString(5, owner.getPhone());

            rows = stmt.executeUpdate();
            if (rows > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                rs.next();
                id = rs.getInt(1);
            }
        } catch (SQLException ex) {
            throw new DAOException("Произошла ошибка при выполнении " + ex.getMessage());
        } finally {
            try {
                if (stmt != null && !stmt.isClosed()) {
                    stmt.close();
                }
            } catch (Exception e) {
                // do nothing
            }
            closeConnection(conn);
        }
        if (id == -1) {
            throw new DAOException("Ошибка при добавлении");
        }
    }

    public Car selectOneCar(int carId) throws DAOException {
        Connection conn = establishConnection();
        Car car = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = conn.prepareStatement("SELECT CarId, Brand, Model, Color, Year, Mileage, Owner, Price FROM Car WHERE CarId=" + carId,
                    Statement.RETURN_GENERATED_KEYS);
            rs = stmt.executeQuery();

            if (rs.next()) {
                car = new Car(
                        carId,
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getDate(5),
                        rs.getInt(6),
                        rs.getInt(7),
                        rs.getBigDecimal(8)
                );
            }
        } catch (SQLException ex) {
            throw new DAOException("Произошла ошибка при выполнении " + ex.getMessage());
        } finally {
            try {
                if (rs != null && !rs.isClosed()) {
                    rs.close();
                }
            } catch (Exception e) {
                // do nothing
            }
            try {
                if (stmt != null && !stmt.isClosed()) {
                    stmt.close();
                }
            } catch (Exception e) {
                // do nothing
            }
            closeConnection(conn);
        }
        if (car == null) {
            throw new DAOException("Ошибка при чтении");
        }
        return car;
    }

    public List<Car> selectAllCars() throws DAOException {
        Connection conn = establishConnection();
        establishConnection();
        List<Car> cars = new ArrayList<>();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = conn.prepareStatement("SELECT CarId, Brand, Model, Color, Year, Mileage, Owner, Price FROM Car ORDER BY CarId",
                    Statement.RETURN_GENERATED_KEYS);
            rs = stmt.executeQuery();

            int counter = 0;
            while (rs.next()) {
                cars.add(
                    new Car(
                            rs.getInt("CarId"),
                            rs.getString("Brand"),
                            rs.getString("Model"),
                            rs.getString("Color"),
                            rs.getDate("Year"),
                            rs.getInt("Mileage"),
                            rs.getInt("Owner"),
                            rs.getBigDecimal("Price")
                    ));
            }
        } catch (SQLException ex) {
            throw new DAOException("Произошла ошибка при выполнении " + ex.getMessage());
        } finally {
            try {
                if (rs != null && !rs.isClosed()) {
                    rs.close();
                }
            } catch (Exception e) {
                // do nothing
            }
            try {
                if (stmt != null && !stmt.isClosed()) {
                    stmt.close();
                }
            } catch (Exception e) {
                // do nothing
            }
            closeConnection(conn);
        }
        if (cars.isEmpty()) {
            throw new DAOException("Ошибка при чтении");
        }
        return cars;
    }

    public Owner selectOneOwner(int ownerId) throws DAOException {
        Connection conn = establishConnection();
        Owner owner = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = conn.prepareStatement("SELECT OwnerId, Name, Lastname, Email, Phone, Birthday FROM Owner WHERE OwnerId=?",
                    Statement.RETURN_GENERATED_KEYS);
            stmt.setInt(1, ownerId);
            rs = stmt.executeQuery();

            if (rs.next()) {
                owner = new Owner(
                        ownerId,
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getDate(6)
                );
            }
        } catch (SQLException ex) {
            throw new DAOException("Произошла ошибка при выполнении " + ex.getMessage());
        } finally {
            try {
                if (rs != null && !rs.isClosed()) {
                    rs.close();
                }
            } catch (Exception e) {
                // do nothing
            }
            try {
                if (stmt != null && !stmt.isClosed()) {
                    stmt.close();
                }
            } catch (Exception e) {
                // do nothing
            }
            closeConnection(conn);
        }

        if (owner == null) {
            throw new DAOException("Ошибка при чтении");
        }
        return owner;
    }

    public List<Owner> selectAllOwners() throws DAOException {
        Connection conn = establishConnection();
        List<Owner> owners = new ArrayList<>();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = conn.prepareStatement("SELECT OwnerId, Name, Lastname, Email, Phone, Birthday FROM Owner order by LastName, Name",
                    Statement.RETURN_GENERATED_KEYS);
            rs = stmt.executeQuery();
            int counter = 0;
            while (rs.next()) {
                owners.add(new Owner(
                        rs.getInt("OwnerId"),
                        rs.getString("Name"),
                        rs.getString("Lastname"),
                        rs.getString("Email"),
                        rs.getString("Phone"),
                        rs.getDate("Birthday")
                ));
            }
        } catch (SQLException ex) {
            throw new DAOException("Произошла ошибка при выполнении " + ex.getMessage());
        } finally {
            try {
                if (rs != null && !rs.isClosed()) {
                    rs.close();
                }
            } catch (Exception e) {
                // do nothing
            }
            try {
                if (stmt != null && !stmt.isClosed()) {
                    stmt.close();
                }
            } catch (Exception e) {
                // do nothing
            }
            closeConnection(conn);
        }
        if (owners.isEmpty()) {
            throw new DAOException("Ошибка при чтении");
        }
        return owners;
    }
}
