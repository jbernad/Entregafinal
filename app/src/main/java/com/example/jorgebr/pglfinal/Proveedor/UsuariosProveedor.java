package com.example.jorgebr.pglfinal.Proveedor;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.widget.Toast;
import java.io.IOException;

import com.example.jorgebr.pglfinal.Imagenes.Utilidades;
import com.example.jorgebr.pglfinal.Pojos.Usuarios;
/**
 * Created by jorge on 14/11/2017.
 */
public class UsuariosProveedor {
    /*****INSERTAR USUARIO*****/
    public static void insert(ContentResolver contentresolver, Usuarios usuarios, Context contexto){
        Uri uri= Contrato.Usuarios.CONTENT_URI;
        ContentValues values= new ContentValues();                      //contendrá todos los valores que le vamos a pasar
        values.put(Contrato.Usuarios.USERNAME,usuarios.getNuser());    // añadimos datos obtenidos del objeto
        values.put(Contrato.Usuarios.PASSUSER,usuarios.getPass());
        values.put(Contrato.Usuarios.EMAILUSER,usuarios.getEmail());
        values.put(Contrato.Usuarios.DNI,usuarios.getDni());
        values.put(Contrato.Usuarios.PHONE,usuarios.getPhone());
        values.put(Contrato.Usuarios.PETNAME,usuarios.getMascota());
        values.put(Contrato.Usuarios.TYPEUSER,2);
        Uri uriresultado= contentresolver.insert(uri, values);  //Devuelve la ID
        //Toast.makeText(contexto, ""+uriresultado.getLastPathSegment(), Toast.LENGTH_SHORT).show();
        if (usuarios.getImagen()!=null) {
            //el contentresolver identifica al proveedor de contenido
            //guardamos en almacenamiento interno
            try {
                Utilidades.storeImage(usuarios.getImagen(), contexto, "img_" + uriresultado.getLastPathSegment() + ".jpg");
            } catch (IOException e) {
                Toast.makeText(contexto, "No se pudo guardar la imagen", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /*****INSERTAR BORRAR USUARIO*****/
    public static void delete (ContentResolver contentresolver,int usuarioid){
        Uri uri= Uri.parse(Contrato.Usuarios.CONTENT_URI+"/"+usuarioid);
        contentresolver.delete(uri, null, null);  //el contentresolver identifica al proveedor de contenido
    }

    /*****ACTUALIZAR USUARIO*****/
    public static void update(ContentResolver contentresolver,Usuarios usuarios, Context contexto){
        /*PRIMER PARÁMETRO*/
        Uri uri= Uri.parse(Contrato.Usuarios.CONTENT_URI+"/"+usuarios.getID());  //ID
        /*SEGUNDO PARÁMETRO*/
        ContentValues values= new ContentValues(); //contendrá todos los valores que le vamos a pasar
        values.put(Contrato.Usuarios.USERNAME,usuarios.getNuser());    // añadimos datos obtenidos del objeto
        values.put(Contrato.Usuarios.PASSUSER,usuarios.getPass());
        values.put(Contrato.Usuarios.EMAILUSER,usuarios.getEmail());
        values.put(Contrato.Usuarios.DNI,usuarios.getDni());
        values.put(Contrato.Usuarios.PHONE,usuarios.getPhone());
        values.put(Contrato.Usuarios.PETNAME,usuarios.getMascota());
        values.put(Contrato.Usuarios.TYPEUSER,usuarios.getTypeuser());
        //LLAMADA A UPDATE
        contentresolver.update(uri, values, null,null);
        if (usuarios.getImagen()!=null){
            try {
                Utilidades.storeImage(usuarios.getImagen(),contexto, "img_"+usuarios.getID()+".jpg");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /*****CONSULTAR USUARIO POR ID*****/
    static public Usuarios readRecord(ContentResolver contentresolver, int usuarioid){
        /*PRIMER PARÁMETRO*/
        Uri uri= Uri.parse(Contrato.Usuarios.CONTENT_URI+"/"+usuarioid);  //tomamos la Uri de la clase contrato aquí requerimos el número tb convertida por el int
        //CAMPOS QUE QUEREMOS QUE NOS DEVUELVA
        String[] proyection={
                Contrato.Usuarios.USERNAME,
                Contrato.Usuarios.PASSUSER,
                Contrato.Usuarios.EMAILUSER,
                Contrato.Usuarios.DNI,
                Contrato.Usuarios.PHONE,
                Contrato.Usuarios.PETNAME,
                Contrato.Usuarios.TYPEUSER
        };
        Cursor cursor=contentresolver.query (uri,proyection,null,null,null); //devuelve un Cursor
        if (cursor.moveToFirst()) {                  //encontramos datos lo devolvemos
            Usuarios usuarios=new Usuarios();
            usuarios.setID(usuarioid);
            usuarios.setNuser(cursor.getString(cursor.getColumnIndex(Contrato.Usuarios.USERNAME)));
            usuarios.setPass(cursor.getString(cursor.getColumnIndex(Contrato.Usuarios.PASSUSER)));
            usuarios.setEmail(cursor.getString(cursor.getColumnIndex(Contrato.Usuarios.EMAILUSER)));
            usuarios.setDni(cursor.getString(cursor.getColumnIndex(Contrato.Usuarios.DNI)));
            usuarios.setPhone(cursor.getString(cursor.getColumnIndex(Contrato.Usuarios.PHONE)));
            usuarios.setMascota(cursor.getString(cursor.getColumnIndex(Contrato.Usuarios.PETNAME)));
            usuarios.setTypeuser(cursor.getInt(cursor.getColumnIndex(Contrato.Usuarios.TYPEUSER)));
            return usuarios;  //lo encuentra lo devuelve
        }
        return null; //no lo encuentra     //no lo encuentra
    }
}
