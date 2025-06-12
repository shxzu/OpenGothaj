package org.jsoup.nodes;

enum Entities$CoreCharset {
    ascii,
    utf,
    fallback;


    static Entities$CoreCharset byName(String name) {
        if (name.equals("US-ASCII")) {
            return ascii;
        }
        if (name.startsWith("UTF-")) {
            return utf;
        }
        return fallback;
    }
}
