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

    public static int[] frequencyAnalysis(String password) {
        int[] frequencies = new int[ALPHABET_LENGTH];

        for (char c : password.toCharArray()) {
            int index = CHARACTERS.indexOf(c);
            if (index >= 0) {
                frequencies[index]++;
            }
        }
        return frequencies;
    }

    public static void displayFrequency(int[] frequencies) {
        System.out.println("Частотный анализ символов:");
        for (int i = 0; i < ALPHABET_LENGTH; i++) {
            if (frequencies[i] > 0) {
                System.out.printf("%c: %d\n", CHARACTERS.charAt(i), frequencies[i]);
            }
        }
    }

    public static double calculateBruteForceTime(int length) {
        double possibleCombinations = Math.pow(ALPHABET_LENGTH, length) / 2;
        double timePerAttempt = 0.000001;
        return possibleCombinations * timePerAttempt;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Введите длину строки: ");
        int length = scanner.nextInt();

        String randomString = generateRandomString(length);
        System.out.println("Сгенерированная строка: " + randomString);

        int[] frequencies = frequencyAnalysis(randomString);
        displayFrequency(frequencies);

        double bruteForceTime = calculateBruteForceTime(length);
        System.out.printf("Среднее время подбора пароля: %.2f секунд\n", bruteForceTime);

        scanner.close();
    }
}