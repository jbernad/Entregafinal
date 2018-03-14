package com.example.jorgebr.pglfinal.Usuarios;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.jorgebr.pglfinal.R;

public class UsuariosActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuarios);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("LISTA USUARIOS");

        //Necesario para mandar contenido del listFragment
        UsuariosListFragment usuariosListFragment = new UsuariosListFragment();             //Creamos el Fragmento que se va a mostrar
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();   //proceso de carga de los fragmentos en la lista
        transaction.add(R.id.fragment_usuarios, usuariosListFragment);
        transaction.commit();
    }
}
