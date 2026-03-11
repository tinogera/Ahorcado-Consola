package santi.example;

import java.util.Scanner;

public class    Main {
    private static Scanner scannermain;

    public static void main(String[] args) {
        scannermain = new Scanner(System.in);
        Juego juego = new Juego();
        juego.iniciar();
        System.out.println("Volver a juger?Si/s o No/n:");
        String respueta = scannermain.nextLine().trim();
        if (respueta.toLowerCase().equals("si") || respueta.toLowerCase().equals("s") ) {
            juego = new Juego();
            juego.iniciar();
        } else {
            System.out.println("Seguro que no queres jugar? (Si/s Para salir)");
            respueta = scannermain.nextLine().trim();
            if (respueta.toLowerCase().equals("Si") || respueta.toLowerCase().equals("S")) {
                System.out.println("Gracias por jugar ♥");
                System.exit(0);
            } else {
                juego = new Juego();
                juego.iniciar();
            }
        }
    }
}
