package com.example.jorgebr.pglfinal.Proveedor;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;
import android.util.SparseArray;

/**
 * Created by jorge on 10/03/2018.
 */

public class ProveedorDeContenido extends ContentProvider {
    /***** DECLARACIÓN DE VARIABLES *****/
            /*TABLA USUARIOS*/
    private static final int USUARIOS_ONE_REG = 1;
    private static final int USUARIOS_ALL_REGS = 2;
    private static final int CITAS_ONE_REG = 3;
    private static final int CITAS_ALL_REGS = 4;
    private static final int PAGOS_ONE_REG = 5;
    private static final int PAGOS_ALL_REGS = 6;


    public SQLiteDatabase getSqlDB() {
        return sqlDB;
    }

    public void setSqlDB(SQLiteDatabase sqlDB) {
        this.sqlDB = sqlDB;
    }

    public DatabaseHelper getDbHelper() {
        return dbHelper;
    }

    public void setDbHelper(DatabaseHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    //variables necesarias para la BD
    private SQLiteDatabase sqlDB;
    public DatabaseHelper dbHelper;                             //probamos a crear DATABASEHELPER EN OTRO FICHERO
    public static final int INVALID_URI = -1;                   // Indicates an invalid content URI

    private static final String DATABASE_NAME = "ClinicaVet.db";
    private static final int DATABASE_VERSION = 8;

    /*MÉTODOS ÚTILES DE ACCESO A VARIABLES CONTENT PROVIDER*/

    public static String getDatabaseName() {
        return DATABASE_NAME;
    }            // nombre BD

    public static int getDatabaseVersion() {
        return DATABASE_VERSION;
    }         // version BD

    private static final String USUARIOS_TABLE_NAME = "Usuarios";               // Nombre de la tabla

    private static final String CITAS_TABLE_NAME = "Citas";                     // Nombre de la tabla

    private static final String PAGOS_TABLE_NAME = "Pagos";                     // Nombre de la tabla


    //getter para obtener el nombre de la tabla
    public static String getUsuariosTableName() {
        return USUARIOS_TABLE_NAME;
    }
    public static String getCitasTableName() { return CITAS_TABLE_NAME;    }
    public static String getPagosTableName() { return PAGOS_TABLE_NAME; }

    // Variables necesarias para los Urimatcher y los tipos mime
    private static final UriMatcher sUriMatcher;
    private static final SparseArray<String> sMimeTypes;

    static {
        sUriMatcher = new UriMatcher(0);              /*inicializamos sus valores*/
        sMimeTypes = new SparseArray<String>();

        /*URIMATCHER*/
        sUriMatcher.addURI(
                Contrato.AUTHORITY,
                USUARIOS_TABLE_NAME,
                USUARIOS_ALL_REGS);
        sUriMatcher.addURI(
                Contrato.AUTHORITY,
                USUARIOS_TABLE_NAME + "/#",
                USUARIOS_ONE_REG);
        sUriMatcher.addURI(
                Contrato.AUTHORITY,
                CITAS_TABLE_NAME,
                CITAS_ALL_REGS);
        sUriMatcher.addURI(
                Contrato.AUTHORITY,
                CITAS_TABLE_NAME + "/#",
                CITAS_ONE_REG);
        sUriMatcher.addURI(
                Contrato.AUTHORITY,
                PAGOS_TABLE_NAME,
                PAGOS_ALL_REGS);
        sUriMatcher.addURI(
                Contrato.AUTHORITY,
                PAGOS_TABLE_NAME + "/#",
                PAGOS_ONE_REG);

        /*TIPOS MIME*/
        sMimeTypes.put(
                USUARIOS_ALL_REGS,
                "vnd.android.cursor.dir/vnd." +
                        Contrato.AUTHORITY + "." + USUARIOS_TABLE_NAME);
        sMimeTypes.put(
                USUARIOS_ONE_REG,
                "vnd.android.cursor.item/vnd."+
                        Contrato.AUTHORITY + "." + USUARIOS_TABLE_NAME);
        sMimeTypes.put(
                CITAS_ALL_REGS,
                "vnd.android.cursor.dir/vnd." +
                        Contrato.AUTHORITY + "." + CITAS_TABLE_NAME);
        sMimeTypes.put(
                CITAS_ONE_REG,
                "vnd.android.cursor.item/vnd."+
                        Contrato.AUTHORITY + "." + CITAS_TABLE_NAME);
        sMimeTypes.put(
                PAGOS_ALL_REGS,
                "vnd.android.cursor.dir/vnd." +
                        Contrato.AUTHORITY + "." + PAGOS_TABLE_NAME);
        sMimeTypes.put(
                PAGOS_ONE_REG,
                "vnd.android.cursor.item/vnd."+
                        Contrato.AUTHORITY + "." + PAGOS_TABLE_NAME);
    }

    /*********MÉTODOS CONTENT PROVIDER*****************/
    /*CONSTRUCTOR*/
    public ProveedorDeContenido(){}

    @Override
    public String getType(Uri uri) {
        return null;
    }

    /*MÉTODO CREAR*/
    @Override
    public boolean onCreate() {
        dbHelper = new DatabaseHelper(getContext());
        return (dbHelper == null) ? false : true;
    }

    /*MÉTODO CONSULTA*/
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = null;
        /*********BUSCAMOS CORRESPONDENCIA URIMATCHER*****************/
        switch (sUriMatcher.match(uri)) {
            //TABLA USUARIOS
            case USUARIOS_ONE_REG:
                if (null == selection) selection = "";
                selection += Contrato.Usuarios._ID + " = "
                        + uri.getLastPathSegment();
                qb.setTables(USUARIOS_TABLE_NAME);
                break;
            case USUARIOS_ALL_REGS:
                if (TextUtils.isEmpty(sortOrder)) sortOrder =
                        Contrato.Usuarios._ID + " ASC";
                qb.setTables(USUARIOS_TABLE_NAME);
                break;
            //TABLA CITAS
            case CITAS_ONE_REG:
                if (null == selection) selection = "";
                selection += Contrato.Citas._ID + " = "
                        + uri.getLastPathSegment();
                qb.setTables(CITAS_TABLE_NAME);
                break;
            case CITAS_ALL_REGS:
                if (TextUtils.isEmpty(sortOrder)) sortOrder =
                        Contrato.Citas._ID + " ASC";
                qb.setTables(CITAS_TABLE_NAME);
                break;
            //TABLA PAGOS
            case PAGOS_ONE_REG:
                if (null == selection) selection = "";
                selection += Contrato.Citas._ID + " = "
                        + uri.getLastPathSegment();
                qb.setTables(PAGOS_TABLE_NAME);
                break;
            case PAGOS_ALL_REGS:
                if (TextUtils.isEmpty(sortOrder)) sortOrder =
                        Contrato.Citas._ID + " ASC";
                qb.setTables(PAGOS_TABLE_NAME);
                break;


        }
        Cursor c;
        c = qb.query(db, projection, selection, selectionArgs, null, null, sortOrder);
        c.setNotificationUri(getContext().getContentResolver(), uri);  //notifica a los suscritos
        return c;  //DEVUELVE EL CURSOR
    }

