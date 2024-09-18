public class CaesarCipher {

    public static String encrypt(String text, int shift) {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (Character.isLetter(c)) {
                char base = Character.isUpperCase(c) ? 'A' : 'a';
                result.append((char) ((c - base + shift) % 26 + base));
            } else {
                result.append(c);
            }
        }
        return result.toString();
    }

    public static String decrypt(String text, int shift) {
        return encrypt(text, 26 - shift);
    }

    public static void bruteForceAttack(String encryptedText, String originalText) {
        for (int key = 0; key < 26; key++) {
            String decrypted = decrypt(encryptedText, key);
            System.out.println("Ключ: " + key + " -> " + decrypted);

            if (decrypted.equals(originalText)) {
                System.out.println("Ключ найден: " + key);
                break;
            }
        }
    }

    public static void main(String[] args) {
        String text = "Hello World!";
        int shift = 3;

        String encrypted = encrypt(text, shift);
        System.out.println("Зашифрованный текст: " + encrypted);
        String decrypted = decrypt(encrypted, shift);
        System.out.println("Расшифрованный текст: " + decrypted);

        String encryptedText = "Khoor Zruog!";
        String originalText = "Hello World!";

        bruteForceAttack(encryptedText, originalText);
    }
}
