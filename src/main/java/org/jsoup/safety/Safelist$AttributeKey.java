package org.jsoup.safety;

import org.jsoup.safety.Safelist;

class Safelist$AttributeKey
extends Safelist.TypedValue {
    Safelist$AttributeKey(String value) {
        super(value);
    }

    static Safelist$AttributeKey valueOf(String value) {
        return new Safelist$AttributeKey(value);
    }
}
