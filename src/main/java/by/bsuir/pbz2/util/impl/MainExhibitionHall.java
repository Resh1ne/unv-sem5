package by.bsuir.pbz2.util.impl;

import by.bsuir.pbz2.data.ExhibitionHallDao;
import by.bsuir.pbz2.data.OwnerDao;
import by.bsuir.pbz2.data.connection.DataSource;
import by.bsuir.pbz2.data.connection.impl.DataSourceImpl;
import by.bsuir.pbz2.data.entity.ExhibitionHall;
import by.bsuir.pbz2.data.impl.ExhibitionHallDaoImpl;
import by.bsuir.pbz2.data.impl.OwnerDaoImpl;
import by.bsuir.pbz2.util.PropertiesManager;

import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainExhibitionHall {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Pattern pattern = Pattern.compile("\\d+");
        ExhibitionHallDao exhibitionHallDao = getExhibitionHallDao();

        while (true) {
            printMenu();

            String userInput = scanner.nextLine();
            Matcher matcher = pattern.matcher(userInput);
            String command = "";
            long id = 0;
            if (matcher.find()) {
                command = matcher.group();
                id = Long.parseLong(command);
                command = matcher.replaceAll("");
            }

            if (!usingMenu(userInput, id, command, scanner, exhibitionHallDao)) {
                return;
            }
        }
    }

    private static ExhibitionHallDao getExhibitionHallDao() {
        PropertiesManager propertiesManager = new PropertiesManagerImpl("src\\main\\resources\\app.properties");
        String profile = propertiesManager.getKey("my.app.profile");
        String url = propertiesManager.getKey("my.app.db." + profile + ".url");
        String user = propertiesManager.getKey("my.app.db." + profile + ".user");
        String password = propertiesManager.getKey("my.app.db." + profile + ".password");
        String driver = propertiesManager.getKey("my.app.db." + profile + ".driver");
        DataSource dataSource = new DataSourceImpl(password, user, url, driver);
        return new ExhibitionHallDaoImpl(dataSource);
    }

    private static void printMenu() {
        String commandAll = "\u001B[35m" + "/all" + "\u001B[0m\n";
        String commandGet = "\u001B[35m" + "/get{id}" + "\u001B[0m\n";
        String commandDelete = "\u001B[35m" + "/delete{id}" + "\u001B[0m\n";
        String commandExit = "\u001B[35m" + "/exit" + "\u001B[0m\n";
        String commandCreate = "\u001B[35m" + "/create" + "\u001B[0m\n";
        String commandUpdate = "\u001B[35m" + "/update{id}" + "\u001B[0m\n";
        System.out.print("-------Menu-------\n" +
                "~To view all exhibition hall, enter: " + commandAll +
                "~To update the exhibition hall, enter: " + commandUpdate +
                "~To display detailed information about the exhibition hall, enter: " + commandGet +
                "~To delete exhibition hall, enter: " + commandDelete +
                "~To create exhibition hall, enter: " + commandCreate +
                "~To exit, enter: " + commandExit);
    }

    private static boolean usingMenu(String userInput, Long id, String command, Scanner scanner, ExhibitionHallDao exhibitionHallDao) {
        if (id > 0 && "/update{}".equals(command)) {
            ExhibitionHall exhibitionHall = updateExhibitionHall(scanner, exhibitionHallDao, id);
            System.out.println(exhibitionHallDao.update(exhibitionHall).toString());
        } else if (userInput.equals("/all")) {
            List<ExhibitionHall> exhibitionHalls = exhibitionHallDao.findAll();
            for (ExhibitionHall exhibitionHall : exhibitionHalls) {
                System.out.println(exhibitionHall.toString());
            }
        } else if (userInput.equals("/exit")) {
            return false;
        } else if (id > 0 && "/get{}".equals(command)) {
            ExhibitionHall exhibitionHall = exhibitionHallDao.findById(id);
            System.out.println(exhibitionHall);
        } else if (id > 0 && "/delete{}".equals(command)) {
            boolean deleted = exhibitionHallDao.delete(id);
            System.out.println(deleted);
        } else if (userInput.equals("/create")) {
            ExhibitionHall exhibitionHall = createExhibitionHallWithoutID(scanner);
            ExhibitionHall createdExhibitionHall = exhibitionHallDao.create(exhibitionHall);
            System.out.println(createdExhibitionHall.toString());
        } else {
            System.out.println("Incorrect command!");
        }
        return true;
    }

    private static ExhibitionHall updateExhibitionHall(Scanner scanner, ExhibitionHallDao exhibitionHallDao, long id) {
        while (true) {
            if (exhibitionHallDao.findById(id) == null) {
                System.out.println("There is no exhibition hall with this id! Enter it again!");
                id = scanner.nextLong();
                //Here, the scanner is called to clear the scanner's clipboard of \n that was left after the call scanner.NextLong()
                scanner.nextLine();
                continue;
            }
            break;
        }
        ExhibitionHall exhibitionHall = createExhibitionHallWithoutID(scanner);
        exhibitionHall.setId(id);
        return exhibitionHall;
    }

    private static ExhibitionHall createExhibitionHallWithoutID(Scanner scanner) {
        ExhibitionHall exhibitionHall = new ExhibitionHall();
        setName(scanner, exhibitionHall);
        setArea(scanner, exhibitionHall);
        setAddress(scanner, exhibitionHall);
        setPhone(scanner, exhibitionHall);
        setOwner(scanner, exhibitionHall);
        return exhibitionHall;
    }

    private static void setPhone(Scanner scanner, ExhibitionHall exhibitionHall) {
        System.out.print("Enter phone of the exhibition hall: ");
        exhibitionHall.setPhone(scanner.nextLine());
    }

    private static void setName(Scanner scanner, ExhibitionHall exhibitionHall) {
        System.out.print("Enter name of the exhibitionHall: ");
        exhibitionHall.setName(scanner.nextLine());
    }

    private static void setOwner(Scanner scanner, ExhibitionHall exhibitionHall) {
        OwnerDao ownerDao = getOwnerDao();
        System.out.print("Enter the Owner id of the exhibitionHall(Enter Owner id): ");
        exhibitionHall.setOwnerId(ownerDao.findById(scanner.nextLong()));
        scanner.nextLine();
    }

    private static void setArea(Scanner scanner, ExhibitionHall exhibitionHall) {
        System.out.print("Enter exhibitionHall area: ");
        exhibitionHall.setArea(scanner.nextBigDecimal());
    }

    private static void setAddress(Scanner scanner, ExhibitionHall exhibitionHall) {
        System.out.print("Enter the address of exhibition hall: ");
        exhibitionHall.setAddress(scanner.nextLine());
        scanner.nextLine();
    }

    private static OwnerDao getOwnerDao() {
        PropertiesManager propertiesManager = new PropertiesManagerImpl("src\\main\\resources\\app.properties");
        String profile = propertiesManager.getKey("my.app.profile");
        String url = propertiesManager.getKey("my.app.db." + profile + ".url");
        String user = propertiesManager.getKey("my.app.db." + profile + ".user");
        String password = propertiesManager.getKey("my.app.db." + profile + ".password");
        String driver = propertiesManager.getKey("my.app.db." + profile + ".driver");
        DataSource dataSource = new DataSourceImpl(password, user, url, driver);
        return new OwnerDaoImpl(dataSource);
    }
}
