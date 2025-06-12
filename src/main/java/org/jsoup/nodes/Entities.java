package org.jsoup.nodes;

import java.io.IOException;
import java.nio.charset.CharsetEncoder;
import java.util.Arrays;
import java.util.HashMap;
import org.jsoup.SerializationException;
import org.jsoup.helper.Validate;
import org.jsoup.internal.StringUtil;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.EntitiesData;
import org.jsoup.parser.CharacterReader;
import org.jsoup.parser.Parser;

public class Entities {
    private static final int empty = -1;
    private static final String emptyName = "";
    static final int codepointRadix = 36;
    private static final char[] codeDelims = new char[]{',', ';'};
    private static final HashMap<String, String> multipoints = new HashMap();
    private static final Document.OutputSettings DefaultOutput = new Document.OutputSettings();

    private Entities() {
    }

    public static boolean isNamedEntity(String name) {
        return EscapeMode.extended.codepointForName(name) != -1;
    }

    public static boolean isBaseNamedEntity(String name) {
        return EscapeMode.base.codepointForName(name) != -1;
    }

    public static String getByName(String name) {
        String val = multipoints.get(name);
        if (val != null) {
            return val;
        }
        int codepoint = EscapeMode.extended.codepointForName(name);
        if (codepoint != -1) {
            return new String(new int[]{codepoint}, 0, 1);
        }
        return emptyName;
    }

    public static int codepointsForName(String name, int[] codepoints) {
        String val = multipoints.get(name);
        if (val != null) {
            codepoints[0] = val.codePointAt(0);
            codepoints[1] = val.codePointAt(1);
            return 2;
        }
        int codepoint = EscapeMode.extended.codepointForName(name);
        if (codepoint != -1) {
            codepoints[0] = codepoint;
            return 1;
        }
        return 0;
    }

    public static String escape(String string, Document.OutputSettings out) {
        if (string == null) {
            return emptyName;
        }
        StringBuilder accum = StringUtil.borrowBuilder();
        try {
            Entities.escape(accum, string, out, false, false, false, false);
        }
        catch (IOException e) {
            throw new SerializationException(e);
        }
        return StringUtil.releaseBuilder(accum);
    }

    public static String escape(String string) {
        return Entities.escape(string, DefaultOutput);
    }

    static void escape(Appendable accum, String string, Document.OutputSettings out, boolean inAttribute, boolean normaliseWhite, boolean stripLeadingWhite, boolean trimTrailing) throws IOException {
        int codePoint;
        boolean lastWasWhite = false;
        boolean reachedNonWhite = false;
        EscapeMode escapeMode = out.escapeMode();
        CharsetEncoder encoder = out.encoder();
        CoreCharset coreCharset = out.coreCharset;
        int length = string.length();
        boolean skipped = false;
        for (int offset = 0; offset < length; offset += Character.charCount(codePoint)) {
            codePoint = string.codePointAt(offset);
            if (normaliseWhite) {
                if (StringUtil.isWhitespace(codePoint)) {
                    if (stripLeadingWhite && !reachedNonWhite || lastWasWhite) continue;
                    if (trimTrailing) {
                        skipped = true;
                        continue;
                    }
                    accum.append(' ');
                    lastWasWhite = true;
                    continue;
                }
                lastWasWhite = false;
                reachedNonWhite = true;
                if (skipped) {
                    accum.append(' ');
                    skipped = false;
                }
            }
            if (codePoint < 65536) {
                char c = (char)codePoint;
                switch (c) {
                    case '&': {
                        accum.append("&amp;");
                        break;
                    }
                    case ' ': {
                        if (escapeMode != EscapeMode.xhtml) {
                            accum.append("&nbsp;");
                            break;
                        }
                        accum.append("&#xa0;");
                        break;
                    }
                    case '<': {
                        if (!inAttribute || escapeMode == EscapeMode.xhtml || out.syntax() == Document.OutputSettings.Syntax.xml) {
                            accum.append("&lt;");
                            break;
                        }
                        accum.append(c);
                        break;
                    }
                    case '>': {
                        if (!inAttribute) {
                            accum.append("&gt;");
                            break;
                        }
                        accum.append(c);
                        break;
                    }
                    case '\"': {
                        if (inAttribute) {
                            accum.append("&quot;");
                            break;
                        }
                        accum.append(c);
                        break;
                    }
                    case '\t': 
                    case '\n': 
                    case '\r': {
                        accum.append(c);
                        break;
                    }
                    default: {
                        if (c < ' ' || !Entities.canEncode(coreCharset, c, encoder)) {
                            Entities.appendEncoded(accum, escapeMode, codePoint);
                            break;
                        }
                        accum.append(c);
                        break;
                    }
                }
                continue;
            }
            String c = new String(Character.toChars(codePoint));
            if (encoder.canEncode(c)) {
                accum.append(c);
                continue;
            }
            Entities.appendEncoded(accum, escapeMode, codePoint);
        }
    }

