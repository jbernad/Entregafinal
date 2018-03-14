package com.example.jorgebr.pglfinal.Usuarios;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.jorgebr.pglfinal.Constantes.G;
import com.example.jorgebr.pglfinal.Pojos.Usuarios;
import com.example.jorgebr.pglfinal.Proveedor.UsuariosProveedor;
import com.example.jorgebr.pglfinal.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UsuariosInsertarActivity extends AppCompatActivity implements View.OnClickListener {
    EditText dni;
    EditText phone;
    EditText mascota;
    Button Btfoto,Btgaleria;
    ImageView Imgfoto;
    //permisos camara y galeria
    private final int PETICION_SACAR_FOTO = 1;
    private final int PETICION_GALERIA = 2;

    Bitmap foto;  //foto para insertar
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario_detalle);
        /*AÑADIMOS LA TOOLBAR*/
        Toolbar toolbar= (Toolbar)findViewById(R.id.toolbar_detalleactivity);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //flechita para ir al padre
        setTitle("INSERTAR USUARIO");

        foto = null;
        /*TOMAMOS LOS BOTONES*/
        Btfoto=(Button)findViewById(R.id.btncamara);
        Btgaleria=(Button)findViewById(R.id.btgaleria);
        Imgfoto=(ImageView)findViewById(R.id.camarita);
        //sobrecargamos los eventos
        Btfoto.setOnClickListener(this);
        Btgaleria.setOnClickListener(this);
    }

    /*AÑADIMOS PARA QUE APAREZCA EL BOTÓN DE GUARDAR*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuItem menuitem=menu.add(Menu.NONE, G.Guardar, Menu.NONE,"Guardar"); //sin grupos,identificador, da igual orden
        menuitem.setIcon(R.mipmap.ic_guardar);  //añadimos el icono
        menuitem.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS); //mostramos siempre el icono
        return super.onCreateOptionsMenu(menu);
    }

    /*MÉTODO PARA TRATAR QUE HACER AL PULSAR GUARDAR*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case G.Guardar: attemptGuardar();//llamamos a un métofo para guardar
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /*MÉTODO LLAMADO PARA GUARDAR DATOS*/
    private void attemptGuardar() {
        /*eSTE MÉTODO SE USA PARA VALIDAR EL FORMULARIO*/
        //variables que toman datos de sus campos
        EditText edittextNombre=(EditText)findViewById(R.id.TxtNombreUsuario);
        EditText edittextPasswd=(EditText)findViewById(R.id.Txtcontraseña);
        EditText editTextmail=(EditText)findViewById(R.id.Txtemail);
        dni = (EditText) findViewById(R.id.TxtDNI);
        phone = (EditText) findViewById(R.id.TxtPhone);
        mascota = (EditText) findViewById(R.id.Txtmascota);
        //borramos posibles mensajes que tengamos
        edittextNombre.setError(null);
        edittextPasswd.setError(null);
        editTextmail.setError(null);
        //tomamos los datos de los campos
        String nombre=String.valueOf(edittextNombre.getText());
        String passwd=String.valueOf(edittextPasswd.getText());
        String mail=String.valueOf(editTextmail.getText());
        String nif = String.valueOf(dni.getText());
        String telef = String.valueOf(phone.getText());
        String petname = String.valueOf(mascota.getText());
        //Empezamos la validación
        if (TextUtils.isEmpty(nombre)){  //no se insertó nombre
            edittextNombre.setError(getString(R.string.requerido));
            edittextNombre.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(passwd)){  //no se insertó nombre
            edittextPasswd.setError(getString(R.string.requerido));
            edittextPasswd.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(mail)){  //no se insertó nombre
            editTextmail.setError(getString(R.string.requerido));
            edittextNombre.requestFocus();
            return;
        }
        if (!validaEmail(mail)) {
            editTextmail.setError(getString(R.string.formatoIncorrecto));
            editTextmail.requestFocus(); //mantiene el foco
            return;  //vuelve a previa validacion
        }
        if (TextUtils.isEmpty(nif)){  //no se insertó dni
            dni.setError(getString(R.string.requerido));
            dni.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(telef)){  //no se insertó dni
            phone.setError(getString(R.string.requerido));
            phone.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(petname)){  //no se insertó dni
            mascota.setError(getString(R.string.requerido));
            mascota.requestFocus();
            return;
        }
        //creamos un nuevo cliente con los datos obtenidos
        Usuarios usuarios=new Usuarios(G.SIN_VALOR_INT,nombre,passwd,mail,nif,telef,petname,foto);
        //Creamos una nueva clase que se encarga de la inserción, se llama ClientesProveedor
        UsuariosProveedor.insert(getContentResolver(),usuarios,this);
        //Salimos de la actividad tras insertar
        finish();
    }


    /*******************FUNCIÓN QUE RECOGE EL INTENT DEL OTRO ACTIVITY Y RELLENA CAMPOS***************************************************/
    public static boolean validaEmail(String email) {
        //Expresión regular para validar un Email
        String PATTERN_EMAIL = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(PATTERN_EMAIL);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    /*SOBRECARGAMOS LA PULSACIÓN DEL BOTÓN*/
    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.btncamara: sacarFoto();
                break;
            case R.id.btgaleria: ElegirdeGaleria();
                break;
        }
    }

    /*intent implícito para hacer foto*/
    public void sacarFoto(){
        Intent intentfoto=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intentfoto,PETICION_SACAR_FOTO);
    }

    /*intent implícito para elegir foto de galeria*/
    private void ElegirdeGaleria() {
        Intent intentgaleria=new Intent(Intent.ACTION_GET_CONTENT);
        intentgaleria.addCategory(Intent.CATEGORY_OPENABLE);
        intentgaleria.setType("image/*");
        startActivityForResult(intentgaleria,PETICION_GALERIA);
    }

    /*MÉTODO QUE TIENE EN CUENTA LA PETICIÓN DE LA FOTO O EL ACCESO A LA GALERIA*/
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //REQUESTCODE ES CÓDIGO PETICIÓN, LA CONSTANTE, RESULTCODE OK O CANCELAR -->resultado
        switch (requestCode){
            case PETICION_SACAR_FOTO:   if (resultCode == RESULT_OK){
                foto=(Bitmap)data.getExtras().get("data");
                Imgfoto.setImageBitmap(foto); //lo convertimos a Bitmap
            } else
                Toast.makeText(this,"El usuario canceló la foto",Toast.LENGTH_SHORT).show();
                break;
            case PETICION_GALERIA:      if (resultCode == RESULT_OK){
                Uri uri = data.getData();
                Imgfoto.setImageURI(uri);
                foto = ((BitmapDrawable)Imgfoto.getDrawable()).getBitmap(); //añadimos la imagen
            }
            else
                Toast.makeText(this,"El usuario canceló acceso a galeria",Toast.LENGTH_SHORT).show();
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
