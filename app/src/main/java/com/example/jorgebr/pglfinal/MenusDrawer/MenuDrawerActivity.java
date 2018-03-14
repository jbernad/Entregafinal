package com.example.jorgebr.pglfinal.MenusDrawer;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.example.jorgebr.pglfinal.Admin.BuscaFechaActivity;
import com.example.jorgebr.pglfinal.Admin.TodoClienteActivity;
import com.example.jorgebr.pglfinal.Pagos.PagosActivity;
import com.example.jorgebr.pglfinal.Proveedor.Contrato;
import com.example.jorgebr.pglfinal.R;
import com.example.jorgebr.pglfinal.Usuarios.*;
import com.example.jorgebr.pglfinal.Citas.*;
import com.example.jorgebr.pglfinal.Validaciones.Validaciones;

public class MenuDrawerActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    /*********DECLARAMOS VARIABLES PARA RECOGER VISTAS*****************/
    String nuser;
    EditText fecha, iduser;
    Cursor fila;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_drawer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*********TÍTULO PARA EL ACTIVITY******************/
        setTitle("MENU ADMINISTRADOR");
        RecogeDatos();

        /*********RECOGEMOS LAS VISTAS******************/
        fecha = (EditText)findViewById(R.id.editfecha);
        iduser = (EditText)findViewById(R.id.iduser);

        fecha.setText("");

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    /*********MENU APPBAR*****************/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.salir) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START))
            drawer.closeDrawer(GravityCompat.START);
        else
            super.onBackPressed();
    }


    /*********OPCIONES DEL MENU ADMIN*****************/
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.usuarios) {
            Intent intentusuarios = new Intent (getApplicationContext(), UsuariosActivity.class);  //BD USUARIOS
            startActivity(intentusuarios);
        } else if (id == R.id.citas) {
            Intent intentcitas = new Intent (getApplicationContext(), CitasActivity.class);  //BD CITAS
            startActivity(intentcitas);
            Toast.makeText(this,"Accederemos a mostrar la tabla de citas",Toast.LENGTH_SHORT).show();
        } else if (id == R.id.pagos) {
            Intent intentpagos = new Intent (getApplicationContext(), PagosActivity.class);  //BD CITAS
            startActivity(intentpagos);
            Toast.makeText(this,"Accederemos a mostrar la tabla de citas",Toast.LENGTH_SHORT).show();
        } else if (id == R.id.ElCliente) {
            Intent intentid = new Intent (getApplicationContext(), TodoClienteActivity.class); //muestra los datos de las dos tablas
            if (iduser.getText().toString().length()==0)
                Toast.makeText(this,"No ha introducido id del usuario a buscar",Toast.LENGTH_SHORT).show();
            else{
                int idbusq = Integer.parseInt(iduser.getText().toString());
                ContentResolver contentresolver=getContentResolver();
                String[] campos = new String[] {Contrato.Usuarios._ID,Contrato.Usuarios.USERNAME, Contrato.Usuarios.PASSUSER};  //comprobamos campos usuario y contraseña
                String where = Contrato.Usuarios._ID + " = " + "'" + idbusq + "'";                       //condicion
                fila = contentresolver.query(Contrato.Usuarios.CONTENT_URI, campos, where, null,null);     //cursor
                if (fila.moveToFirst()) {
                    intentid.putExtra("id",idbusq);
                    startActivity(intentid);
                }else {
                    Toast.makeText(this, "Usuario No Existe", Toast.LENGTH_LONG).show();
                }
            }
        } else if (id == R.id.Fecha) {
            Intent intentfecha = new Intent (getApplicationContext(), BuscaFechaActivity.class);
            if (fecha.getText().toString().length()==0){
                Toast.makeText(this,"No ha introducido fecha a mostrar",Toast.LENGTH_SHORT).show();
            } else{
                //Comprobamos que la fecha se correcta
                if (Validaciones.Compruebafecha(fecha.getText().toString())==true){
                    intentfecha.putExtra("fecha",fecha.getText().toString());
                    startActivity(intentfecha);        //lanzamos el activity
                }else
                    Toast.makeText(this,"Formato de fecha incorrecto",Toast.LENGTH_SHORT).show();
            }
        } else if (id == R.id.nav_llamar) {
            Intent intenttfno = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:659551719"));  //LLAMADA AL AUTOR INTENT IMPLICITO
            startActivity(intenttfno);

        } else if (id == R.id.nav_agenda) {
            Intent intentcontact = new Intent(Intent.ACTION_VIEW, Uri.parse("content://contacts/people")); //ACCESO A LA AGENDA DEL USUARIO
            startActivity(intentcontact);

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /*********MÉTODO PARA RECOGER EL ID DEL USUARIO LOGUEADO EN ESTE CASO ADMIN******************/
    public void RecogeDatos(){
        Bundle datosIDuser = getIntent().getExtras();
        if (datosIDuser!=null)
            nuser = datosIDuser.getString("logueado");  //recogemos el ID del usuario
    }
}
