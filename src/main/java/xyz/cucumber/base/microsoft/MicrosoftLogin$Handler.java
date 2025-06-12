package xyz.cucumber.base.microsoft;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import xyz.cucumber.base.microsoft.MicrosoftLogin;
import xyz.cucumber.base.utils.BroswerUtil;

class MicrosoftLogin$Handler
implements HttpHandler {
    private MicrosoftLogin$Handler() {
    }

    @Override
    public void handle(HttpExchange req) throws IOException {
        if (req.getRequestMethod().equals("GET")) {
            List query = URLEncodedUtils.parse((URI)req.getRequestURI(), (String)StandardCharsets.UTF_8.name());
            boolean ok = false;
            for (NameValuePair pair : query) {
                if (!pair.getName().equals("code")) continue;
                this.handleCode(pair.getValue());
                ok = true;
                break;
            }
            if (!ok) {
                this.writeText(req, "Cannot authenticate.");
            } else {
                this.writeText(req, "<html>You may now close this page.<script>close()</script></html>");
            }
        }
        MicrosoftLogin.stopServer();
    }

    private void handleCode(String code) {
        String response = BroswerUtil.postExternal("https://login.live.com/oauth20_token.srf", "client_id=ba89e6e0-8490-4a26-8746-f389a0d3ccc7&code=" + code + "&client_secret=" + MicrosoftLogin.CLIENT_SECRET + "&grant_type=authorization_code&redirect_uri=http://localhost:" + 8247, false);
        MicrosoftLogin.AuthTokenResponse res = (MicrosoftLogin.AuthTokenResponse)gson.fromJson(response, MicrosoftLogin.AuthTokenResponse.class);
        if (res == null) {
            callback.accept(null);
        } else {
            callback.accept(res.refresh_token);
        }
    }

    private void writeText(HttpExchange req, String text) throws IOException {
        OutputStream out = req.getResponseBody();
        req.getResponseHeaders().add("Content-Type", "text/html; charset=utf-8");
        req.sendResponseHeaders(200, text.length());
        out.write(text.getBytes(StandardCharsets.UTF_8));
        out.flush();
        out.close();
    }
}
