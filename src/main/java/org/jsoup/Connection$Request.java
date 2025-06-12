package org.jsoup;

import java.net.Proxy;
import java.util.Collection;
import javax.annotation.Nullable;
import javax.net.ssl.SSLSocketFactory;
import org.jsoup.Connection;
import org.jsoup.parser.Parser;

public interface Connection$Request
extends Connection.Base<Connection$Request> {
    @Nullable
    public Proxy proxy();

    public Connection$Request proxy(@Nullable Proxy var1);

    public Connection$Request proxy(String var1, int var2);

    public int timeout();

    public Connection$Request timeout(int var1);

    public int maxBodySize();

    public Connection$Request maxBodySize(int var1);

    public boolean followRedirects();

    public Connection$Request followRedirects(boolean var1);

    public boolean ignoreHttpErrors();

    public Connection$Request ignoreHttpErrors(boolean var1);

    public boolean ignoreContentType();

    public Connection$Request ignoreContentType(boolean var1);

    @Nullable
    public SSLSocketFactory sslSocketFactory();

    public void sslSocketFactory(SSLSocketFactory var1);

    public Connection$Request data(Connection.KeyVal var1);

    public Collection<Connection.KeyVal> data();

    public Connection$Request requestBody(@Nullable String var1);

    @Nullable
    public String requestBody();

    public Connection$Request parser(Parser var1);

    public Parser parser();

    public Connection$Request postDataCharset(String var1);

    public String postDataCharset();
}
