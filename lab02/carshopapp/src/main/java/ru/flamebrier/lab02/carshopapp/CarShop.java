package ru.flamebrier.lab02.carshopapp;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Scanner;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Brier
 */
public class CarShop {
    private static final String PROPERTIES_FILENAME = "dbinfo.properties";
    private static final String DB_URL_PROPERTY = "db.url";
    private static final String DB_USERNAME_PROPERTY = "db.username";
    private static final String DB_PASSWORD_PROPERTY = "db.password";
    
    private Properties settings = new Properties();
    private Scanner scanner = new Scanner(System.in);
    private Connection conn;
    
    public static void main(String args[]) {
        CarShop carShop = new CarShop();
        carShop.run();
        
    }
    
    private void run() {
        loadProperties();
        establishConnection();
        showMainMenu();
        closeConnection();
        this.scanner.close();
    }
    
    private void showMainMenu() {
        clearConsole();
        
        System.out.println("Основное меню:");
        System.out.println("\tПросмотр");
        System.out.println("\t\t1 - Просмотреть список владельцев автомобилей");
        System.out.println("\t\t2 - Просмотреть список автомобилей");
        System.out.println("\tВыполнение действий");
        System.out.println("\t\t3 - Добавить владельца");
        System.out.println("\t\t\t4 - Добавить автомобиль");
        System.out.println("\t\t\t5 - Изменить автомобиль");
        System.out.println("\t\t\t6 - Удалить автомобиль");
        System.out.println("\t0 - Закончить работу (выход)");
        
        while (true) {
            System.out.print("Выберите один из вариантов [0-6]: ");
            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    showOwners();
                    break;
                case "2":
                    showCars();
                    break;
                case "3":
                    addOwner();
                    break;
                case "4":
                    addCar();
                    break;
                case "5":
                    updateCar();
                    break;
                case "6":
                    deleteCar();
                    break;
                case "0":
                    System.exit(0);
                    break;
            }
        }
    }
    
    /**
     * Добавить автомобиль
     */
    private void addCar() {
        clearConsole();
        
        PreparedStatement stmt = null;
        Date carYear = null;
        int tmp;
        Calendar calendar;
        boolean isYear = false;
        
        System.out.println("Введите данные об автомобиле");
        System.out.print("\tМарка: ");
        String carBrand = scanner.nextLine();
        System.out.print("\tМодель: ");
        String carModel = scanner.nextLine();
        System.out.print("\tЦвет: ");
        String carColor = scanner.nextLine();
        
        do {
            try {
                System.out.print("\tГод выпуска [ГГГГ]: ");
                tmp = scanner.nextInt();
                calendar = new GregorianCalendar(tmp, 0, 0);
                carYear = calendar.getTime();
                isYear = true;
            } catch (Exception ex) {
                System.out.print("\tНекорректный год, попробуйте снова");
            }
        } while(!isYear);
        System.out.print("\tПробег: ");
        int carMile = scanner.nextInt();
        System.out.print("\tID владельца: ");
        int carOwner = scanner.nextInt();
        System.out.print("\tЦена: ");
        BigDecimal carPrice = scanner.nextBigDecimal();
        try {
            stmt = conn.prepareStatement(
                    "INSERT INTO Car(Brand, Model, Color, Year, Mileage, Owner, Price)"
                            + "VALUES (?, ?, ?, ?, ?, ?, ?)");
            stmt.setString(1, carBrand);
            stmt.setString(2, carModel);
            stmt.setString(3, carColor);
            stmt.setDate(4, new java.sql.Date(carYear.getTime()));
            stmt.setInt(5, carMile);
            stmt.setInt(6, carOwner);
            stmt.setBigDecimal(7, carPrice);
            
            int rows = stmt.executeUpdate();
            
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
            System.out.print("\nНажмите Enter...");
            scanner.nextLine();
            showMainMenu();
        }
        
        
    }
    
    /**
     * Редактировать автомобиль
     */
    private void updateCar() {
        clearConsole();
        
        PreparedStatement stmt = null;
        
        System.out.print("Введите id редактируемого автомобиля: ");
        int carId = scanner.nextInt();
        
        showOneCar(carId);
        
        System.out.println("\t1 - Марка\n\t2 - Модель\n\t3 - Цвет\n\t4 - Год\n\t5 - Пробег\n\t6 - ID-владельца\n\t7 - Цена");
        System.out.print("Введите номер изменяемого поля: ");
        int field = scanner.nextInt();
        
        System.out.println("Введите новое значение поля");
        try {
            stmt = conn.prepareStatement(
                    "UPDATE Car SET Brand=? WHERE CarId=?");
            switch (field) {
                case 1:
                    System.out.print("\tМарка: ");
                    String carBrand = scanner.nextLine();
                    stmt.setString(1, carBrand);
                   stmt.setInt(2, carId);
                    break;
                case 2:
                    System.out.print("\tМодель: ");
                    String carModel = scanner.nextLine();
                    stmt.setString(1, carModel);
                    stmt.setInt(2, carId);
                    break;
                case 3:
                    System.out.print("\tЦвет: ");
                    String carColor = scanner.nextLine();
                    stmt.setString(1, carColor);
                    stmt.setInt(2, carId);
                    break;
                case 4:
                    Date carYear = null;
                    int tmp;
                    Calendar calendar;
                    boolean isYear = false;
                    do {
                        try {
                            System.out.print("\tГод выпуска [ГГГГ]: ");
                            tmp = scanner.nextInt();
                            calendar = new GregorianCalendar(tmp, 0, 0);
                            carYear = calendar.getTime();
                            isYear = true;
                        } catch (Exception ex) {
                            System.out.print("\tНекорректный год, попробуйте снова");
                        }
                    } while(!isYear);
                    stmt.setDate(1, new java.sql.Date(carYear.getTime()));
                    stmt.setInt(2, carId);
                    break;
                case 5:
                    System.out.print("\tПробег: ");
                    int carMile = scanner.nextInt();
                    stmt.setInt(1, carMile);
                    stmt.setInt(2, carId);
                    break;
                case 6:
                    System.out.print("\tID владельца: ");
                    int carOwner = scanner.nextInt();
                    stmt.setInt(1, carOwner);
                    stmt.setInt(2, carId);
                    break;
                case 7:
                    System.out.print("\tЦена: ");
                    BigDecimal carPrice = scanner.nextBigDecimal();
                    stmt.setBigDecimal(1, carPrice);
                    stmt.setInt(2, carId);
                    break;
                default:
                    break;
            }
            stmt.executeUpdate();
            
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
            
        
        System.out.print("\nНажмите Enter...");
        scanner.nextLine();
        showMainMenu();
        }
    }
    
    /**
     * Удалить автомобиль
     */
    private void deleteCar() {
        clearConsole();
        
        PreparedStatement stmt = null;
        
        System.out.print("Введите id удаляемого автомобиля: ");
        int carId = scanner.nextInt();
        
        showOneCar(carId);
        
        System.out.print("Удалить этот автомобиль? [Y/n] ");
        String field = scanner.next("[Yn]");
        
        if (field.equals("Y")) {
            try {
                stmt = conn.prepareStatement(
                        "DELETE FROM Car WHERE CarId=?");
                stmt.setInt(1, carId);
                stmt.executeUpdate();
                System.out.println("Удалено");
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
        } else {
            System.out.println("Отмена");
        }
        
        System.out.println("\nНажмите Enter...");
        scanner.nextLine();
        showMainMenu();
    }
    
    /**
     * Добавить владельца
     */
    private void addOwner() {
        clearConsole();
        
        String query =
                "insert into Owner(Name, Lastname, Birthday, Email, Phone)" +
                " values (?, ?, ?, ?, ?)";
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        System.out.println("Введите сведения о владельце");
        System.out.print("\tИмя: ");
        String name = scanner.nextLine();
        System.out.print("\tФамилия: ");
        String lastName = scanner.nextLine();
        boolean isDate = false;
        Date birthDate = null;
        do {
            System.out.print("\tДата рождения (ДД.ММ.ГГГГ): ");
            try {
                birthDate = sdf.parse(scanner.nextLine());
                isDate = true;
            } catch (ParseException pe) {
                System.out.println("\tНекорректная дата, попробуйте снова.");
            }
        } while(!isDate);
        
        Matcher matcher = null;
        String email;
        boolean isValid = false;
        Pattern regex;
        regex = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
        do {
            System.out.print("\tEmail: ");
            email = scanner.nextLine();
            matcher = regex.matcher(email);
            if (matcher.find()) {
                isValid = true;
            } else {
                System.out.println("\tНекорректный e-mail, попробуйте снова.");
            }
        } while(!isValid);
        
        String phone;
        regex = Pattern.compile("^[+]7[0-9]{10}", Pattern.CASE_INSENSITIVE);
        isValid = false;
        do {
            System.out.print("\tМобильный телефон [+70000000000]: ");
            phone = scanner.nextLine();
            matcher = regex.matcher(phone);
            if (matcher.find()) {
                isValid = true;
            } else {
                System.out.println("\tНекорректный номер, попробуйте снова.");
            }
        } while(!isValid);
        
        try {
            stmt = this.conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, name);
            stmt.setString(2, lastName);
            stmt.setDate(3, new java.sql.Date(birthDate.getTime()));
            stmt.setString(4, email);
            stmt.setString(5, phone);
            int rows = stmt.executeUpdate();
            
            if (rows > 0) {
                rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    System.out.println("Пользователь успешно добавлен с ID = " + rs.getInt(1));
                }
            } else {
                System.out.println("Не удалось добавить пользователя.");
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
            System.out.print("\nНажмите Enter...");
            scanner.nextLine();
            showMainMenu();
        }
    }
    
    /**
     * Вывести всех владельцев автомобилей
     */
    private void showOwners() {
        clearConsole();
        
        String lineFormat = "%s(%s).\t%s\t%s\t%s\t%s\t%s";
        String query = "select OwnerId, Name, Lastname, Birthday, Email, Phone from Owner order by LastName, Name";
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = this.conn.prepareStatement(query);
            rs = stmt.executeQuery();
            
            int rowNum = 0;
            while(rs.next()) {
                System.out.println(String.format(lineFormat,
                        ++rowNum,
                        rs.getString("OwnerId"),
                        rs.getString("Lastname"),
                        rs.getString("Name"),
                        rs.getString("Birthday"),
                        rs.getString("Email"),
                        rs.getString("Phone")
                ));
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
        System.out.print("\nНажмите Enter...");
        scanner.nextLine();
        showMainMenu();
    }
    
    /**
     * Вывести все автомобили
     */
    private void showCars() {
        clearConsole();
        
        String lineFormat = "%s(%s).\t%s\t%s\t%s\t%s\t%s\t%s\t%s";
        String query = "select CarId, Brand, Model, Color, Year(Year), Mileage, Price, Owner from Car order by Brand, Model, Year";
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = this.conn.prepareStatement(query);
            rs = stmt.executeQuery();
            
            System.out.println("№(id).\tМарка\tМодель\tЦвет\tГод\tПробег\tЦена\tВладелец");
            
            int rowNum = 0;
            while(rs.next()) {
                System.out.println(String.format(lineFormat,
                        ++rowNum,
                        rs.getString("CarId"),
                        rs.getString("Brand"),
                        rs.getString("Model"),
                        rs.getString("Color"),
                        rs.getString("Year(Year)"),
                        rs.getString("Mileage"),
                        rs.getString("Price"),
                        rs.getString("Owner")
                ));
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
        System.out.print("\nНажмите Enter...");
        scanner.nextLine();
        showMainMenu();
    }
    
    /**
     * Вывести один автомобиль
     */
    private void showOneCar(int id) {
        String lineFormat = "%s(%s).\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s (%s)";
        PreparedStatement stmt = null;
        
        ResultSet rs = null;
        try {
            String query = "select CarId, Brand, Model, Color, Year(Year), Mileage, Price, Owner.LastName, Owner.Name, OwnerId from Car JOIN Owner On OwnerId=Owner WHERE CarId=? order by Brand, Model, Year";
            stmt = this.conn.prepareStatement(query);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
            
            System.out.println("№(id).\tМарка\tМодель\tЦвет\tГод\tПробег\tЦена\tВладелец(id)");
            
            int rowNum = 0;
            while(rs.next()) {
                System.out.println(String.format(lineFormat,
                        ++rowNum,
                        rs.getString("CarId"),
                        rs.getString("Brand"),
                        rs.getString("Model"),
                        rs.getString("Color"),
                        rs.getString("Year(Year)"),
                        rs.getString("Mileage"),
                        rs.getString("Price"),
                        rs.getString("LastName"),
                        rs.getString("Name"),
                        rs.getString("OwnerId")
                ));
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
    }
    
    /**
     * Очистка консоли
     */
    private static void clearConsole() {
        try {
            final String os = System.getProperty("os.name");
            
            if (os.contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                new ProcessBuilder("clear").inheritIO().start().waitFor();
            }
        } catch (IOException | InterruptedException e) {
            System.out.println(e);
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
}
