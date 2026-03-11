package santi.example.model;

public class Jugador {
    private String nombre;
    private int puntos;
    private int rachaActual;
    private int mayorRacha;

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre= nombre; }


    public int getPuntos() { return puntos; }
    public void setPuntos(int puntos) { this.puntos = puntos; }

    public int getRachaActual() { return rachaActual; }
    public void setRachaActual(int rachaActual) { this.rachaActual = rachaActual; }

    public int getMayorRacha() { return mayorRacha; }
    public void setMayorRacha(int mayorRacha) { this.mayorRacha = mayorRacha; }
}