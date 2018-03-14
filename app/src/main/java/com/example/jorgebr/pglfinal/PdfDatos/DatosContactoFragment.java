package com.example.jorgebr.pglfinal.PdfDatos;

import android.content.Context;
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
 * {@link DatosContactoFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DatosContactoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DatosContactoFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String dni;
    private String tfno;
    private String petname;

    EditText TxtDni, TxtTfno, TxtPet;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    /*GETTER AND SETTER*/
    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getTfno() {
        return tfno;
    }

    public void setTfno(String tfno) {
        this.tfno = tfno;
    }

    public String getPetname() {
        return petname;
    }

    public void setPetname(String petname) {
        this.petname = petname;
    }

    private OnFragmentInteractionListener mListener;

    public DatosContactoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DatosContactoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DatosContactoFragment newInstance(String param1, String param2) {
        DatosContactoFragment fragment = new DatosContactoFragment();
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
            dni = getArguments().getString("dni");   //datos del activity obtenidos
            tfno = getArguments().getString("phone");
            petname = getArguments().getString("pet");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View aux = container;
        aux=(View)inflater.inflate(R.layout.fragment_datos_contacto, container, false);
        TxtDni = aux.findViewById(R.id.TxtDni);
        TxtTfno =   aux.findViewById(R.id.TxtTelefono);
        TxtPet =  aux.findViewById(R.id.TxtPet);
        TxtDni.setText(dni);
        TxtTfno.setText(tfno);
        TxtPet.setText(petname);
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
