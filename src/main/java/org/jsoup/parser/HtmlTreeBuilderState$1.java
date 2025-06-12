package org.jsoup.parser;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.DocumentType;
import org.jsoup.parser.HtmlTreeBuilder;
import org.jsoup.parser.HtmlTreeBuilderState;
import org.jsoup.parser.Token;

final class HtmlTreeBuilderState$1
extends HtmlTreeBuilderState {
    @Override
    boolean process(Token t, HtmlTreeBuilder tb) {
        if (HtmlTreeBuilderState.isWhitespace(t)) {
            return true;
        }
        if (t.isComment()) {
            tb.insert(t.asComment());
        } else if (t.isDoctype()) {
            Token.Doctype d = t.asDoctype();
            DocumentType doctype = new DocumentType(tb.settings.normalizeTag(d.getName()), d.getPublicIdentifier(), d.getSystemIdentifier());
            doctype.setPubSysKey(d.getPubSysKey());
            tb.getDocument().appendChild(doctype);
            tb.onNodeInserted(doctype, t);
            if (d.isForceQuirks()) {
                tb.getDocument().quirksMode(Document.QuirksMode.quirks);
            }
            tb.transition(BeforeHtml);
        } else {
            tb.transition(BeforeHtml);
            return tb.process(t);
        }
        return true;
    }
}
