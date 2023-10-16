package com.example.ejemplomayormenoredad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private EditText etNombre, etEdad;
    private Button btAceptar;
    private TextView tvResultado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initReferences();
        setListenerToButtons();

        if(savedInstanceState!=null){
            tvResultado.setVisibility(savedInstanceState.getInt("visibilidad"));
            tvResultado.setText(savedInstanceState.getString("texto"));
        }
    }


    /**
     * Método que asigna los escuchadores click a los botones
     */
    private void setListenerToButtons() {
        btAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textoResultado = "";
                String nombre = etNombre.getText().toString();
                String edad = etEdad.getText().toString();
                if (!edad.isEmpty()) {
                    if (Integer.valueOf(edad) >= 18) {
                        textoResultado= (!nombre.isEmpty())? nombre+"es mayor de edad" : "anonimo es mayor de edad";
                    }else{
                        textoResultado=(!nombre.isEmpty())? nombre + "es menor de edad" : "anonimo es menor de edad";
                    }
                    tvResultado.setText(textoResultado);
                    tvResultado.setVisibility(View.VISIBLE);
                }else{
                    Toast.makeText(MainActivity.this, "ERROR: Introduce una edad", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * Método que inicializa las vistas XML
     */
    private void initReferences() {
        etNombre = findViewById(R.id.etNombre);
        etEdad = findViewById(R.id.etEdad);
        btAceptar = findViewById(R.id.btAceptar);
        tvResultado = findViewById(R.id.tvResultado);

    }


    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("texto",tvResultado.getText().toString());
        outState.putInt("visibilidad",tvResultado.getVisibility());
    }
/*
    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        tvResultado.setVisibility(savedInstanceState.getInt("visibilidad"));
        tvResultado.setText(savedInstanceState.getString("texto"));
    }*/

}