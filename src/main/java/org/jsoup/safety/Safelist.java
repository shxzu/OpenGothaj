package org.jsoup.safety;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.jsoup.helper.Validate;
import org.jsoup.internal.Normalizer;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Element;

public class Safelist {
    private final Set<TagName> tagNames = new HashSet<TagName>();
    private final Map<TagName, Set<AttributeKey>> attributes = new HashMap<TagName, Set<AttributeKey>>();
    private final Map<TagName, Map<AttributeKey, AttributeValue>> enforcedAttributes = new HashMap<TagName, Map<AttributeKey, AttributeValue>>();
    private final Map<TagName, Map<AttributeKey, Set<Protocol>>> protocols = new HashMap<TagName, Map<AttributeKey, Set<Protocol>>>();
    private boolean preserveRelativeLinks = false;

    public static Safelist none() {
        return new Safelist();
    }

    public static Safelist simpleText() {
        return new Safelist().addTags("b", "em", "i", "strong", "u");
    }

    public static Safelist basic() {
        return new Safelist().addTags("a", "b", "blockquote", "br", "cite", "code", "dd", "dl", "dt", "em", "i", "li", "ol", "p", "pre", "q", "small", "span", "strike", "strong", "sub", "sup", "u", "ul").addAttributes("a", "href").addAttributes("blockquote", "cite").addAttributes("q", "cite").addProtocols("a", "href", "ftp", "http", "https", "mailto").addProtocols("blockquote", "cite", "http", "https").addProtocols("cite", "cite", "http", "https").addEnforcedAttribute("a", "rel", "nofollow");
    }

    public static Safelist basicWithImages() {
        return Safelist.basic().addTags("img").addAttributes("img", "align", "alt", "height", "src", "title", "width").addProtocols("img", "src", "http", "https");
    }

    public static Safelist relaxed() {
        return new Safelist().addTags("a", "b", "blockquote", "br", "caption", "cite", "code", "col", "colgroup", "dd", "div", "dl", "dt", "em", "h1", "h2", "h3", "h4", "h5", "h6", "i", "img", "li", "ol", "p", "pre", "q", "small", "span", "strike", "strong", "sub", "sup", "table", "tbody", "td", "tfoot", "th", "thead", "tr", "u", "ul").addAttributes("a", "href", "title").addAttributes("blockquote", "cite").addAttributes("col", "span", "width").addAttributes("colgroup", "span", "width").addAttributes("img", "align", "alt", "height", "src", "title", "width").addAttributes("ol", "start", "type").addAttributes("q", "cite").addAttributes("table", "summary", "width").addAttributes("td", "abbr", "axis", "colspan", "rowspan", "width").addAttributes("th", "abbr", "axis", "colspan", "rowspan", "scope", "width").addAttributes("ul", "type").addProtocols("a", "href", "ftp", "http", "https", "mailto").addProtocols("blockquote", "cite", "http", "https").addProtocols("cite", "cite", "http", "https").addProtocols("img", "src", "http", "https").addProtocols("q", "cite", "http", "https");
    }

    public Safelist() {
    }

    public Safelist(Safelist copy) {
        this();
        this.tagNames.addAll(copy.tagNames);
        for (Map.Entry<TagName, Set<AttributeKey>> entry : copy.attributes.entrySet()) {
            this.attributes.put(entry.getKey(), new HashSet(entry.getValue()));
        }
        for (Map.Entry<TagName, Object> entry : copy.enforcedAttributes.entrySet()) {
            this.enforcedAttributes.put(entry.getKey(), new HashMap((Map)entry.getValue()));
        }
        for (Map.Entry<TagName, Object> entry : copy.protocols.entrySet()) {
            HashMap attributeProtocolsCopy = new HashMap();
            for (Map.Entry attributeProtocols : ((Map)entry.getValue()).entrySet()) {
                attributeProtocolsCopy.put((AttributeKey)attributeProtocols.getKey(), new HashSet((Collection)attributeProtocols.getValue()));
            }
            this.protocols.put(entry.getKey(), attributeProtocolsCopy);
        }
        this.preserveRelativeLinks = copy.preserveRelativeLinks;
    }

    public Safelist addTags(String ... tags) {
        Validate.notNull(tags);
        for (String tagName : tags) {
            Validate.notEmpty(tagName);
            this.tagNames.add(TagName.valueOf(tagName));
        }
        return this;
    }

    public Safelist removeTags(String ... tags) {
        Validate.notNull(tags);
        for (String tag : tags) {
            Validate.notEmpty(tag);
            TagName tagName = TagName.valueOf(tag);
            if (!this.tagNames.remove(tagName)) continue;
            this.attributes.remove(tagName);
            this.enforcedAttributes.remove(tagName);
            this.protocols.remove(tagName);
        }
        return this;
    }

