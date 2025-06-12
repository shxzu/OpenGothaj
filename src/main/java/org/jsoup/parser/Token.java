package org.jsoup.parser;

import javax.annotation.Nullable;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Attributes;
import org.jsoup.parser.ParseSettings;

abstract class Token {
    TokenType type;
    static final int Unset = -1;
    private int startPos;
    private int endPos = -1;

    private Token() {
    }

    String tokenType() {
        return this.getClass().getSimpleName();
    }

    Token reset() {
        this.startPos = -1;
        this.endPos = -1;
        return this;
    }

    int startPos() {
        return this.startPos;
    }

    void startPos(int pos) {
        this.startPos = pos;
    }

    int endPos() {
        return this.endPos;
    }

    void endPos(int pos) {
        this.endPos = pos;
    }

    static void reset(StringBuilder sb) {
        if (sb != null) {
            sb.delete(0, sb.length());
        }
    }

    final boolean isDoctype() {
        return this.type == TokenType.Doctype;
    }

    final Doctype asDoctype() {
        return (Doctype)this;
    }

    final boolean isStartTag() {
        return this.type == TokenType.StartTag;
    }

    final StartTag asStartTag() {
        return (StartTag)this;
    }

    final boolean isEndTag() {
        return this.type == TokenType.EndTag;
    }

    final EndTag asEndTag() {
        return (EndTag)this;
    }

    final boolean isComment() {
        return this.type == TokenType.Comment;
    }

    final Comment asComment() {
        return (Comment)this;
    }

    final boolean isCharacter() {
        return this.type == TokenType.Character;
    }

    final boolean isCData() {
        return this instanceof CData;
    }

    final Character asCharacter() {
        return (Character)this;
    }

    final boolean isEOF() {
        return this.type == TokenType.EOF;
    }

    public static enum TokenType {
        Doctype,
        StartTag,
        EndTag,
        Comment,
        Character,
        EOF;

    }

    static final class Doctype
    extends Token {
        final StringBuilder name = new StringBuilder();
        String pubSysKey = null;
        final StringBuilder publicIdentifier = new StringBuilder();
        final StringBuilder systemIdentifier = new StringBuilder();
        boolean forceQuirks = false;

        Doctype() {
            this.type = TokenType.Doctype;
        }

        @Override
        Token reset() {
            super.reset();
            Doctype.reset(this.name);
            this.pubSysKey = null;
            Doctype.reset(this.publicIdentifier);
            Doctype.reset(this.systemIdentifier);
            this.forceQuirks = false;
            return this;
        }

        String getName() {
            return this.name.toString();
        }

        String getPubSysKey() {
            return this.pubSysKey;
        }

        String getPublicIdentifier() {
            return this.publicIdentifier.toString();
        }

        public String getSystemIdentifier() {
            return this.systemIdentifier.toString();
        }

        public boolean isForceQuirks() {
            return this.forceQuirks;
        }

        public String toString() {
            return "<!doctype " + this.getName() + ">";
        }
    }

    static final class StartTag
    extends Tag {
        StartTag() {
            this.type = TokenType.StartTag;
        }

        @Override
        Tag reset() {
            super.reset();
            this.attributes = null;
            return this;
        }

        StartTag nameAttr(String name, Attributes attributes) {
            this.tagName = name;
            this.attributes = attributes;
            this.normalName = ParseSettings.normalName(this.tagName);
            return this;
        }

        @Override
        public String toString() {
            if (this.hasAttributes() && this.attributes.size() > 0) {
                return "<" + this.toStringName() + " " + this.attributes.toString() + ">";
            }
            return "<" + this.toStringName() + ">";
        }
    }

    static final class EndTag
    extends Tag {
        EndTag() {
            this.type = TokenType.EndTag;
        }

        @Override
        public String toString() {
            return "</" + this.toStringName() + ">";
        }
    }

    static final class Comment
    extends Token {
        private final StringBuilder data = new StringBuilder();
        private String dataS;
        boolean bogus = false;

        @Override
        Token reset() {
            super.reset();
            Comment.reset(this.data);
            this.dataS = null;
            this.bogus = false;
            return this;
        }

        Comment() {
            this.type = TokenType.Comment;
        }

        String getData() {
            return this.dataS != null ? this.dataS : this.data.toString();
        }

        final Comment append(String append) {
            this.ensureData();
            if (this.data.length() == 0) {
                this.dataS = append;
            } else {
                this.data.append(append);
            }
            return this;
        }

        final Comment append(char append) {
            this.ensureData();
            this.data.append(append);
            return this;
        }

        private void ensureData() {
            if (this.dataS != null) {
                this.data.append(this.dataS);
                this.dataS = null;
            }
        }

        public String toString() {
            return "<!--" + this.getData() + "-->";
        }
    }

    static final class CData
    extends Character {
        CData(String data) {
            this.data(data);
        }

        @Override
        public String toString() {
            return "<![CDATA[" + this.getData() + "]]>";
        }
    }

