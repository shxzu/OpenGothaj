package org.jsoup.parser;

import org.jsoup.parser.Token;

final class Token$Doctype
extends Token {
    final StringBuilder name = new StringBuilder();
    String pubSysKey = null;
    final StringBuilder publicIdentifier = new StringBuilder();
    final StringBuilder systemIdentifier = new StringBuilder();
    boolean forceQuirks = false;

    Token$Doctype() {
        super(null);
        this.type = Token.TokenType.Doctype;
    }

    @Override
    Token reset() {
        super.reset();
        Token$Doctype.reset(this.name);
        this.pubSysKey = null;
        Token$Doctype.reset(this.publicIdentifier);
        Token$Doctype.reset(this.systemIdentifier);
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
