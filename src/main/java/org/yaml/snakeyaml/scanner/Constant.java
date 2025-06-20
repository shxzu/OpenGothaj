package org.yaml.snakeyaml.scanner;

import java.util.Arrays;

public final class Constant {
    private static final String ALPHA_S = "abcdefghijklmnopqrstuvwxyz0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ-_";
    private static final String LINEBR_S = "\n  ";
    private static final String FULL_LINEBR_S = "\r\n  ";
    private static final String NULL_OR_LINEBR_S = "\u0000\r\n  ";
    private static final String NULL_BL_LINEBR_S = " \u0000\r\n  ";
    private static final String NULL_BL_T_LINEBR_S = "\t \u0000\r\n  ";
    private static final String NULL_BL_T_S = "\u0000 \t";
    private static final String URI_CHARS_S = "abcdefghijklmnopqrstuvwxyz0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ-_-;/?:@&=+$,_.!~*'()[]%";
    public static final Constant LINEBR = new Constant("\n  ");
    public static final Constant NULL_OR_LINEBR = new Constant("\u0000\r\n  ");
    public static final Constant NULL_BL_LINEBR = new Constant(" \u0000\r\n  ");
    public static final Constant NULL_BL_T_LINEBR = new Constant("\t \u0000\r\n  ");
    public static final Constant NULL_BL_T = new Constant("\u0000 \t");
    public static final Constant URI_CHARS = new Constant("abcdefghijklmnopqrstuvwxyz0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ-_-;/?:@&=+$,_.!~*'()[]%");
    public static final Constant ALPHA = new Constant("abcdefghijklmnopqrstuvwxyz0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ-_");
    private String content;
    boolean[] contains = new boolean[128];
    boolean noASCII = false;

    private Constant(String content) {
        Arrays.fill(this.contains, false);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < content.length(); ++i) {
            int c = content.codePointAt(i);
            if (c < 128) {
                this.contains[c] = true;
                continue;
            }
            sb.appendCodePoint(c);
        }
        if (sb.length() > 0) {
            this.noASCII = true;
            this.content = sb.toString();
        }
    }

    public boolean has(int c) {
        return c < 128 ? this.contains[c] : this.noASCII && this.content.indexOf(c) != -1;
    }

    public boolean hasNo(int c) {
        return !this.has(c);
    }

    public boolean has(int c, String additional) {
        return this.has(c) || additional.indexOf(c) != -1;
    }

    public boolean hasNo(int c, String additional) {
        return !this.has(c, additional);
    }
}
