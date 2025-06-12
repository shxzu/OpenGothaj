package org.jsoup.nodes;

import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Attributes;

class Attributes$Dataset
extends AbstractMap<String, String> {
    private final Attributes attributes;

    private Attributes$Dataset(Attributes attributes) {
        this.attributes = attributes;
    }

    @Override
    public Set<Map.Entry<String, String>> entrySet() {
        return new EntrySet();
    }

    @Override
    public String put(String key, String value) {
        String dataKey = Attributes.dataKey(key);
        String oldValue = this.attributes.hasKey(dataKey) ? this.attributes.get(dataKey) : null;
        this.attributes.put(dataKey, value);
        return oldValue;
    }

    private class EntrySet
    extends AbstractSet<Map.Entry<String, String>> {
        private EntrySet() {
        }

        @Override
        public Iterator<Map.Entry<String, String>> iterator() {
            return new DatasetIterator();
        }

        @Override
        public int size() {
            int count = 0;
            DatasetIterator iter = new DatasetIterator();
            while (iter.hasNext()) {
                ++count;
            }
            return count;
        }
    }

    private class DatasetIterator
    implements Iterator<Map.Entry<String, String>> {
        private Iterator<Attribute> attrIter;
        private Attribute attr;

        private DatasetIterator() {
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
}
