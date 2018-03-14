package com.example.jorgebr.pglfinal.Pojos;

/**
 * Created by jorge on 13/03/2018.
 */

public class Pago {
    /*ATRIBUTOS*/
    private int ID;
    String dni, metodo;

    /*CONSTRUCTORES*/
    public Pago(){}

    public Pago(int ID, String dni, String metodo) {
        this.ID = ID;
        this.dni = dni;
        this.metodo = metodo;
    }

    /*GETTER AND SETTER*/
    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getMetodo() {
        return metodo;
    }

    public void setMetodo(String metodo) {
        this.metodo = metodo;
    }
}
