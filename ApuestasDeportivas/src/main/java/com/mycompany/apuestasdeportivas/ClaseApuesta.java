/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.apuestasdeportivas;

import java.io.Serializable;

/**
 *
 * @author nelsonrivas
 */
public class ClaseApuesta implements Serializable {

private static final long serialVersionUID = 1L; 

private int id;         
private String fecha;         
private String apostador;      
private String evento; 
private String ganador;
private float monto;

    public ClaseApuesta(int id, String fecha, String apostador, String evento, String ganador, float monto) {
        this.id = id;
        this.fecha = fecha;
        this.apostador = apostador;
        this.evento = evento;
        this.ganador = ganador;
        this.monto = monto;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getApostador() {
        return apostador;
    }

    public void setApostador(String apostador) {
        this.apostador = apostador;
    }

    public String getEvento() {
        return evento;
    }

    public void setEvento(String evento) {
        this.evento = evento;
    }

    public String getGanador() {
        return ganador;
    }

    public void setGanador(String ganador) {
        this.ganador = ganador;
    }

    public float getMonto() {
        return monto;
    }

    public void setMonto(float monto) {
        this.monto = monto;
    }

    @Override
    public String toString() {
        return "ClaseApuesta{" + "id=" + id + ", fecha=" + fecha + ", apostador=" + apostador + ", evento=" + evento + ", ganador=" + ganador + ", monto=" + monto + '}';
    }


}
