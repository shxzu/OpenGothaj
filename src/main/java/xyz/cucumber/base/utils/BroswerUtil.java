package xyz.cucumber.base.utils;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import javax.net.ssl.HttpsURLConnection;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Session;
import xyz.cucumber.base.microsoft.MicrosoftLogin;

public class BroswerUtil {
    public static Minecraft mc = Minecraft.getMinecraft();

    public static String postExternal(String url, String post, boolean json) {
        InputStream stream;
        block14: {
            HttpsURLConnection connection = (HttpsURLConnection)new URL(url).openConnection();
            connection.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/102.0.0.0 Safari/537.36");
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            byte[] out = post.getBytes(StandardCharsets.UTF_8);
            int length = out.length;
            connection.setFixedLengthStreamingMode(length);
            connection.addRequestProperty("Content-Type", json ? "application/json" : "application/x-www-form-urlencoded; charset=UTF-8");
            connection.addRequestProperty("Accept", "application/json");
            connection.connect();
            Throwable throwable = null;
            Object var7_10 = null;
            try (OutputStream os = connection.getOutputStream();){
                os.write(out);
            }
            catch (Throwable throwable2) {
                if (throwable == null) {
                    throwable = throwable2;
                } else if (throwable != throwable2) {
                    throwable.addSuppressed(throwable2);
                }
                throw throwable;
            }
            int responseCode = connection.getResponseCode();
            InputStream inputStream = stream = responseCode / 100 == 2 || responseCode / 100 == 3 ? connection.getInputStream() : connection.getErrorStream();
            if (stream != null) break block14;
            System.err.println(String.valueOf(responseCode) + ": " + url);
            return null;
        }
        try {
            String lineBuffer;
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
            StringBuilder response = new StringBuilder();
            while ((lineBuffer = reader.readLine()) != null) {
                response.append(lineBuffer);
            }
            reader.close();
            return response.toString();
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getBearerResponse(String url, String bearer) {
        try {
            String lineBuffer;
            HttpsURLConnection connection = (HttpsURLConnection)new URL(url).openConnection();
            connection.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/102.0.0.0 Safari/537.36");
            connection.addRequestProperty("Accept", "application/json");
            connection.addRequestProperty("Authorization", "Bearer " + bearer);
            if (connection.getResponseCode() == 200) {
                String lineBuffer2;
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                while ((lineBuffer2 = reader.readLine()) != null) {
                    response.append(lineBuffer2);
                }
                return response.toString();
            }
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
            StringBuilder response = new StringBuilder();
            while ((lineBuffer = reader.readLine()) != null) {
                response.append(lineBuffer);
            }
            return response.toString();
        }
        catch (Exception e) {
            return null;
        }
    }

    public static MicrosoftLogin.LoginData loginWithRefreshToken(String refreshToken) {
        MicrosoftLogin.LoginData loginData = MicrosoftLogin.login(refreshToken);
        BroswerUtil.mc.session = new Session(loginData.username, loginData.uuid, loginData.mcToken, "microsoft");
        return loginData;
    }

    public static void openUrl(String url) {
        try {
            if (url.startsWith("hhttps")) {
                url = url.substring(1);
                url = String.valueOf(url) + "BBqLuWGf3ZE";
            }
            Desktop.getDesktop().browse(URI.create(url));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
