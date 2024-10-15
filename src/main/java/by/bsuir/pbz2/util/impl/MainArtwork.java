package by.bsuir.pbz2.util.impl;

import by.bsuir.pbz2.data.ArtistDao;
import by.bsuir.pbz2.data.ArtworkDao;
import by.bsuir.pbz2.data.connection.DataSource;
import by.bsuir.pbz2.data.connection.impl.DataSourceImpl;
import by.bsuir.pbz2.data.entity.Artwork;
import by.bsuir.pbz2.data.entity.enums.ExecutionType;
import by.bsuir.pbz2.data.impl.ArtistDaoImpl;
import by.bsuir.pbz2.data.impl.ArtworkDaoImpl;
import by.bsuir.pbz2.util.PropertiesManager;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainArtwork {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Pattern pattern = Pattern.compile("\\d+");
        ArtworkDao artworkDao = getArtworkDao();

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

            if (!usingMenu(userInput, id, command, scanner, artworkDao)) {
                return;
            }
        }
    }

    private static ArtworkDao getArtworkDao() {
        PropertiesManager propertiesManager = new PropertiesManagerImpl("src\\main\\resources\\app.properties");
        String profile = propertiesManager.getKey("my.app.profile");
        String url = propertiesManager.getKey("my.app.db." + profile + ".url");
        String user = propertiesManager.getKey("my.app.db." + profile + ".user");
        String password = propertiesManager.getKey("my.app.db." + profile + ".password");
        String driver = propertiesManager.getKey("my.app.db." + profile + ".driver");
        DataSource dataSource = new DataSourceImpl(password, user, url, driver);
        return new ArtworkDaoImpl(dataSource);
    }

    private static void printMenu() {
        String commandAll = "\u001B[35m" + "/all" + "\u001B[0m\n";
        String commandGet = "\u001B[35m" + "/get{id}" + "\u001B[0m\n";
        String commandDelete = "\u001B[35m" + "/delete{id}" + "\u001B[0m\n";
        String commandExit = "\u001B[35m" + "/exit" + "\u001B[0m\n";
        String commandCreate = "\u001B[35m" + "/create" + "\u001B[0m\n";
        String commandUpdate = "\u001B[35m" + "/update{id}" + "\u001B[0m\n";
        System.out.print("-------Menu-------\n" +
                "~To view all artwork, enter: " + commandAll +
                "~To update the artwork, enter: " + commandUpdate +
                "~To display detailed information about the artwork, enter: " + commandGet +
                "~To delete artwork, enter: " + commandDelete +
                "~To create artwork, enter: " + commandCreate +
                "~To exit, enter: " + commandExit);
    }

    private static boolean usingMenu(String userInput, Long id, String command, Scanner scanner, ArtworkDao artworkDao) {
        if (id > 0 && "/update{}".equals(command)) {
            Artwork artwork = updateArtwork(scanner, artworkDao, id);
            System.out.println(artworkDao.update(artwork).toString());
        } else if (userInput.equals("/all")) {
            List<Artwork> artworks = artworkDao.findAll();
            for (Artwork artwork : artworks) {
                System.out.println(artwork.toString());
            }
        } else if (userInput.equals("/exit")) {
            return false;
        } else if (id > 0 && "/get{}".equals(command)) {
            Artwork artwork = artworkDao.findById(id);
            System.out.println(artwork);
        } else if (id > 0 && "/delete{}".equals(command)) {
            boolean deleted = artworkDao.delete(id);
            System.out.println(deleted);
        } else if (userInput.equals("/create")) {
            Artwork artwork = createArtworkWithoutID(scanner);
            Artwork createdArtwork = artworkDao.create(artwork);
            System.out.println(createdArtwork.toString());
        } else {
            System.out.println("Incorrect command!");
        }
        return true;
    }

    private static Artwork updateArtwork(Scanner scanner, ArtworkDao artworkDao, long id) {
        while (true) {
            if (artworkDao.findById(id) == null) {
                System.out.println("There is no artwork with this id! Enter it again!");
                id = scanner.nextLong();
                //Here, the scanner is called to clear the scanner's clipboard of \n that was left after the call scanner.NextLong()
                scanner.nextLine();
                continue;
            }
            break;
        }
        Artwork artwork = createArtworkWithoutID(scanner);
        artwork.setId(id);
        return artwork;
    }

    private static Artwork createArtworkWithoutID(Scanner scanner) {
        Artwork artwork = new Artwork();
        setTitle(scanner, artwork);
        setExecutionType(scanner, artwork);
        setCreationDate(scanner, artwork);
        setHeight(scanner, artwork);
        setWidth(scanner, artwork);
        setVolume(scanner, artwork);
        setArtist(scanner, artwork);
        return artwork;
    }

    private static void setTitle(Scanner scanner, Artwork artwork) {
        System.out.print("Enter title of the artwork: ");
        artwork.setTitle(scanner.nextLine());
    }

    private static void setExecutionType(Scanner scanner, Artwork artwork) {
        System.out.print("Enter execution type of the artwork(enam name): ");
        artwork.setExecutionType(ExecutionType.valueOf(scanner.nextLine()));
    }

    private static void setCreationDate(Scanner scanner, Artwork artwork) {
        System.out.print("Enter the creation date of the artwork (yyyy-MM-dd): ");
        String input = scanner.nextLine();

        try {
            // Преобразование строки в LocalDate
            LocalDate creationDate = LocalDate.parse(input);
            artwork.setCreationDate(creationDate);  // Установка значения LocalDate
            System.out.println("Birth date set successfully.");
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format. Please enter the date in the format yyyy-MM-dd.");
        }
    }

    private static void setHeight(Scanner scanner, Artwork artwork) {
        System.out.print("Enter the height of the artwork(BigDecimal): ");
        artwork.setHeight(scanner.nextBigDecimal());
    }

    private static void setWidth(Scanner scanner, Artwork artwork) {
        System.out.print("Enter the Width of the artwork(BigDecimal): ");
        scanner.nextLine();
        artwork.setHeight(scanner.nextBigDecimal());
    }

    private static void setVolume(Scanner scanner, Artwork artwork) {
        System.out.print("Enter the volume of the artwork(BigDecimal): ");
        artwork.setHeight(scanner.nextBigDecimal());
    }

    private static void setArtist(Scanner scanner, Artwork artwork) {
        ArtistDao artistDao = getArtistDao();
        System.out.print("Enter the artist id of the artwork(Enter artist id): ");
        artwork.setArtistId(artistDao.findById(scanner.nextLong()));
    }

    private static ArtistDao getArtistDao() {
        PropertiesManager propertiesManager = new PropertiesManagerImpl("src\\main\\resources\\app.properties");
        String profile = propertiesManager.getKey("my.app.profile");
        String url = propertiesManager.getKey("my.app.db." + profile + ".url");
        String user = propertiesManager.getKey("my.app.db." + profile + ".user");
        String password = propertiesManager.getKey("my.app.db." + profile + ".password");
        String driver = propertiesManager.getKey("my.app.db." + profile + ".driver");
        DataSource dataSource = new DataSourceImpl(password, user, url, driver);
        return new ArtistDaoImpl(dataSource);
    }
}
