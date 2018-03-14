package com.example.jorgebr.pglfinal.MenusDrawer;
import android.content.Intent;
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
import android.widget.TextView;

import com.example.jorgebr.pglfinal.CitasUsuarioLog.CitasUsuarioLogActivity;
import com.example.jorgebr.pglfinal.CitasUsuarioLog.PedirCitasLogActivity;
import com.example.jorgebr.pglfinal.PdfDatos.DatosPersonalesActivity;
import com.example.jorgebr.pglfinal.R;

public class MenuEstandarActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    /*DECLARACIÓN DE VARIABLES*/
    int iduser;
    TextView idprueba;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_estandar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*********TÍTULO PARA EL ACTIVITY******************/
        setTitle("MENU USUARIO ESTANDAR");

        RecogeDatos();

        /*********RECOGEMOS LA VISTA DONDE PONEMOS ID******************/
        idprueba = (TextView) findViewById(R.id.idprueba);
        idprueba.setText(String.valueOf("iduser: "+iduser));

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    /*********MENU APPBAR*****************/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.back, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id== R.id.back)
            finish();
        return super.onOptionsItemSelected(item);
    }


    /*********OPCIONES DEL MENU ESTANDAR*****************/
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.datospersonales) {
            Intent intentdatospersonales = new Intent (getApplicationContext(), DatosPersonalesActivity.class);  //BD USUARIOS
            intentdatospersonales.putExtra("clave",iduser);
            startActivity(intentdatospersonales);
        } else if (id == R.id.citasdelusuario) {
            Intent intentcitasusuario = new Intent (getApplicationContext(), CitasUsuarioLogActivity.class);  //BD USUARIOS
            intentcitasusuario.putExtra("clave",iduser);
            startActivity(intentcitasusuario);

        } else if (id == R.id.PedirCita) {
            Intent intentpidecita = new Intent (getApplicationContext(), PedirCitasLogActivity.class);  //BD USUARIOS
            intentpidecita.putExtra("clave",iduser);
            startActivity(intentpidecita);


        } else if (id == R.id.llamar) {
            Intent intenttfno = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:659551719"));  //llamada al autor con intent implicito
            startActivity(intenttfno);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /*Recogemos los datos del usuario*/
    public void RecogeDatos(){
        Bundle datosIDuser = getIntent().getExtras();
        if (datosIDuser!=null)
            iduser = datosIDuser.getInt("clave");        //recogemos el ID del usuario
    }
}
