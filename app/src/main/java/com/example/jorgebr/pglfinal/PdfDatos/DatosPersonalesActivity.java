package com.example.jorgebr.pglfinal.PdfDatos;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import com.example.jorgebr.pglfinal.Pojos.Usuarios;
import com.example.jorgebr.pglfinal.Proveedor.UsuariosProveedor;
import com.example.jorgebr.pglfinal.R;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class DatosPersonalesActivity extends AppCompatActivity {
    int iduser;
    Cursor fila;
    //DATOS DEL REGISTRO
    String nomActivity,passwdActivity,emailActivity,dniActivity,phoneActivity,petActivity;
    int TypeActivity;
    Bitmap foto;


    Context context = this;
    private static final int PDFWRITE = 23;



    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datos_personales);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        RecogeDatos();           //recogemos el dato
        Usuarios usuarios= UsuariosProveedor.readRecord(getContentResolver(),iduser);  //recuperamos el usuario

        //Pasamos los datos a cada fragment
        nomActivity = usuarios.getNuser();        //pasar estos 3 datos al primer fragmente
        passwdActivity = usuarios.getPass();
        emailActivity = usuarios.getEmail();
        dniActivity = usuarios.getDni();        //pasar estos 3 datos al segundo fragmente
        phoneActivity = usuarios.getPhone();
        petActivity = usuarios.getMascota();
        /*AQUI DEBE TENER EL TIPO DE USUARIO*/
        TypeActivity = usuarios.getTypeuser();   //pasar al tercerfragmente deberia ser 2 entero
        foto = usuarios.getImagen();





        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //PERMISO CONCEDIDO
                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED){
                    GuardaPdf();
                }
                else{
                    ExplicarusoPermiso();
                    SolicitarpermisoPdf();
                }
            }
        });

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
    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_datos_personales, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    DatosCuentaFragment datosCuentaFragment = new DatosCuentaFragment();
                    Bundle bundlecuenta = new Bundle();
                    bundlecuenta.putString("nombre",nomActivity);      //primer fragment
                    bundlecuenta.putString("pass",passwdActivity);
                    bundlecuenta.putString("mail",emailActivity);
                    datosCuentaFragment.setArguments(bundlecuenta);
                    return datosCuentaFragment;
                case 1:
                    DatosContactoFragment datosContactoFragment = new DatosContactoFragment();
                    Bundle bundlecontacto = new Bundle();
                    bundlecontacto.putString("dni",dniActivity);      //segundo fragment
                    bundlecontacto.putString("phone",phoneActivity);
                    bundlecontacto.putString("pet",petActivity);
                    datosContactoFragment.setArguments(bundlecontacto);
                    return datosContactoFragment;
                case 2:
                    TipoUsurarioFragment tipoUsuarioFragment = new TipoUsurarioFragment();
                    Bundle bundleextra = new Bundle();
                    bundleextra.putInt("tipo", TypeActivity);
                    //PENDIENTE FOTO
                    tipoUsuarioFragment.setArguments(bundleextra);
                    return tipoUsuarioFragment;
            }
            return null;
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Cuenta Fragment";
                case 1:
                    return "Contacto Fragment";
                case 2:
                    return "Imagen Fragment";
            }
            return null;
        }
    }

    //Recogemos los datos del usuario
    public void RecogeDatos(){
        Bundle datosIDuser = getIntent().getExtras();
        if (datosIDuser!=null)
            iduser = datosIDuser.getInt("clave");  //recogemos el ID del usuario
    }



    public void GuardaPdf(){
        /*VARIABLE STRING QUE SE VOLCARÁ AL PDF*/
        String mjePdf;
        mjePdf = "Nombre usuario: "+ nomActivity +"\n";
        mjePdf = mjePdf + "Contraseña usuario: "+ passwdActivity +"\n";
        mjePdf = mjePdf + "Email usuario: "+ emailActivity +"\n";
        mjePdf = mjePdf + "DNI usuario: "+ dniActivity +"\n";
        mjePdf = mjePdf + "Teléfono usuario: "+ phoneActivity +"\n";
        mjePdf = mjePdf + "Nombre del Perro usuario: "+ petActivity +"\n";
        if (TypeActivity==1)
            mjePdf = mjePdf + "Tipo de Usuario: Administrador\n";
        else
            mjePdf = mjePdf + "Tipo de Usuario: Estandar\n";
        //create document object
        Document doc=new Document();
        //output file path
        String outpath=Environment.getExternalStorageDirectory()+"/"+nomActivity+".pdf";  //con el nombre del usurio
        try {
            //create pdf writer instance
            PdfWriter.getInstance(doc, new FileOutputStream(outpath));
            //open the document for writing
            doc.open();
            //add paragraph to the document
            doc.add(new Paragraph(mjePdf));
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

    /*SOBREESCRIBIMOS METODOS*/

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PDFWRITE){   //permiso concedido
            if (grantResults.length==1 && grantResults[0]== PackageManager.PERMISSION_GRANTED){
                GuardaPdf();
            }
            else
                Toast.makeText(this,"No tiene permisos",Toast.LENGTH_SHORT).show();
        }
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
