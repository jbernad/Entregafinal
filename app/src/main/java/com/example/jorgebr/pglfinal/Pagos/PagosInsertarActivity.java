package com.example.jorgebr.pglfinal.Pagos;

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

import com.example.jorgebr.pglfinal.Constantes.G;
import com.example.jorgebr.pglfinal.Pojos.Pago;
import com.example.jorgebr.pglfinal.Proveedor.Contrato;
import com.example.jorgebr.pglfinal.Proveedor.PagosProveedor;
import com.example.jorgebr.pglfinal.R;


public class PagosInsertarActivity extends AppCompatActivity {
    EditText dni,metodo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pago_detalle);
        /*AÑADIMOS LA TOOLBAR*/
        Toolbar toolbar= (Toolbar)findViewById(R.id.toolbar_detalleactivity);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //flechita para ir al padre
        setTitle("INSERTAR PAGO");
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
        EditText edittextmetodo=(EditText)findViewById(R.id.editTextPagosMetodo);
        EditText editTexDNI=(EditText)findViewById(R.id.editTextPagosDNI);
        //borramos posibles mensajes que tengamos
        edittextmetodo.setError(null);
        editTexDNI.setError(null);
        //tomamos los datos de los campos
        String metodo=String.valueOf(edittextmetodo.getText());
        String dnipago=String.valueOf(editTexDNI.getText());
        //Empezamos la validación
        if (TextUtils.isEmpty(dnipago)){  //no se insertó nombre
            editTexDNI.setError(getString(R.string.requerido));
            editTexDNI.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(metodo)){  //no se insertó nombre
            edittextmetodo.setError(getString(R.string.requerido));
            edittextmetodo.requestFocus();
            return;
        }
        //Solo inserta si el cliente ya está en la BD
        if (ExisteDNI(dnipago)){
            //creamos un nuevo cliente con los datos obtenidos
            Pago pagos=new Pago(G.SIN_VALOR_INT,dnipago,metodo);
            //Creamos una nueva clase que se encarga de la inserción, se llama ClientesProveedor
            PagosProveedor.insert(getContentResolver(),pagos,this);
            //Salimos de la actividad tras insertar
            finish();
        }else
            Toast.makeText(this,"No tenemos ese cliente en nuestra BD",Toast.LENGTH_SHORT).show();

    }



    /*******************MÉTODO COMPRUEBA SI EXISTE EL DNI EN LA TABLA CLIENTES***************************************************/
    public boolean ExisteDNI(String dni){
        Cursor fila;
        ContentResolver contentresolver=getContentResolver();
        String[] campos = new String[] {Contrato.Pagos.DNI};  //comprobamos campos usuario y contraseña
        String where = Contrato.Pagos.DNI + " = " + "'" + dni + "'";                       //condicion
        fila= contentresolver.query(Contrato.Pagos.CONTENT_URI, campos, where, null,null);     //cursor
        System.out.println(fila);
        if (fila.moveToFirst()){
            if (dni.equals(fila.getString(fila.getColumnIndex(Contrato.Pagos.DNI))))
                return true;
        }
        return false;
    }
}
