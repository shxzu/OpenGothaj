package org.jsoup.select;

import java.util.List;
import org.jsoup.nodes.Comment;
import org.jsoup.nodes.DocumentType;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.XmlDeclaration;
import org.jsoup.select.Evaluator;

public final class Evaluator$IsEmpty
extends Evaluator {
    @Override
    public boolean matches(Element root, Element element) {
        List<Node> family = element.childNodes();
        for (Node n : family) {
            if (n instanceof Comment || n instanceof XmlDeclaration || n instanceof DocumentType) continue;
            return false;
        }
        return true;
    }

    public String toString() {
        return ":empty";
    }
}
