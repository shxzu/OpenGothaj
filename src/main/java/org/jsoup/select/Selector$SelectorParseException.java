package org.jsoup.select;

public class Selector$SelectorParseException
extends IllegalStateException {
    public Selector$SelectorParseException(String msg) {
        super(msg);
    }

    public Selector$SelectorParseException(String msg, Object ... params) {
        super(String.format(msg, params));
    }
}
