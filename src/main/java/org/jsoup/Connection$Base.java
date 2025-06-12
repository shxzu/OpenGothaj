package org.jsoup;

import java.net.URL;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;
import org.jsoup.Connection;

public interface Connection$Base<T extends Connection$Base<T>> {
    public URL url();

    public T url(URL var1);

    public Connection.Method method();

    public T method(Connection.Method var1);

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
