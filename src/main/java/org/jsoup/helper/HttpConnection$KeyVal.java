package org.jsoup.helper;

import java.io.InputStream;
import javax.annotation.Nullable;
import org.jsoup.Connection;
import org.jsoup.helper.Validate;

public class HttpConnection$KeyVal
implements Connection.KeyVal {
    private String key;
    private String value;
    @Nullable
    private InputStream stream;
    @Nullable
    private String contentType;

    public static HttpConnection$KeyVal create(String key, String value) {
        return new HttpConnection$KeyVal(key, value);
    }

    public static HttpConnection$KeyVal create(String key, String filename, InputStream stream) {
        return new HttpConnection$KeyVal(key, filename).inputStream(stream);
    }

    private HttpConnection$KeyVal(String key, String value) {
        Validate.notEmptyParam(key, "key");
        Validate.notNullParam(value, "value");
        this.key = key;
        this.value = value;
    }

    @Override
    public HttpConnection$KeyVal key(String key) {
        Validate.notEmptyParam(key, "key");
        this.key = key;
        return this;
    }

    @Override
    public String key() {
        return this.key;
    }

    @Override
    public HttpConnection$KeyVal value(String value) {
        Validate.notNullParam(value, "value");
        this.value = value;
        return this;
    }

    @Override
    public String value() {
        return this.value;
    }

    @Override
    public HttpConnection$KeyVal inputStream(InputStream inputStream) {
        Validate.notNullParam(this.value, "inputStream");
        this.stream = inputStream;
        return this;
    }

    @Override
    public InputStream inputStream() {
        return this.stream;
    }

    @Override
    public boolean hasInputStream() {
        return this.stream != null;
    }

    @Override
    public Connection.KeyVal contentType(String contentType) {
        Validate.notEmpty(contentType);
        this.contentType = contentType;
        return this;
    }

    @Override
    public String contentType() {
        return this.contentType;
    }

    public String toString() {
        return this.key + "=" + this.value;
    }
}
