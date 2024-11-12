package com.ulp.samsinmibiliaria.modelo;

import java.io.Serializable;

public class Contrato implements Serializable {
    private int id;
    private int inquilinoid;
    private int inmuebleid;
    private String fecha_inicio;
    private String fecha_final;
    private String fecha_correcta;
    private double monto;
    private boolean borrado;
    private Inquilino inquilino;
    private Inmueble inmueble;

    public Contrato(int id, int inquilinoid, int inmuebleid, String fecha_inicio, String fecha_final, String fecha_correcta, double monto, boolean borrado, Inquilino inquilino, Inmueble inmueble) {
        this.id = id;
        this.inquilinoid = inquilinoid;
        this.inmuebleid = inmuebleid;
        this.fecha_inicio = fecha_inicio;
        this.fecha_final = fecha_final;
        this.fecha_correcta = fecha_correcta;
        this.monto = monto;
        this.borrado = borrado;
        this.inquilino = inquilino;
        this.inmueble = inmueble;
    }

    // Getters y Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getInquilinoid() {
        return inquilinoid;
    }

    public void setInquilinoid(int inquilinoid) {
        this.inquilinoid = inquilinoid;
    }

    public int getInmuebleid() {
        return inmuebleid;
    }

    public void setInmuebleid(int inmuebleid) {
        this.inmuebleid = inmuebleid;
    }

    public String getFecha_inicio() {
        return fecha_inicio;
    }

    public void setFecha_inicio(String fecha_inicio) {
        this.fecha_inicio = fecha_inicio;
    }

    public String getFecha_final() {
        return fecha_final;
    }

    public void setFecha_final(String fecha_final) {
        this.fecha_final = fecha_final;
    }

    public String getFecha_correcta() {
        return fecha_correcta;
    }

    public void setFecha_correcta(String fecha_correcta) {
        this.fecha_correcta = fecha_correcta;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public boolean isBorrado() {
        return borrado;
    }

    public void setBorrado(boolean borrado) {
        this.borrado = borrado;
    }

    public Inquilino getInquilino() {
        return inquilino;
    }

    public void setInquilino(Inquilino inquilino) {
        this.inquilino = inquilino;
    }

    public Inmueble getInmueble() {
        return inmueble;
    }

    public void setInmueble(Inmueble inmueble) {
        this.inmueble = inmueble;
    }

    @Override
    public String toString() {
        return "Contrato{" +
                "id=" + id +
                ", inquilinoid=" + inquilinoid +
                ", inmuebleid=" + inmuebleid +
                ", fecha_inicio='" + fecha_inicio + '\'' +
                ", fecha_fin='" + fecha_final + '\'' +
                ", fecha_efectiva='" + fecha_correcta+ '\'' +
                ", monto=" + monto +
                ", borrado=" + borrado +
                ", inquilino=" + inquilino +
                ", inmueble=" + inmueble +
                '}';
    }
}
