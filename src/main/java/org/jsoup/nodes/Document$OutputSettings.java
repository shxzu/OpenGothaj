package org.jsoup.nodes;

import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import javax.annotation.Nullable;
import org.jsoup.helper.DataUtil;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Entities;

public class Document$OutputSettings
implements Cloneable {
    private Entities.EscapeMode escapeMode = Entities.EscapeMode.base;
    private Charset charset = DataUtil.UTF_8;
    private final ThreadLocal<CharsetEncoder> encoderThreadLocal = new ThreadLocal();
    @Nullable
    Entities.CoreCharset coreCharset;
    private boolean prettyPrint = true;
    private boolean outline = false;
    private int indentAmount = 1;
    private int maxPaddingWidth = 30;
    private Syntax syntax = Syntax.html;

    public Entities.EscapeMode escapeMode() {
        return this.escapeMode;
    }

    public Document$OutputSettings escapeMode(Entities.EscapeMode escapeMode) {
        this.escapeMode = escapeMode;
        return this;
    }

    public Charset charset() {
        return this.charset;
    }

    public Document$OutputSettings charset(Charset charset) {
        this.charset = charset;
        return this;
    }

    public Document$OutputSettings charset(String charset) {
        this.charset(Charset.forName(charset));
        return this;
    }

    CharsetEncoder prepareEncoder() {
        CharsetEncoder encoder = this.charset.newEncoder();
        this.encoderThreadLocal.set(encoder);
        this.coreCharset = Entities.CoreCharset.byName(encoder.charset().name());
        return encoder;
    }

    CharsetEncoder encoder() {
        CharsetEncoder encoder = this.encoderThreadLocal.get();
        return encoder != null ? encoder : this.prepareEncoder();
    }

    public Syntax syntax() {
        return this.syntax;
    }

    public Document$OutputSettings syntax(Syntax syntax) {
        this.syntax = syntax;
        return this;
    }

    public boolean prettyPrint() {
        return this.prettyPrint;
    }

    public Document$OutputSettings prettyPrint(boolean pretty) {
        this.prettyPrint = pretty;
        return this;
    }

    public boolean outline() {
        return this.outline;
    }

    public Document$OutputSettings outline(boolean outlineMode) {
        this.outline = outlineMode;
        return this;
    }

    public int indentAmount() {
        return this.indentAmount;
    }

    public Document$OutputSettings indentAmount(int indentAmount) {
        Validate.isTrue(indentAmount >= 0);
        this.indentAmount = indentAmount;
        return this;
    }

    public int maxPaddingWidth() {
        return this.maxPaddingWidth;
    }

    public Document$OutputSettings maxPaddingWidth(int maxPaddingWidth) {
        Validate.isTrue(maxPaddingWidth >= -1);
        this.maxPaddingWidth = maxPaddingWidth;
        return this;
    }

    public Document$OutputSettings clone() {
        Document$OutputSettings clone;
        try {
            clone = (Document$OutputSettings)super.clone();
        }
        catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
        clone.charset(this.charset.name());
        clone.escapeMode = Entities.EscapeMode.valueOf(this.escapeMode.name());
        return clone;
    }

    public static enum Syntax {
        html,
        xml;

    }
}
