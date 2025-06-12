package org.jsoup.internal;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Stack;
import java.util.regex.Pattern;
import javax.annotation.Nullable;
import org.jsoup.helper.Validate;

public final class StringUtil {
    static final String[] padding = new String[]{"", " ", "  ", "   ", "    ", "     ", "      ", "       ", "        ", "         ", "          ", "           ", "            ", "             ", "              ", "               ", "                ", "                 ", "                  ", "                   ", "                    "};
    private static final Pattern extraDotSegmentsPattern = Pattern.compile("^/((\\.{1,2}/)+)");
    private static final Pattern validUriScheme = Pattern.compile("^[a-zA-Z][a-zA-Z0-9+-.]*:");
    private static final Pattern controlChars = Pattern.compile("[\\x00-\\x1f]*");
    private static final ThreadLocal<Stack<StringBuilder>> threadLocalBuilders = new ThreadLocal<Stack<StringBuilder>>(){

        @Override
        protected Stack<StringBuilder> initialValue() {
            return new Stack<StringBuilder>();
        }
    };
    private static final int MaxCachedBuilderSize = 8192;
    private static final int MaxIdleBuilders = 8;

    public static String join(Collection<?> strings, String sep) {
        return StringUtil.join(strings.iterator(), sep);
    }

    public static String join(Iterator<?> strings, String sep) {
        if (!strings.hasNext()) {
            return "";
        }
        String start = strings.next().toString();
        if (!strings.hasNext()) {
            return start;
        }
        StringJoiner j = new StringJoiner(sep);
        j.add(start);
        while (strings.hasNext()) {
            j.add(strings.next());
        }
        return j.complete();
    }

    public static String join(String[] strings, String sep) {
        return StringUtil.join(Arrays.asList(strings), sep);
    }

    public static String padding(int width) {
        return StringUtil.padding(width, 30);
    }

    public static String padding(int width, int maxPaddingWidth) {
        Validate.isTrue(width >= 0, "width must be >= 0");
        Validate.isTrue(maxPaddingWidth >= -1);
        if (maxPaddingWidth != -1) {
            width = Math.min(width, maxPaddingWidth);
        }
        if (width < padding.length) {
            return padding[width];
        }
        char[] out = new char[width];
        for (int i = 0; i < width; ++i) {
            out[i] = 32;
        }
        return String.valueOf(out);
    }

    public static boolean isBlank(String string) {
        if (string == null || string.length() == 0) {
            return true;
        }
        int l = string.length();
        for (int i = 0; i < l; ++i) {
            if (StringUtil.isWhitespace(string.codePointAt(i))) continue;
            return false;
        }
        return true;
    }

    public static boolean startsWithNewline(String string) {
        if (string == null || string.length() == 0) {
            return false;
        }
        return string.charAt(0) == '\n';
    }

    public static boolean isNumeric(String string) {
        if (string == null || string.length() == 0) {
            return false;
        }
        int l = string.length();
        for (int i = 0; i < l; ++i) {
            if (Character.isDigit(string.codePointAt(i))) continue;
            return false;
        }
        return true;
    }

    public static boolean isWhitespace(int c) {
        return c == 32 || c == 9 || c == 10 || c == 12 || c == 13;
    }

    public static boolean isActuallyWhitespace(int c) {
        return c == 32 || c == 9 || c == 10 || c == 12 || c == 13 || c == 160;
    }

    public static boolean isInvisibleChar(int c) {
        return c == 8203 || c == 173;
    }

    public static String normaliseWhitespace(String string) {
        StringBuilder sb = StringUtil.borrowBuilder();
        StringUtil.appendNormalisedWhitespace(sb, string, false);
        return StringUtil.releaseBuilder(sb);
    }

    public static void appendNormalisedWhitespace(StringBuilder accum, String string, boolean stripLeading) {
        int c;
        boolean lastWasWhite = false;
        boolean reachedNonWhite = false;
        int len = string.length();
        for (int i = 0; i < len; i += Character.charCount(c)) {
            c = string.codePointAt(i);
            if (StringUtil.isActuallyWhitespace(c)) {
                if (stripLeading && !reachedNonWhite || lastWasWhite) continue;
                accum.append(' ');
                lastWasWhite = true;
                continue;
            }
            if (StringUtil.isInvisibleChar(c)) continue;
            accum.appendCodePoint(c);
            lastWasWhite = false;
            reachedNonWhite = true;
        }
    }

    public static boolean in(String needle, String ... haystack) {
        int len = haystack.length;
        for (int i = 0; i < len; ++i) {
            if (!haystack[i].equals(needle)) continue;
            return true;
        }
        return false;
    }

    public static boolean inSorted(String needle, String[] haystack) {
        return Arrays.binarySearch(haystack, needle) >= 0;
    }

    public static boolean isAscii(String string) {
        Validate.notNull(string);
        for (int i = 0; i < string.length(); ++i) {
            char c = string.charAt(i);
            if (c <= '') continue;
            return false;
        }
        return true;
    }

    public static URL resolve(URL base, String relUrl) throws MalformedURLException {
        if ((relUrl = StringUtil.stripControlChars(relUrl)).startsWith("?")) {
            relUrl = base.getPath() + relUrl;
        }
        URL url = new URL(base, relUrl);
        String fixedFile = extraDotSegmentsPattern.matcher(url.getFile()).replaceFirst("/");
        if (url.getRef() != null) {
            fixedFile = fixedFile + "#" + url.getRef();
        }
        return new URL(url.getProtocol(), url.getHost(), url.getPort(), fixedFile);
    }

    public static String resolve(String baseUrl, String relUrl) {
        baseUrl = StringUtil.stripControlChars(baseUrl);
        relUrl = StringUtil.stripControlChars(relUrl);
        try {
            URL base;
            try {
                base = new URL(baseUrl);
            }
            catch (MalformedURLException e) {
                URL abs = new URL(relUrl);
                return abs.toExternalForm();
            }
            return StringUtil.resolve(base, relUrl).toExternalForm();
        }
        catch (MalformedURLException e) {
            return validUriScheme.matcher(relUrl).find() ? relUrl : "";
        }
    }

    private static String stripControlChars(String input) {
        return controlChars.matcher(input).replaceAll("");
    }

    public static StringBuilder borrowBuilder() {
        Stack<StringBuilder> builders = threadLocalBuilders.get();
        return builders.empty() ? new StringBuilder(8192) : builders.pop();
    }

    public static String releaseBuilder(StringBuilder sb) {
        Validate.notNull(sb);
        String string = sb.toString();
        if (sb.length() > 8192) {
            sb = new StringBuilder(8192);
        } else {
            sb.delete(0, sb.length());
        }
        Stack<StringBuilder> builders = threadLocalBuilders.get();
        builders.push(sb);
        while (builders.size() > 8) {
            builders.pop();
        }
        return string;
    }

    public static class StringJoiner {
        @Nullable
        StringBuilder sb = StringUtil.borrowBuilder();
        final String separator;
        boolean first = true;

        public StringJoiner(String separator) {
            this.separator = separator;
        }

        public StringJoiner add(Object stringy) {
            Validate.notNull(this.sb);
            if (!this.first) {
                this.sb.append(this.separator);
            }
            this.sb.append(stringy);
            this.first = false;
            return this;
        }

        public StringJoiner append(Object stringy) {
            Validate.notNull(this.sb);
            this.sb.append(stringy);
            return this;
        }

        public String complete() {
            String string = StringUtil.releaseBuilder(this.sb);
            this.sb = null;
            return string;
        }
    }
}
