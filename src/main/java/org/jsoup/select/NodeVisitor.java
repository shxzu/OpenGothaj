package org.jsoup.select;

import org.jsoup.nodes.Node;

public interface NodeVisitor {
    public void head(Node var1, int var2);

    default public void tail(Node node, int depth) {
    }
}
