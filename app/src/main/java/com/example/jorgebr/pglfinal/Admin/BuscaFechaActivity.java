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
import java.util.ArrayList;
import java.util.List;

import com.example.jorgebr.pglfinal.Proveedor.CitasProveedor;
import com.example.jorgebr.pglfinal.Proveedor.Contrato;
import com.example.jorgebr.pglfinal.R;

public class BuscaFechaActivity extends AppCompatActivity {
    String fech;
    String elemento;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busca_fecha);

        /*********TÍTULO PARA EL ACTIVITY******************/
        setTitle("BUSCAR CITAS FECHA");


        RecogeDatos();           //recogemos el dato
        Cursor resultado= CitasProveedor.readfecha(getContentResolver(),fech);  //recuperamos el usuario
        ListView lista = (ListView)findViewById(R.id.lista);       //listview
        TextView res = (TextView)findViewById(R.id.resultado);     //txtview
        ArrayAdapter<String> adaptador ;
        List<String> cita = new ArrayList<>();   //aqui guardamos cada iteración
        res.append("Las citas de la clinica con fecha:  "+ fech+"\n");
        int i=0;
        if (resultado.moveToFirst()){
            do {
                i++;
                elemento="";
                elemento = elemento + "Cita número: "+ i+"\n";
                int idcita = resultado.getInt(resultado.getColumnIndex(Contrato.Citas._ID));
                String fech = resultado.getString(resultado.getColumnIndex(Contrato.Citas.FECHA));
                String hora = resultado.getString(resultado.getColumnIndex(Contrato.Citas.HORA));
                elemento = elemento + "id: "+String.valueOf(idcita);
                elemento = elemento + "  Fecha: "+String.valueOf(fech);
                elemento = elemento + "  Hora: "+String.valueOf(hora);
                cita.add(elemento);                                                 //guardamos la cita
            } while(resultado.moveToNext());
            adaptador = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,cita);
            lista.setAdapter(adaptador);
        }
    }

    /*Método que recoge la fecha desde el menú drawer*/
    private void RecogeDatos() {
        Bundle datosIDuser = getIntent().getExtras();
        if (datosIDuser!=null)
            fech = datosIDuser.getString("fecha");  //recogemos la fecha usuario
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
