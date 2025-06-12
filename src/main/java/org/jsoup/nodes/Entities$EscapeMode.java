package org.jsoup.nodes;

import java.util.Arrays;
import org.jsoup.nodes.Entities;
import org.jsoup.nodes.EntitiesData;

public enum Entities$EscapeMode {
    xhtml(EntitiesData.xmlPoints, 4),
    base(EntitiesData.basePoints, 106),
    extended(EntitiesData.fullPoints, 2125);

    private String[] nameKeys;
    private int[] codeVals;
    private int[] codeKeys;
    private String[] nameVals;

    private Entities$EscapeMode(String file, int size) {
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

    static String[] access$102(Entities$EscapeMode x0, String[] x1) {
        x0.nameKeys = x1;
        return x1;
    }

    static int[] access$202(Entities$EscapeMode x0, int[] x1) {
        x0.codeVals = x1;
        return x1;
    }

    static int[] access$302(Entities$EscapeMode x0, int[] x1) {
        x0.codeKeys = x1;
        return x1;
    }

    static String[] access$402(Entities$EscapeMode x0, String[] x1) {
        x0.nameVals = x1;
        return x1;
    }

    static String[] access$100(Entities$EscapeMode x0) {
        return x0.nameKeys;
    }

    static int[] access$200(Entities$EscapeMode x0) {
        return x0.codeVals;
    }

    static int[] access$300(Entities$EscapeMode x0) {
        return x0.codeKeys;
    }

    static String[] access$400(Entities$EscapeMode x0) {
        return x0.nameVals;
    }
}
