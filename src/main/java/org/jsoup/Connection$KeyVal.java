package org.jsoup;

import java.io.InputStream;
import javax.annotation.Nullable;

public interface Connection$KeyVal {
    public Connection$KeyVal key(String var1);

    public String key();

    public Connection$KeyVal value(String var1);

    public String value();

    public Connection$KeyVal inputStream(InputStream var1);

    @Nullable
    public InputStream inputStream();

    public boolean hasInputStream();

    public Connection$KeyVal contentType(String var1);

    @Nullable
    public String contentType();
}
