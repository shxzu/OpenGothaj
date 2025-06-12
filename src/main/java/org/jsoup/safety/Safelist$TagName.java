package org.jsoup.safety;

import org.jsoup.safety.Safelist;

class Safelist$TagName
extends Safelist.TypedValue {
    Safelist$TagName(String value) {
        super(value);
    }

    static Safelist$TagName valueOf(String value) {
        return new Safelist$TagName(value);
    }
}
