package com.example.ejemplosubirbajarbotones;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    EditText etTextoArriba, etTextoAbajo;
    Button btSubir;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inicializarVistas();
        //asigno escuchador al botón subir
        btSubir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                subirTexto();
            }
        });


    }

    /**
     * Método que inicializa vistas XML
     */
    private void inicializarVistas() {
        // inicializar vistas
        etTextoArriba = findViewById(R.id.etPalabraArriba);
        etTextoAbajo = findViewById(R.id.etPalabraAbajo);
        btSubir = findViewById(R.id.btSubir);
    }


    public void bajarTexto(View view) {
        if (!TextUtils.isEmpty(etTextoArriba.getText().toString())) {
            etTextoAbajo.setText(etTextoArriba.getText().toString());
            etTextoArriba.setText("");

        } else {
            Toast.makeText(this, R.string.mensaje_no_texto, Toast.LENGTH_LONG).show();
        }
    }

    private void subirTexto() {
        if (!TextUtils.isEmpty(etTextoAbajo.getText().toString())) {
            etTextoArriba.setText(etTextoAbajo.getText().toString());
            etTextoAbajo.setText("");
        } else {
            Toast.makeText(this, R.string.mensaje_no_texto, Toast.LENGTH_SHORT).show();
        }
    }

}