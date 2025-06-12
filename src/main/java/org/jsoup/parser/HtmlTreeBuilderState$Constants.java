package org.jsoup.parser;

final class HtmlTreeBuilderState$Constants {
    static final String[] InHeadEmpty = new String[]{"base", "basefont", "bgsound", "command", "link"};
    static final String[] InHeadRaw = new String[]{"noframes", "style"};
    static final String[] InHeadEnd = new String[]{"body", "br", "html"};
    static final String[] AfterHeadBody = new String[]{"body", "br", "html"};
    static final String[] BeforeHtmlToHead = new String[]{"body", "br", "head", "html"};
    static final String[] InHeadNoScriptHead = new String[]{"basefont", "bgsound", "link", "meta", "noframes", "style"};
    static final String[] InBodyStartToHead = new String[]{"base", "basefont", "bgsound", "command", "link", "meta", "noframes", "script", "style", "template", "title"};
    static final String[] InBodyStartPClosers = new String[]{"address", "article", "aside", "blockquote", "center", "details", "dir", "div", "dl", "fieldset", "figcaption", "figure", "footer", "header", "hgroup", "menu", "nav", "ol", "p", "section", "summary", "ul"};
    static final String[] Headings = new String[]{"h1", "h2", "h3", "h4", "h5", "h6"};
    static final String[] InBodyStartLiBreakers = new String[]{"address", "div", "p"};
    static final String[] DdDt = new String[]{"dd", "dt"};
    static final String[] InBodyStartApplets = new String[]{"applet", "marquee", "object"};
    static final String[] InBodyStartMedia = new String[]{"param", "source", "track"};
    static final String[] InBodyStartInputAttribs = new String[]{"action", "name", "prompt"};
    static final String[] InBodyStartDrop = new String[]{"caption", "col", "colgroup", "frame", "head", "tbody", "td", "tfoot", "th", "thead", "tr"};
    static final String[] InBodyEndClosers = new String[]{"address", "article", "aside", "blockquote", "button", "center", "details", "dir", "div", "dl", "fieldset", "figcaption", "figure", "footer", "header", "hgroup", "listing", "menu", "nav", "ol", "pre", "section", "summary", "ul"};
    static final String[] InBodyEndAdoptionFormatters = new String[]{"a", "b", "big", "code", "em", "font", "i", "nobr", "s", "small", "strike", "strong", "tt", "u"};
    static final String[] InBodyEndTableFosters = new String[]{"table", "tbody", "tfoot", "thead", "tr"};
    static final String[] InTableToBody = new String[]{"tbody", "tfoot", "thead"};
    static final String[] InTableAddBody = new String[]{"td", "th", "tr"};
    static final String[] InTableToHead = new String[]{"script", "style", "template"};
    static final String[] InCellNames = new String[]{"td", "th"};
    static final String[] InCellBody = new String[]{"body", "caption", "col", "colgroup", "html"};
    static final String[] InCellTable = new String[]{"table", "tbody", "tfoot", "thead", "tr"};
    static final String[] InCellCol = new String[]{"caption", "col", "colgroup", "tbody", "td", "tfoot", "th", "thead", "tr"};
    static final String[] InTableEndErr = new String[]{"body", "caption", "col", "colgroup", "html", "tbody", "td", "tfoot", "th", "thead", "tr"};
    static final String[] InTableFoster = new String[]{"table", "tbody", "tfoot", "thead", "tr"};
    static final String[] InTableBodyExit = new String[]{"caption", "col", "colgroup", "tbody", "tfoot", "thead"};
    static final String[] InTableBodyEndIgnore = new String[]{"body", "caption", "col", "colgroup", "html", "td", "th", "tr"};
    static final String[] InRowMissing = new String[]{"caption", "col", "colgroup", "tbody", "tfoot", "thead", "tr"};
    static final String[] InRowIgnore = new String[]{"body", "caption", "col", "colgroup", "html", "td", "th"};
    static final String[] InSelectEnd = new String[]{"input", "keygen", "textarea"};
    static final String[] InSelectTableEnd = new String[]{"caption", "table", "tbody", "td", "tfoot", "th", "thead", "tr"};
    static final String[] InTableEndIgnore = new String[]{"tbody", "tfoot", "thead"};
    static final String[] InHeadNoscriptIgnore = new String[]{"head", "noscript"};
    static final String[] InCaptionIgnore = new String[]{"body", "col", "colgroup", "html", "tbody", "td", "tfoot", "th", "thead", "tr"};
    static final String[] InTemplateToHead = new String[]{"base", "basefont", "bgsound", "link", "meta", "noframes", "script", "style", "template", "title"};
    static final String[] InTemplateToTable = new String[]{"caption", "colgroup", "tbody", "tfoot", "thead"};

    HtmlTreeBuilderState$Constants() {
    }
}
