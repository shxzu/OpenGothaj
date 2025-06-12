package org.jsoup.select;

import java.util.List;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.PseudoTextElement;
import org.jsoup.nodes.TextNode;
import org.jsoup.parser.Tag;
import org.jsoup.select.Evaluator;

public final class Evaluator$MatchText
extends Evaluator {
    @Override
    public boolean matches(Element root, Element element) {
        if (element instanceof PseudoTextElement) {
            return true;
        }
        List<TextNode> textNodes = element.textNodes();
        for (TextNode textNode : textNodes) {
            PseudoTextElement pel = new PseudoTextElement(Tag.valueOf(element.tagName()), element.baseUri(), element.attributes());
            textNode.replaceWith(pel);
            pel.appendChild(textNode);
        }
        return false;
    }

    public String toString() {
        return ":matchText";
    }
}
