package com.example.ejemplodevolverdatosactivity;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ImporteFacturaActivity extends AppCompatActivity {
    public static final String EXTRA_IMPORTE = "importe";
    // VIEWS
    private EditText etImporte;
    // CONTROL DEVOLUCION DATOS ACTIVITY
    ActivityResultLauncher<Intent> mLauncherCalcularPropinaActivity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_importe_factura);
        initReferences();
        // registro el lanzador
        mLauncherCalcularPropinaActivity=registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        // código que se ejecuta cuando vuelva la activity
                        if(result.getResultCode()==RESULT_OK){
                            Intent data=result.getData();
                            actualizarUI(data);
                        }
                    }
                }
        );
    }



    private void initReferences() {
        etImporte=findViewById(R.id.etImporte);

    }

    /** Método que se ejecuta al pulsar el botón CALCULAR PROPINA
     *
     * @param view
     */
    public void lanzarCalcularPropinaActivity(View view) {
        String importeTexto=etImporte.getText().toString();
        if(importeTexto.isEmpty()){
            etImporte.setError(getString(R.string.error_introduce_un_texto));
        }else{
            Intent intent=new Intent(this,CalcularPropinaActivity.class);
            intent.putExtra(EXTRA_IMPORTE,Double.parseDouble(importeTexto));
            mLauncherCalcularPropinaActivity.launch(intent);
        }


    }

    /** Método que actualiza las vistas del cuadro verde
     *
     * @param data
     */
    private void actualizarUI(Intent data) {
     if(data!=null){

     }
    }
}