    private static void appendEncoded(Appendable accum, EscapeMode escapeMode, int codePoint) throws IOException {
        String name = escapeMode.nameForCodepoint(codePoint);
        if (!emptyName.equals(name)) {
            accum.append('&').append(name).append(';');
        } else {
            accum.append("&#x").append(Integer.toHexString(codePoint)).append(';');
        }
    }

    public static String unescape(String string) {
        return Entities.unescape(string, false);
    }

    static String unescape(String string, boolean strict) {
        return Parser.unescapeEntities(string, strict);
    }

    private static boolean canEncode(CoreCharset charset, char c, CharsetEncoder fallback) {
        switch (charset) {
            case ascii: {
                return c < '';
            }
            case utf: {
                return true;
            }
        }
        return fallback.canEncode(c);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private static void load(EscapeMode e, String pointsData, int size) {
        EscapeMode.access$102(e, new String[size]);
        EscapeMode.access$202(e, new int[size]);
        EscapeMode.access$302(e, new int[size]);
        EscapeMode.access$402(e, new String[size]);
        int i = 0;
        try (CharacterReader reader = new CharacterReader(pointsData);){
            while (!reader.isEmpty()) {
                int cp2;
                String name = reader.consumeTo('=');
                reader.advance();
                int cp1 = Integer.parseInt(reader.consumeToAny(codeDelims), 36);
                char codeDelim = reader.current();
                reader.advance();
                if (codeDelim == ',') {
                    cp2 = Integer.parseInt(reader.consumeTo(';'), 36);
                    reader.advance();
                } else {
                    cp2 = -1;
                }
                String indexS = reader.consumeTo('&');
                int index = Integer.parseInt(indexS, 36);
                reader.advance();
                ((EscapeMode)e).nameKeys[i] = name;
                ((EscapeMode)e).codeVals[i] = cp1;
                ((EscapeMode)e).codeKeys[index] = cp1;
                ((EscapeMode)e).nameVals[index] = name;
                if (cp2 != -1) {
                    multipoints.put(name, new String(new int[]{cp1, cp2}, 0, 2));
                }
                ++i;
            }
            Validate.isTrue(i == size, "Unexpected count of entities loaded");
        }
    }

    public static enum EscapeMode {
        xhtml(EntitiesData.xmlPoints, 4),
        base(EntitiesData.basePoints, 106),
        extended(EntitiesData.fullPoints, 2125);

        private String[] nameKeys;
        private int[] codeVals;
        private int[] codeKeys;
        private String[] nameVals;

        private EscapeMode(String file, int size) {
            Entities.load(this, file, size);
        }

        int codepointForName(String name) {
            int index = Arrays.binarySearch(this.nameKeys, name);
            return index >= 0 ? this.codeVals[index] : -1;
        }

        String nameForCodepoint(int codepoint) {
            int index = Arrays.binarySearch(this.codeKeys, codepoint);
            if (index >= 0) {
                return index < this.nameVals.length - 1 && this.codeKeys[index + 1] == codepoint ? this.nameVals[index + 1] : this.nameVals[index];
            }
            return Entities.emptyName;
        }

        private int size() {
            return this.nameKeys.length;
        }

        static String[] access$102(EscapeMode x0, String[] x1) {
            x0.nameKeys = x1;
            return x1;
        }

        static int[] access$202(EscapeMode x0, int[] x1) {
            x0.codeVals = x1;
            return x1;
        }

        static int[] access$302(EscapeMode x0, int[] x1) {
            x0.codeKeys = x1;
            return x1;
        }

        static String[] access$402(EscapeMode x0, String[] x1) {
            x0.nameVals = x1;
            return x1;
        }
    }

    static enum CoreCharset {
        ascii,
        utf,
        fallback;


        static CoreCharset byName(String name) {
            if (name.equals("US-ASCII")) {
                return ascii;
            }
            if (name.startsWith("UTF-")) {
                return utf;
            }
            return fallback;
        }
    }
}
