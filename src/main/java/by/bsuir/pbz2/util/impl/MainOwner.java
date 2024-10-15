package by.bsuir.pbz2.util.impl;

import by.bsuir.pbz2.data.OwnerDao;
import by.bsuir.pbz2.data.connection.DataSource;
import by.bsuir.pbz2.data.connection.impl.DataSourceImpl;
import by.bsuir.pbz2.data.entity.Owner;
import by.bsuir.pbz2.data.entity.enums.OwnerType;
import by.bsuir.pbz2.data.impl.OwnerDaoImpl;
import by.bsuir.pbz2.util.PropertiesManager;

import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainOwner {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Pattern pattern = Pattern.compile("\\d+");
        OwnerDao ownerDao = getOwnerDao();

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

            if (!usingMenu(userInput, id, command, scanner, ownerDao)) {
                return;
            }
        }
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

    private static void printMenu() {
        String commandAll = "\u001B[35m" + "/all" + "\u001B[0m\n";
        String commandGet = "\u001B[35m" + "/get{id}" + "\u001B[0m\n";
        String commandDelete = "\u001B[35m" + "/delete{id}" + "\u001B[0m\n";
        String commandExit = "\u001B[35m" + "/exit" + "\u001B[0m\n";
        String commandCreate = "\u001B[35m" + "/create" + "\u001B[0m\n";
        String commandUpdate = "\u001B[35m" + "/update{id}" + "\u001B[0m\n";
        System.out.print("-------Menu-------\n" +
                "~To view all owners, enter: " + commandAll +
                "~To update the owner, enter: " + commandUpdate +
                "~To display detailed information about the owners, enter: " + commandGet +
                "~To delete owner, enter: " + commandDelete +
                "~To create owner, enter: " + commandCreate +
                "~To exit, enter: " + commandExit);
    }

    private static boolean usingMenu(String userInput, Long id, String command, Scanner scanner, OwnerDao ownerDao) {
        if (id > 0 && "/update{}".equals(command)) {
            Owner owner = updateOwner(scanner, ownerDao, id);
            System.out.println(ownerDao.update(owner).toString());
        } else if (userInput.equals("/all")) {
            List<Owner> owners = ownerDao.findAll();
            for (Owner owner : owners) {
                System.out.println(owner.toString());
            }
        } else if (userInput.equals("/exit")) {
            return false;
        } else if (id > 0 && "/get{}".equals(command)) {
            Owner owner = ownerDao.findById(id);
            System.out.println(owner);
        } else if (id > 0 && "/delete{}".equals(command)) {
            boolean deleted = ownerDao.delete(id);
            System.out.println(deleted);
        } else if (userInput.equals("/create")) {
            Owner owner = createOwnerWithoutID(scanner);
            Owner createdOwner = ownerDao.create(owner);
            System.out.println(createdOwner.toString());
        } else {
            System.out.println("Incorrect command!");
        }
        return true;
    }

    private static Owner updateOwner(Scanner scanner, OwnerDao ownerDao, long id) {
        while (true) {
            if (ownerDao.findById(id) == null) {
                System.out.println("There is no owner with this id! Enter it again!");
                id = scanner.nextLong();
                //Here, the scanner is called to clear the scanner's clipboard of \n that was left after the call scanner.NextLong()
                scanner.nextLine();
                continue;
            }
            break;
        }
        Owner owner = createOwnerWithoutID(scanner);
        owner.setId(id);
        return owner;
    }

    private static Owner createOwnerWithoutID(Scanner scanner) {
        Owner owner = new Owner();
        setName(scanner, owner);
        setAddress(scanner, owner);
        setPhone(scanner, owner);
        setOwnerType(scanner, owner);
        return owner;
    }

    private static void setName(Scanner scanner, Owner owner) {
        System.out.print("Enter name of the owner: ");
        owner.setName(scanner.nextLine());
    }

    private static void setAddress(Scanner scanner, Owner owner) {
        System.out.print("Enter address of the owner: ");
        owner.setAddress(scanner.nextLine());
    }

    private static void setPhone(Scanner scanner, Owner owner) {
        System.out.print("Enter the phone of the owner: ");
        owner.setPhone(scanner.nextLine());
    }

    private static void setOwnerType(Scanner scanner, Owner owner) {
        System.out.print("Enter the type of the owner(enum name): ");
        owner.setOwnerType(OwnerType.valueOf(scanner.nextLine()));
    }
}
