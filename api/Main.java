import com.sun.net.httpserver.HttpServer;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;

public class Main {
    public static void main(String[] args) throws Exception {
        HttpServer server = HttpServer.create(
            new InetSocketAddress(8080), 0
        );

        server.createContext("/saludo", exchange -> {
            String json = "{\"mensaje\":\"Hola desde mi API Java\"}";
            byte[] respuesta = json.getBytes(StandardCharsets.UTF_8);

            exchange.getResponseHeaders().set(
                "Content-Type", "application/json; charset=utf-8"
            );
            exchange.sendResponseHeaders(200, respuesta.length);

            try (var salida = exchange.getResponseBody()) {
                salida.write(respuesta);
            }
        });

        server.start();
        System.out.println("API iniciada en el puerto 8080");
    }
}