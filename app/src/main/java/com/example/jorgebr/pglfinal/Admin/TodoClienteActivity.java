package com.example.jorgebr.pglfinal.Admin;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.jorgebr.pglfinal.Pojos.Usuarios;
import com.example.jorgebr.pglfinal.Proveedor.CitasProveedor;
import com.example.jorgebr.pglfinal.Proveedor.Contrato;
import com.example.jorgebr.pglfinal.Proveedor.UsuariosProveedor;
import com.example.jorgebr.pglfinal.R;

import java.util.ArrayList;
import java.util.List;

public class TodoClienteActivity extends AppCompatActivity {
    int idk;
    String dnibusqueda;
    Cursor CitasTotales;
    String elemento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_cliente);
        /*********TÍTULO PARA EL ACTIVITY******************/
        setTitle("TODO DE UN CLIENTE");

        RecogeDatos();           //recogemos el dato
        Usuarios usuarios= UsuariosProveedor.readRecord(getContentResolver(),idk);  //recuperamos el usuario
        dnibusqueda = usuarios.getDni();   //ya tenemos DNI a buscar en citas
        CitasTotales = CitasProveedor.readDNI(getContentResolver(), dnibusqueda);      //FILTRO CURSOR
        ListView lista = (ListView)findViewById(R.id.lista);       //listview
        TextView res = (TextView)findViewById(R.id.resultado);     //txtview
        ArrayAdapter<String> adaptador ;
        List<String> cita = new ArrayList<>();   //aqui guardamos cada iteración
        res.append("Los datos del cliente con id :  "+ idk+"\n");
        int i=0;
        if (CitasTotales.moveToFirst()){
            do {
                i++;
                elemento="";
                elemento = elemento + "Cita número: "+ i+"\n";
                int idcita = CitasTotales.getInt(CitasTotales.getColumnIndex(Contrato.Citas._ID));
                String fech = CitasTotales.getString(CitasTotales.getColumnIndex(Contrato.Citas.FECHA));
                String hora = CitasTotales.getString(CitasTotales.getColumnIndex(Contrato.Citas.HORA));
                elemento = elemento + "id: "+String.valueOf(idcita);
                elemento = elemento + "  Fecha: "+String.valueOf(fech);
                elemento = elemento + "  Hora: "+String.valueOf(hora);
                cita.add(elemento);                                                 //guardamos la cita
            } while(CitasTotales.moveToNext());
            adaptador = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,cita);
            lista.setAdapter(adaptador);
        }


    }

    /*Método que recoge la fecha desde el menú drawer*/
    private void RecogeDatos() {
        Bundle datosIDuser = getIntent().getExtras();
        if (datosIDuser!=null)
            idk = datosIDuser.getInt("id");  //recogemos la fecha usuario
    }

    /***** MENÚ DEL APPBAR DE *****/
    //menú de vuelta atrás
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menumain=getMenuInflater();
        menumain.inflate(R.menu.back, menu);
        return true;
    }

    /*********ACCIONES A REALIZAR SI SE PULSAN LOS ELEMENTOS DEL MENU APPBAR******************/
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id== R.id.back)
            finish();
        return super.onOptionsItemSelected(item);
    }
}
