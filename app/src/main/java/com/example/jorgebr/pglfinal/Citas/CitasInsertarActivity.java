package com.example.jorgebr.pglfinal.Citas;
import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.example.jorgebr.pglfinal.Constantes.G;
import com.example.jorgebr.pglfinal.Pojos.Citas;
import com.example.jorgebr.pglfinal.Proveedor.CitasProveedor;
import com.example.jorgebr.pglfinal.Proveedor.Contrato;
import com.example.jorgebr.pglfinal.R;

public class CitasInsertarActivity extends AppCompatActivity {
    EditText dni;
    EditText fecha;
    EditText hora;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cita_detalle);
        /*AÑADIMOS LA TOOLBAR*/
        Toolbar toolbar= (Toolbar)findViewById(R.id.toolbar_detalleactivity);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //flechita para ir al padre
        setTitle("INSERTAR CITA");
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
        EditText edittextFecha=(EditText)findViewById(R.id.editTextCitasFecha);
        EditText edittextHora=(EditText)findViewById(R.id.editTextCitasHora);
        EditText editTexDNI=(EditText)findViewById(R.id.editTextCitasDNI);
        //borramos posibles mensajes que tengamos
        edittextFecha.setError(null);
        edittextHora.setError(null);
        editTexDNI.setError(null);
        //tomamos los datos de los campos
        String fecha=String.valueOf(edittextFecha.getText());
        String hora=String.valueOf(edittextHora.getText());
        String dnicita=String.valueOf(editTexDNI.getText());
        //Empezamos la validación
        if (TextUtils.isEmpty(fecha)){  //no se insertó nombre
            edittextFecha.setError(getString(R.string.requerido));
            edittextFecha.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(hora)){  //no se insertó nombre
            edittextHora.setError(getString(R.string.requerido));
            edittextHora.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(dnicita)){  //no se insertó nombre
            editTexDNI.setError(getString(R.string.requerido));
            editTexDNI.requestFocus();
            return;
        }
        //Solo inserta si el cliente ya está en la BD
        if (ExisteDNI(dnicita)){
            //creamos un nuevo cliente con los datos obtenidos
            Citas citas=new Citas(G.SIN_VALOR_INT,fecha,hora,dnicita);
            //Creamos una nueva clase que se encarga de la inserción, se llama ClientesProveedor
            //COMPROBAMOS QUE LA CITA NO ESTÉ YA COGIDA
            if (CitasProveedor.consultaCitaLibre(getContentResolver(),fecha,hora)){
                CitasProveedor.insert(getContentResolver(),citas,getApplicationContext());
                Toast.makeText(getApplicationContext(),"Cita insertada",Toast.LENGTH_LONG).show();
                finish();
            }
            else
                Toast.makeText(getApplicationContext(),"Esa cita ya está ocupada",Toast.LENGTH_LONG).show();
        }else
            Toast.makeText(this,"No tenemos ese cliente en nuestra BD",Toast.LENGTH_SHORT).show();
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

    /*******************MÉTODO COMPRUEBA SI EXISTE EL DNI EN LA TABLA CLIENTES***************************************************/
    public boolean ExisteDNI(String dni){
        Cursor fila;
        ContentResolver contentresolver=getContentResolver();
        String[] campos = new String[] {Contrato.Usuarios.DNI};  //comprobamos campos usuario y contraseña
        String where = Contrato.Usuarios.DNI + " = " + "'" + dni + "'";                       //condicion
        fila= contentresolver.query(Contrato.Usuarios.CONTENT_URI, campos, where, null,null);     //cursor
        System.out.println(fila);
        if (fila.moveToFirst()){
            if (dni.equals(fila.getString(fila.getColumnIndex(Contrato.Usuarios.DNI))))
                return true;
        }
        return false;
    }
}
