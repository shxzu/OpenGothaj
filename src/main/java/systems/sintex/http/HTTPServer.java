package systems.sintex.http;

import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

public class HTTPServer {
    private HttpServer server;
    private IStopServer iStopServer;

    public HTTPServer(IStopServer iStopServer, int port) throws IOException {
        this.iStopServer = iStopServer;
        this.server = HttpServer.create(new InetSocketAddress(port), 0);
        this.server.createContext("/", exchange -> {
            String requestMethod = exchange.getRequestMethod();
            if (requestMethod.equalsIgnoreCase("GET")) {
                String query = exchange.getRequestURI().getQuery();
                String response = "";
                String code = "";
                try {
                    code = query.substring(5);
                    response = response + "You can close this window now.";
                    exchange.sendResponseHeaders(200, response.length());
                }
                catch (Exception e) {
                    response = response + "Unknown error has occurred";
                    exchange.sendResponseHeaders(400, response.length());
                }
                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
                if (code.length() > 0) {
                    exchange.close();
                    this.server.stop(0);
                    iStopServer.stop(code);
                }
            }
        });
        this.server.setExecutor(null);
        this.server.start();
    }

    public static interface IStopServer {
        public void stop(String var1);
    }
}
