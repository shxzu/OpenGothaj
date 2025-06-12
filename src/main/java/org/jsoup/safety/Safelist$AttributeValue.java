package org.jsoup.safety;

import org.jsoup.safety.Safelist;

class Safelist$AttributeValue
extends Safelist.TypedValue {
    Safelist$AttributeValue(String value) {
        super(value);
    }

    static Safelist$AttributeValue valueOf(String value) {
        return new Safelist$AttributeValue(value);
    }
}
