package com.example.jorgebr.pglfinal.PdfDatos;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.jorgebr.pglfinal.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DatosCuentaFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DatosCuentaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DatosCuentaFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String nombre;
    private String password;
    private String mail;

    EditText TxtNuser, TxtPass, TxtMail;

    /*GETTER AND SETTER DEL CONTENIDO DE LOS CUADROS DE TEXTO*/
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }



    private OnFragmentInteractionListener mListener;

    public DatosCuentaFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DatosCuentaFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DatosCuentaFragment newInstance(String param1, String param2) {
        DatosCuentaFragment fragment = new DatosCuentaFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            nombre = getArguments().getString("nombre");   //datos del activity obtenidos
            password = getArguments().getString("pass");
            mail = getArguments().getString("mail");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View aux = container;
        aux=(View)inflater.inflate(R.layout.fragment_datos_cuenta, container, false);
        TxtNuser = aux.findViewById(R.id.TxtNuser);
        TxtPass =   aux.findViewById(R.id.TxtPassword);
        TxtMail =  aux.findViewById(R.id.TxtEmail);
        TxtNuser.setText(nombre);
        TxtPass.setText(password);
        TxtMail.setText(mail);
        return aux;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }



    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
