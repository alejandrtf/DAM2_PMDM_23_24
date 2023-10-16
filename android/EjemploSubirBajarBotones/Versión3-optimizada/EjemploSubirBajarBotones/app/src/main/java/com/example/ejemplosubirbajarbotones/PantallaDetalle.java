package com.example.ejemplosubirbajarbotones;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class PantallaDetalle extends AppCompatActivity {
    TextView tvTextoRecibido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_detalle);
        inicializarVistas();

        //recojo los datos que me llegan
        String textoQueLlega = getDatosFromMainActivity();
        if (!textoQueLlega.isEmpty()) {
            tvTextoRecibido.setText(textoQueLlega);
        }
    }


    /**
     * Método que inicializa vistas XML
     */
    private void inicializarVistas() {
        tvTextoRecibido = findViewById(R.id.tvPalabraDetalle);
    }


    /**
     * Método que recoge los datos que se reciben en el intent que envía
     * MainActivity para lanzar esta segunda pantalla
     *
     * @return el texto recibido o cadena vacía si no se recibe ningún extra
     */
    private String getDatosFromMainActivity() {
        Intent intent = getIntent();
        if (intent.hasExtra(MainActivity.EXTRA_TEXTO)) {
            return intent.getStringExtra(MainActivity.EXTRA_TEXTO);
        } else {
            return "";
        }
    }

}