    public Safelist addAttributes(String tag, String ... attributes) {
        Validate.notEmpty(tag);
        Validate.notNull(attributes);
        Validate.isTrue(attributes.length > 0, "No attribute names supplied.");
        TagName tagName = TagName.valueOf(tag);
        this.tagNames.add(tagName);
        HashSet<AttributeKey> attributeSet = new HashSet<AttributeKey>();
        for (String key : attributes) {
            Validate.notEmpty(key);
            attributeSet.add(AttributeKey.valueOf(key));
        }
        if (this.attributes.containsKey(tagName)) {
            Set<AttributeKey> currentSet = this.attributes.get(tagName);
            currentSet.addAll(attributeSet);
        } else {
            this.attributes.put(tagName, attributeSet);
        }
        return this;
    }

    public Safelist removeAttributes(String tag, String ... attributes) {
        Validate.notEmpty(tag);
        Validate.notNull(attributes);
        Validate.isTrue(attributes.length > 0, "No attribute names supplied.");
        TagName tagName = TagName.valueOf(tag);
        HashSet<AttributeKey> attributeSet = new HashSet<AttributeKey>();
        for (String key : attributes) {
            Validate.notEmpty(key);
            attributeSet.add(AttributeKey.valueOf(key));
        }
        if (this.tagNames.contains(tagName) && this.attributes.containsKey(tagName)) {
            Set<AttributeKey> currentSet = this.attributes.get(tagName);
            currentSet.removeAll(attributeSet);
            if (currentSet.isEmpty()) {
                this.attributes.remove(tagName);
            }
        }
        if (tag.equals(":all")) {
            for (TagName name : this.attributes.keySet()) {
                Set<AttributeKey> currentSet = this.attributes.get(name);
                currentSet.removeAll(attributeSet);
                if (!currentSet.isEmpty()) continue;
                this.attributes.remove(name);
            }
        }
        return this;
    }

    public Safelist addEnforcedAttribute(String tag, String attribute, String value) {
        Validate.notEmpty(tag);
        Validate.notEmpty(attribute);
        Validate.notEmpty(value);
        TagName tagName = TagName.valueOf(tag);
        this.tagNames.add(tagName);
        AttributeKey attrKey = AttributeKey.valueOf(attribute);
        AttributeValue attrVal = AttributeValue.valueOf(value);
        if (this.enforcedAttributes.containsKey(tagName)) {
            this.enforcedAttributes.get(tagName).put(attrKey, attrVal);
        } else {
            HashMap<AttributeKey, AttributeValue> attrMap = new HashMap<AttributeKey, AttributeValue>();
            attrMap.put(attrKey, attrVal);
            this.enforcedAttributes.put(tagName, attrMap);
        }
        return this;
    }

    public Safelist removeEnforcedAttribute(String tag, String attribute) {
        Validate.notEmpty(tag);
        Validate.notEmpty(attribute);
        TagName tagName = TagName.valueOf(tag);
        if (this.tagNames.contains(tagName) && this.enforcedAttributes.containsKey(tagName)) {
            AttributeKey attrKey = AttributeKey.valueOf(attribute);
            Map<AttributeKey, AttributeValue> attrMap = this.enforcedAttributes.get(tagName);
            attrMap.remove(attrKey);
            if (attrMap.isEmpty()) {
                this.enforcedAttributes.remove(tagName);
            }
        }
        return this;
    }

    public Safelist preserveRelativeLinks(boolean preserve) {
        this.preserveRelativeLinks = preserve;
        return this;
    }

    public Safelist addProtocols(String tag, String attribute, String ... protocols) {
        Set<Protocol> protSet;
        Map<Object, Object> attrMap;
        Validate.notEmpty(tag);
        Validate.notEmpty(attribute);
        Validate.notNull(protocols);
        TagName tagName = TagName.valueOf(tag);
        AttributeKey attrKey = AttributeKey.valueOf(attribute);
        if (this.protocols.containsKey(tagName)) {
            attrMap = this.protocols.get(tagName);
        } else {
            attrMap = new HashMap();
            this.protocols.put(tagName, attrMap);
        }
        if (attrMap.containsKey(attrKey)) {
            protSet = (Set)attrMap.get(attrKey);
        } else {
            protSet = new HashSet();
            attrMap.put(attrKey, protSet);
        }
        for (String protocol : protocols) {
            Validate.notEmpty(protocol);
            Protocol prot = Protocol.valueOf(protocol);
            protSet.add(prot);
        }
        return this;
    }

