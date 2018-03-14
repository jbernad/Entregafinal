package com.example.jorgebr.pglfinal.Pojos;

/**
 * Created by jorge on 10/03/2018.
 */

public class Citas {
    /*ATRIBUTOS*/
    private int ID;
    String fecha, hora, dni;

    /*Constructor por defecto*/
    public Citas() {
    }

    /*CONSTRUCTOR POR PARÁMETROS*/
    public Citas(int ID, String fecha, String hora, String dni) {
        this.ID = ID;
        this.fecha = fecha;
        this.hora = hora;
        this.dni = dni;
    }


    /*GETTER AND SETTER*/
    public int getID() {
        return ID;
    }                               //ID

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getFecha() {
        return fecha;
    }                     //FECHA

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }                       //HORA

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getDni() {
        return dni;
    }                        //DNI

    public void setDni(String dni) {
        this.dni = dni;
    }
}
