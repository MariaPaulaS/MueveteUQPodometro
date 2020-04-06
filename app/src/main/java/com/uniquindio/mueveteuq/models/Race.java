package com.uniquindio.mueveteuq.models;

public class Race {

    private float distancia;
    private int pasos;
    private float calorias;
    private int puntos;
    private String nicknameUsuario;


    public Race() {

    }


    public Race(float distancia, int pasos, float calorias, int puntos, String nicknameUsuario) {

        this.distancia = distancia;
        this.pasos = pasos;
        this.calorias = calorias;
        this.puntos = puntos;
        this.nicknameUsuario = nicknameUsuario;
    }


    public float getDistancia() {
        return distancia;
    }

    public void setDistancia(float distancia) {
        this.distancia = distancia;
    }

    public int getPasos() {
        return pasos;
    }

    public void setPasos(int pasos) {
        this.pasos = pasos;
    }

    public float getCalorias() {
        return calorias;
    }

    public void setCalorias(float calorias) {
        this.calorias = calorias;
    }

    public int getPuntos() {
        return puntos;
    }

    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }

    public String getNicknameUsuario() {
        return nicknameUsuario;
    }

    public void setNicknameUsuario(String nicknameUsuario) {
        this.nicknameUsuario = nicknameUsuario;
    }
}
