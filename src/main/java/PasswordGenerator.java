import java.util.Random;
import java.util.Scanner;

public class PasswordGenerator {

    private static final String CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int ALPHABET_LENGTH = CHARACTERS.length();

    public static String generateRandomString(int length) {
        Random rand = new Random(System.currentTimeMillis());
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(CHARACTERS.charAt(rand.nextInt(ALPHABET_LENGTH)));
        }
        return sb.toString();
    }

    // Частотный анализ символов
    public static void frequencyAnalysis(String password) {
        int[] frequencies = new int[ALPHABET_LENGTH];

        // Подсчет частот символов
        for (char c : password.toCharArray()) {
            int index = CHARACTERS.indexOf(c);
            if (index >= 0) {
                frequencies[index]++;
            }
        }

        // Вывод результатов
        System.out.println("Частотный анализ символов:");
        for (int i = 0; i < ALPHABET_LENGTH; i++) {
            if (frequencies[i] > 0) {
                System.out.printf("%c: %d\n", CHARACTERS.charAt(i), frequencies[i]);
            }
        }
    }

    // Оценка среднего времени подбора пароля (brute-force)
    public static double calculateBruteForceTime(int length) {
        double possibleCombinations = Math.pow(ALPHABET_LENGTH, length);
        double timePerAttempt = 0.00001; // условное время на одну попытку (в секундах)
        return possibleCombinations * timePerAttempt;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Введите длину строки: ");
        int length = scanner.nextInt();

        String randomString = generateRandomString(length);
        System.out.println("Сгенерированная строка: " + randomString);

        frequencyAnalysis(randomString);

        double bruteForceTime = calculateBruteForceTime(length);
        System.out.printf("Среднее время подбора пароля: %.2f секунд\n", bruteForceTime);

        scanner.close();
    }
}