package com.example.jorgebr.pglfinal.Pojos;

import android.graphics.Bitmap;

/**
 * Created by jorge on 10/03/2018.
 */

public class Usuarios {
    /*ATRIBUTOS*/
    private int ID;
    String Nuser, Pass, Email, Dni, Phone, Mascota;
    int Typeuser;
    private Bitmap imagen;

    /*CONSTRUCTOR POR DEFECTO*/
    public Usuarios() {
    }

    /*CONSTRUCTOR POR PARÁMETROS*/
    public Usuarios(int ID, String nuser, String pass, String email, String dni, String phone, String mascota,Bitmap imagen) {
        this.ID = ID;
        Nuser = nuser;
        Pass = pass;
        Email = email;
        Dni = dni;
        Phone = phone;
        Mascota = mascota;
        this.imagen=imagen;
        Typeuser = 2;         //por defecto será siempre 2 usuario estandar
    }

    /*GETTER AND SETTER*/
    public int getID() {
        return ID;
    }                       //ID

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getNuser() {
        return Nuser;
    }             //NUSER

    public void setNuser(String nuser) {
        Nuser = nuser;
    }

    public String getPass() {
        return Pass;
    }              //PASSWORD

    public void setPass(String pass) {
        Pass = pass;
    }

    public String getEmail() {
        return Email;
    }            //EMAIL

    public void setEmail(String email) {
        Email = email;
    }

    public String getDni() {
        return Dni;
    }                //DNI

    public void setDni(String dni) {
        Dni = dni;
    }

    public String getPhone() {
        return Phone;
    }             //TELEFONO

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getMascota() {
        return Mascota;
    }        //MASCOTA

    public void setMascota(String mascota) {
        Mascota = mascota;
    }

    public int getTypeuser() {
        return Typeuser;
    }          //TIPO USUARIO

    public void setTypeuser(int typeuser) {
        Typeuser = typeuser;
    }

    public Bitmap getImagen() {
        return imagen;
    }         //FOTO

    public void setImagen(Bitmap imagen) {
        this.imagen = imagen;
    }
}
