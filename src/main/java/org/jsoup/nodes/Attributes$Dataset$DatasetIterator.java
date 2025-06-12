package org.jsoup.nodes;

import java.util.Iterator;
import java.util.Map;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Attributes;

class Attributes$Dataset$DatasetIterator
implements Iterator<Map.Entry<String, String>> {
    private Iterator<Attribute> attrIter;
    private Attribute attr;

    private Attributes$Dataset$DatasetIterator() {
        this.attrIter = Dataset.this.attributes.iterator();
    }

    @Override
    public boolean hasNext() {
        while (this.attrIter.hasNext()) {
            this.attr = this.attrIter.next();
            if (!this.attr.isDataAttribute()) continue;
            return true;
        }
        return false;
    }

    @Override
    public Map.Entry<String, String> next() {
        return new Attribute(this.attr.getKey().substring(Attributes.dataPrefix.length()), this.attr.getValue());
    }

    @Override
    public void remove() {
        Dataset.this.attributes.remove(this.attr.getKey());
    }
}
