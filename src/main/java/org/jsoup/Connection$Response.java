package org.jsoup;

import java.io.BufferedInputStream;
import java.io.IOException;
import javax.annotation.Nullable;
import org.jsoup.Connection;
import org.jsoup.nodes.Document;

public interface Connection$Response
extends Connection.Base<Connection$Response> {
    public int statusCode();

    public String statusMessage();

    @Nullable
    public String charset();

    public Connection$Response charset(String var1);

    @Nullable
    public String contentType();

    public Document parse() throws IOException;

    public String body();

    public byte[] bodyAsBytes();

    public Connection$Response bufferUp();

    public BufferedInputStream bodyStream();
}
