package com.uniquindio.mueveteuq.listener;

/**
 * Prueba Podometro
 * 1. Interfaz StepListener
 * Esta interfaz escuchará las alertas sobre pasos siendo detectados.
 */
public interface StepListener {

    public void step(long timeNs);
}
