package org.jsoup.nodes;

import java.util.Iterator;
import org.jsoup.nodes.Attribute;

class Attributes$1
implements Iterator<Attribute> {
    int i = 0;

    Attributes$1() {
    }

    @Override
    public boolean hasNext() {
        while (this.i < Attributes.this.size && Attributes.this.isInternalKey(Attributes.this.keys[this.i])) {
            ++this.i;
        }
        return this.i < Attributes.this.size;
    }

    @Override
    public Attribute next() {
        Attribute attr = new Attribute(Attributes.this.keys[this.i], (String)Attributes.this.vals[this.i], Attributes.this);
        ++this.i;
        return attr;
    }

    @Override
    public void remove() {
        Attributes.this.remove(--this.i);
    }
}
