package ru.flamebrier.lab03.carshopdaoapp;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CarShop {
    private Scanner scanner = new Scanner(System.in);
    private static DAO dao = new DAO();

    public static void main(String[] args) {
        CarShop carShop = new CarShop();
        carShop.showMainMenu();

        dao.closeDAO();
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
                    editCar();
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

    private void showOwners() {
        clearConsole();

        Owner[] owners = dao.selectAllOwners();

        String lineFormat = "%s(%s).\t%s\t%s\t%s\t%s\t%s";
        System.out.println(String.format(lineFormat,
                "№",
                "ID",
                "Имя",
                "Фамилия",
                "Дата рождения",
                "E-mail",
                "Телефон"
        ));

        int rowNum = 0;
        for (Owner owner : owners) {
            System.out.println(String.format(lineFormat,
                    ++rowNum,
                    owner.getId(),
                    owner.getName(),
                    owner.getLastName(),
                    owner.getBirthday(),
                    owner.getEmail(),
                    owner.getPhone()
            ));
        }

        System.out.println("\nНажмите Enter...");
        scanner.nextLine();
        showMainMenu();
    }

    /**
     * Вывести все автомобили
     */
    private void showCars() {
        clearConsole();

        Car[] cars = dao.selectAllCars();

        String lineFormat = "%s(%s).\t%s\t%s\t%s\t%s\t%s\t%s\t%s";
        System.out.println(String.format(lineFormat,
                "№",
                "ID",
                "Марка",
                "Модель",
                "Цвет",
                "Год",
                "Пробег",
                "ID владельца",
                "Цена"
        ));

        int rowNum = 0;
        for (Car car : cars) {
            System.out.println(String.format(lineFormat,
                    ++rowNum,
                    car.getId(),
                    car.getBrand(),
                    car.getModel(),
                    car.getColor(),
                    car.getYear(),
                    car.getMileage(),
                    car.getOwner(),
                    car.getPrice()
            ));
        }

        System.out.println("\nНажмите Enter...");
        scanner.nextLine();
        showMainMenu();
    }

    /**
     * Показать один автомобиль и владельца
     */
    private boolean showOneCarWithOwner(int carId) {
        Car car = dao.selectOneCar(carId);
        if (car == null) {
            System.out.println("Такой машины не существует");
            return false;
        }

        String lineFormat = "%s.\t%s\t%s\t%s\t%s\t%s\t%s";
        System.out.println(String.format(lineFormat,
                "ID",
                "Марка",
                "Модель",
                "Цвет",
                "Год",
                "Пробег",
                "Цена"
        ));

        System.out.println(
                String.format(lineFormat,
                        car.getId(),
                        car.getBrand(),
                        car.getModel(),
                        car.getColor(),
                        car.getYear(),
                        car.getMileage(),
                        car.getPrice()
                ));

        try {
            Owner owner = dao.selectOneOwner(car.getOwner());

            lineFormat = "\tВладелец\n" +
                    "\tID: %s\n" +
                    "\tИмя: %s\n" +
                    "\tФамилия: %s\n" +
                    "\tДата рождения: %s\n" +
                    "\tE-mail: %s\n" +
                    "\tТелефон%s\n";
            System.out.println(String.format(lineFormat,
                    owner.getId(),
                    owner.getName(),
                    owner.getLastName(),
                    owner.getBirthday(),
                    owner.getEmail(),
                    owner.getPhone()
            ));
        } catch (Exception ex) {
            //Ничего
        }
        return true;
    }

    /**
     * Добавить владельца
     */
    private void addOwner() {
        clearConsole();

        Owner owner = new Owner();

        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        Matcher matcher;
        boolean isValid;
        Pattern regex;

        System.out.println("Введите сведения о владельце");

        System.out.print("\tИмя: ");
        owner.setName(scanner.nextLine());

        System.out.print("\tФамилия: ");
        owner.setLastName(scanner.nextLine());

        isValid = false;
        do {
            System.out.print("\tДата рождения (ДД.ММ.ГГГГ): ");
            try {
                owner.setBirthday(sdf.parse(scanner.nextLine()));
                isValid = true;
            } catch (ParseException pe) {
                System.out.println("\tНекорректная дата, попробуйте снова.");
            }
        } while(!isValid);

        String email;
        isValid = false;
        regex = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
        do {
            System.out.print("\tEmail: ");
            email = scanner.nextLine();
            matcher = regex.matcher(email);
            if (matcher.find()) {
                isValid = true;
                owner.setEmail(email);
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
                owner.setPhone(phone);
            } else {
                System.out.println("\tНекорректный номер, попробуйте снова.");
            }
        } while(!isValid);

        int id = dao.insertOwner(owner);
        if (id > 0) {
            System.out.println("Владелец успешно добавлен с id " + id);
        } else {
            System.out.println("Ошибка при добавлении");
        }

        System.out.println("\nНажмите Enter...");
        scanner.next();
        showMainMenu();
    }

    /**
     * Добавить автомобиль
     */
    private void addCar() {
        clearConsole();

        Car car = new Car();

        boolean isYear = false;
        int tmp;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());

        System.out.println("Введите данные об автомобиле");
        System.out.print("\tМарка: ");
        car.setBrand(scanner.nextLine());
        System.out.print("\tМодель: ");
        car.setModel(scanner.nextLine());
        System.out.print("\tЦвет: ");
        car.setColor(scanner.nextLine());

        do {
            System.out.print("\tГод выпуска [ГГГГ]: ");
            tmp = scanner.nextInt();
            if (tmp > 1950 && tmp <= calendar.get(Calendar.YEAR)) {
                car.setIntYear(tmp);
                isYear = true;
            } else {
                System.out.print("\tНекорректный год, попробуйте снова");
            }
        } while(!isYear);

        System.out.print("\tПробег: ");
        car.setMileage(scanner.nextInt());
        System.out.print("\tID владельца: ");
        car.setOwner(scanner.nextInt());
        System.out.print("\tЦена: ");
        car.setPrice(scanner.nextBigDecimal());

        if (dao.insertCar(car)) {
            System.out.println("Успешно добавлено");
        } else {
            System.out.println("Ошибка, не добавлено");
        }

        System.out.println("\nНажмите Enter...");
        scanner.nextLine();
        showMainMenu();
    }

    /**
     * Редактировать автомобиль
     */
    private void editCar() {
        clearConsole();

        System.out.print("Введите id редактируемого автомобиля: ");
        int carId = scanner.nextInt();

        if (!showOneCarWithOwner(carId)) {
            return;
        }

        Car car = dao.selectOneCar(carId);

        System.out.println("\t1 - Марка\n\t2 - Модель\n\t3 - Цвет\n\t4 - Год\n\t5 - Пробег\n\t6 - ID-владельца\n\t7 - Цена");
        System.out.print("Введите номер изменяемого поля: ");
        int field = scanner.nextInt();

        System.out.println("Введите новое значение поля");
        String value;
        switch (field) {
            case 1:
                System.out.println("\tМарка: ");
                value = scanner.nextLine();
                car.setBrand(value);
                break;
            case 2:
                System.out.println("\tМодель: ");
                value = scanner.nextLine();
                car.setModel(value);
                break;
            case 3:
                System.out.println("\tЦвет: ");
                value = scanner.nextLine();
                car.setColor(value);
                break;
            case 4:
                int tmp;
                boolean isYear = false;
                Calendar calendar = GregorianCalendar.getInstance();
                do {
                    System.out.println("\tГод выпуска [ГГГГ]: ");
                    tmp = scanner.nextInt();
                    if (tmp > 1950 && tmp <= calendar.get(Calendar.YEAR)) {
                        car.setIntYear(tmp);
                        isYear = true;
                    } else {
                        System.out.print("\tНекорректный год, попробуйте снова");
                    }
                } while(!isYear);
                break;
            case 5:
                System.out.println("\tПробег: ");
                car.setMileage(scanner.nextInt());
                break;
            case 6:
                System.out.println("\tID владельца: ");
                car.setOwner(scanner.nextInt());
                break;
            case 7:
                System.out.println("\tЦена: ");
                car.setPrice(scanner.nextBigDecimal());
                break;
            default:
                break;
        }

        if (dao.updateCar(car, carId)) {
            System.out.println("Успешно изменено");
        } else {
            System.out.println("Ошибка, не изменено");
        }

        System.out.println("\nНажмите Enter...");
        scanner.nextLine();
        showMainMenu();
    }

    /**
     * Удалить автомобиль
     */
    private void deleteCar() {
        clearConsole();

        System.out.print("Введите id удаляемого автомобиля: ");
        int carId = scanner.nextInt();

        if (!showOneCarWithOwner(carId)) {
            return;
        }

        System.out.print("Удалить этот автомобиль? [Y/n] ");
        String field = scanner.next("[Yn]");

        if (field.equals("Y")) {
            if (dao.deleteCar(carId)) {
                System.out.println("Успешно удалено");
            } else {
                System.out.println("Ошибка, не удалено");
            }
        } else {
            System.out.println("Отмена");
        }

        System.out.println("\nНажмите Enter...");
        scanner.nextLine();
        showMainMenu();
    }

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
}
