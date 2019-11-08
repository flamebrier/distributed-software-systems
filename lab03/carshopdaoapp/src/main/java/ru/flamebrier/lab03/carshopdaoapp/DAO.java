package ru.flamebrier.lab03.carshopdaoapp;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.sql.Date;
import java.util.*;

public class DAO {
    private static final String PROPERTIES_FILENAME = "dbinfo.properties";
    private static final String DB_URL_PROPERTY = "db.url";
    private static final String DB_USERNAME_PROPERTY = "db.username";
    private static final String DB_PASSWORD_PROPERTY = "db.password";

    private Properties settings = new Properties();
    private Connection conn;

    /**
     * Загрузка параметров подключения к БД
     */
    private void loadProperties() {
        InputStream inputStream = null;
        try {
            inputStream = getClass().getClassLoader().getResourceAsStream(PROPERTIES_FILENAME);
            this.settings.load(inputStream);
        } catch (IOException ioe) {
            System.out.println("Произошла ошибка при загрузке параметров подключения к БД");
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException ioe) {
                // do nothing
            }
        }
    }

    /**
     * Установить подключение к базе данных
     */
    private void establishConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            System.out.println("Произошла ошибка при загрузке MySQL драйвера.");
            System.exit(1);
        }

        try {
            this.conn = DriverManager.getConnection(
                    settings.getProperty(DB_URL_PROPERTY),
                    settings.getProperty(DB_USERNAME_PROPERTY),
                    settings.getProperty(DB_PASSWORD_PROPERTY)
            );
        } catch (SQLException ex) {
            System.out.println("Произошла ошибка при установке соединения с БД: ");
            System.out.println(ex.getMessage());
            System.exit(1);
        }
    }

    /**
     * Закрытие соединения с БД
     */
    private void closeConnection() {
        try {
            if (this.conn != null) {
                this.conn.close();
            }
        } catch (Exception sqle) {
            System.out.println("Произошла ошибка при закрытии соединения:");
            System.out.println(sqle.getMessage());
        }
    }

    public DAO() {
        loadProperties();
        establishConnection();
    }

    public void closeDAO() {
        closeConnection();
    }

    public boolean insertCar(Car car) {
        PreparedStatement stmt = null;
        int rows = 0;

        try {
            stmt = conn.prepareStatement(
                    "INSERT INTO Car(Brand, Model, Color, Year, Mileage, Owner, Price)"
                            + "VALUES (?, ?, ?, ?, ?, ?, ?)");
            stmt.setString(1, car.getBrand());
            stmt.setString(2, car.getModel());
            stmt.setString(3, car.getColor());
            stmt.setDate(4, new java.sql.Date(car.getDateYear().getTime()));
            stmt.setInt(5, car.getMileage());
            stmt.setInt(6, car.getOwner());
            stmt.setBigDecimal(7, car.getPrice());

            rows = stmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Произошла ошибка при выполнении SQL запроса: ");
            System.out.println(ex.getMessage());
        } finally {
            try {
                if (stmt != null && !stmt.isClosed()) {
                    stmt.close();
                }
            } catch (Exception e) {
                // do nothing
            }
        }
        return rows > 0;
    }

    public boolean updateCar(Car car, int field) {
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
                    stmt.setDate(1, new java.sql.Date(car.getDateYear().getTime()));
                    break;
                case 5:
                    stmt = conn.prepareStatement(
                            "UPDATE Car SET Mileage=? WHERE CarId=?");
                    stmt.setInt(1, car.getMileage());
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
            System.out.println("Произошла ошибка при выполнении SQL запроса:");
            System.out.println(ex.getMessage());
        } finally {
            try {
                if (stmt != null && !stmt.isClosed()) {
                    stmt.close();
                }
            } catch (Exception e) {
                // do nothing
            }
        }
        return rows > 0;
    }

    public boolean deleteCar(int carId) {
        PreparedStatement stmt = null;
        int rows = 0;

        try {
            stmt = conn.prepareStatement(
                    "DELETE FROM Car WHERE CarId=?");
            stmt.setInt(1, carId);
            rows = stmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Произошла ошибка при выполнении SQL запроса:");
            System.out.println(ex.getMessage());
        } finally {
            try {
                if (stmt != null && !stmt.isClosed()) {
                    stmt.close();
                }
            } catch (Exception e) {
                // do nothing
            }
        }

        return rows > 0;
    }

    public int insertOwner(Owner owner) {
        PreparedStatement stmt = null;
        int rows = 0;
        int id = -1;

        try {
            stmt = conn.prepareStatement(
                    "INSERT INTO Owner(Name, Lastname, Birthday, Email, Phone)" +
                            " VALUES (?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, owner.getName());
            stmt.setString(2, owner.getLastName());
            stmt.setDate(3, new java.sql.Date(owner.getBirthday().getTime()));
            stmt.setString(4, owner.getEmail());
            stmt.setString(5, owner.getPhone());

            rows = stmt.executeUpdate();
            if (rows > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                rs.next();
                id = rs.getInt(1);
            }
        } catch (SQLException ex) {
            System.out.println("Произошла ошибка при выполнении SQL запроса: ");
            System.out.println(ex.getMessage());
        } finally {
            try {
                if (stmt != null && !stmt.isClosed()) {
                    stmt.close();
                }
            } catch (Exception e) {
                // do nothing
            }
        }
        return id;
    }

    public Car selectOneCar(int carId) {
        Car car = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = this.conn.prepareStatement("SELECT CarId, Brand, Model, Color, Year, Mileage, Owner, Price FROM Car WHERE CarId=" + carId,
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
        } catch (SQLException sqle) {
            System.out.println("Произошла ошибка при выполнении SQL запроса:");
            System.out.println(sqle.getMessage());
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
        }

        return car;
    }

    public Car[] selectAllCars() {
        Car[] cars = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = this.conn.prepareStatement("SELECT COUNT(*) FROM Car");
            rs = stmt.executeQuery();
            rs.next();
            int length = rs.getInt(1);

            cars = new Car[length];

            stmt = this.conn.prepareStatement("SELECT CarId, Brand, Model, Color, Year, Mileage, Owner, Price FROM Car ORDER BY Year, CarId",
                    Statement.RETURN_GENERATED_KEYS);
            rs = stmt.executeQuery();

            int counter = 0;
            while (rs.next()) {
                cars[counter++] = new Car(
                        rs.getInt("CarId"),
                        rs.getString("Brand"),
                        rs.getString("Model"),
                        rs.getString("Color"),
                        rs.getDate("Year"),
                        rs.getInt("Mileage"),
                        rs.getInt("Owner"),
                        rs.getBigDecimal("Price")
                );
            }
        } catch (SQLException sqle) {
            System.out.println("Произошла ошибка при выполнении SQL запроса:");
            System.out.println(sqle.getMessage());
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
        }

        return cars;
    }

    public Owner selectOneOwner(int ownerId) {
        Owner owner = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = this.conn.prepareStatement("SELECT OwnerId, Name, Lastname, Email, Phone, Birthday FROM Owner WHERE OwnerId=?",
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
        } catch (SQLException sqle) {
            System.out.println("Произошла ошибка при выполнении SQL запроса:");
            System.out.println(sqle.getMessage());
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
        }

        return owner;
    }

    public Owner[] selectAllOwners() {
        Owner[] owners = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = this.conn.prepareStatement("SELECT COUNT(*) FROM Owner");
            rs = stmt.executeQuery();
            rs.next();
            int length = rs.getInt(1);

            stmt = this.conn.prepareStatement("SELECT OwnerId, Name, Lastname, Email, Phone, Birthday FROM Owner order by LastName, Name",
                    Statement.RETURN_GENERATED_KEYS);
            rs = stmt.executeQuery();

            owners = new Owner[length];
            int counter = 0;
            while (rs.next()) {
                owners[counter++] = new Owner(
                        rs.getInt("OwnerId"),
                        rs.getString("Name"),
                        rs.getString("Lastname"),
                        rs.getString("Email"),
                        rs.getString("Phone"),
                        rs.getDate("Birthday")
                );
            }
        } catch (SQLException sqle) {
            System.out.println("Произошла ошибка при выполнении SQL запроса:");
            System.out.println(sqle.getMessage());
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
        }

        return owners;
    }
}
