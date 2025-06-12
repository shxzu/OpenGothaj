package org.jsoup.helper;

import java.net.CookieManager;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.nio.charset.Charset;
import java.nio.charset.IllegalCharsetNameException;
import java.util.ArrayList;
import java.util.Collection;
import javax.annotation.Nullable;
import javax.net.ssl.SSLSocketFactory;
import org.jsoup.Connection;
import org.jsoup.helper.DataUtil;
import org.jsoup.helper.HttpConnection;
import org.jsoup.helper.Validate;
import org.jsoup.parser.Parser;

public class HttpConnection$Request
extends HttpConnection.Base<Connection.Request>
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

    HttpConnection$Request() {
        super(null);
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

    HttpConnection$Request(HttpConnection$Request copy) {
        super(copy, null);
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
    public HttpConnection$Request proxy(@Nullable Proxy proxy) {
        this.proxy = proxy;
        return this;
    }

    @Override
    public HttpConnection$Request proxy(String host, int port) {
        this.proxy = new Proxy(Proxy.Type.HTTP, InetSocketAddress.createUnresolved(host, port));
        return this;
    }

    @Override
    public int timeout() {
        return this.timeoutMilliseconds;
    }

    @Override
    public HttpConnection$Request timeout(int millis) {
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
    public HttpConnection$Request data(Connection.KeyVal keyval) {
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
    public HttpConnection$Request parser(Parser parser) {
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

    static CookieManager access$002(HttpConnection$Request x0, CookieManager x1) {
        x0.cookieManager = x1;
        return x0.cookieManager;
    }

    static CookieManager access$000(HttpConnection$Request x0) {
        return x0.cookieManager;
    }

    static boolean access$600(HttpConnection$Request x0) {
        return x0.executing;
    }

    static boolean access$602(HttpConnection$Request x0, boolean x1) {
        x0.executing = x1;
        return x0.executing;
    }

    static boolean access$700(HttpConnection$Request x0) {
        return x0.parserDefined;
    }

    static {
        System.setProperty("sun.net.http.allowRestrictedHeaders", "true");
    }
}
