package com.example.jorgebr.pglfinal.Pagos;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.jorgebr.pglfinal.R;


public class PagosActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagos);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("LISTA PAGOS");

        //Necesario para mandar contenido del listFragment
        PagosListFragment pagosListFragment = new PagosListFragment();  //Creamos el Fragmento que se va a mostrar
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction(); //proceso de carga de los fragmentos en la lista
        transaction.add(R.id.fragment_pagos, pagosListFragment);
        transaction.commit();
    }
}
