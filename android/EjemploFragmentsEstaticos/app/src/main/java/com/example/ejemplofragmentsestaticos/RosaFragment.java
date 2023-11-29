package com.example.ejemplofragmentsestaticos;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RosaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RosaFragment extends Fragment {
    // VIEWS
    TextView tvTextoMostrar;
    Button btContarLetras;

    // INTERFACE LISTENER
    private OnBotonContarPulsadoListener mListener;


    public RosaFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment RosaFragment.
     */

    public static RosaFragment newInstance() {
        RosaFragment fragment = new RosaFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_rosa, container, false);
        tvTextoMostrar = view.findViewById(R.id.tvAlReves);
        btContarLetras = view.findViewById(R.id.btContarLetras);
        btContarLetras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int numeroLetras = tvTextoMostrar.getText().length();
                mListener.onBotonContarPulsado(numeroLetras);
            }
        });
        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnBotonContarPulsadoListener) {
            mListener = (OnBotonContarPulsadoListener) context;
        } else {
            throw new ClassCastException(context.toString() + " debe implementar el interface OnBotonContarPulsadoListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (mListener != null) {
            mListener = null;
        }
    }

    /**
     * Mostrar en un TextView la palabra que se le pasa
     *
     * @param palabra texto a mostrar
     */
    public void mostrarTexto(String palabra) {
        tvTextoMostrar.setText(palabra);
    }


    /**
     * Interface que comunica el fragment rosa con la activity
     * Es llamado al pulsar el botón CONTARLETRAS
     */
    public interface OnBotonContarPulsadoListener {
        public void onBotonContarPulsado(int numLetras);
    }
}