package com.example.jorgebr.pglfinal.Proveedor;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.widget.Toast;
import com.example.jorgebr.pglfinal.Pojos.Citas;
/**
 * Created by jorge on 11/03/2018.
 */

public class CitasProveedor {
    /*****INSERTAR USUARIO*****/
    public static void insert(ContentResolver contentresolver, Citas citas, Context contexto){
        Uri uri= Contrato.Citas.CONTENT_URI;
        ContentValues values= new ContentValues();                      //contendrá todos los valores que le vamos a pasar
        values.put(Contrato.Citas.FECHA,citas.getFecha());    // añadimos datos obtenidos del objeto
        values.put(Contrato.Citas.HORA,citas.getHora());
        values.put(Contrato.Citas.DNI,citas.getDni());
        Uri uriresultado= contentresolver.insert(uri, values);  //Devuelve la ID
        Toast.makeText(contexto, ""+uriresultado.getLastPathSegment(), Toast.LENGTH_SHORT).show();
    }

    /*****INSERTAR BORRAR USUARIO*****/
    public static void delete (ContentResolver contentresolver,int citasid){
        Uri uri= Uri.parse(Contrato.Citas.CONTENT_URI+"/"+citasid);
        contentresolver.delete(uri, null, null);  //el contentresolver identifica al proveedor de contenido
    }

    /*****ACTUALIZAR USUARIO*****/
    public static void update(ContentResolver contentresolver, Citas citas, Context contexto){
        /*PRIMER PARÁMETRO*/
        Uri uri= Uri.parse(Contrato.Citas.CONTENT_URI+"/"+citas.getID());  //ID
        /*SEGUNDO PARÁMETRO*/
        ContentValues values= new ContentValues(); //contendrá todos los valores que le vamos a pasar
        values.put(Contrato.Citas.FECHA,citas.getFecha());    // añadimos datos obtenidos del objeto
        values.put(Contrato.Citas.HORA,citas.getHora());
        values.put(Contrato.Citas.DNI,citas.getDni());
        //LLAMADA A UPDATE
        contentresolver.update(uri, values, null,null);
    }

    /*****CONSULTAR USUARIO POR ID*****/
    static public Citas readRecord(ContentResolver contentresolver, int citasid){
        /*PRIMER PARÁMETRO*/
        Uri uri= Uri.parse(Contrato.Citas.CONTENT_URI+"/"+citasid);  //tomamos la Uri de la clase contrato aquí requerimos el número tb convertida por el int
        //CAMPOS QUE QUEREMOS QUE NOS DEVUELVA
        String[] proyection={
                Contrato.Citas.FECHA,
                Contrato.Citas.HORA,
                Contrato.Citas.DNI
        };
        Cursor cursor=contentresolver.query (uri,proyection,null,null,null); //devuelve un Cursor
        if (cursor.moveToFirst()) {                  //encontramos datos lo devolvemos
            Citas citas=new Citas();
            citas.setID(citasid);
            citas.setFecha(cursor.getString(cursor.getColumnIndex(Contrato.Citas.FECHA)));
            citas.setHora(cursor.getString(cursor.getColumnIndex(Contrato.Citas.HORA)));
            citas.setDni(cursor.getString(cursor.getColumnIndex(Contrato.Citas.DNI)));
            return citas;  //lo encuentra lo devuelve
        }
        return null; //no lo encuentra     //no lo encuentra
    }

    /*****CONSULTAR CITAS POR DNI*****/
    static public Cursor readDNI(ContentResolver contentresolver, String dni ){
        /*PRIMER PARÁMETRO*/
        Uri uri= Contrato.Citas.CONTENT_URI;  //tomamos la Uri de la clase contrato aquí requerimos el número tb convertida por el int
        //CAMPOS QUE QUEREMOS QUE NOS DEVUELVA
        String[] proyection={
                Contrato.Citas._ID,
                Contrato.Citas.FECHA,
                Contrato.Citas.HORA,
                Contrato.Citas.DNI
        };
        String where = Contrato.Citas.DNI + " = " + "'" + dni + "'";                       //condicion
        Cursor cursor=contentresolver.query (uri,proyection,where,null,null); //devuelve un Cursor
        return cursor; //no lo encuentra     //no lo encuentra
    }

    /*****CONSULTAR CITAS POR DNI*****/
    static public Cursor readfecha(ContentResolver contentresolver, String fecha ){
        /*PRIMER PARÁMETRO*/
        Uri uri= Contrato.Citas.CONTENT_URI;  //tomamos la Uri de la clase contrato aquí requerimos el número tb convertida por el int
        //CAMPOS QUE QUEREMOS QUE NOS DEVUELVA
        String[] proyection={
                Contrato.Citas._ID,
                Contrato.Citas.FECHA,
                Contrato.Citas.HORA,
                Contrato.Citas.DNI
        };
        String where = Contrato.Citas.FECHA + " = " + "'" + fecha + "'";                             //condicion
        Cursor cursor=contentresolver.query (uri,proyection,where,null,null);
        return cursor;                                                                               //devuelve un Cursor
    }

    /*****CONSULTAR CITAS POR FECHA Y HORA*****/
    static public boolean consultaCitaLibre(ContentResolver contentresolver, String fec, String hora ){
        Uri uri= Contrato.Citas.CONTENT_URI;
        String[] proyection={
                Contrato.Citas.FECHA,
                Contrato.Citas.HORA,
        };
        String where = Contrato.Citas.FECHA + " = " + "'" + fec + "' AND " + Contrato.Citas.HORA + " = " + "'" + hora + "'";                   //condicion
        Cursor cursor=contentresolver.query (uri,proyection,where,null,null);
        if (cursor.moveToFirst() )
            return false;          //libre
        else
             return true;          //esta ocupada
    }

}
