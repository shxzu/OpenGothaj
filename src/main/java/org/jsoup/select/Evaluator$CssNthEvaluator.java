package org.jsoup.select;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Evaluator;

public abstract class Evaluator$CssNthEvaluator
extends Evaluator {
    protected final int a;
    protected final int b;

    public Evaluator$CssNthEvaluator(int a, int b) {
        this.a = a;
        this.b = b;
    }

    public Evaluator$CssNthEvaluator(int b) {
        this(0, b);
    }

    @Override
    public boolean matches(Element root, Element element) {
        Element p = element.parent();
        if (p == null || p instanceof Document) {
            return false;
        }
        int pos = this.calculatePosition(root, element);
        if (this.a == 0) {
            return pos == this.b;
        }
        return (pos - this.b) * this.a >= 0 && (pos - this.b) % this.a == 0;
    }

    public String toString() {
        if (this.a == 0) {
            return String.format(":%s(%d)", this.getPseudoClass(), this.b);
        }
        if (this.b == 0) {
            return String.format(":%s(%dn)", this.getPseudoClass(), this.a);
        }
        return String.format(":%s(%dn%+d)", this.getPseudoClass(), this.a, this.b);
    }

    protected abstract String getPseudoClass();

    protected abstract int calculatePosition(Element var1, Element var2);
}
