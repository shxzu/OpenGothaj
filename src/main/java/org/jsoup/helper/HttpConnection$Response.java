package org.jsoup.helper;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;
import javax.annotation.Nullable;
import javax.net.ssl.HttpsURLConnection;
import org.jsoup.Connection;
import org.jsoup.HttpStatusException;
import org.jsoup.UncheckedIOException;
import org.jsoup.UnsupportedMimeTypeException;
import org.jsoup.helper.CookieUtil;
import org.jsoup.helper.DataUtil;
import org.jsoup.helper.HttpConnection;
import org.jsoup.helper.Validate;
import org.jsoup.internal.ConstrainableInputStream;
import org.jsoup.internal.StringUtil;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;
import org.jsoup.parser.TokenQueue;

public class HttpConnection$Response
extends HttpConnection.Base<Connection.Response>
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
    private final HttpConnection.Request req;
    private static final Pattern xmlContentTypeRxp = Pattern.compile("(application|text)/\\w*\\+?xml.*");

    HttpConnection$Response() {
        super(null);
        this.statusCode = 400;
        this.statusMessage = "Request not made";
        this.req = new HttpConnection.Request();
        this.contentType = null;
    }

    static HttpConnection$Response execute(HttpConnection.Request req) throws IOException {
        return HttpConnection$Response.execute(req, null);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    static HttpConnection$Response execute(HttpConnection.Request req, @Nullable HttpConnection$Response previousResponse) throws IOException {
        boolean hasRequestBody;
        HttpConnection.Request request = req;
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
            HttpConnection$Response.serialiseRequestUrl(req);
        } else if (methodHasBody) {
            mimeBoundary = HttpConnection$Response.setOutputContentType(req);
        }
        long startTime = System.nanoTime();
        HttpURLConnection conn = HttpConnection$Response.createConnection(req);
        HttpConnection$Response res = null;
        try {
            conn.connect();
            if (conn.getDoOutput()) {
                try (OutputStream out = conn.getOutputStream();){
                    HttpConnection$Response.writePost(req, out, mimeBoundary);
                }
            }
            int status = conn.getResponseCode();
            res = new HttpConnection$Response(conn, req, previousResponse);
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
                HttpConnection$Response response = HttpConnection$Response.execute(req, res);
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
    public HttpConnection$Response charset(String charset) {
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
        doc.connection(new HttpConnection(this.req, this, null));
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

    private static HttpURLConnection createConnection(HttpConnection.Request req) throws IOException {
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

    private HttpConnection$Response(HttpURLConnection conn, HttpConnection.Request request, @Nullable HttpConnection$Response previousResponse) throws IOException {
        super(null);
        this.conn = conn;
        this.req = request;
        this.method = Connection.Method.valueOf(conn.getRequestMethod());
        this.url = conn.getURL();
        this.statusCode = conn.getResponseCode();
        this.statusMessage = conn.getResponseMessage();
        this.contentType = conn.getContentType();
        LinkedHashMap<String, List<String>> resHeaders = HttpConnection$Response.createHeaderMap(conn);
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
