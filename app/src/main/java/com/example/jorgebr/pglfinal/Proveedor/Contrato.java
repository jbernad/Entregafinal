package com.example.jorgebr.pglfinal.Proveedor;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by jorge on 10/03/2018.
 */
public class Contrato {
    //VARIABLE QUE REPRESENTA LA AUTORIDAD
    public static final String AUTHORITY = "com.example.jorgebr.proyectofinal.Proveedor.ProveedorDeContenido"; //Identifica proveedor de contenidos

    /*********TABLA USUARIOS APLICACIÓN*****************/
    public static final class Usuarios implements BaseColumns {
        public static final Uri CONTENT_URI = Uri.parse ("content://" + AUTHORITY + "/Usuarios");
        //COLUMNAS DE LA TABLA DE USUARIOS
        public static final String USERNAME = "UserName";
        public static final String PASSUSER = "PassUser";
        public static final String EMAILUSER = "EmailUser";
        public static final String DNI = "Dni";
        public static final String PHONE = "Phone";
        public static final String PETNAME = "Mascota";
        public static final String TYPEUSER = "Typeuser";
        //public static final String IMAGEN = "Imagen";
    }

    /*********TABLA CITAS APLICACIÓN*****************/
    public static final class Citas implements BaseColumns {
        public static final Uri CONTENT_URI = Uri.parse ("content://" + AUTHORITY + "/Citas");

        //COLUMNAS DE LA TABLA DE CITAS
        public static final String FECHA = "Fecha";
        public static final String HORA = "Hora";
        public static final String DNI = "Dni";
    }

    /*********TABLA CITAS APLICACIÓN*****************/
    public static final class Pagos implements BaseColumns {
        public static final Uri CONTENT_URI = Uri.parse ("content://" + AUTHORITY + "/Pagos");

        //COLUMNAS DE LA TABLA DE CITAS
        public static final String DNI = "Dni";
        public static final String METODO = "Metodo";
    }
}