    static class Character
    extends Token {
        private String data;

        Character() {
            this.type = TokenType.Character;
        }

        @Override
        Token reset() {
            super.reset();
            this.data = null;
            return this;
        }

        Character data(String data) {
            this.data = data;
            return this;
        }

        String getData() {
            return this.data;
        }

        public String toString() {
            return this.getData();
        }
    }

    static final class EOF
    extends Token {
        EOF() {
            this.type = TokenType.EOF;
        }

        @Override
        Token reset() {
            super.reset();
            return this;
        }

        public String toString() {
            return "";
        }
    }

    static abstract class Tag
    extends Token {
        @Nullable
        protected String tagName;
        @Nullable
        protected String normalName;
        private final StringBuilder attrName = new StringBuilder();
        @Nullable
        private String attrNameS;
        private boolean hasAttrName = false;
        private final StringBuilder attrValue = new StringBuilder();
        @Nullable
        private String attrValueS;
        private boolean hasAttrValue = false;
        private boolean hasEmptyAttrValue = false;
        boolean selfClosing = false;
        @Nullable
        Attributes attributes;
        private static final int MaxAttributes = 512;

        Tag() {
        }

        @Override
        Tag reset() {
            super.reset();
            this.tagName = null;
            this.normalName = null;
            Tag.reset(this.attrName);
            this.attrNameS = null;
            this.hasAttrName = false;
            Tag.reset(this.attrValue);
            this.attrValueS = null;
            this.hasEmptyAttrValue = false;
            this.hasAttrValue = false;
            this.selfClosing = false;
            this.attributes = null;
            return this;
        }

        final void newAttribute() {
            if (this.attributes == null) {
                this.attributes = new Attributes();
            }
            if (this.hasAttrName && this.attributes.size() < 512) {
                String name = this.attrName.length() > 0 ? this.attrName.toString() : this.attrNameS;
                if ((name = name.trim()).length() > 0) {
                    String value = this.hasAttrValue ? (this.attrValue.length() > 0 ? this.attrValue.toString() : this.attrValueS) : (this.hasEmptyAttrValue ? "" : null);
                    this.attributes.add(name, value);
                }
            }
            Tag.reset(this.attrName);
            this.attrNameS = null;
            this.hasAttrName = false;
            Tag.reset(this.attrValue);
            this.attrValueS = null;
            this.hasAttrValue = false;
            this.hasEmptyAttrValue = false;
        }

        final boolean hasAttributes() {
            return this.attributes != null;
        }

        final boolean hasAttribute(String key) {
            return this.attributes != null && this.attributes.hasKey(key);
        }

        final void finaliseTag() {
            if (this.hasAttrName) {
                this.newAttribute();
            }
        }

        final String name() {
            Validate.isFalse(this.tagName == null || this.tagName.length() == 0);
            return this.tagName;
        }

        final String normalName() {
            return this.normalName;
        }

        final String toStringName() {
            return this.tagName != null ? this.tagName : "[unset]";
        }

        final Tag name(String name) {
            this.tagName = name;
            this.normalName = ParseSettings.normalName(this.tagName);
            return this;
        }

        final boolean isSelfClosing() {
            return this.selfClosing;
        }

        final void appendTagName(String append) {
            append = append.replace('\u0000', '�');
            this.tagName = this.tagName == null ? append : this.tagName.concat(append);
            this.normalName = ParseSettings.normalName(this.tagName);
        }

        final void appendTagName(char append) {
            this.appendTagName(String.valueOf(append));
        }

        final void appendAttributeName(String append) {
            append = append.replace('\u0000', '�');
            this.ensureAttrName();
            if (this.attrName.length() == 0) {
                this.attrNameS = append;
            } else {
                this.attrName.append(append);
            }
        }

        final void appendAttributeName(char append) {
            this.ensureAttrName();
            this.attrName.append(append);
        }

        final void appendAttributeValue(String append) {
            this.ensureAttrValue();
            if (this.attrValue.length() == 0) {
                this.attrValueS = append;
            } else {
                this.attrValue.append(append);
            }
        }

        final void appendAttributeValue(char append) {
            this.ensureAttrValue();
            this.attrValue.append(append);
        }

        final void appendAttributeValue(char[] append) {
            this.ensureAttrValue();
            this.attrValue.append(append);
        }

        final void appendAttributeValue(int[] appendCodepoints) {
            this.ensureAttrValue();
            for (int codepoint : appendCodepoints) {
                this.attrValue.appendCodePoint(codepoint);
            }
        }

        final void setEmptyAttributeValue() {
            this.hasEmptyAttrValue = true;
        }

        private void ensureAttrName() {
            this.hasAttrName = true;
            if (this.attrNameS != null) {
                this.attrName.append(this.attrNameS);
                this.attrNameS = null;
            }
        }

        private void ensureAttrValue() {
            this.hasAttrValue = true;
            if (this.attrValueS != null) {
                this.attrValue.append(this.attrValueS);
                this.attrValueS = null;
            }
        }

        public abstract String toString();
    }
}
