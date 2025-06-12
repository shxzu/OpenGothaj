package fr.litarvan.openauth.microsoft;

import fr.litarvan.openauth.microsoft.MicrosoftAuthenticationException;
import fr.litarvan.openauth.microsoft.MicrosoftPatchedHttpURLConnection;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.CompletableFuture;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.web.WebView;
import javax.swing.JFrame;
import sun.net.www.protocol.https.Handler;

public class LoginFrame
extends JFrame {
    private CompletableFuture<String> future;

    public LoginFrame() {
        this.setTitle("Connexion à Microsoft");
        this.setSize(750, 750);
        this.setLocationRelativeTo(null);
        this.setContentPane(new JFXPanel());
    }

    public CompletableFuture<String> start(String url) {
        if (this.future != null) {
            return this.future;
        }
        this.future = new CompletableFuture();
        this.addWindowListener(new WindowAdapter(){

            @Override
            public void windowClosing(WindowEvent e) {
                LoginFrame.this.future.completeExceptionally(new MicrosoftAuthenticationException("User closed the authentication window"));
            }
        });
        Platform.runLater(() -> this.init(url));
        return this.future;
    }

    protected void init(String url) {
        try {
            LoginFrame.overrideFactory();
        }
        catch (Throwable throwable) {
            // empty catch block
        }
        WebView webView = new WebView();
        JFXPanel content = (JFXPanel)this.getContentPane();
        content.setScene(new Scene(webView, this.getWidth(), this.getHeight()));
        webView.getEngine().locationProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.contains("access_token")) {
                this.setVisible(false);
                this.future.complete((String)newValue);
            }
        });
        webView.getEngine().load(url);
        this.setVisible(true);
    }

    protected static void overrideFactory() {
        URL.setURLStreamHandlerFactory(protocol -> {
            if ("https".equals(protocol)) {
                return new Handler(){

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
                };
            }
            return null;
        });
    }
}
