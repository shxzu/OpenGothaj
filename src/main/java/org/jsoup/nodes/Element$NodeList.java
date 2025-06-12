package org.jsoup.nodes;

import org.jsoup.helper.ChangeNotifyingArrayList;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;

final class Element$NodeList
extends ChangeNotifyingArrayList<Node> {
    private final Element owner;

    Element$NodeList(Element owner, int initialCapacity) {
        super(initialCapacity);
        this.owner = owner;
    }

    @Override
    public void onContentsChanged() {
        this.owner.nodelistChanged();
    }
}
