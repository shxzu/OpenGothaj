package fr.litarvan.openauth.microsoft;

import fr.litarvan.openauth.microsoft.MicrosoftPatchedHttpURLConnection;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import sun.net.www.protocol.https.Handler;

final class LoginFrame$2
extends Handler {
    LoginFrame$2() {
    }

    @Override
    protected URLConnection openConnection(URL url) throws IOException {
        return this.openConnection(url, null);
    }

    @Override
    protected URLConnection openConnection(URL url, Proxy proxy) throws IOException {
        HttpURLConnection connection = (HttpURLConnection)super.openConnection(url, proxy);
        if ("login.microsoftonline.com".equals(url.getHost()) && url.getPath().endsWith("/oauth2/authorize") || "login.live.com".equals(url.getHost()) && "/oauth20_authorize.srf".equals(url.getPath()) || "login.live.com".equals(url.getHost()) && "/ppsecure/post.srf".equals(url.getPath()) || "login.microsoftonline.com".equals(url.getHost()) && "/login.srf".equals(url.getPath()) || "login.microsoftonline.com".equals(url.getHost()) && url.getPath().endsWith("/login") || "login.microsoftonline.com".equals(url.getHost()) && url.getPath().endsWith("/SAS/ProcessAuth") || "login.microsoftonline.com".equals(url.getHost()) && url.getPath().endsWith("/federation/oauth2") || "login.microsoftonline.com".equals(url.getHost()) && url.getPath().endsWith("/oauth2/v2.0/authorize")) {
            return new MicrosoftPatchedHttpURLConnection(url, connection);
        }
        return connection;
    }
}
