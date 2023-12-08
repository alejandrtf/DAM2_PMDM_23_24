package com.example.ejemploservicionotificacionesreceptoresanuncios2023;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.example.ejemploservicionotificacionesreceptoresanuncios2023.utilities.NetworkUtilities;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 */
public class ServicioLeerArchivoCSV extends IntentService {
    // CONSTANTES
    public static final String ACTION_FIN_CARGA_DATOS = "com.example.ejemploservicionotificacionesreceptoresanuncios2023.ACTION_FIN_CARGA_DATOS";
    public static final String ACTION_ERROR_IO = "com.example.ejemploservicionotificacionesreceptoresanuncios2023.ACTION_ERROR_IO";
    public static final String ACTION_ERROR_URL = "com.example.ejemploservicionotificacionesreceptoresanuncios2023.ACTION_ERROR_URL";
    public static final String EXTRA_DATOS_CARGADOS = "com.example.ejemploservicionotificacionesreceptoresanuncios2023.EXTRA_DATOS_CARGADOS";

    // URL donde descargo
    private URL url;
    // String[] donde guardo los datos descargados
    private String[] notasAlumnoAsignatura;


    public ServicioLeerArchivoCSV() {
        super("ServicioLeerArchivoCSV");
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            if (intent.hasExtra(MainActivity.EXTRA_URI_CONEXION)) {
                try {
                    // recoger la URL para descargar
                    url = new URL(intent.getStringExtra(MainActivity.EXTRA_URI_CONEXION));
                    //leer archivo
                    notasAlumnoAsignatura = NetworkUtilities.getResponseFromHttpUrl(url);
                    avisarEventoOcurrido(ACTION_FIN_CARGA_DATOS);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                    avisarEventoOcurrido(ACTION_ERROR_URL);
                } catch (IOException e) {
                    e.printStackTrace();
                    avisarEventoOcurrido(ACTION_ERROR_IO);
                }
            }
        }

    }


    /**
     * Método que lanza un aviso de un evento ocurrido.
     * El evento se le pasa por parámetro
     */
    private void avisarEventoOcurrido(String action) {
        Intent iAviso = new Intent(action);
        if (action == ACTION_FIN_CARGA_DATOS) {
            iAviso.putExtra(EXTRA_DATOS_CARGADOS, notasAlumnoAsignatura);
        }
        sendBroadcast(iAviso);
        Log.d("MIAPLI",action);
        Log.d("MIAPLI",notasAlumnoAsignatura.toString());
    }

}