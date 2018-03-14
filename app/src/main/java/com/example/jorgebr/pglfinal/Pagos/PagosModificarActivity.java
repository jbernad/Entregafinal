/*ACTIVITY CREADO PARA INSERTAR*/
package com.example.jorgebr.pglfinal.Pagos;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.example.jorgebr.pglfinal.Constantes.G;
import com.example.jorgebr.pglfinal.Pojos.Pago;
import com.example.jorgebr.pglfinal.Proveedor.Contrato;
import com.example.jorgebr.pglfinal.Proveedor.PagosProveedor;
import com.example.jorgebr.pglfinal.R;



public class PagosModificarActivity extends AppCompatActivity {
    EditText dnipago,metodo;
    int idpago;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pago_detalle);
        /*AÑADIMOS LA TOOLBAR*/
        Toolbar toolbar= (Toolbar)findViewById(R.id.toolbar_detalleactivity);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //flechita para ir al padre
        setTitle("MODIFICAR PAGOS");
        //variables que toman datos de sus campos
        dnipago = (EditText) findViewById(R.id.editTextPagosDNI);
        metodo = (EditText) findViewById(R.id.editTextPagosMetodo);



        idpago= this.getIntent().getExtras().getInt(Contrato.Pagos._ID); //obtenemos el ID
        //Leemos el registro
        Pago pagos= PagosProveedor.readRecord(getContentResolver(),idpago);
        //ya tenemos el cliente y debemos cargarlo
        dnipago.setText(pagos.getDni());
        metodo.setText(pagos.getMetodo());
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
            case G.Guardar: attemptEditar();//llamamos a un métofo para guardar
                            break;
        }
        return super.onOptionsItemSelected(item);
    }

    /*MÉTODO LLAMADO PARA EDITAR DATOS*/
    private void attemptEditar() {
        //borramos posibles mensajes que tengamos
        dnipago.setError(null);
        metodo.setError(null);
        //tomamos los datos de los campos
        String nif=String.valueOf(dnipago.getText());
        String met=String.valueOf(metodo.getText());
        //Empezamos la validación
        if (TextUtils.isEmpty(nif)){  //no se insertó nombre
            dnipago.setError(getString(R.string.requerido));
            dnipago.requestFocus();
            return;
        }
        //Empezamos la validación
        if (TextUtils.isEmpty(met)){  //no se insertó nombre
            metodo.setError(getString(R.string.requerido));
            metodo.requestFocus();
            return;
        }
        //COMPLETAR EL FORMULARIO
        Pago pagos=new Pago(idpago,nif,met);         //creamos un nuevo EMPLEADOS con los datos obtenidos
        PagosProveedor.update(getContentResolver(),pagos,this); //Creamos una nueva clase que se encarga de la ACTUALIZACIÓN
        finish();  //Salimos de la actividad tras insertar
    }
}
