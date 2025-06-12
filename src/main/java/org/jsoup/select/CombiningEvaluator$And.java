package org.jsoup.select;

import java.util.Arrays;
import java.util.Collection;
import org.jsoup.internal.StringUtil;
import org.jsoup.nodes.Element;
import org.jsoup.select.CombiningEvaluator;
import org.jsoup.select.Evaluator;

public final class CombiningEvaluator$And
extends CombiningEvaluator {
    CombiningEvaluator$And(Collection<Evaluator> evaluators) {
        super(evaluators);
    }

    CombiningEvaluator$And(Evaluator ... evaluators) {
        this(Arrays.asList(evaluators));
    }

    @Override
    public boolean matches(Element root, Element node) {
        for (int i = this.num - 1; i >= 0; --i) {
            Evaluator s = (Evaluator)this.evaluators.get(i);
            if (s.matches(root, node)) continue;
            return false;
        }
        return true;
    }

    public String toString() {
        return StringUtil.join(this.evaluators, "");
    }
}
