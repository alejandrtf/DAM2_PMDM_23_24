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
        Intent intent = getIntent();
        if (intent.hasExtra(MainActivity.EXTRA_TEXTO)) {
            String textoQueLlega = intent.getStringExtra(MainActivity.EXTRA_TEXTO);
            tvTextoRecibido.setText(textoQueLlega);
        }


    }

    /**
     * Método que inicializa vistas XML
     */
    private void inicializarVistas() {
        tvTextoRecibido = findViewById(R.id.tvPalabraDetalle);
    }
}
