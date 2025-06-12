package xyz.cucumber.base.utils;

import java.util.Random;

public class StringUtils {
    public static String alphabet = "qwertzuiopsdghjklyxcvbnmQWERTZUIOPAFGHJKLYCVBNM1234567890";
    public static String numbers = "1234567890";

    public static String generateRandomCharacterFromAlphaBet() {
        Random random = new Random();
        int index = random.nextInt(alphabet.length());
        return Character.toString(alphabet.charAt(index));
    }

    public static String generateRandomNumber() {
        Random random = new Random();
        int index = random.nextInt(numbers.length());
        return Character.toString(numbers.charAt(index));
    }

    public static String generateNamesForMinecraft(String[] firstNames, String[] lastNames) {
        Random random = new Random();
        String firstNamesString = firstNames[random.nextInt(firstNames.length)];
        String lastNamesString = lastNames[random.nextInt(lastNames.length)];
        String name = "";
        name = String.valueOf(firstNamesString) + lastNamesString;
        int i = 0;
        while (i < 16) {
            name = String.valueOf(name) + StringUtils.generateRandomNumber();
            ++i;
        }
        name = name.substring(0, 16);
        return name;
    }
}
