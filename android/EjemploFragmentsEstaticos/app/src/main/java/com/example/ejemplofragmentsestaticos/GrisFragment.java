package com.example.ejemplofragmentsestaticos;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GrisFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GrisFragment extends Fragment {
    // VIEWS
    EditText etPalabra;
    Button btInvertir;

    // INTERFACE LISTENER
    private OnInvertirPulsadoListener mListener;

    public GrisFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment GrisFragment.
     */
    public static GrisFragment newInstance() {
        GrisFragment fragment = new GrisFragment();
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
        View view = inflater.inflate(R.layout.fragment_gris, container, false);
        etPalabra = view.findViewById(R.id.etPalabra);
        btInvertir = view.findViewById(R.id.btInvertir);
        btInvertir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String palabra = etPalabra.getText().toString();
                String palabraAlreves = new StringBuilder(palabra).reverse().toString();
                mListener.onInvertirPulsado(palabraAlreves);
            }
        });
        return view;
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnInvertirPulsadoListener) {
            mListener = (OnInvertirPulsadoListener) context;
        } else {
            throw new ClassCastException(context.toString() + " debe implementar el interface OnInvertirPulsadoListener");
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
     * Interface que se llama al pulsar el botón INVERTIR
     */
    public interface OnInvertirPulsadoListener {
        public void onInvertirPulsado(String palabra);
    }
}