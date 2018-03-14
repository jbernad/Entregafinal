package com.example.jorgebr.pglfinal.CitasUsuarioLog;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jorgebr.pglfinal.Pojos.Usuarios;
import com.example.jorgebr.pglfinal.Proveedor.CitasProveedor;
import com.example.jorgebr.pglfinal.Proveedor.Contrato;
import com.example.jorgebr.pglfinal.Proveedor.UsuariosProveedor;
import com.example.jorgebr.pglfinal.R;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class CitasUsuarioLogActivity extends AppCompatActivity {
    int iduser;
    String dnibusqueda;
    Cursor CitasTotales;
    String elemento;
    private ListView listacitas;
    String pdfwriter;
    private static final int PDFWRITE = 23;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_citas_usuario_log);

        /*********TÍTULO PARA EL ACTIVITY******************/
        setTitle("CITAS DEL USUARIO LOGUEADO");

        RecogeDatos();           //recogemos el dato
        Usuarios usuarios= UsuariosProveedor.readRecord(getContentResolver(),iduser);  //recuperamos el usuario
        dnibusqueda = usuarios.getDni();   //ya tenemos DNI a buscar en citas
        CitasTotales = CitasProveedor.readDNI(getContentResolver(), dnibusqueda);      //FILTRO CURSOR
        TextView res = (TextView)findViewById(R.id.resultado);     //txtview
        ListView lista = (ListView)findViewById(R.id.lista);       //listview
        ArrayAdapter<String> adaptador ;
        List<String> cita = new ArrayList<>();   //aqui guardamos cada iteración
        res.append("Las citas del cliente con dni:  "+ dnibusqueda+"\n");
        pdfwriter = ""+"Las citas del cliente con dni:  "+ dnibusqueda+"\n";
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
                 elemento = elemento + "  Hora: "+String.valueOf(hora)+"\n";
                 pdfwriter = pdfwriter + elemento;
                 cita.add(elemento);                                    //guardamos la cita
            } while(CitasTotales.moveToNext());
            adaptador = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,cita);
            lista.setAdapter(adaptador);
        }
    }

    //Recogemos los datos del usuario
    public void RecogeDatos(){
        Bundle datosIDuser = getIntent().getExtras();
        if (datosIDuser!=null)
            iduser = datosIDuser.getInt("clave");  //recogemos el ID del usuario
    }

    /***** MENÚ DEL APPBAR DE *****/
    //menú de vuelta atrás
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menumain=getMenuInflater();
        menumain.inflate(R.menu.pdfbar, menu);
        menumain.inflate(R.menu.back, menu);
        return true;
    }

    /*********ACCIONES A REALIZAR SI SE PULSAN LOS ELEMENTOS DEL MENU APPBAR******************/
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id== R.id.pdf)
            rellenapdf();
        if (id==R.id.back)
            finish();
        return super.onOptionsItemSelected(item);
    }

    //Método donde vamos a recoger los datos del listview y los volcamos al pdf
    private void rellenapdf() {
        //PERMISO CONCEDIDO
        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED){
            GuardaPdf();
        }
        else{
            ExplicarusoPermiso();
            SolicitarpermisoPdf();
        }
    }

    private void SolicitarpermisoPdf() {
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},PDFWRITE);
        Toast.makeText(this,"Pedimos permiso en tiempo de ejecución",Toast.LENGTH_SHORT).show();

    }

    private void ExplicarusoPermiso() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.WRITE_EXTERNAL_STORAGE)){
            Toast.makeText(this,"Explicamos uso del permiso",Toast.LENGTH_SHORT).show();
            alertdialogbasico();
        }
    }

    private void alertdialogbasico() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Sin permiso no podemos guardar el pdf, hagalo manualemten");
        builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
    }

    public void GuardaPdf(){
        //create document object
        Document doc=new Document();
        //output file path
        String outpath= Environment.getExternalStorageDirectory()+"/" + dnibusqueda + ".pdf";  //con el nombre del usurio
        try {
            //create pdf writer instance
            PdfWriter.getInstance(doc, new FileOutputStream(outpath));
            //open the document for writing
            doc.open();
            //add paragraph to the document
            doc.add(new Paragraph(pdfwriter));
            //close the document
            doc.close();

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (DocumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
