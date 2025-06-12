package org.jsoup.nodes;

import java.io.IOException;
import org.jsoup.helper.Validate;
import org.jsoup.internal.StringUtil;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.LeafNode;

public class DocumentType
extends LeafNode {
    public static final String PUBLIC_KEY = "PUBLIC";
    public static final String SYSTEM_KEY = "SYSTEM";
    private static final String NAME = "name";
    private static final String PUB_SYS_KEY = "pubSysKey";
    private static final String PUBLIC_ID = "publicId";
    private static final String SYSTEM_ID = "systemId";

    public DocumentType(String name, String publicId, String systemId) {
        Validate.notNull(name);
        Validate.notNull(publicId);
        Validate.notNull(systemId);
        this.attr(NAME, name);
        this.attr(PUBLIC_ID, publicId);
        this.attr(SYSTEM_ID, systemId);
        this.updatePubSyskey();
    }

    public void setPubSysKey(String value) {
        if (value != null) {
            this.attr(PUB_SYS_KEY, value);
        }
    }

    private void updatePubSyskey() {
        if (this.has(PUBLIC_ID)) {
            this.attr(PUB_SYS_KEY, PUBLIC_KEY);
        } else if (this.has(SYSTEM_ID)) {
            this.attr(PUB_SYS_KEY, SYSTEM_KEY);
        }
    }

    public String name() {
        return this.attr(NAME);
    }

    public String publicId() {
        return this.attr(PUBLIC_ID);
    }

    public String systemId() {
        return this.attr(SYSTEM_ID);
    }

    @Override
    public String nodeName() {
        return "#doctype";
    }

    @Override
    void outerHtmlHead(Appendable accum, int depth, Document.OutputSettings out) throws IOException {
        if (this.siblingIndex > 0 && out.prettyPrint()) {
            accum.append('\n');
        }
        if (out.syntax() == Document.OutputSettings.Syntax.html && !this.has(PUBLIC_ID) && !this.has(SYSTEM_ID)) {
            accum.append("<!doctype");
        } else {
            accum.append("<!DOCTYPE");
        }
        if (this.has(NAME)) {
            accum.append(" ").append(this.attr(NAME));
        }
        if (this.has(PUB_SYS_KEY)) {
            accum.append(" ").append(this.attr(PUB_SYS_KEY));
        }
        if (this.has(PUBLIC_ID)) {
            accum.append(" \"").append(this.attr(PUBLIC_ID)).append('\"');
        }
        if (this.has(SYSTEM_ID)) {
            accum.append(" \"").append(this.attr(SYSTEM_ID)).append('\"');
        }
        accum.append('>');
    }

    @Override
    void outerHtmlTail(Appendable accum, int depth, Document.OutputSettings out) {
    }

    private boolean has(String attribute) {
        return !StringUtil.isBlank(this.attr(attribute));
    }
}
