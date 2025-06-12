package org.jsoup.select;

import org.jsoup.helper.Validate;
import org.jsoup.internal.Normalizer;
import org.jsoup.select.Evaluator;

public abstract class Evaluator$AttributeKeyPair
extends Evaluator {
    String key;
    String value;

    public Evaluator$AttributeKeyPair(String key, String value) {
        this(key, value, true);
    }

    public Evaluator$AttributeKeyPair(String key, String value, boolean trimValue) {
        boolean isStringLiteral;
        Validate.notEmpty(key);
        Validate.notEmpty(value);
        this.key = Normalizer.normalize(key);
        boolean bl = isStringLiteral = value.startsWith("'") && value.endsWith("'") || value.startsWith("\"") && value.endsWith("\"");
        if (isStringLiteral) {
            value = value.substring(1, value.length() - 1);
        }
        this.value = trimValue ? Normalizer.normalize(value) : Normalizer.normalize(value, isStringLiteral);
    }
}
