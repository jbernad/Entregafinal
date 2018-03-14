package com.example.jorgebr.pglfinal.Proveedor;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.widget.Toast;

import com.example.jorgebr.pglfinal.Pojos.Pago;

/**
 * Created by jorge on 13/03/2018.
 */

public class PagosProveedor {
    /*****INSERTAR USUARIO*****/
    public static void insert(ContentResolver contentresolver, Pago pagos, Context contexto){
        Uri uri= Contrato.Pagos.CONTENT_URI;
        ContentValues values= new ContentValues();                      //contendrá todos los valores que le vamos a pasar
        values.put(Contrato.Pagos.DNI,pagos.getDni());
        values.put(Contrato.Pagos.METODO,pagos.getMetodo());
        Uri uriresultado= contentresolver.insert(uri, values);  //Devuelve la ID
        Toast.makeText(contexto, ""+uriresultado.getLastPathSegment(), Toast.LENGTH_SHORT).show();
    }

    /*****INSERTAR BORRAR USUARIO*****/
    public static void delete (ContentResolver contentresolver,int pagosid){
        Uri uri= Uri.parse(Contrato.Pagos.CONTENT_URI+"/"+pagosid);
        contentresolver.delete(uri, null, null);  //el contentresolver identifica al proveedor de contenido
    }

    /*****ACTUALIZAR USUARIO*****/
    public static void update(ContentResolver contentresolver, Pago pagos, Context contexto){
        /*PRIMER PARÁMETRO*/
        Uri uri= Uri.parse(Contrato.Pagos.CONTENT_URI+"/"+pagos.getID());  //ID
        /*SEGUNDO PARÁMETRO*/
        ContentValues values= new ContentValues(); //contendrá todos los valores que le vamos a pasar
        values.put(Contrato.Pagos.DNI,pagos.getDni());
        values.put(Contrato.Pagos.METODO,pagos.getMetodo());
        //LLAMADA A UPDATE
        contentresolver.update(uri, values, null,null);
    }

    /*****CONSULTAR USUARIO POR ID*****/
    static public Pago readRecord(ContentResolver contentresolver, int pagosid){
        /*PRIMER PARÁMETRO*/
        Uri uri= Uri.parse(Contrato.Pagos.CONTENT_URI+"/"+pagosid);  //tomamos la Uri de la clase contrato aquí requerimos el número tb convertida por el int
        //CAMPOS QUE QUEREMOS QUE NOS DEVUELVA
        String[] proyection={
                Contrato.Pagos.DNI,
                Contrato.Pagos.METODO
        };
        Cursor cursor=contentresolver.query (uri,proyection,null,null,null); //devuelve un Cursor
        if (cursor.moveToFirst()) {                  //encontramos datos lo devolvemos
            Pago pagos=new Pago();
            pagos.setID(pagosid);
            pagos.setDni(cursor.getString(cursor.getColumnIndex(Contrato.Pagos.DNI)));
            pagos.setMetodo(cursor.getString(cursor.getColumnIndex(Contrato.Pagos.METODO)));
            return pagos;  //lo encuentra lo devuelve
        }
        return null; //no lo encuentra     //no lo encuentra
    }

    /*****CONSULTAR PAGOS POR DNI*****/
    static public Cursor readDNI(ContentResolver contentresolver, String dni ){
        /*PRIMER PARÁMETRO*/
        Uri uri= Contrato.Pagos.CONTENT_URI;  //tomamos la Uri de la clase contrato aquí requerimos el número tb convertida por el int
        //CAMPOS QUE QUEREMOS QUE NOS DEVUELVA
        String[] proyection={
                Contrato.Pagos.DNI,
                Contrato.Pagos.METODO
        };
        String where = Contrato.Pagos.DNI + " = " + "'" + dni + "'";                       //condicion
        Cursor cursor=contentresolver.query (uri,proyection,where,null,null); //devuelve un Cursor
        return cursor; //no lo encuentra     //no lo encuentra
    }
}
