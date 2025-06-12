package org.jsoup.helper;

class DataUtil$BomCharset {
    private final String charset;
    private final boolean offset;

    public DataUtil$BomCharset(String charset, boolean offset) {
        this.charset = charset;
        this.offset = offset;
    }

    static String access$000(DataUtil$BomCharset x0) {
        return x0.charset;
    }

    static boolean access$100(DataUtil$BomCharset x0) {
        return x0.offset;
    }
}
