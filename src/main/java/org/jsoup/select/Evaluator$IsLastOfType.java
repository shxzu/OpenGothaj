package org.jsoup.select;

import org.jsoup.select.Evaluator;

public final class Evaluator$IsLastOfType
extends Evaluator.IsNthLastOfType {
    public Evaluator$IsLastOfType() {
        super(0, 1);
    }

    @Override
    public String toString() {
        return ":last-of-type";
    }
}
