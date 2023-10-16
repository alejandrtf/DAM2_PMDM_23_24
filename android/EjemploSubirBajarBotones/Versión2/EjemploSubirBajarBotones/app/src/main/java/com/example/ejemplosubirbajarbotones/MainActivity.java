package com.example.ejemplosubirbajarbotones;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    EditText etTextoArriba, etTextoAbajo;
    Button btSubir, btBajar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inicializarVistas();
        setListenersToButtons();
    }


    /**
     * Método que inicializa vistas XML
     */
    private void inicializarVistas() {
        // inicializar vistas
        etTextoArriba = findViewById(R.id.etPalabraArriba);
        etTextoAbajo = findViewById(R.id.etPalabraAbajo);
        btSubir = findViewById(R.id.btSubir);
        btBajar = findViewById(R.id.btBajar);
    }


    /**
     * Método que asigna los listeners a los botones
     */
    private void setListenersToButtons() {
        btSubir.setOnClickListener(this);
        btBajar.setOnClickListener(this);

    }


    /**
     * Método que baja el texto que haya en el cuadro de texto de arriba
     * al de abajo
     */
    private void bajarTexto() {
        if (!TextUtils.isEmpty(etTextoArriba.getText().toString())) {
            etTextoAbajo.setText(etTextoArriba.getText().toString());
            etTextoArriba.setText("");

        } else {
            Toast.makeText(this, R.string.mensaje_no_texto, Toast.LENGTH_LONG).show();
        }
    }


    /**
     * Método que sube el texto que haya en el cuadro de texto
     * de abajo al de arriba
     */
    private void subirTexto() {
        if (!TextUtils.isEmpty(etTextoAbajo.getText().toString())) {
            etTextoArriba.setText(etTextoAbajo.getText().toString());
            etTextoAbajo.setText("");
        } else {
            Toast.makeText(this, R.string.mensaje_no_texto, Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * Método que se ejecuta cada vez que se pulsa cualquiera de los botones
     *
     * @param v The view that was clicked. (en nuestro caso el botón)
     */
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btBajar) {
            // se pulsó botón BajarTexto
            bajarTexto();
        } else {
            subirTexto();
        }
    }
}