    /*INSERTAR*/
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        sqlDB = dbHelper.getWritableDatabase();
        String table = "";
        /*BUSCAMOS LAS CORRESPONDENCIAS*/
        switch (sUriMatcher.match(uri)) {
            case USUARIOS_ALL_REGS:            //PRIMERA TABLA USUARIOS
                table = USUARIOS_TABLE_NAME;
                break;
            case CITAS_ALL_REGS:            //PRIMERA CITAS
                table = CITAS_TABLE_NAME;
                break;
            case PAGOS_ALL_REGS:            //PRIMERA PAGOS
                table = PAGOS_TABLE_NAME;
                break;
        }
        long rowId = sqlDB.insert(table, "", values);  //insertamos el valor
        if (rowId > 0) {
            Uri rowUri = ContentUris.appendId(
                    uri.buildUpon(), rowId).build();
            getContext().getContentResolver().notifyChange(rowUri, null);  //notifica a suscritos
            return rowUri;
        }
        throw new SQLException("Failed to insert row into " + uri);
    }

    /*BORRAR*/
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        sqlDB = dbHelper.getWritableDatabase();
        String table = "";
        /*********BUSCAMOS CORRESPONDENCIA URIMATCHER*****************/
        switch (sUriMatcher.match(uri)) {
            //TABLA CLIENTES
            case USUARIOS_ONE_REG:
                if (null == selection) selection = "";
                selection += Contrato.Usuarios._ID + " = "
                        + uri.getLastPathSegment();
                table = USUARIOS_TABLE_NAME;
                break;
            case USUARIOS_ALL_REGS:
                table = USUARIOS_TABLE_NAME;
                break;
            //TABLA CITAS
            case CITAS_ONE_REG:
                if (null == selection) selection = "";
                selection += Contrato.Citas._ID + " = "
                        + uri.getLastPathSegment();
                table = CITAS_TABLE_NAME;
                break;
            case CITAS_ALL_REGS:
                table = CITAS_TABLE_NAME;
                break;
            //TABLA PAGOS
            case PAGOS_ONE_REG:
                if (null == selection) selection = "";
                selection += Contrato.Citas._ID + " = "
                        + uri.getLastPathSegment();
                table = PAGOS_TABLE_NAME;
                break;
            case PAGOS_ALL_REGS:
                table = PAGOS_TABLE_NAME;
                break;
        }
        int rows = sqlDB.delete(table, selection, selectionArgs);  //borramos de la BD
        if (rows > 0) {
            getContext().getContentResolver().notifyChange(uri, null); //notifica cambio a los suscritos
            return rows;
        }
        throw new SQLException("Failed to delete row into " + uri);
    }

    /*ACTUALIZAR*/
    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        sqlDB = dbHelper.getWritableDatabase();
        // insert record in user table and get the row number of recently inserted record
        String table = "";
        /*********BUSCAMOS CORRESPONDENCIA DE LOS URI*****************/
        switch (sUriMatcher.match(uri)) {
            //TABLA CLIENTES
            case USUARIOS_ONE_REG:
                if (null == selection) selection = "";
                selection += Contrato.Usuarios._ID + " = "
                        + uri.getLastPathSegment();
                table = USUARIOS_TABLE_NAME;
                break;
            case USUARIOS_ALL_REGS:
                table = USUARIOS_TABLE_NAME;
                break;
            //TABLA CITAS
            case CITAS_ONE_REG:
                if (null == selection) selection = "";
                selection += Contrato.Citas._ID + " = "
                        + uri.getLastPathSegment();
                table = CITAS_TABLE_NAME;
                break;
            case CITAS_ALL_REGS:
                table = CITAS_TABLE_NAME;
                break;
            //TABLA PAGOS
            case PAGOS_ONE_REG:
                if (null == selection) selection = "";
                selection += Contrato.Citas._ID + " = "
                        + uri.getLastPathSegment();
                table = PAGOS_TABLE_NAME;
                break;
            case PAGOS_ALL_REGS:
                table = PAGOS_TABLE_NAME;
                break;
        }
        int rows = sqlDB.update(table, values, selection, selectionArgs);  //actualizamos
        if (rows > 0) {
            getContext().getContentResolver().notifyChange(uri, null);  //notifica a suscritos los cambios
            return rows;
        }
        throw new SQLException("Failed to update row into " + uri);  //falló la actualización
    }
}
