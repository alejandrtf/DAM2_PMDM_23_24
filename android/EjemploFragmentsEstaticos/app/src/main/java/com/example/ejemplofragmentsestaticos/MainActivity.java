package com.example.ejemplofragmentsestaticos;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements GrisFragment.OnInvertirPulsadoListener,
        RosaFragment.OnBotonContarPulsadoListener {
    // VIEWS
    private TextView tvNumeroLetras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvNumeroLetras = findViewById(R.id.tvNumLetras);
    }


    /**
     * Método que se ejecuta al pulsar el botón INVERTIR en el fragment gris
     *
     * @param palabra palabra invertida
     */
    @Override
    public void onInvertirPulsado(String palabra) {
        RosaFragment rosaFragment = (RosaFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentRosa);
        if (rosaFragment != null) {
            // estoy en una pantalla con los dos fragments (multipanel)
            rosaFragment.mostrarTexto(palabra);
        }
    }


    /**
     * Método que se ejecuta al pulsar el botón CONTAR LETRAS del fragment rosa
     *
     * @param numLetras es el nº de letras de la palabra escrita
     */
    @Override
    public void onBotonContarPulsado(int numLetras) {
        tvNumeroLetras.setText(String.valueOf(numLetras));

    }
}