package org.jsoup.select;

import org.jsoup.nodes.Node;

public interface NodeFilter {
    public FilterResult head(Node var1, int var2);

    default public FilterResult tail(Node node, int depth) {
        return FilterResult.CONTINUE;
    }

    public static enum FilterResult {
        CONTINUE,
        SKIP_CHILDREN,
        SKIP_ENTIRELY,
        REMOVE,
        STOP;

    }
}
