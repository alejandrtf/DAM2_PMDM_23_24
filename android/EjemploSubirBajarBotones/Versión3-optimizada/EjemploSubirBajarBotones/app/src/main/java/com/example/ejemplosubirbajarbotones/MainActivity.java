package com.example.ejemplosubirbajarbotones;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    // CONSTANTS
    protected final static String EXTRA_TEXTO = "texto_escrito";

    // VIEWS
    EditText etTextoArriba, etTextoAbajo;
    Button btSubir, btBajar, btEnviar;


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
        btEnviar = findViewById(R.id.btEnviar);
    }


    /**
     * Método que asigna los listeners a los botones
     */
    private void setListenersToButtons() {
        btSubir.setOnClickListener(this);
        btBajar.setOnClickListener(this);
        btEnviar.setOnClickListener(this);

    }


    /**
     * Método que mueve el texto que haya en el cuadro de texto origen al
     * cuadro de texto destino. Si no hay texto, muestra un mensaje indicándolo
     *
     * @param origen  cuadro de texto que actúa como origen
     * @param destino cuadro de texto que actúa como destino
     */
    private void moverTextoFromTo(EditText origen, EditText destino) {
        if (!TextUtils.isEmpty(origen.getText().toString())) {
            destino.setText(origen.getText().toString());
            origen.setText("");
        } else Toast.makeText(this, R.string.mensaje_no_texto, Toast.LENGTH_SHORT).show();
    }


    /**
     * Método lanza segunda pantalla con el texto (Pantalla Detalle)
     */
    private void lanzarPantallaDetalle() {
        String texto = "";
        Intent iPantallaDetalle = new Intent(this, PantallaDetalle.class);
        if (etTextoArriba.getText().length() != 0) {
            texto = etTextoArriba.getText().toString();
        } else if (etTextoAbajo.getText().length() != 0) {
            texto = etTextoAbajo.getText().toString();
        }
        iPantallaDetalle.putExtra(EXTRA_TEXTO, texto);
        startActivity(iPantallaDetalle);
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
            moverTextoFromTo(etTextoArriba, etTextoAbajo);
        } else if (v.getId() == R.id.btSubir) {
            moverTextoFromTo(etTextoAbajo, etTextoArriba);
        } else {
            lanzarPantallaDetalle();
        }
    }
}