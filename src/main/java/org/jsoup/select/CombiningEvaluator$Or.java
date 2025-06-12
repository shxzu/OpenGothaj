package org.jsoup.select;

import java.util.Arrays;
import java.util.Collection;
import org.jsoup.internal.StringUtil;
import org.jsoup.nodes.Element;
import org.jsoup.select.CombiningEvaluator;
import org.jsoup.select.Evaluator;

public final class CombiningEvaluator$Or
extends CombiningEvaluator {
    CombiningEvaluator$Or(Collection<Evaluator> evaluators) {
        if (this.num > 1) {
            this.evaluators.add(new CombiningEvaluator.And(evaluators));
        } else {
            this.evaluators.addAll(evaluators);
        }
        this.updateNumEvaluators();
    }

    CombiningEvaluator$Or(Evaluator ... evaluators) {
        this(Arrays.asList(evaluators));
    }

    CombiningEvaluator$Or() {
    }

    public void add(Evaluator e) {
        this.evaluators.add(e);
        this.updateNumEvaluators();
    }

    @Override
    public boolean matches(Element root, Element node) {
        for (int i = 0; i < this.num; ++i) {
            Evaluator s = (Evaluator)this.evaluators.get(i);
            if (!s.matches(root, node)) continue;
            return true;
        }
        return false;
    }

    public String toString() {
        return StringUtil.join(this.evaluators, ", ");
    }
}
