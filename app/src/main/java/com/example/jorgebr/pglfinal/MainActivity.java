package com.example.jorgebr.pglfinal;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.jorgebr.pglfinal.Validaciones.Validaciones;
import com.example.jorgebr.pglfinal.MenusDrawer.MenuDrawerActivity;
import com.example.jorgebr.pglfinal.MenusDrawer.MenuEstandarActivity;
import com.example.jorgebr.pglfinal.Proveedor.Contrato;

public class
MainActivity extends AppCompatActivity implements View.OnClickListener {
    /*********DECLARAMOS VARIABLES PARA RECOGER VISTAS*****************/
    EditText editnombreusuario,editpassw,editmail;   //Datos tipo texto del formulario
    Button acceder,registrarse;             //Botones acceder y registrarse
    ImageButton contactmain;                //Botón de imagen para enviar al correo al admin
    private Cursor fila;                    //Opción con el cursor aquí
    int idkey;

    Intent intentadmin, intentestandar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*********AÑADIMOS ICONO A LA APPBAR*****************/
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_perro);

        /*********CAPTURAMOS LAS VISTAS NECESARIAS*****************/
        editnombreusuario = (EditText) findViewById (R.id.TxtNombreUsuarioMain);
        editpassw = (EditText) findViewById (R.id.TxtcontraseñaMain);
        editmail = (EditText) findViewById (R.id.TxtMailUser);
        acceder = (Button) findViewById (R.id.Btnlogin);
        registrarse = (Button) findViewById (R.id.BtnRegistro);
        contactmain = (ImageButton) findViewById (R.id.BtnContact);

        /*********SOBRECARGAMOS LA PULSACION DE LOS BOTONES*****************/
        acceder.setOnClickListener(this);
        registrarse.setOnClickListener(this);
        contactmain.setOnClickListener(this);
    }

    /******* APPBAR *******/
    /*MENÚ DEL APPBAR DE DATOS CLIENTES, COMÚN A TODOS LOS ACTIVITYS*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menumain=getMenuInflater();
        menumain.inflate(R.menu.menuayudamain, menu);
        return true;
    }

    /*********ACCIONES A REALIZAR SI SE PULSAN LOS ELEMENTOS DEL MENU APPBAR******************/
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id== R.id.ayudamain) {
            Intent ayudadatos = new Intent(MainActivity.this, AyudaMainActivity.class);
            startActivity(ayudadatos);
        }
        return super.onOptionsItemSelected(item);
    }

    /*MÉTODO PARA SOBRECARGAR LOS BOTONES*/
    @Override
    public void onClick(View btpulsado) {
        if ( btpulsado.getId() == R.id.BtnRegistro ){
            Intent intentregistro = new Intent(MainActivity.this, RegistroActivity.class);          //REGISTRO
            startActivity(intentregistro);
        }
        else{
            if ( btpulsado.getId() == R.id.BtnContact ){                                            //CONTACTO ADMINISTRADOR
                String email = editmail.getText().toString(); //obtenemos el texto de dicho campo
                if (Validaciones.validaEmail (email)) {                    //Enviamos por mail
                    Intent intentMail = new Intent(Intent.ACTION_SEND, Uri.parse(email)); //Campo De
                    //datos complementarios necesarios
                    intentMail.setType("plain/text");
                    intentMail.putExtra(Intent.EXTRA_SUBJECT, "Contactar con administrador");        //campo asunto
                    intentMail.putExtra(Intent.EXTRA_EMAIL, new String[]{"jbr1980es@hotmail.com"});  //cc lista de correos donde enviar
                    startActivity(Intent.createChooser(intentMail, "Elige cliente de correo"));      //añadimos la opcion para seleccionar el mail
                }else {
                    Toast.makeText(MainActivity.this, "El mail introducido no es válido", Toast.LENGTH_SHORT).show();
                    editmail.setText("");                       //limpiamos el campo
                }
            }else
                ValidaDatos();      //validamos los datos del login
        }
    }

    /*********MÉTODO QUE VALIDA EL FORMULARIO*****************/
    public void ValidaDatos(){
        editnombreusuario.setError(null);                         //inicializamos errores
        editpassw.setError(null);
        String nombre = editnombreusuario.getText().toString();   //Obtener valores de los campos
        String contraseña = editpassw.getText().toString();

        /*******************EMPEZAMOS VALIDACIONES************************************************/
        if (TextUtils.isEmpty(nombre)) {                                        //VACIO
            editnombreusuario.setError(getString(R.string.requerido));
            editnombreusuario.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(contraseña)) {
            editpassw.setError(getString(R.string.requerido));
            editpassw.requestFocus();
            return;
        }
        if (contraseña.length()<3){        //requerimos al menos 3 caracteres para la contraseña
            editpassw.setError(getString(R.string.Long_insuf));
            editpassw.requestFocus();
            return;
        }
        //COMPROBAMOS QUE LOS DATOS DEL USUARIO SEAN CORRECTOS
        if (ExisteUSer(nombre,contraseña)){
            if (nombre.equals("admin")) {
                intentadmin = new Intent(MainActivity.this, MenuDrawerActivity.class);  //Datos correctos saltamos al Menú
                intentadmin.putExtra("logueado",nombre);
                vaciarCampos();
                startActivity(intentadmin);
            }
            else{
                intentestandar = new Intent(MainActivity.this, MenuEstandarActivity.class);  //Datos correctos saltamos al Menú
                intentestandar.putExtra("clave",idkey);
                vaciarCampos();
                startActivity(intentestandar);
            }
        }
        vaciarCampos();
    }


    /*********COMPROBACIÓN DE SI EXISTE USUARIO******************/
    public boolean ExisteUSer(String nombre, String contraseña){
        ContentResolver contentresolver=getContentResolver();
        String[] campos = new String[] {Contrato.Usuarios._ID,Contrato.Usuarios.USERNAME, Contrato.Usuarios.PASSUSER};  //comprobamos campos usuario y contraseña
        String where = Contrato.Usuarios.USERNAME + " = " + "'" + nombre + "'";                       //condicion
        fila = contentresolver.query(Contrato.Usuarios.CONTENT_URI, campos, where, null,null);     //cursor
        if (fila.moveToFirst()) {
            idkey = fila.getInt(fila.getColumnIndex(Contrato.Usuarios._ID));//movemos cursor al primer elemento
            if (contraseña.equals(fila.getString(fila.getColumnIndex(Contrato.Usuarios.PASSUSER)))) {
                vaciarCampos();
                return true;
            } else {
                editpassw.setError(getString(R.string.incorrecta));
                editpassw.requestFocus();
                editpassw.setText("");
                return false;
            }
        }else {
            Toast.makeText(MainActivity.this, "Usuario No Existe", Toast.LENGTH_LONG).show();
            vaciarCampos();
            return false;
        }
    }

    /*VACIAR CAMPOS*/
    public void vaciarCampos(){
        editnombreusuario.setText("");
        editpassw.setText("");
    }
}
