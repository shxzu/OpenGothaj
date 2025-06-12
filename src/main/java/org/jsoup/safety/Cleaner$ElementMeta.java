package org.jsoup.safety;

import org.jsoup.nodes.Element;

class Cleaner$ElementMeta {
    Element el;
    int numAttribsDiscarded;

    Cleaner$ElementMeta(Element el, int numAttribsDiscarded) {
        this.el = el;
        this.numAttribsDiscarded = numAttribsDiscarded;
    }
}
