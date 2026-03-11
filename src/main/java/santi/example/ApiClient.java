package santi.example;

import com.google.gson.Gson;
import santi.example.model.Jugador;
import santi.example.model.Palabra;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ApiClient {
    private static final String BASE_URL = "http://localhost:8080";
    private final HttpClient client = HttpClient.newHttpClient();
    private final Gson gson = new Gson();

    public Jugador buscarJugador(String nombre) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL + "/jugador/" + nombre))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request,
                    HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 404) return crearJugador(nombre);

            return gson.fromJson(response.body(), Jugador.class);

        } catch (Exception e) {
            System.out.println("Error al conectar con la API");
            return null;
        }
    }

    public Jugador crearJugador(String nombre) {
        try {
            String json = "{\"nombre\":\"" + nombre + "\"}";

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL + "/jugador"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();

            HttpResponse<String> response = client.send(request,
                    HttpResponse.BodyHandlers.ofString());

            return gson.fromJson(response.body(), Jugador.class);

        } catch (Exception e) {
            System.out.println("Error al crear jugador");
            return null;
        }
    }

    public Palabra obtenerPalabraRandom() {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL + "/palabras/random"))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request,
                    HttpResponse.BodyHandlers.ofString());

            return gson.fromJson(response.body(), Palabra.class);

        } catch (Exception e) {
            System.out.println("Error al obtener palabra");
            return null;
        }
    }

    public void actualizarJugador(Jugador jugador) {
        try {
            String json = gson.toJson(jugador);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL + "/jugador/" + jugador.getNombre()))
                    .header("Content-Type", "application/json")
                    .PUT(HttpRequest.BodyPublishers.ofString(json))
                    .build();

            client.send(request, HttpResponse.BodyHandlers.ofString());

        } catch (Exception e) {
            System.out.println("Error al actualizar jugador");
        }
    }

    public void guardarPartida(String usuarioJugador, int palabraId, boolean gano) {
        try {
            String json = String.format(
                    "{\"jugador\":{\"nombre\":\"%s\"},\"palabra\":{\"id\":%d},\"gano\":%b}",
                    usuarioJugador, palabraId, gano
            );

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL + "/partidas"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();

            client.send(request, HttpResponse.BodyHandlers.ofString());

        } catch (Exception e) {
            System.out.println("Error al guardar partida");
        }
    }
}