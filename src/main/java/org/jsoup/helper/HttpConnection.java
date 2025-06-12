package org.jsoup.helper;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.CookieManager;
import java.net.CookieStore;
import java.net.HttpURLConnection;
import java.net.IDN;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.IllegalCharsetNameException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;
import javax.annotation.Nullable;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSocketFactory;
import org.jsoup.Connection;
import org.jsoup.HttpStatusException;
import org.jsoup.UncheckedIOException;
import org.jsoup.UnsupportedMimeTypeException;
import org.jsoup.helper.CookieUtil;
import org.jsoup.helper.DataUtil;
import org.jsoup.helper.Validate;
import org.jsoup.internal.ConstrainableInputStream;
import org.jsoup.internal.Normalizer;
import org.jsoup.internal.StringUtil;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;
import org.jsoup.parser.TokenQueue;

public class HttpConnection
implements Connection {
    public static final String CONTENT_ENCODING = "Content-Encoding";
    public static final String DEFAULT_UA = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.130 Safari/537.36";
    private static final String USER_AGENT = "User-Agent";
    public static final String CONTENT_TYPE = "Content-Type";
    public static final String MULTIPART_FORM_DATA = "multipart/form-data";
    public static final String FORM_URL_ENCODED = "application/x-www-form-urlencoded";
    private static final int HTTP_TEMP_REDIR = 307;
    private static final String DefaultUploadType = "application/octet-stream";
    private static final Charset UTF_8 = Charset.forName("UTF-8");
    private static final Charset ISO_8859_1 = Charset.forName("ISO-8859-1");
    private Request req;
    @Nullable
    private Connection.Response res;

    public static Connection connect(String url) {
        HttpConnection con = new HttpConnection();
        con.url(url);
        return con;
    }

    public static Connection connect(URL url) {
        HttpConnection con = new HttpConnection();
        con.url(url);
        return con;
    }

    public HttpConnection() {
        this.req = new Request();
    }

    HttpConnection(Request copy) {
        this.req = new Request(copy);
    }

    private static String encodeUrl(String url) {
        try {
            URL u = new URL(url);
            return HttpConnection.encodeUrl(u).toExternalForm();
        }
        catch (Exception e) {
            return url;
        }
    }

    static URL encodeUrl(URL u) {
        u = HttpConnection.punyUrl(u);
        try {
            String urlS = u.toExternalForm();
            urlS = urlS.replace(" ", "%20");
            URI uri = new URI(urlS);
            return new URL(uri.toASCIIString());
        }
        catch (MalformedURLException | URISyntaxException e) {
            return u;
        }
    }

    private static URL punyUrl(URL url) {
        if (!StringUtil.isAscii(url.getHost())) {
            try {
                String puny = IDN.toASCII(url.getHost());
                url = new URL(url.getProtocol(), puny, url.getPort(), url.getFile());
            }
            catch (MalformedURLException e) {
                throw new IllegalArgumentException(e);
            }
        }
        return url;
    }

    private static String encodeMimeName(String val) {
        return val.replace("\"", "%22");
    }

    @Override
    public Connection newRequest() {
        return new HttpConnection(this.req);
    }

    private HttpConnection(Request req, Response res) {
        this.req = req;
        this.res = res;
    }

    @Override
    public Connection url(URL url) {
        this.req.url(url);
        return this;
    }

    @Override
    public Connection url(String url) {
        Validate.notEmptyParam(url, "url");
        try {
            this.req.url(new URL(HttpConnection.encodeUrl(url)));
        }
        catch (MalformedURLException e) {
            throw new IllegalArgumentException(String.format("The supplied URL, '%s', is malformed. Make sure it is an absolute URL, and starts with 'http://' or 'https://'. See https://jsoup.org/cookbook/extracting-data/working-with-urls", url), e);
        }
        return this;
    }

    @Override
    public Connection proxy(@Nullable Proxy proxy) {
        this.req.proxy(proxy);
        return this;
    }

    @Override
    public Connection proxy(String host, int port) {
        this.req.proxy(host, port);
        return this;
    }

    @Override
    public Connection userAgent(String userAgent) {
        Validate.notNullParam(userAgent, "userAgent");
        this.req.header(USER_AGENT, userAgent);
        return this;
    }

    @Override
    public Connection timeout(int millis) {
        this.req.timeout(millis);
        return this;
    }

    @Override
    public Connection maxBodySize(int bytes) {
        this.req.maxBodySize(bytes);
        return this;
    }

    @Override
    public Connection followRedirects(boolean followRedirects) {
        this.req.followRedirects(followRedirects);
        return this;
    }

    @Override
    public Connection referrer(String referrer) {
        Validate.notNullParam(referrer, "referrer");
        this.req.header("Referer", referrer);
        return this;
    }

    @Override
    public Connection method(Connection.Method method) {
        this.req.method(method);
        return this;
    }

    @Override
    public Connection ignoreHttpErrors(boolean ignoreHttpErrors) {
        this.req.ignoreHttpErrors(ignoreHttpErrors);
        return this;
    }

    @Override
    public Connection ignoreContentType(boolean ignoreContentType) {
        this.req.ignoreContentType(ignoreContentType);
        return this;
    }

    @Override
    public Connection data(String key, String value) {
        this.req.data(KeyVal.create(key, value));
        return this;
    }

    @Override
    public Connection sslSocketFactory(SSLSocketFactory sslSocketFactory) {
        this.req.sslSocketFactory(sslSocketFactory);
        return this;
    }

    @Override
    public Connection data(String key, String filename, InputStream inputStream) {
        this.req.data(KeyVal.create(key, filename, inputStream));
        return this;
    }

    @Override
    public Connection data(String key, String filename, InputStream inputStream, String contentType) {
        this.req.data(KeyVal.create(key, filename, inputStream).contentType(contentType));
        return this;
    }

    @Override
    public Connection data(Map<String, String> data) {
        Validate.notNullParam(data, "data");
        for (Map.Entry<String, String> entry : data.entrySet()) {
            this.req.data(KeyVal.create(entry.getKey(), entry.getValue()));
        }
        return this;
    }

    @Override
    public Connection data(String ... keyvals) {
        Validate.notNullParam(keyvals, "keyvals");
        Validate.isTrue(keyvals.length % 2 == 0, "Must supply an even number of key value pairs");
        for (int i = 0; i < keyvals.length; i += 2) {
            String key = keyvals[i];
            String value = keyvals[i + 1];
            Validate.notEmpty(key, "Data key must not be empty");
            Validate.notNull(value, "Data value must not be null");
            this.req.data(KeyVal.create(key, value));
        }
        return this;
    }

    @Override
    public Connection data(Collection<Connection.KeyVal> data) {
        Validate.notNullParam(data, "data");
        for (Connection.KeyVal entry : data) {
            this.req.data(entry);
        }
        return this;
    }

    @Override
    public Connection.KeyVal data(String key) {
        Validate.notEmptyParam(key, "key");
        for (Connection.KeyVal keyVal : this.request().data()) {
            if (!keyVal.key().equals(key)) continue;
            return keyVal;
        }
        return null;
    }

    @Override
    public Connection requestBody(String body) {
        this.req.requestBody(body);
        return this;
    }

    @Override
    public Connection header(String name, String value) {
        this.req.header(name, value);
        return this;
    }

    @Override
    public Connection headers(Map<String, String> headers) {
        Validate.notNullParam(headers, "headers");
        for (Map.Entry<String, String> entry : headers.entrySet()) {
            this.req.header(entry.getKey(), entry.getValue());
        }
        return this;
    }

    @Override
    public Connection cookie(String name, String value) {
        this.req.cookie(name, value);
        return this;
    }

    @Override
    public Connection cookies(Map<String, String> cookies) {
        Validate.notNullParam(cookies, "cookies");
        for (Map.Entry<String, String> entry : cookies.entrySet()) {
            this.req.cookie(entry.getKey(), entry.getValue());
        }
        return this;
    }

    @Override
    public Connection cookieStore(CookieStore cookieStore) {
        this.req.cookieManager = new CookieManager(cookieStore, null);
        return this;
    }

    @Override
    public CookieStore cookieStore() {
        return this.req.cookieManager.getCookieStore();
    }

    @Override
    public Connection parser(Parser parser) {
        this.req.parser(parser);
        return this;
    }

    @Override
    public Document get() throws IOException {
        this.req.method(Connection.Method.GET);
        this.execute();
        Validate.notNull(this.res);
        return this.res.parse();
    }

    @Override
    public Document post() throws IOException {
        this.req.method(Connection.Method.POST);
        this.execute();
        Validate.notNull(this.res);
        return this.res.parse();
    }

    @Override
    public Connection.Response execute() throws IOException {
        this.res = Response.execute(this.req);
        return this.res;
    }

    @Override
    public Connection.Request request() {
        return this.req;
    }

    @Override
    public Connection request(Connection.Request request) {
        this.req = (Request)request;
        return this;
    }

    @Override
    public Connection.Response response() {
        if (this.res == null) {
            throw new IllegalArgumentException("You must execute the request before getting a response.");
        }
        return this.res;
    }

    @Override
    public Connection response(Connection.Response response) {
        this.res = response;
        return this;
    }

    @Override
    public Connection postDataCharset(String charset) {
        this.req.postDataCharset(charset);
        return this;
    }

    private static boolean needsMultipart(Connection.Request req) {
        for (Connection.KeyVal keyVal : req.data()) {
            if (!keyVal.hasInputStream()) continue;
            return true;
        }
        return false;
    }

    public static class Request
    extends Base<Connection.Request>
    implements Connection.Request {
        @Nullable
        private Proxy proxy;
        private int timeoutMilliseconds;
        private int maxBodySizeBytes;
        private boolean followRedirects;
        private final Collection<Connection.KeyVal> data;
        @Nullable
        private String body = null;
        private boolean ignoreHttpErrors = false;
        private boolean ignoreContentType = false;
        private Parser parser;
        private boolean parserDefined = false;
        private String postDataCharset = DataUtil.defaultCharsetName;
        @Nullable
        private SSLSocketFactory sslSocketFactory;
        private CookieManager cookieManager;
        private volatile boolean executing = false;

        Request() {
            this.timeoutMilliseconds = 30000;
            this.maxBodySizeBytes = 0x200000;
            this.followRedirects = true;
            this.data = new ArrayList<Connection.KeyVal>();
            this.method = Connection.Method.GET;
            this.addHeader("Accept-Encoding", "gzip");
            this.addHeader(HttpConnection.USER_AGENT, HttpConnection.DEFAULT_UA);
            this.parser = Parser.htmlParser();
            this.cookieManager = new CookieManager();
        }

        Request(Request copy) {
            super(copy);
            this.proxy = copy.proxy;
            this.postDataCharset = copy.postDataCharset;
            this.timeoutMilliseconds = copy.timeoutMilliseconds;
            this.maxBodySizeBytes = copy.maxBodySizeBytes;
            this.followRedirects = copy.followRedirects;
            this.data = new ArrayList<Connection.KeyVal>();
            this.data.addAll(copy.data());
            this.body = copy.body;
            this.ignoreHttpErrors = copy.ignoreHttpErrors;
            this.ignoreContentType = copy.ignoreContentType;
            this.parser = copy.parser.newInstance();
            this.parserDefined = copy.parserDefined;
            this.sslSocketFactory = copy.sslSocketFactory;
            this.cookieManager = copy.cookieManager;
            this.executing = false;
        }

        @Override
        public Proxy proxy() {
            return this.proxy;
        }

        @Override
        public Request proxy(@Nullable Proxy proxy) {
            this.proxy = proxy;
            return this;
        }

        @Override
        public Request proxy(String host, int port) {
            this.proxy = new Proxy(Proxy.Type.HTTP, InetSocketAddress.createUnresolved(host, port));
            return this;
        }

        @Override
        public int timeout() {
            return this.timeoutMilliseconds;
        }

        @Override
        public Request timeout(int millis) {
            Validate.isTrue(millis >= 0, "Timeout milliseconds must be 0 (infinite) or greater");
            this.timeoutMilliseconds = millis;
            return this;
        }

        @Override
        public int maxBodySize() {
            return this.maxBodySizeBytes;
        }

        @Override
        public Connection.Request maxBodySize(int bytes) {
            Validate.isTrue(bytes >= 0, "maxSize must be 0 (unlimited) or larger");
            this.maxBodySizeBytes = bytes;
            return this;
        }

        @Override
        public boolean followRedirects() {
            return this.followRedirects;
        }

        @Override
        public Connection.Request followRedirects(boolean followRedirects) {
            this.followRedirects = followRedirects;
            return this;
        }

        @Override
        public boolean ignoreHttpErrors() {
            return this.ignoreHttpErrors;
        }

        @Override
        public SSLSocketFactory sslSocketFactory() {
            return this.sslSocketFactory;
        }

        @Override
        public void sslSocketFactory(SSLSocketFactory sslSocketFactory) {
            this.sslSocketFactory = sslSocketFactory;
        }

        @Override
        public Connection.Request ignoreHttpErrors(boolean ignoreHttpErrors) {
            this.ignoreHttpErrors = ignoreHttpErrors;
            return this;
        }

        @Override
        public boolean ignoreContentType() {
            return this.ignoreContentType;
        }

        @Override
        public Connection.Request ignoreContentType(boolean ignoreContentType) {
            this.ignoreContentType = ignoreContentType;
            return this;
        }

        @Override
        public Request data(Connection.KeyVal keyval) {
            Validate.notNullParam(keyval, "keyval");
            this.data.add(keyval);
            return this;
        }

        @Override
        public Collection<Connection.KeyVal> data() {
            return this.data;
        }

        @Override
        public Connection.Request requestBody(@Nullable String body) {
            this.body = body;
            return this;
        }

        @Override
        public String requestBody() {
            return this.body;
        }

        @Override
        public Request parser(Parser parser) {
            this.parser = parser;
            this.parserDefined = true;
            return this;
        }

        @Override
        public Parser parser() {
            return this.parser;
        }

        @Override
        public Connection.Request postDataCharset(String charset) {
            Validate.notNullParam(charset, "charset");
            if (!Charset.isSupported(charset)) {
                throw new IllegalCharsetNameException(charset);
            }
            this.postDataCharset = charset;
            return this;
        }

        @Override
        public String postDataCharset() {
            return this.postDataCharset;
        }

        CookieManager cookieManager() {
            return this.cookieManager;
        }

        static {
            System.setProperty("sun.net.http.allowRestrictedHeaders", "true");
        }
    }

    public static class Response
    extends Base<Connection.Response>
    implements Connection.Response {
        private static final int MAX_REDIRECTS = 20;
        private static final String LOCATION = "Location";
        private final int statusCode;
        private final String statusMessage;
        @Nullable
        private ByteBuffer byteData;
        @Nullable
        private InputStream bodyStream;
        @Nullable
        private HttpURLConnection conn;
        @Nullable
        private String charset;
        @Nullable
        private final String contentType;
        private boolean executed = false;
        private boolean inputStreamRead = false;
        private int numRedirects = 0;
        private final Request req;
        private static final Pattern xmlContentTypeRxp = Pattern.compile("(application|text)/\\w*\\+?xml.*");

        Response() {
            this.statusCode = 400;
            this.statusMessage = "Request not made";
            this.req = new Request();
            this.contentType = null;
        }

        static Response execute(Request req) throws IOException {
            return Response.execute(req, null);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        static Response execute(Request req, @Nullable Response previousResponse) throws IOException {
            boolean hasRequestBody;
            Request request = req;
            synchronized (request) {
                Validate.isFalse(req.executing, "Multiple threads were detected trying to execute the same request concurrently. Make sure to use Connection#newRequest() and do not share an executing request between threads.");
                req.executing = true;
            }
            Validate.notNullParam(req, "req");
            URL url = req.url();
            Validate.notNull(url, "URL must be specified to connect");
            String protocol = url.getProtocol();
            if (!protocol.equals("http") && !protocol.equals("https")) {
                throw new MalformedURLException("Only http & https protocols supported");
            }
            boolean methodHasBody = req.method().hasBody();
            boolean bl = hasRequestBody = req.requestBody() != null;
            if (!methodHasBody) {
                Validate.isFalse(hasRequestBody, "Cannot set a request body for HTTP method " + (Object)((Object)req.method()));
            }
            String mimeBoundary = null;
            if (req.data().size() > 0 && (!methodHasBody || hasRequestBody)) {
                Response.serialiseRequestUrl(req);
            } else if (methodHasBody) {
                mimeBoundary = Response.setOutputContentType(req);
            }
            long startTime = System.nanoTime();
            HttpURLConnection conn = Response.createConnection(req);
            Response res = null;
            try {
                conn.connect();
                if (conn.getDoOutput()) {
                    try (OutputStream out = conn.getOutputStream();){
                        Response.writePost(req, out, mimeBoundary);
                    }
                }
                int status = conn.getResponseCode();
                res = new Response(conn, req, previousResponse);
                if (res.hasHeader(LOCATION) && req.followRedirects()) {
                    if (status != 307) {
                        req.method(Connection.Method.GET);
                        req.data().clear();
                        req.requestBody(null);
                        req.removeHeader(HttpConnection.CONTENT_TYPE);
                    }
                    String location = res.header(LOCATION);
                    Validate.notNull(location);
                    if (location.startsWith("http:/") && location.charAt(6) != '/') {
                        location = location.substring(6);
                    }
                    URL redir = StringUtil.resolve(req.url(), location);
                    req.url(HttpConnection.encodeUrl(redir));
                    req.executing = false;
                    Response response = Response.execute(req, res);
                    return response;
                }
                if (!(status >= 200 && status < 400 || req.ignoreHttpErrors())) {
                    throw new HttpStatusException("HTTP error fetching URL", status, req.url().toString());
                }
                String contentType = res.contentType();
                if (!(contentType == null || req.ignoreContentType() || contentType.startsWith("text/") || xmlContentTypeRxp.matcher(contentType).matches())) {
                    throw new UnsupportedMimeTypeException("Unhandled content type. Must be text/*, application/xml, or application/*+xml", contentType, req.url().toString());
                }
                if (contentType != null && xmlContentTypeRxp.matcher(contentType).matches() && !req.parserDefined) {
                    req.parser(Parser.xmlParser());
                }
                res.charset = DataUtil.getCharsetFromContentType(res.contentType);
                if (conn.getContentLength() != 0 && req.method() != Connection.Method.HEAD) {
                    res.bodyStream = conn.getErrorStream() != null ? conn.getErrorStream() : conn.getInputStream();
                    Validate.notNull(res.bodyStream);
                    if (res.hasHeaderWithValue(HttpConnection.CONTENT_ENCODING, "gzip")) {
                        res.bodyStream = new GZIPInputStream(res.bodyStream);
                    } else if (res.hasHeaderWithValue(HttpConnection.CONTENT_ENCODING, "deflate")) {
                        res.bodyStream = new InflaterInputStream(res.bodyStream, new Inflater(true));
                    }
                    res.bodyStream = ConstrainableInputStream.wrap(res.bodyStream, 32768, req.maxBodySize()).timeout(startTime, req.timeout());
                } else {
                    res.byteData = DataUtil.emptyByteBuffer();
                }
            }
            catch (IOException e) {
                if (res != null) {
                    super.safeClose();
                }
                throw e;
            }
            finally {
                req.executing = false;
            }
            res.executed = true;
            return res;
        }

        @Override
        public int statusCode() {
            return this.statusCode;
        }

        @Override
        public String statusMessage() {
            return this.statusMessage;
        }

        @Override
        public String charset() {
            return this.charset;
        }

        @Override
        public Response charset(String charset) {
            this.charset = charset;
            return this;
        }

        @Override
        public String contentType() {
            return this.contentType;
        }

        @Override
        public Document parse() throws IOException {
            Validate.isTrue(this.executed, "Request must be executed (with .execute(), .get(), or .post() before parsing response");
            if (this.byteData != null) {
                this.bodyStream = new ByteArrayInputStream(this.byteData.array());
                this.inputStreamRead = false;
            }
            Validate.isFalse(this.inputStreamRead, "Input stream already read and parsed, cannot re-read.");
            Document doc = DataUtil.parseInputStream(this.bodyStream, this.charset, this.url.toExternalForm(), this.req.parser());
            doc.connection(new HttpConnection(this.req, this));
            this.charset = doc.outputSettings().charset().name();
            this.inputStreamRead = true;
            this.safeClose();
            return doc;
        }

        private void prepareByteData() {
            Validate.isTrue(this.executed, "Request must be executed (with .execute(), .get(), or .post() before getting response body");
            if (this.bodyStream != null && this.byteData == null) {
                Validate.isFalse(this.inputStreamRead, "Request has already been read (with .parse())");
                try {
                    this.byteData = DataUtil.readToByteBuffer(this.bodyStream, this.req.maxBodySize());
                }
                catch (IOException e) {
                    throw new UncheckedIOException(e);
                }
                finally {
                    this.inputStreamRead = true;
                    this.safeClose();
                }
            }
        }

        @Override
        public String body() {
            this.prepareByteData();
            Validate.notNull(this.byteData);
            String body = (this.charset == null ? DataUtil.UTF_8 : Charset.forName(this.charset)).decode(this.byteData).toString();
            this.byteData.rewind();
            return body;
        }

        @Override
        public byte[] bodyAsBytes() {
            this.prepareByteData();
            Validate.notNull(this.byteData);
            return this.byteData.array();
        }

        @Override
        public Connection.Response bufferUp() {
            this.prepareByteData();
            return this;
        }

        @Override
        public BufferedInputStream bodyStream() {
            Validate.isTrue(this.executed, "Request must be executed (with .execute(), .get(), or .post() before getting response body");
            Validate.isFalse(this.inputStreamRead, "Request has already been read");
            this.inputStreamRead = true;
            return ConstrainableInputStream.wrap(this.bodyStream, 32768, this.req.maxBodySize());
        }

        private static HttpURLConnection createConnection(Request req) throws IOException {
            Proxy proxy = req.proxy();
            HttpURLConnection conn = (HttpURLConnection)(proxy == null ? req.url().openConnection() : req.url().openConnection(proxy));
            conn.setRequestMethod(req.method().name());
            conn.setInstanceFollowRedirects(false);
            conn.setConnectTimeout(req.timeout());
            conn.setReadTimeout(req.timeout() / 2);
            if (req.sslSocketFactory() != null && conn instanceof HttpsURLConnection) {
                ((HttpsURLConnection)conn).setSSLSocketFactory(req.sslSocketFactory());
            }
            if (req.method().hasBody()) {
                conn.setDoOutput(true);
            }
            CookieUtil.applyCookiesToRequest(req, conn);
            for (Map.Entry header : req.multiHeaders().entrySet()) {
                for (String value : (List)header.getValue()) {
                    conn.addRequestProperty((String)header.getKey(), value);
                }
            }
            return conn;
        }

        private void safeClose() {
            if (this.bodyStream != null) {
                try {
                    this.bodyStream.close();
                }
                catch (IOException iOException) {
                }
                finally {
                    this.bodyStream = null;
                }
            }
            if (this.conn != null) {
                this.conn.disconnect();
                this.conn = null;
            }
        }

        private Response(HttpURLConnection conn, Request request, @Nullable Response previousResponse) throws IOException {
            this.conn = conn;
            this.req = request;
            this.method = Connection.Method.valueOf(conn.getRequestMethod());
            this.url = conn.getURL();
            this.statusCode = conn.getResponseCode();
            this.statusMessage = conn.getResponseMessage();
            this.contentType = conn.getContentType();
            LinkedHashMap<String, List<String>> resHeaders = Response.createHeaderMap(conn);
            this.processResponseHeaders(resHeaders);
            CookieUtil.storeCookies(this.req, this.url, resHeaders);
            if (previousResponse != null) {
                for (Map.Entry prevCookie : previousResponse.cookies().entrySet()) {
                    if (this.hasCookie((String)prevCookie.getKey())) continue;
                    this.cookie((String)prevCookie.getKey(), (String)prevCookie.getValue());
                }
                previousResponse.safeClose();
                this.numRedirects = previousResponse.numRedirects + 1;
                if (this.numRedirects >= 20) {
                    throw new IOException(String.format("Too many redirects occurred trying to load URL %s", previousResponse.url()));
                }
            }
        }

        private static LinkedHashMap<String, List<String>> createHeaderMap(HttpURLConnection conn) {
            LinkedHashMap<String, List<String>> headers = new LinkedHashMap<String, List<String>>();
            int i = 0;
            while (true) {
                String key = conn.getHeaderFieldKey(i);
                String val = conn.getHeaderField(i);
                if (key == null && val == null) break;
                ++i;
                if (key == null || val == null) continue;
                if (headers.containsKey(key)) {
                    headers.get(key).add(val);
                    continue;
                }
                ArrayList<String> vals = new ArrayList<String>();
                vals.add(val);
                headers.put(key, vals);
            }
            return headers;
        }

        void processResponseHeaders(Map<String, List<String>> resHeaders) {
            for (Map.Entry<String, List<String>> entry : resHeaders.entrySet()) {
                String name = entry.getKey();
                if (name == null) continue;
                List<String> values = entry.getValue();
                if (name.equalsIgnoreCase("Set-Cookie")) {
                    for (String value : values) {
                        if (value == null) continue;
                        TokenQueue cd = new TokenQueue(value);
                        String cookieName = cd.chompTo("=").trim();
                        String cookieVal = cd.consumeTo(";").trim();
                        if (cookieName.length() <= 0 || this.cookies.containsKey(cookieName)) continue;
                        this.cookie(cookieName, cookieVal);
                    }
                }
                for (String value : values) {
                    this.addHeader(name, value);
                }
            }
        }

        @Nullable
        private static String setOutputContentType(Connection.Request req) {
            String contentType = req.header(HttpConnection.CONTENT_TYPE);
            String bound = null;
            if (contentType != null) {
                if (contentType.contains(HttpConnection.MULTIPART_FORM_DATA) && !contentType.contains("boundary")) {
                    bound = DataUtil.mimeBoundary();
                    req.header(HttpConnection.CONTENT_TYPE, "multipart/form-data; boundary=" + bound);
                }
            } else if (HttpConnection.needsMultipart(req)) {
                bound = DataUtil.mimeBoundary();
                req.header(HttpConnection.CONTENT_TYPE, "multipart/form-data; boundary=" + bound);
            } else {
                req.header(HttpConnection.CONTENT_TYPE, "application/x-www-form-urlencoded; charset=" + req.postDataCharset());
            }
            return bound;
        }

        private static void writePost(Connection.Request req, OutputStream outputStream, @Nullable String boundary) throws IOException {
            Collection<Connection.KeyVal> data = req.data();
            BufferedWriter w = new BufferedWriter(new OutputStreamWriter(outputStream, Charset.forName(req.postDataCharset())));
            if (boundary != null) {
                for (Connection.KeyVal keyVal : data) {
                    w.write("--");
                    w.write(boundary);
                    w.write("\r\n");
                    w.write("Content-Disposition: form-data; name=\"");
                    w.write(HttpConnection.encodeMimeName(keyVal.key()));
                    w.write("\"");
                    InputStream input = keyVal.inputStream();
                    if (input != null) {
                        w.write("; filename=\"");
                        w.write(HttpConnection.encodeMimeName(keyVal.value()));
                        w.write("\"\r\nContent-Type: ");
                        String contentType = keyVal.contentType();
                        w.write(contentType != null ? contentType : HttpConnection.DefaultUploadType);
                        w.write("\r\n\r\n");
                        w.flush();
                        DataUtil.crossStreams(input, outputStream);
                        outputStream.flush();
                    } else {
                        w.write("\r\n\r\n");
                        w.write(keyVal.value());
                    }
                    w.write("\r\n");
                }
                w.write("--");
                w.write(boundary);
                w.write("--");
            } else {
                String body = req.requestBody();
                if (body != null) {
                    w.write(body);
                } else {
                    boolean first = true;
                    for (Connection.KeyVal keyVal : data) {
                        if (!first) {
                            w.append('&');
                        } else {
                            first = false;
                        }
                        w.write(URLEncoder.encode(keyVal.key(), req.postDataCharset()));
                        w.write(61);
                        w.write(URLEncoder.encode(keyVal.value(), req.postDataCharset()));
                    }
                }
            }
            w.close();
        }

        private static void serialiseRequestUrl(Connection.Request req) throws IOException {
            URL in = req.url();
            StringBuilder url = StringUtil.borrowBuilder();
            boolean first = true;
            url.append(in.getProtocol()).append("://").append(in.getAuthority()).append(in.getPath()).append("?");
            if (in.getQuery() != null) {
                url.append(in.getQuery());
                first = false;
            }
            for (Connection.KeyVal keyVal : req.data()) {
                Validate.isFalse(keyVal.hasInputStream(), "InputStream data not supported in URL query string.");
                if (!first) {
                    url.append('&');
                } else {
                    first = false;
                }
                url.append(URLEncoder.encode(keyVal.key(), DataUtil.defaultCharsetName)).append('=').append(URLEncoder.encode(keyVal.value(), DataUtil.defaultCharsetName));
            }
            req.url(new URL(StringUtil.releaseBuilder(url)));
            req.data().clear();
        }
    }

    public static class KeyVal
    implements Connection.KeyVal {
        private String key;
        private String value;
        @Nullable
        private InputStream stream;
        @Nullable
        private String contentType;

        public static KeyVal create(String key, String value) {
            return new KeyVal(key, value);
        }

        public static KeyVal create(String key, String filename, InputStream stream) {
            return new KeyVal(key, filename).inputStream(stream);
        }

        private KeyVal(String key, String value) {
            Validate.notEmptyParam(key, "key");
            Validate.notNullParam(value, "value");
            this.key = key;
            this.value = value;
        }

        @Override
        public KeyVal key(String key) {
            Validate.notEmptyParam(key, "key");
            this.key = key;
            return this;
        }

        @Override
        public String key() {
            return this.key;
        }

        @Override
        public KeyVal value(String value) {
            Validate.notNullParam(value, "value");
            this.value = value;
            return this;
        }

        @Override
        public String value() {
            return this.value;
        }

        @Override
        public KeyVal inputStream(InputStream inputStream) {
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

    private static abstract class Base<T extends Connection.Base<T>>
    implements Connection.Base<T> {
        private static final URL UnsetUrl;
        URL url = UnsetUrl;
        Connection.Method method = Connection.Method.GET;
        Map<String, List<String>> headers;
        Map<String, String> cookies;

        private Base() {
            this.headers = new LinkedHashMap<String, List<String>>();
            this.cookies = new LinkedHashMap<String, String>();
        }

        private Base(Base<T> copy) {
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
            values.add(Base.fixHeaderEncoding(value));
            return (T)this;
        }

        @Override
        public List<String> headers(String name) {
            Validate.notEmptyParam(name, "name");
            return this.getHeadersCaseInsensitive(name);
        }

        private static String fixHeaderEncoding(String val) {
            byte[] bytes = val.getBytes(ISO_8859_1);
            if (!Base.looksLikeUtf8(bytes)) {
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
}
