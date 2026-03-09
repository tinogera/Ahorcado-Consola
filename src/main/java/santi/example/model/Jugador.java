package santi.example.model;

public class Jugador {
    private String usuario;
    private int puntos;
    private int rachaActual;
    private int mayorRacha;

    public String getNombre() { return usuario; }
    public void setNombre(String usuario) { this.usuario = usuario; }

    public int getPuntos() { return puntos; }
    public void setPuntos(int puntos) { this.puntos = puntos; }

    public int getRachaActual() { return rachaActual; }
    public void setRachaActual(int rachaActual) { this.rachaActual = rachaActual; }

    public int getMayorRacha() { return mayorRacha; }
    public void setMayorRacha(int mayorRacha) { this.mayorRacha = mayorRacha; }
}