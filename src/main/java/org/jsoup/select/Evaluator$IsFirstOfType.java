package org.jsoup.select;

import org.jsoup.select.Evaluator;

public final class Evaluator$IsFirstOfType
extends Evaluator.IsNthOfType {
    public Evaluator$IsFirstOfType() {
        super(0, 1);
    }

    @Override
    public String toString() {
        return ":first-of-type";
    }
}
