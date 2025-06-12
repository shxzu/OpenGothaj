package org.jsoup;

public enum Connection$Method {
    GET(false),
    POST(true),
    PUT(true),
    DELETE(false),
    PATCH(true),
    HEAD(false),
    OPTIONS(false),
    TRACE(false);

    private final boolean hasBody;

    private Connection$Method(boolean hasBody) {
        this.hasBody = hasBody;
    }

    public final boolean hasBody() {
        return this.hasBody;
    }
}
