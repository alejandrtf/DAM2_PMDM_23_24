package com.example.ejemplodevolverdatosactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class CalcularPropinaActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String EXTRA_PROPINA = "extra_propina";
    public static final String EXTRA_TOTAL_PAGAR = "extra_total_pagar";
    // VIEWS
    private TextView tvImporteRecibido;
    private RadioGroup rgOpcionesDePorcentaje;
    private Button btCalcularPropina, btCancelar;

    // OTRAS VARIABLES
    double importeSinPropina;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calcular_propina);

        initReferences();
        //recojo datos
        Intent iDatos=getIntent();
        if(iDatos.hasExtra(ImporteFacturaActivity.EXTRA_IMPORTE)){
            importeSinPropina=iDatos.getDoubleExtra(ImporteFacturaActivity.EXTRA_IMPORTE,0.0);
        }
        setListenersToButtones();
    }



    private void initReferences() {
        tvImporteRecibido=findViewById(R.id.tvImporteSinPropina);
        rgOpcionesDePorcentaje=findViewById(R.id.rgPorcentajesPropina);
        btCancelar=findViewById(R.id.btCancelar);
        btCalcularPropina=findViewById(R.id.btCalcularPropinaPorcentaje);
    }


    private void setListenersToButtones() {
        btCalcularPropina.setOnClickListener(this);
        btCancelar.setOnClickListener(this);
    }


    /** Método que se ejecuta al pulsar los dos botones
     *
     * @param view
     */
    @Override
    public void onClick(View view) {
        int id=view.getId();
        if(id==R.id.btCancelar){
            finish();
        }else{
            Bundle infoPropinaDevolver=calcularPropinaSegunPorcentaje(importeSinPropina);
            Intent resultIntent = new Intent();
            resultIntent.putExtras(infoPropinaDevolver);
            setResult(RESULT_OK,resultIntent);
            finish();
        }
    }

    private Bundle calcularPropinaSegunPorcentaje(double importeSinPropina) {
        int idRadioButtonElegido=rgOpcionesDePorcentaje.getCheckedRadioButtonId();
        RadioButton rbElegido=findViewById(idRadioButtonElegido);
        String textoPorcentaje=rbElegido.getText().toString().split("%")[0];
        double porcentaje=Double.parseDouble(textoPorcentaje);
        // calculo los datos a devovler
        double propina=(importeSinPropina*porcentaje)/100;
        double totalPagar=importeSinPropina+propina;
        // devolver datos
        Bundle b=new Bundle();
        b.putDouble(EXTRA_PROPINA,propina);
        b.putDouble(EXTRA_TOTAL_PAGAR,totalPagar);
        return b;
    }
}