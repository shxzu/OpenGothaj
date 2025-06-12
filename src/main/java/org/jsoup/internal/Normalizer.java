package org.jsoup.internal;

import java.util.Locale;

public final class Normalizer {
    public static String lowerCase(String input) {
        return input != null ? input.toLowerCase(Locale.ENGLISH) : "";
    }

    public static String normalize(String input) {
        return Normalizer.lowerCase(input).trim();
    }

    public static String normalize(String input, boolean isStringLiteral) {
        return isStringLiteral ? Normalizer.lowerCase(input) : Normalizer.normalize(input);
    }
}
