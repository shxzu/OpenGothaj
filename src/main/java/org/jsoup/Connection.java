package org.jsoup;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.CookieStore;
import java.net.Proxy;
import java.net.URL;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;
import javax.net.ssl.SSLSocketFactory;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;

public interface Connection {
    public Connection newRequest();

    public Connection url(URL var1);

    public Connection url(String var1);

    public Connection proxy(@Nullable Proxy var1);

    public Connection proxy(String var1, int var2);

    public Connection userAgent(String var1);

    public Connection timeout(int var1);

    public Connection maxBodySize(int var1);

    public Connection referrer(String var1);

    public Connection followRedirects(boolean var1);

    public Connection method(Method var1);

    public Connection ignoreHttpErrors(boolean var1);

    public Connection ignoreContentType(boolean var1);

    public Connection sslSocketFactory(SSLSocketFactory var1);

    public Connection data(String var1, String var2);

    public Connection data(String var1, String var2, InputStream var3);

    public Connection data(String var1, String var2, InputStream var3, String var4);

    public Connection data(Collection<KeyVal> var1);

    public Connection data(Map<String, String> var1);

    public Connection data(String ... var1);

    @Nullable
    public KeyVal data(String var1);

    public Connection requestBody(String var1);

    public Connection header(String var1, String var2);

    public Connection headers(Map<String, String> var1);

    public Connection cookie(String var1, String var2);

    public Connection cookies(Map<String, String> var1);

    public Connection cookieStore(CookieStore var1);

    public CookieStore cookieStore();

    public Connection parser(Parser var1);

    public Connection postDataCharset(String var1);

    public Document get() throws IOException;

    public Document post() throws IOException;

    public Response execute() throws IOException;

    public Request request();

    public Connection request(Request var1);

    public Response response();

    public Connection response(Response var1);

    public static interface KeyVal {
        public KeyVal key(String var1);

        public String key();

        public KeyVal value(String var1);

        public String value();

        public KeyVal inputStream(InputStream var1);

        @Nullable
        public InputStream inputStream();

        public boolean hasInputStream();

        public KeyVal contentType(String var1);

        @Nullable
        public String contentType();
    }

    public static interface Response
    extends Base<Response> {
        public int statusCode();

        public String statusMessage();

        @Nullable
        public String charset();

        public Response charset(String var1);

        @Nullable
        public String contentType();

        public Document parse() throws IOException;

        public String body();

        public byte[] bodyAsBytes();

        public Response bufferUp();

        public BufferedInputStream bodyStream();
    }

    public static interface Request
    extends Base<Request> {
        @Nullable
        public Proxy proxy();

        public Request proxy(@Nullable Proxy var1);

        public Request proxy(String var1, int var2);

        public int timeout();

        public Request timeout(int var1);

        public int maxBodySize();

        public Request maxBodySize(int var1);

        public boolean followRedirects();

        public Request followRedirects(boolean var1);

        public boolean ignoreHttpErrors();

        public Request ignoreHttpErrors(boolean var1);

        public boolean ignoreContentType();

        public Request ignoreContentType(boolean var1);

        @Nullable
        public SSLSocketFactory sslSocketFactory();

        public void sslSocketFactory(SSLSocketFactory var1);

        public Request data(KeyVal var1);

        public Collection<KeyVal> data();

        public Request requestBody(@Nullable String var1);

        @Nullable
        public String requestBody();

        public Request parser(Parser var1);

        public Parser parser();

        public Request postDataCharset(String var1);

        public String postDataCharset();
    }

    public static interface Base<T extends Base<T>> {
        public URL url();

        public T url(URL var1);

        public Method method();

        public T method(Method var1);

        @Nullable
        public String header(String var1);

        public List<String> headers(String var1);

        public T header(String var1, String var2);

        public T addHeader(String var1, String var2);

        public boolean hasHeader(String var1);

        public boolean hasHeaderWithValue(String var1, String var2);

        public T removeHeader(String var1);

        public Map<String, String> headers();

        public Map<String, List<String>> multiHeaders();

        @Nullable
        public String cookie(String var1);

        public T cookie(String var1, String var2);

        public boolean hasCookie(String var1);

        public T removeCookie(String var1);

        public Map<String, String> cookies();
    }

    public static enum Method {
        GET(false),
        POST(true),
        PUT(true),
        DELETE(false),
        PATCH(true),
        HEAD(false),
        OPTIONS(false),
        TRACE(false);

        private final boolean hasBody;

        private Method(boolean hasBody) {
            this.hasBody = hasBody;
        }

        public final boolean hasBody() {
            return this.hasBody;
        }
    }
}
