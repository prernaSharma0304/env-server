import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.io.File;
import java.nio.file.Files;
import java.net.InetSocketAddress;
import java.util.Random;

public class EnvServer {
    public static void main(String[] args) throws IOException {
        int port = Integer.parseInt(System.getenv().getOrDefault("PORT", "10000"));
HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);

        // Serve frontend files
        server.createContext("/", new FileHandler());

        // API endpoint with random data
        server.createContext("/data", new DataHandler());

        server.setExecutor(null);
        System.out.println("✅ Server started on http://localhost:3000");
        server.start();
    }

    // Serve static files (index.html, style.css, script.js)
    static class FileHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String path = exchange.getRequestURI().getPath();
            if (path.equals("/"))
                path = "/frontend/index.html";

            File file = new File("." + path);
            if (file.exists() && !file.isDirectory()) {
                byte[] bytes = Files.readAllBytes(file.toPath());
                String type = "text/html";
                if (path.endsWith(".css"))
                    type = "text/css";
                if (path.endsWith(".js"))
                    type = "application/javascript";

                exchange.getResponseHeaders().add("Content-Type", type);
                exchange.sendResponseHeaders(200, bytes.length);
                OutputStream os = exchange.getResponseBody();
                os.write(bytes);
                os.close();
            } else {
                String msg = "File Not Found";
                exchange.sendResponseHeaders(404, msg.length());
                OutputStream os = exchange.getResponseBody();
                os.write(msg.getBytes());
                os.close();
            }
        }
    }

    // API Handler with random sample data
    static class DataHandler implements HttpHandler {
        private final String[] cities = { "Bareilly", "Delhi", "Varanasi", "Pune" };
        private final Random random = new Random();

        @Override
        public void handle(HttpExchange exchange) throws IOException {
            StringBuilder json = new StringBuilder("{");

            for (int i = 0; i < cities.length; i++) {
                String city = cities[i];
                json.append("\"").append(city).append("\": {");
                json.append("\"weather\": {\"hourly\": {");
                json.append("\"temperature_2m\": ").append(randomArray(24, 20, 40)).append(",");
                json.append("\"humidity_2m\": ").append(randomArray(24, 30, 90)).append(",");
                json.append("\"windspeed_10m\": ").append(randomArray(24, 0, 20)).append(",");
                json.append("\"precipitation\": ").append(randomArray(24, 0, 15)).append(",");
                json.append("\"uv_index\": ").append(randomArray(24, 0, 12));
                json.append("}},");
                json.append("\"air\": {\"hourly\": {");
                json.append("\"aqi\": ").append(randomArray(24, 50, 200)).append(",");
                json.append("\"pm2_5\": ").append(randomArray(24, 10, 150));
                json.append("}}}");
                if (i < cities.length - 1)
                    json.append(",");
            }

            json.append("}");
            exchange.getResponseHeaders().add("Content-Type", "application/json");
            exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
            exchange.sendResponseHeaders(200, json.toString().getBytes().length);
            OutputStream os = exchange.getResponseBody();
            os.write(json.toString().getBytes());
            os.close();
        }

        private String randomArray(int size, int min, int max) {
            StringBuilder sb = new StringBuilder("[");
            for (int i = 0; i < size; i++) {
                int val = random.nextInt(max - min + 1) + min;
                sb.append(val);
                if (i < size - 1)
                    sb.append(",");
            }
            sb.append("]");
            return sb.toString();
        }
    }
}


