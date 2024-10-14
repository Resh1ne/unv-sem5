package by.bsuir.pbz2.util.impl;

import by.bsuir.pbz2.data.ArtistDao;
import by.bsuir.pbz2.data.connection.DataSource;
import by.bsuir.pbz2.data.connection.impl.DataSourceImpl;
import by.bsuir.pbz2.data.entity.Artist;
import by.bsuir.pbz2.data.impl.ArtistDaoImpl;
import by.bsuir.pbz2.util.PropertiesManager;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Pattern pattern = Pattern.compile("\\d+");
        ArtistDao artistDao = getArtistDao();

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

            if (!usingMenu(userInput, id, command, scanner, artistDao)) {
                return;
            }
        }
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

    private static void printMenu() {
        String commandAll = "\u001B[35m" + "/all" + "\u001B[0m\n";
        String commandGet = "\u001B[35m" + "/get{id}" + "\u001B[0m\n";
        String commandDelete = "\u001B[35m" + "/delete{id}" + "\u001B[0m\n";
        String commandExit = "\u001B[35m" + "/exit" + "\u001B[0m\n";
        String commandCreate = "\u001B[35m" + "/create" + "\u001B[0m\n";
        String commandUpdate = "\u001B[35m" + "/update{id}" + "\u001B[0m\n";
        System.out.print("-------Menu-------\n" +
                "~To view all artists, enter: " + commandAll +
                "~To update the artist, enter: " + commandUpdate +
                "~To display detailed information about the artist, enter: " + commandGet +
                "~To delete artist, enter: " + commandDelete +
                "~To create artist, enter: " + commandCreate +
                "~To exit, enter: " + commandExit);
    }

    private static boolean usingMenu(String userInput, Long id, String command, Scanner scanner, ArtistDao artistDao) {
        if (id > 0 && "/update{}".equals(command)) {
            Artist artist = updateArtist(scanner, artistDao, id);
            System.out.println(artistDao.update(artist).toString());
        } else if (userInput.equals("/all")) {
            List<Artist> artists = artistDao.findAll();
            for (Artist artist : artists) {
                System.out.println(artist.toString());
            }
        } else if (userInput.equals("/exit")) {
            return false;
        } else if (id > 0 && "/get{}".equals(command)) {
            Artist artist = artistDao.findById(id);
            System.out.println(artist);
        } else if (id > 0 && "/delete{}".equals(command)) {
            boolean deleted = artistDao.delete(id);
            System.out.println(deleted);
        } else if (userInput.equals("/create")) {
            Artist artist = createArtistWithoutID(scanner);
            Artist createdArtist = artistDao.create(artist);
            System.out.println(createdArtist.toString());
        } else {
            System.out.println("Incorrect command!");
        }
        return true;
    }

    private static Artist updateArtist(Scanner scanner, ArtistDao artistDao, long id) {
        while (true) {
            if (artistDao.findById(id) == null) {
                System.out.println("There is no artist with this id! Enter it again!");
                id = scanner.nextLong();
                //Here, the scanner is called to clear the scanner's clipboard of \n that was left after the call scanner.NextLong()
                scanner.nextLine();
                continue;
            }
            break;
        }
        Artist artist = createArtistWithoutID(scanner);
        artist.setId(id);
        return artist;
    }

    private static Artist createArtistWithoutID(Scanner scanner) {
        Artist artist = new Artist();
        setName(scanner, artist);
        setBrithPlace(scanner, artist);
        setBirthDate(scanner, artist);
        setBiography(scanner, artist);
        setEducation(scanner, artist);
        return artist;
    }

    private static void setName(Scanner scanner, Artist artist) {
        System.out.print("Enter name of the artist: ");
        artist.setName(scanner.nextLine());
    }

    private static void setBrithPlace(Scanner scanner, Artist artist) {
        System.out.print("Enter the birth place of the artist: ");
        artist.setBirthPlace(scanner.nextLine());
    }

    private static void setBirthDate(Scanner scanner, Artist artist) {
        System.out.print("Enter the birth date of the artist (yyyy-MM-dd): ");
        String input = scanner.nextLine();

        try {
            // Преобразование строки в LocalDate
            LocalDate birthDate = LocalDate.parse(input);
            artist.setBirthDate(birthDate);  // Установка значения LocalDate
            System.out.println("Birth date set successfully.");
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format. Please enter the date in the format yyyy-MM-dd.");
        }
    }

    private static void setBiography(Scanner scanner, Artist artist) {
        System.out.print("Enter the biography of the artist: ");
        artist.setBiography(scanner.nextLine());
    }

    private static void setEducation(Scanner scanner, Artist artist) {
        System.out.print("Enter the education of the artist: ");
        artist.setEducation(scanner.nextLine());
    }
}
