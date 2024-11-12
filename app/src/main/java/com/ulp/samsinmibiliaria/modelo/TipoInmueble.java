package com.ulp.samsinmibiliaria.modelo;

import java.io.Serializable;

public class TipoInmueble implements Serializable
{
    private int id;
    private String tipo;
    private boolean borrado;

    public TipoInmueble(int id,String tipo)
    {
        this.id = id;
        this.tipo = tipo;
    }
    public TipoInmueble() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public boolean getBorrado() {
        return borrado;
    }

    public void setBorrado(boolean borrado) {
        this.borrado = borrado;
    }

    @Override
    public String toString() {
        return tipo;
    }
}
