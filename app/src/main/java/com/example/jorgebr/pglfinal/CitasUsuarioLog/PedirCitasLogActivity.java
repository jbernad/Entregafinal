package com.example.jorgebr.pglfinal.CitasUsuarioLog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;
import com.example.jorgebr.pglfinal.Constantes.G;
import com.example.jorgebr.pglfinal.Pojos.Citas;
import com.example.jorgebr.pglfinal.Pojos.Usuarios;
import com.example.jorgebr.pglfinal.Proveedor.CitasProveedor;
import com.example.jorgebr.pglfinal.Proveedor.UsuariosProveedor;
import com.example.jorgebr.pglfinal.R;
import java.util.Calendar;

import static com.example.jorgebr.pglfinal.Constantes.G.DATE_ID;

public class PedirCitasLogActivity extends AppCompatActivity {
    /***** DECLARACIÓN DE VARIABLES *****/
    int iduser;                                   //recoge iduser del activity anterior
    String dni;                                   //comprueba citas de este usuario
    Context contexto=this;
    //VISTAS
    TimePicker tiempo;
        int hora,minuto;
        String horainsertada;
    Button insertar , confirmar;
    EditText etfecha;
    Calendar C = Calendar.getInstance();
        private int mYearIni, mMonthIni, mDayIni, sYearIni, sMonthIni, sDayIni;
        String fechinsertar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedir_citas_log);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //flecha atras

        /*********TÍTULO PARA EL ACTIVITY******************/
        setTitle("PEDIR CITA");
        //Obtenemos los valores de calendario iniciales
        sMonthIni = C.get(Calendar.MONTH);
        sDayIni = C.get(Calendar.DAY_OF_MONTH);
        sYearIni = C.get(Calendar.YEAR);
        horainsertada ="";                     //No tenemos valor inicial
        RecogeDatos();

        /*********RECOGEMOS VISTAS******************/
        etfecha = (EditText)findViewById(R.id.editTextFechaCita);
        tiempo = (TimePicker)findViewById(R.id.tphora);
        confirmar = (Button) findViewById(R.id.Confirmarhora);

        //pulsar el botón de fecha
        etfecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(DATE_ID);
            }
        });

        //botón para confirmar la hora
        confirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hora = tiempo.getHour();                        //depende de versión de Api debo cambiarlo
                minuto = tiempo.getMinute();
                horainsertada = hora +":";
                if (minuto<10) {
                    String auxmin = "0" + minuto;
                    horainsertada = horainsertada + auxmin;
                }else
                    horainsertada = horainsertada + minuto;
                horainsertada = horainsertada + ":00";    //actualizamos la fecha
            }
        });

        /*BOTÓN PARA INSERTAR EN LA BD*/
        insertar = (Button) findViewById(R.id.btInsertar);
        insertar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etfecha.getText().toString().length()==0)                            //no seleccionamos fecha
                    Toast.makeText(getApplicationContext(),"No ha introducido fecha",Toast.LENGTH_SHORT).show();
                else{
                    if (horainsertada.equals(""))
                        Toast.makeText(getApplicationContext(),"No ha introducido hora",Toast.LENGTH_LONG).show();
                    else{
                        Usuarios usuarios= UsuariosProveedor.readRecord(getContentResolver(),iduser);
                        dni = usuarios.getDni();
                        Citas citas=new Citas(G.SIN_VALOR_INT,fechinsertar,horainsertada,dni);
                        //cOMPROBAR SI EXISTE EL USUARIO
                        if (CitasProveedor.consultaCitaLibre(getContentResolver(),fechinsertar,horainsertada)){
                            CitasProveedor.insert(getContentResolver(),citas,contexto);
                            Toast.makeText(getApplicationContext(),"Cita insertada",Toast.LENGTH_LONG).show();
                        }
                        else
                            Toast.makeText(getApplicationContext(),"Esa cita ya está ocupada",Toast.LENGTH_LONG).show();
                        finish();  //Salimos de la actividad tras insertar
                    }
                }
            }
        });
    }





    /******* DATE PICKER *******/
        /*Pulsamos el botón para coger la fecha*/
    private DatePickerDialog.OnDateSetListener mDateSetListener =
            new DatePickerDialog.OnDateSetListener() {
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    mYearIni = year;
                    mMonthIni = monthOfYear;
                    mDayIni = dayOfMonth;
                    colocar_fecha();
                }
            };


    /*MÉTODO QUE OBTIENE LA FECHA DEL DIALOG DESPLEGADO*/
    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_ID: return new DatePickerDialog(this, mDateSetListener, sYearIni, sMonthIni, sDayIni);
        }
        return null;
    }

    /*METODO PARA COLOCAR LA FECHA EN EL EDITTEXTVIEW*/
    private void colocar_fecha() {

        etfecha.setText((mMonthIni + 1) + "-" + mDayIni + "-" + mYearIni + " ");
        rellenafecha(etfecha.getText().toString());

    }

    /*MÉTODO QUE CONSTRUYE LA FECHA*/
    private void rellenafecha(String fecha) {
        String mes,year,day;
        String eligemes;
        String aux =fecha;
        mes = aux.substring(0,aux.indexOf("-"));           //obtenemos los minutos
        if (mes.length()==1) {
            eligemes = "0" + mes;
            mes = eligemes;
        }
        aux = aux.substring(aux.indexOf("-")+1);
        day = aux.substring(0,aux.indexOf("-"));           //obtenemos DIA
        aux = aux.substring(aux.indexOf("-")+1);
        year = aux.substring(0,aux.length()-1);            //obtenemos año
        fechinsertar = year+ "-" + mes + "-" + day;
    }

    /*Método que recoge el ID del usuario del intent anterior*/
    public void RecogeDatos(){
        Bundle datosIDuser = getIntent().getExtras();
        if (datosIDuser!=null)
            iduser = datosIDuser.getInt("clave");  //recogemos el ID del usuario
    }

    /*SELECCION DE FLECHA ATRAS, CIERRA EL ACTIVITY REGISTRO SIN RECORDAR DATOS*/
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: finish();  //volver atras cerramos el Activity
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
