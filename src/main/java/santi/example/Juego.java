package santi.example;

import santi.example.model.*;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Juego {
    private static final int MAX_INTENTOS = 6;
    private final ApiClient apiClient;
    private final Scanner scanner;

    public Juego() {
        this.apiClient = new ApiClient();
        this.scanner = new Scanner(System.in);
    }

    public void iniciar() {
        System.out.println("=== AHORCADO ===");
        System.out.print("Ingresá tu nombre de usuario: ");
        String usuario = scanner.nextLine().trim();

        // Busca el jugador en la API, si no existe lo crea
        Jugador jugador = apiClient.buscarJugador(usuario);
        if (jugador == null) {
            System.out.println("Usuario nuevo, creando perfil...");
            jugador = apiClient.crearJugador(usuario);
        }

        System.out.println("Bienvenido " + jugador.getNombre() + "!");
        System.out.println("Puntos: " + jugador.getPuntos() +
                " | Racha actual: " + jugador.getRachaActual() +
                " | Mayor racha: " + jugador.getMayorRacha());

        // Arranca la partida
        jugar(jugador);
    }

    private void jugar(Jugador jugador) {
        Palabra palabraObj = apiClient.obtenerPalabraRandom();
        String palabraSecreta = palabraObj.getPalabra().toLowerCase();//siempre en minuscula

        char[] progreso = new char[palabraSecreta.length()];
        for (int i = 0; i < progreso.length; i++) {
            progreso[i] = '_';
        }

        Set<Character> letrasUsadas = new HashSet<>();
        int intentosFallidos = 0;

        System.out.println("\nComenzar, ingrece una letra: ");

        while (intentosFallidos < MAX_INTENTOS && !gano(progreso, palabraSecreta)) {

            mostrarMuneco(intentosFallidos);

            System.out.print("Palabra: ");
            for (char c : progreso) {
                System.out.print(c + " ");
            }

            System.out.println("\nIntentos fallidos: " + intentosFallidos + "/" + MAX_INTENTOS);
            System.out.println("Letras usadas: " + letrasUsadas);
            System.out.print("Ingresá una letra: ");
            String input = scanner.nextLine().trim().toLowerCase();

            if (input.length() != 1 || !Character.isLetter(input.charAt(0))) {
                System.out.println("⚠ Ingresá una sola letra válida.");
                continue;
            }

            char letra = input.charAt(0);

            if (letrasUsadas.contains(letra)) {
                System.out.println("⚠ Ya usaste esa letra, probá otra.");
                continue;
            }

            letrasUsadas.add(letra);

            if (palabraSecreta.indexOf(letra) >= 0) {
                for (int i = 0; i < palabraSecreta.length(); i++) {
                    if (palabraSecreta.charAt(i) == letra) {
                        progreso[i] = letra;
                    }
                }
                System.out.println("La letra '" + letra + "' está en la palabra!");
            } else {
                intentosFallidos++;
                System.out.println("✗ La letra '" + letra + "' no está en la palabra.");
            }
        }

        boolean gano = gano(progreso, palabraSecreta);
        terminarPartida(jugador, palabraObj, gano, palabraSecreta);
    }

    private boolean gano(char[] progreso, String palabraSecreta) {
        return new String(progreso).equals(palabraSecreta);
    }

    private void terminarPartida(Jugador jugador, Palabra palabraObj,
                                 boolean gano, String palabraSecreta) {
        mostrarMuneco(gano ? 0 : MAX_INTENTOS);

        if (gano) {
            System.out.println("🎉 ¡Ganaste! La palabra era: " + palabraSecreta);
            jugador.setPuntos(jugador.getPuntos() + 10);
            jugador.setRachaActual(jugador.getRachaActual() + 1);
            if (jugador.getRachaActual() > jugador.getMayorRacha()) {
                jugador.setMayorRacha(jugador.getRachaActual());
            }

        } else {
            System.out.println("💀 ¡Perdiste! La palabra era: " + palabraSecreta);
            jugador.setRachaActual(0);
        }

        System.out.println("Puntos: " + jugador.getPuntos() +
                " | Racha actual: " + jugador.getRachaActual() +
                " | Mayor racha: " + jugador.getMayorRacha());
        apiClient.guardarPartida(jugador.getNombre(), palabraObj.getId(), gano);
        apiClient.actualizarJugador(jugador);
    }

    private void mostrarMuneco(int intentosFallidos) {
        System.out.println("  +---+");
        System.out.println("  |   |");
        System.out.println("  |   " + (intentosFallidos >= 1 ? "O" : " "));
        System.out.println("  |  " + (intentosFallidos >= 3 ? "/" : " ") +
                (intentosFallidos >= 2 ? "|" : " ") +
                (intentosFallidos >= 4 ? "\\" : " "));
        System.out.println("  |  " + (intentosFallidos >= 5 ? "/" : " ") +
                " " +
                (intentosFallidos >= 6 ? "\\" : " "));
        System.out.println("  |");
        System.out.println("=========");
    }
}