    public Safelist removeProtocols(String tag, String attribute, String ... removeProtocols) {
        Validate.notEmpty(tag);
        Validate.notEmpty(attribute);
        Validate.notNull(removeProtocols);
        TagName tagName = TagName.valueOf(tag);
        AttributeKey attr = AttributeKey.valueOf(attribute);
        Validate.isTrue(this.protocols.containsKey(tagName), "Cannot remove a protocol that is not set.");
        Map<AttributeKey, Set<Protocol>> tagProtocols = this.protocols.get(tagName);
        Validate.isTrue(tagProtocols.containsKey(attr), "Cannot remove a protocol that is not set.");
        Set<Protocol> attrProtocols = tagProtocols.get(attr);
        for (String protocol : removeProtocols) {
            Validate.notEmpty(protocol);
            attrProtocols.remove(Protocol.valueOf(protocol));
        }
        if (attrProtocols.isEmpty()) {
            tagProtocols.remove(attr);
            if (tagProtocols.isEmpty()) {
                this.protocols.remove(tagName);
            }
        }
        return this;
    }

    protected boolean isSafeTag(String tag) {
        return this.tagNames.contains(TagName.valueOf(tag));
    }

    protected boolean isSafeAttribute(String tagName, Element el, Attribute attr) {
        String attrKey;
        Attributes expect;
        TagName tag = TagName.valueOf(tagName);
        AttributeKey key = AttributeKey.valueOf(attr.getKey());
        Set<AttributeKey> okSet = this.attributes.get(tag);
        if (okSet != null && okSet.contains(key)) {
            if (this.protocols.containsKey(tag)) {
                Map<AttributeKey, Set<Protocol>> attrProts = this.protocols.get(tag);
                return !attrProts.containsKey(key) || this.testValidProtocol(el, attr, attrProts.get(key));
            }
            return true;
        }
        Map<AttributeKey, AttributeValue> enforcedSet = this.enforcedAttributes.get(tag);
        if (enforcedSet != null && (expect = this.getEnforcedAttributes(tagName)).hasKeyIgnoreCase(attrKey = attr.getKey())) {
            return expect.getIgnoreCase(attrKey).equals(attr.getValue());
        }
        return !tagName.equals(":all") && this.isSafeAttribute(":all", el, attr);
    }

    private boolean testValidProtocol(Element el, Attribute attr, Set<Protocol> protocols) {
        String value = el.absUrl(attr.getKey());
        if (value.length() == 0) {
            value = attr.getValue();
        }
        if (!this.preserveRelativeLinks) {
            attr.setValue(value);
        }
        for (Protocol protocol : protocols) {
            String prot = protocol.toString();
            if (prot.equals("#")) {
                if (!this.isValidAnchor(value)) continue;
                return true;
            }
            prot = prot + ":";
            if (!Normalizer.lowerCase(value).startsWith(prot)) continue;
            return true;
        }
        return false;
    }

    private boolean isValidAnchor(String value) {
        return value.startsWith("#") && !value.matches(".*\\s.*");
    }

    Attributes getEnforcedAttributes(String tagName) {
        Attributes attrs = new Attributes();
        TagName tag = TagName.valueOf(tagName);
        if (this.enforcedAttributes.containsKey(tag)) {
            Map<AttributeKey, AttributeValue> keyVals = this.enforcedAttributes.get(tag);
            for (Map.Entry<AttributeKey, AttributeValue> entry : keyVals.entrySet()) {
                attrs.put(entry.getKey().toString(), entry.getValue().toString());
            }
        }
        return attrs;
    }

    static class TagName
    extends TypedValue {
        TagName(String value) {
            super(value);
        }

        static TagName valueOf(String value) {
            return new TagName(value);
        }
    }

    static class AttributeKey
    extends TypedValue {
        AttributeKey(String value) {
            super(value);
        }

        static AttributeKey valueOf(String value) {
            return new AttributeKey(value);
        }
    }

    static class AttributeValue
    extends TypedValue {
        AttributeValue(String value) {
            super(value);
        }

        static AttributeValue valueOf(String value) {
            return new AttributeValue(value);
        }
    }

    static class Protocol
    extends TypedValue {
        Protocol(String value) {
            super(value);
        }

        static Protocol valueOf(String value) {
            return new Protocol(value);
        }
    }

    static abstract class TypedValue {
        private final String value;

        TypedValue(String value) {
            Validate.notNull(value);
            this.value = value;
        }

        public int hashCode() {
            int prime = 31;
            int result = 1;
            result = 31 * result + (this.value == null ? 0 : this.value.hashCode());
            return result;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (this.getClass() != obj.getClass()) {
                return false;
            }
            TypedValue other = (TypedValue)obj;
            if (this.value == null) {
                return other.value == null;
            }
            return this.value.equals(other.value);
        }

        public String toString() {
            return this.value;
        }
    }
}
