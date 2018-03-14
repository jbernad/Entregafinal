package com.example.jorgebr.pglfinal;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import  com.example.jorgebr.pglfinal.Proveedor.UsuariosProveedor;
import com.example.jorgebr.pglfinal.Pojos.Usuarios;
import com.example.jorgebr.pglfinal.Proveedor.Contrato;
import com.example.jorgebr.pglfinal.Constantes.G;
import com.example.jorgebr.pglfinal.Validaciones.Validaciones;

import java.util.regex.Pattern;

public class RegistroActivity extends AppCompatActivity {
    /*********DECLARACIÓN DE VARIABLES*****************/
    EditText txtnuser,txtpasswd,txtmail,txtdni,txtphone,txtnomdog;    //variables para coger los datos del formulario de registro
    Button BtnRegregistrarse;
    boolean correcto;
    String nombre,contraseña,mail,phone,dni,mascota;

    private Cursor fila;          //Cursor
    Context contexto=this;        //Contexto

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        /*********TÍTULO PARA EL ACTIVITY******************/
        setTitle("REGISTRO DE USUARIO");

        /*********AÑADIMOS ICONO A LA APPBAR*****************/
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_perro);

        /*********CAPTURAMOS LAS VISTAS NECESARIAS*****************/
        txtnuser = (EditText)findViewById(R.id.TxtNombreUsuarioReg);
        txtpasswd = (EditText)findViewById(R.id.TxtcontraseñaReg);
        txtmail = (EditText)findViewById(R.id.TxtemailReg);
        txtdni = (EditText)findViewById(R.id.TxtDNIReg);
        txtphone = (EditText)findViewById(R.id.TxtPhoneReg);
        txtnomdog = (EditText)findViewById(R.id.Txtmascota);
        BtnRegregistrarse = (Button)findViewById(R.id.bt);
        BtnRegregistrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ValidaDatos();
                if (correcto == true){    //INSERTAMOS EL DATO EN USUARIOS DE LA BD
                    Usuarios usuarios = new Usuarios(G.SIN_VALOR_INT,nombre,contraseña,mail,dni,phone,mascota,null);       //creamos un nuevo cliente con los datos obtenidos
                    UsuariosProveedor.insert(getContentResolver(),usuarios,contexto);                      //insertamos usando el proveedor de contenidos
                    Toast.makeText(RegistroActivity.this, "Dato insertado", Toast.LENGTH_LONG).show();
                    finish();                                                //Salimos de la actividad tras insertar
                }
            }
        });
    }

    /*******************SELECCION DE FLECHA ATRAS, CIERRA EL ACTIVITY REGISTRO SIN RECORDAR DATOS*******************/
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: finish();  //volver atras cerramos el Activity
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /*******************FUNCIÓN PARA COMPROBAR SI SÓLO HAY LETRAS O ESPACIOS*************************************************/
    private boolean Comprueba_Nombre (String entrada){
        if (Pattern.matches("[[A-Za-z]+\\s{0,1}[A-Za-z]*]+",entrada)){  //cumple patrón
            if (Validaciones.sonEspacios(entrada) == true) {                   //buscamos que sea sólo una cadena de espacios
                Toast.makeText(RegistroActivity.this, "Ha introducido cadena sólo de espacios", Toast.LENGTH_SHORT).show();
                return false;         //cadena sólo de espacios
            }
            else
                return true;          //cadena válida
        }
        else
            return false;  //no cumple patron por tener caracteres no permitidos
    }



    /*********MÉTODO QUE VALIDA EL FORMULARIO*****************/
    public void ValidaDatos(){
        correcto=false;
        txtnuser.setError(null);                         //inicializamos errores
        txtpasswd.setError(null);
        txtmail.setError(null);
        txtdni.setError(null);
        txtphone.setError(null);
        txtnomdog.setError(null);
        nombre = txtnuser.getText().toString();         //obtenemos valores de los campos
        contraseña = txtpasswd.getText().toString();
        mail = txtmail.getText().toString();
        dni = txtdni.getText().toString();
        phone = txtphone.getText().toString();
        mascota = txtnomdog.getText().toString();
        /*******************EMPEZAMOS VALIDACIONES************************************************/
        if (TextUtils.isEmpty(nombre)) {
            txtnuser.setError(getString(R.string.requerido));
            txtnuser.requestFocus();
            return;
        }
        if (Comprueba_Nombre(nombre)==false){
            txtpasswd.setError(getString(R.string.formatoIncorrecto));
            txtpasswd.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(contraseña)) {
            txtpasswd.setError(getString(R.string.requerido));
            txtpasswd.requestFocus();
            return;
        }
        if (Validaciones.sonEspacios(contraseña)){
            txtpasswd.setError(getString(R.string.soloespacios));
            txtpasswd.requestFocus();
            return;
        }
        if (contraseña.length()<3){          //requerimos al menos 3 caracteres para la contraseña
            txtpasswd.setError(getString(R.string.Long_insuf));
            txtpasswd.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(mail)) {
            txtmail.setError(getString(R.string.requerido));
            txtmail.requestFocus();
            return;
        }
        if (!Validaciones.validaEmail(mail)) {             //comprobamos que el correo tengo formato de correo
            txtmail.setError(getString(R.string.formatoIncorrecto));
            txtmail.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(dni)) {
            txtdni.setError(getString(R.string.requerido));
            txtdni.requestFocus();
            return;
        }
        if (!Validaciones.Comprueba_dni(dni)) {             //comprobamos que el correo tengo formato de correo
            txtdni.setError(getString(R.string.formatoIncorrecto));
            txtdni.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(phone)) {
            txtphone.setError(getString(R.string.requerido));
            txtphone.requestFocus();
            return;
        }
        if (!Validaciones.Comprueba_Tfno(phone)) {             //comprobamos que el correo tengo formato de correo
            txtphone.setError(getString(R.string.formatoIncorrecto));
            txtphone.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(mascota)) {
            txtnomdog.setError(getString(R.string.requerido));
            txtnomdog.requestFocus();
            return;
        }
        /*******************COMPROBAMOS SI EL USUARIO YA EXISTE CON UNA CONSULTA************************************************/
        ContentResolver contentresolver = getContentResolver();
        String[] campos = new String[] {Contrato.Usuarios.USERNAME, Contrato.Usuarios.PASSUSER};
        String where = Contrato.Usuarios.USERNAME + " = " + "'"+nombre+"'";
        fila= contentresolver.query(Contrato.Usuarios.CONTENT_URI, campos, where, null,null);
        if (fila.moveToFirst()) {
            Toast.makeText(RegistroActivity.this, "Usuario EXISTENTE", Toast.LENGTH_LONG).show();
        }else
            correcto = true;                //Usuario válido
    }
}
