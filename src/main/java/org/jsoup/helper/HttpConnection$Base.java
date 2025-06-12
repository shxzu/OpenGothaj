package org.jsoup.helper;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;
import org.jsoup.Connection;
import org.jsoup.helper.HttpConnection;
import org.jsoup.helper.Validate;
import org.jsoup.internal.Normalizer;
import org.jsoup.internal.StringUtil;

abstract class HttpConnection$Base<T extends Connection.Base<T>>
implements Connection.Base<T> {
    private static final URL UnsetUrl;
    URL url = UnsetUrl;
    Connection.Method method = Connection.Method.GET;
    Map<String, List<String>> headers;
    Map<String, String> cookies;

    private HttpConnection$Base() {
        this.headers = new LinkedHashMap<String, List<String>>();
        this.cookies = new LinkedHashMap<String, String>();
    }

    private HttpConnection$Base(HttpConnection$Base<T> copy) {
        this.url = copy.url;
        this.method = copy.method;
        this.headers = new LinkedHashMap<String, List<String>>();
        for (Map.Entry<String, List<String>> entry : copy.headers.entrySet()) {
            this.headers.put(entry.getKey(), new ArrayList(entry.getValue()));
        }
        this.cookies = new LinkedHashMap<String, String>();
        this.cookies.putAll(copy.cookies);
    }

    @Override
    public URL url() {
        if (this.url == UnsetUrl) {
            throw new IllegalArgumentException("URL not set. Make sure to call #url(...) before executing the request.");
        }
        return this.url;
    }

    @Override
    public T url(URL url) {
        Validate.notNullParam(url, "url");
        this.url = HttpConnection.punyUrl(url);
        return (T)this;
    }

    @Override
    public Connection.Method method() {
        return this.method;
    }

    @Override
    public T method(Connection.Method method) {
        Validate.notNullParam((Object)method, "method");
        this.method = method;
        return (T)this;
    }

    @Override
    public String header(String name) {
        Validate.notNullParam(name, "name");
        List<String> vals = this.getHeadersCaseInsensitive(name);
        if (vals.size() > 0) {
            return StringUtil.join(vals, ", ");
        }
        return null;
    }

    @Override
    public T addHeader(String name, String value) {
        Validate.notEmptyParam(name, "name");
        value = value == null ? "" : value;
        List<String> values = this.headers(name);
        if (values.isEmpty()) {
            values = new ArrayList<String>();
            this.headers.put(name, values);
        }
        values.add(HttpConnection$Base.fixHeaderEncoding(value));
        return (T)this;
    }

    @Override
    public List<String> headers(String name) {
        Validate.notEmptyParam(name, "name");
        return this.getHeadersCaseInsensitive(name);
    }

    private static String fixHeaderEncoding(String val) {
        byte[] bytes = val.getBytes(ISO_8859_1);
        if (!HttpConnection$Base.looksLikeUtf8(bytes)) {
            return val;
        }
        return new String(bytes, UTF_8);
    }

    private static boolean looksLikeUtf8(byte[] input) {
        int i = 0;
        if (input.length >= 3 && (input[0] & 0xFF) == 239 && (input[1] & 0xFF) == 187 && (input[2] & 0xFF) == 191) {
            i = 3;
        }
        int j = input.length;
        while (i < j) {
            byte o = input[i];
            if ((o & 0x80) != 0) {
                int end;
                if ((o & 0xE0) == 192) {
                    end = i + 1;
                } else if ((o & 0xF0) == 224) {
                    end = i + 2;
                } else if ((o & 0xF8) == 240) {
                    end = i + 3;
                } else {
                    return false;
                }
                if (end >= input.length) {
                    return false;
                }
                while (i < end) {
                    if (((o = input[++i]) & 0xC0) == 128) continue;
                    return false;
                }
            }
            ++i;
        }
        return true;
    }

    @Override
    public T header(String name, String value) {
        Validate.notEmptyParam(name, "name");
        this.removeHeader(name);
        this.addHeader(name, value);
        return (T)this;
    }

    @Override
    public boolean hasHeader(String name) {
        Validate.notEmptyParam(name, "name");
        return !this.getHeadersCaseInsensitive(name).isEmpty();
    }

    @Override
    public boolean hasHeaderWithValue(String name, String value) {
        Validate.notEmpty(name);
        Validate.notEmpty(value);
        List<String> values = this.headers(name);
        for (String candidate : values) {
            if (!value.equalsIgnoreCase(candidate)) continue;
            return true;
        }
        return false;
    }

    @Override
    public T removeHeader(String name) {
        Validate.notEmptyParam(name, "name");
        Map.Entry<String, List<String>> entry = this.scanHeaders(name);
        if (entry != null) {
            this.headers.remove(entry.getKey());
        }
        return (T)this;
    }

    @Override
    public Map<String, String> headers() {
        LinkedHashMap<String, String> map = new LinkedHashMap<String, String>(this.headers.size());
        for (Map.Entry<String, List<String>> entry : this.headers.entrySet()) {
            String header = entry.getKey();
            List<String> values = entry.getValue();
            if (values.size() <= 0) continue;
            map.put(header, values.get(0));
        }
        return map;
    }

    @Override
    public Map<String, List<String>> multiHeaders() {
        return this.headers;
    }

    private List<String> getHeadersCaseInsensitive(String name) {
        Validate.notNull(name);
        for (Map.Entry<String, List<String>> entry : this.headers.entrySet()) {
            if (!name.equalsIgnoreCase(entry.getKey())) continue;
            return entry.getValue();
        }
        return Collections.emptyList();
    }

    @Nullable
    private Map.Entry<String, List<String>> scanHeaders(String name) {
        String lc = Normalizer.lowerCase(name);
        for (Map.Entry<String, List<String>> entry : this.headers.entrySet()) {
            if (!Normalizer.lowerCase(entry.getKey()).equals(lc)) continue;
            return entry;
        }
        return null;
    }

    @Override
    public String cookie(String name) {
        Validate.notEmptyParam(name, "name");
        return this.cookies.get(name);
    }

    @Override
    public T cookie(String name, String value) {
        Validate.notEmptyParam(name, "name");
        Validate.notNullParam(value, "value");
        this.cookies.put(name, value);
        return (T)this;
    }

    @Override
    public boolean hasCookie(String name) {
        Validate.notEmptyParam(name, "name");
        return this.cookies.containsKey(name);
    }

    @Override
    public T removeCookie(String name) {
        Validate.notEmptyParam(name, "name");
        this.cookies.remove(name);
        return (T)this;
    }

    @Override
    public Map<String, String> cookies() {
        return this.cookies;
    }

    static {
        try {
            UnsetUrl = new URL("http://undefined/");
        }
        catch (MalformedURLException e) {
            throw new IllegalStateException(e);
        }
    }
}
