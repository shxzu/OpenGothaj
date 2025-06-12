package org.jsoup.nodes;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.regex.Pattern;
import javax.annotation.Nullable;
import org.jsoup.SerializationException;
import org.jsoup.helper.Validate;
import org.jsoup.internal.Normalizer;
import org.jsoup.internal.StringUtil;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Entities;

public class Attribute
implements Map.Entry<String, String>,
Cloneable {
    private static final String[] booleanAttributes = new String[]{"allowfullscreen", "async", "autofocus", "checked", "compact", "declare", "default", "defer", "disabled", "formnovalidate", "hidden", "inert", "ismap", "itemscope", "multiple", "muted", "nohref", "noresize", "noshade", "novalidate", "nowrap", "open", "readonly", "required", "reversed", "seamless", "selected", "sortable", "truespeed", "typemustmatch"};
    private String key;
    @Nullable
    private String val;
    @Nullable
    Attributes parent;
    private static final Pattern xmlKeyValid = Pattern.compile("[a-zA-Z_:][-a-zA-Z0-9_:.]*");
    private static final Pattern xmlKeyReplace = Pattern.compile("[^-a-zA-Z0-9_:.]");
    private static final Pattern htmlKeyValid = Pattern.compile("[^\\x00-\\x1f\\x7f-\\x9f \"'/=]+");
    private static final Pattern htmlKeyReplace = Pattern.compile("[\\x00-\\x1f\\x7f-\\x9f \"'/=]");

    public Attribute(String key, @Nullable String value) {
        this(key, value, null);
    }

    public Attribute(String key, @Nullable String val, @Nullable Attributes parent) {
        Validate.notNull(key);
        key = key.trim();
        Validate.notEmpty(key);
        this.key = key;
        this.val = val;
        this.parent = parent;
    }

    @Override
    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        int i;
        Validate.notNull(key);
        key = key.trim();
        Validate.notEmpty(key);
        if (this.parent != null && (i = this.parent.indexOfKey(this.key)) != -1) {
            this.parent.keys[i] = key;
        }
        this.key = key;
    }

    @Override
    public String getValue() {
        return Attributes.checkNotNull(this.val);
    }

    public boolean hasDeclaredValue() {
        return this.val != null;
    }

    @Override
    public String setValue(@Nullable String val) {
        int i;
        String oldVal = this.val;
        if (this.parent != null && (i = this.parent.indexOfKey(this.key)) != -1) {
            oldVal = this.parent.get(this.key);
            this.parent.vals[i] = val;
        }
        this.val = val;
        return Attributes.checkNotNull(oldVal);
    }

    public String html() {
        StringBuilder sb = StringUtil.borrowBuilder();
        try {
            this.html(sb, new Document("").outputSettings());
        }
        catch (IOException exception) {
            throw new SerializationException(exception);
        }
        return StringUtil.releaseBuilder(sb);
    }

    protected void html(Appendable accum, Document.OutputSettings out) throws IOException {
        Attribute.html(this.key, this.val, accum, out);
    }

    protected static void html(String key, @Nullable String val, Appendable accum, Document.OutputSettings out) throws IOException {
        if ((key = Attribute.getValidKey(key, out.syntax())) == null) {
            return;
        }
        Attribute.htmlNoValidate(key, val, accum, out);
    }

    static void htmlNoValidate(String key, @Nullable String val, Appendable accum, Document.OutputSettings out) throws IOException {
        accum.append(key);
        if (!Attribute.shouldCollapseAttribute(key, val, out)) {
            accum.append("=\"");
            Entities.escape(accum, Attributes.checkNotNull(val), out, true, false, false, false);
            accum.append('\"');
        }
    }

    @Nullable
    public static String getValidKey(String key, Document.OutputSettings.Syntax syntax) {
        if (syntax == Document.OutputSettings.Syntax.xml && !xmlKeyValid.matcher(key).matches()) {
            return xmlKeyValid.matcher(key = xmlKeyReplace.matcher(key).replaceAll("")).matches() ? key : null;
        }
        if (syntax == Document.OutputSettings.Syntax.html && !htmlKeyValid.matcher(key).matches()) {
            return htmlKeyValid.matcher(key = htmlKeyReplace.matcher(key).replaceAll("")).matches() ? key : null;
        }
        return key;
    }

    public String toString() {
        return this.html();
    }

    public static Attribute createFromEncoded(String unencodedKey, String encodedValue) {
        String value = Entities.unescape(encodedValue, true);
        return new Attribute(unencodedKey, value, null);
    }

    protected boolean isDataAttribute() {
        return Attribute.isDataAttribute(this.key);
    }

    protected static boolean isDataAttribute(String key) {
        return key.startsWith("data-") && key.length() > "data-".length();
    }

    protected final boolean shouldCollapseAttribute(Document.OutputSettings out) {
        return Attribute.shouldCollapseAttribute(this.key, this.val, out);
    }

    protected static boolean shouldCollapseAttribute(String key, @Nullable String val, Document.OutputSettings out) {
        return out.syntax() == Document.OutputSettings.Syntax.html && (val == null || (val.isEmpty() || val.equalsIgnoreCase(key)) && Attribute.isBooleanAttribute(key));
    }

    public static boolean isBooleanAttribute(String key) {
        return Arrays.binarySearch(booleanAttributes, Normalizer.lowerCase(key)) >= 0;
    }

    @Override
    public boolean equals(@Nullable Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        Attribute attribute = (Attribute)o;
        if (this.key != null ? !this.key.equals(attribute.key) : attribute.key != null) {
            return false;
        }
        return this.val != null ? this.val.equals(attribute.val) : attribute.val == null;
    }

    @Override
    public int hashCode() {
        int result = this.key != null ? this.key.hashCode() : 0;
        result = 31 * result + (this.val != null ? this.val.hashCode() : 0);
        return result;
    }

    public Attribute clone() {
        try {
            return (Attribute)super.clone();
        }
        catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }
}
