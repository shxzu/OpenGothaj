package org.jsoup.safety;

import org.jsoup.safety.Safelist;

class Safelist$Protocol
extends Safelist.TypedValue {
    Safelist$Protocol(String value) {
        super(value);
    }

    static Safelist$Protocol valueOf(String value) {
        return new Safelist$Protocol(value);
    }
}
