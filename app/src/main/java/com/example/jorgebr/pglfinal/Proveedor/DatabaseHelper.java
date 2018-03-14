package com.example.jorgebr.pglfinal.Proveedor;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by jorge on 10/03/2018.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    //CONSTRUCTOR
    public DatabaseHelper(Context context) {
        super(context, ProveedorDeContenido.getDatabaseName(), null, ProveedorDeContenido.getDatabaseVersion());
    }

    /*MÉTODO AL ABRIR*/
    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        //if (!db.isReadOnly()){
        db.execSQL("PRAGMA foreign_keys=ON;"); //Habilitamos la integridad referencial
        //}
    }

    /*MÉTODO AL CREAR*/
    @Override
    public void onCreate(SQLiteDatabase db) {
        /*creamos TABLA CLIENTES*/
        db.execSQL("Create table "
                + ProveedorDeContenido.getUsuariosTableName()
                + " ( _id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Contrato.Usuarios.USERNAME + " TEXT , "
                + Contrato.Usuarios.PASSUSER + " TEXT , "
                + Contrato.Usuarios.EMAILUSER + " TEXT , "
                + Contrato.Usuarios.DNI + " TEXT  , "
                + Contrato.Usuarios.PHONE + " TEXT , "
                + Contrato.Usuarios.PETNAME + " TEXT , "
                + Contrato.Usuarios.TYPEUSER + " TEXT ); "
        );
        /*creamos TABLA CITAS*/
        db.execSQL("Create table "
                + ProveedorDeContenido.getCitasTableName()
                + " ( _id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Contrato.Citas.FECHA + " TEXT , "
                + Contrato.Citas.HORA + " TEXT , "
                + Contrato.Citas.DNI + " TEXT ); "
        );
        /*creamos TABLA CITAS*/
        db.execSQL("Create table "
                + ProveedorDeContenido.getPagosTableName()
                + " ( _id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Contrato.Pagos.DNI + " TEXT  , "
                + Contrato.Pagos.METODO + " TEXT ); "
        );
        inicializarDatos(db); //inicializamos datos de ambas tablas
    }

    /*MÉTODO QUE DECIDE QUE HACER AL CAMBIAR DE VERSION*/
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + ProveedorDeContenido.getUsuariosTableName());
        db.execSQL("DROP TABLE IF EXISTS " + ProveedorDeContenido.getCitasTableName());
        db.execSQL("DROP TABLE IF EXISTS " + ProveedorDeContenido.getPagosTableName());
        onCreate(db);
    }

    /*MÉTODO DONDE METEMOS LOS VALORES INICIALES*/
    void inicializarDatos(SQLiteDatabase db){
        /*USUARIOS*/
        db.execSQL("INSERT INTO " + ProveedorDeContenido.getUsuariosTableName() +
                " (" + Contrato.Usuarios.USERNAME + "," + Contrato.Usuarios.PASSUSER + ","
                + Contrato.Usuarios.EMAILUSER + "," + Contrato.Usuarios.DNI + ","
                + Contrato.Usuarios.PHONE + "," + Contrato.Usuarios.PETNAME + ","
                + Contrato.Usuarios.TYPEUSER + ") " +
                "VALUES ('admin','admin','jbr1980es@hotmail.com','78497790d','659551719','Tommy','1')");        //valores de inserción del administrador
        db.execSQL("INSERT INTO " + ProveedorDeContenido.getUsuariosTableName() +
                " (" + Contrato.Usuarios.USERNAME + "," + Contrato.Usuarios.PASSUSER + ","
                + Contrato.Usuarios.EMAILUSER + "," + Contrato.Usuarios.DNI + ","
                + Contrato.Usuarios.PHONE + "," + Contrato.Usuarios.PETNAME + ","
                + Contrato.Usuarios.TYPEUSER + ") " +
                "VALUES ('jorge','jorge','jbr1980es@gmail.com','78787878d','928256161','Cocker','2')");     //valores de inserción cliente de ejemplo
        /*CITAS*/
        db.execSQL("INSERT INTO " + ProveedorDeContenido.getCitasTableName() +
                " (" + Contrato.Citas.FECHA + "," + Contrato.Citas.HORA + "," + Contrato.Citas.DNI +  ") " +
                "VALUES ('2014-03-19','14:33:41','78497790d')");     //valores de inserción cliente de ejemplo
        db.execSQL("INSERT INTO " + ProveedorDeContenido.getCitasTableName() +
                " (" + Contrato.Citas.FECHA + "," + Contrato.Citas.HORA + "," + Contrato.Citas.DNI +  ") " +
                "VALUES ('2014-03-19','13:36:41','78787878d')");     //valores de inserción cliente de ejemplo
        db.execSQL("INSERT INTO " + ProveedorDeContenido.getCitasTableName() +
                " (" + Contrato.Citas.FECHA + "," + Contrato.Citas.HORA + "," + Contrato.Citas.DNI +  ") " +
                "VALUES ('2014-03-19','13:33:41','78787878d')");     //valores de inserción cliente de ejemplo
        /*PAGOS*/
        db.execSQL("INSERT INTO " + ProveedorDeContenido.getPagosTableName() +
                " (" + Contrato.Pagos.DNI + "," + Contrato.Pagos.METODO +  ") " +
                "VALUES ('78787878d','Visa')");     //valores de inserción cliente de ejemplo
        db.execSQL("INSERT INTO " + ProveedorDeContenido.getPagosTableName() +
                " (" + Contrato.Pagos.DNI + "," + Contrato.Pagos.METODO +  ") " +
                "VALUES ('78497790d', 'Paypal')");     //valores de inserción cliente de ejemplo
    }
}
