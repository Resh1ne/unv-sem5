package by.bsuir.pbz2.util.impl;

import by.bsuir.pbz2.data.dao.ExhibitionDao;
import by.bsuir.pbz2.data.dao.ExhibitionHallDao;
import by.bsuir.pbz2.data.connection.DataSource;
import by.bsuir.pbz2.data.connection.impl.DataSourceImpl;
import by.bsuir.pbz2.data.entity.CurrentExhibition;
import by.bsuir.pbz2.data.entity.Exhibition;
import by.bsuir.pbz2.data.entity.ExhibitionParticipantsAndArtworks;
import by.bsuir.pbz2.data.entity.enums.ExhibitionType;
import by.bsuir.pbz2.data.dao.impl.ExhibitionDaoImpl;
import by.bsuir.pbz2.data.dao.impl.ExhibitionHallDaoImpl;
import by.bsuir.pbz2.util.PropertiesManager;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainExhibition {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Pattern pattern = Pattern.compile("\\d+");
        DataSource dataSource = createDataSource();
        ExhibitionDao exhibitionDao = new ExhibitionDaoImpl(dataSource);

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

            if (!usingMenu(userInput, id, command, scanner, exhibitionDao, dataSource)) {
                return;
            }
        }
    }
    private static DataSource createDataSource() {
        PropertiesManager propertiesManager = new PropertiesManagerImpl("src\\main\\resources\\app.properties");
        String profile = propertiesManager.getKey("my.app.profile");
        String url = propertiesManager.getKey("my.app.db." + profile + ".url");
        String user = propertiesManager.getKey("my.app.db." + profile + ".user");
        String password = propertiesManager.getKey("my.app.db." + profile + ".password");
        String driver = propertiesManager.getKey("my.app.db." + profile + ".driver");
        return new DataSourceImpl(password, user, url, driver);
    }

    private static void printMenu() {
        String commandAll = "\u001B[35m" + "/all" + "\u001B[0m\n";
        String commandGet = "\u001B[35m" + "/get{id}" + "\u001B[0m\n";
        String commandFindParAndArt = "\u001B[35m" + "/getParAndArt{id}" + "\u001B[0m\n";
        String commandGetCurExhibition = "\u001B[35m" + "/getCurExh" + "\u001B[0m\n";
        String commandDelete = "\u001B[35m" + "/delete{id}" + "\u001B[0m\n";
        String commandExit = "\u001B[35m" + "/exit" + "\u001B[0m\n";
        String commandCreate = "\u001B[35m" + "/create" + "\u001B[0m\n";
        String commandUpdate = "\u001B[35m" + "/update{id}" + "\u001B[0m\n";
        System.out.print("-------Menu-------\n" +
                "~To view all exhibition, enter: " + commandAll +
                "~To update the exhibition, enter: " + commandUpdate +
                "~To display detailed information about the exhibition, enter: " + commandGet +
                "~To display participant and artworks in exhibition, enter: " + commandFindParAndArt +
                "~To display current exhibition, enter: " + commandGetCurExhibition +
                "~To delete exhibition, enter: " + commandDelete +
                "~To create exhibition, enter: " + commandCreate +
                "~To exit, enter: " + commandExit);
    }

    private static boolean usingMenu(String userInput, Long id, String command, Scanner scanner, ExhibitionDao exhibitionDao, DataSource dataSource) {
        if (id > 0 && "/update{}".equals(command)) {
            Exhibition exhibition = updateExhibition(scanner, exhibitionDao, id, dataSource);
            System.out.println(exhibitionDao.update(exhibition).toString());
        } else if (userInput.equals("/all")) {
            List<Exhibition> exhibitions = exhibitionDao.findAll();
            for (Exhibition exhibition : exhibitions) {
                System.out.println(exhibition.toString());
            }
        } else if (userInput.equals("/getCurExh")) {
            List<CurrentExhibition> exhibitions = exhibitionDao.findCurrentExhibition();
            for (CurrentExhibition exhibition : exhibitions) {
                System.out.println(exhibition.toString());
            }
        } else if (userInput.equals("/exit")) {
            return false;
        } else if (id > 0 && "/get{}".equals(command)) {
            Exhibition exhibition = exhibitionDao.findById(id);
            System.out.println(exhibition);
        } else if (id > 0 && "/getParAndArt{}".equals(command)) {
            List<ExhibitionParticipantsAndArtworks> exhibitions = exhibitionDao.findParticipantsArtworksByExhibitionId(id);
            for (ExhibitionParticipantsAndArtworks exhibition : exhibitions) {
                System.out.println(exhibition.toString());
            }
        } else if (id > 0 && "/delete{}".equals(command)) {
            boolean deleted = exhibitionDao.delete(id);
            System.out.println(deleted);
        } else if (userInput.equals("/create")) {
            Exhibition exhibition = createExhibitionWithoutID(scanner, dataSource);
            Exhibition createdExhibition = exhibitionDao.create(exhibition);
            System.out.println(createdExhibition.toString());
        } else {
            System.out.println("Incorrect command!");
        }
        return true;
    }

    private static Exhibition updateExhibition(Scanner scanner, ExhibitionDao exhibitionDao, long id, DataSource dataSource) {
        while (true) {
            if (exhibitionDao.findById(id) == null) {
                System.out.println("There is no exhibition with this id! Enter it again!");
                id = scanner.nextLong();
                //Here, the scanner is called to clear the scanner's clipboard of \n that was left after the call scanner.NextLong()
                scanner.nextLine();
                continue;
            }
            break;
        }
        Exhibition exhibition = createExhibitionWithoutID(scanner, dataSource);
        exhibition.setId(id);
        return exhibition;
    }

    private static Exhibition createExhibitionWithoutID(Scanner scanner, DataSource dataSource) {
        Exhibition exhibition = new Exhibition();
        setName(scanner, exhibition);
        setExhibitionHall(scanner, exhibition, dataSource);
        setExhibitionType(scanner, exhibition);
        setStartDate(scanner, exhibition);
        setEndDate(scanner, exhibition);
        return exhibition;
    }

    private static void setName(Scanner scanner, Exhibition exhibition) {
        System.out.print("Enter name of the exhibition: ");
        exhibition.setName(scanner.nextLine());
    }

    private static void setExhibitionHall(Scanner scanner, Exhibition exhibition, DataSource dataSource) {
        ExhibitionHallDao exhibitionHallDao = new ExhibitionHallDaoImpl(dataSource);
        System.out.print("Enter the exhibition hall id of the exhibition(Enter exhibition hall id): ");
        exhibition.setHallId(exhibitionHallDao.findById(scanner.nextLong()));
    }

    private static void setExhibitionType(Scanner scanner, Exhibition exhibition) {
        System.out.print("Enter exhibition type of the exhibition(enum): ");
        scanner.nextLine();
        exhibition.setType(ExhibitionType.valueOf(scanner.nextLine()));
    }

    private static void setStartDate(Scanner scanner, Exhibition exhibition) {
        System.out.print("Enter the start date of the exhibition (yyyy-MM-dd): ");
        String input = scanner.nextLine();

        try {
            // Преобразование строки в LocalDate
            LocalDate creationDate = LocalDate.parse(input);
            exhibition.setStartDate(creationDate);  // Установка значения LocalDate
            System.out.println("Birth date set successfully.");
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format. Please enter the date in the format yyyy-MM-dd.");
        }
    }

    private static void setEndDate(Scanner scanner, Exhibition exhibition) {
        System.out.print("Enter the end date of the exhibition (yyyy-MM-dd): ");
        String input = scanner.nextLine();

        try {
            // Преобразование строки в LocalDate
            LocalDate creationDate = LocalDate.parse(input);
            exhibition.setEndDate(creationDate);  // Установка значения LocalDate
            System.out.println("Birth date set successfully.");
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format. Please enter the date in the format yyyy-MM-dd.");
        }
    }
}
