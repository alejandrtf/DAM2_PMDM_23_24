package com.example.ejemplolinearlayoutfondosbotonessegunestados;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button botonCambiarTextoAlPulsar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cambiarTexto(botonCambiarTextoAlPulsar);

    }


    /**
     * Método que cambia el texto del botón "Cambiar texto al pulsarlo"
     *
     * @param btn el botón pulsado
     */
    public void cambiarTexto(Button btn) {


        btn = findViewById(R.id.btEjemploCambioTextoAlPulsar);
        btn.setOnTouchListener(new View.OnTouchListener() {
            String text = getString(R.string.p_lsame_cambio_mi_texto);

            @Override
            public boolean onTouch(View view, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        text = "Botón pulsado";
                        break;
                    case MotionEvent.ACTION_UP:
                        text = getString(R.string.p_lsame_cambio_mi_texto);
                        break;
                    default:
                        return false;

                }
                ((Button) view).setText(text);
                return true;
            }

        });
    }
}