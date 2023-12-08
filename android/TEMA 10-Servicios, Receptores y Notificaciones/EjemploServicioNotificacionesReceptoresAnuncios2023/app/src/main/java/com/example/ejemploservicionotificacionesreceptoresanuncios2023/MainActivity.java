package com.example.ejemploservicionotificacionesreceptoresanuncios2023;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    // CONSTANTES
    public static final String EXTRA_URI_CONEXION = "uri_descarga";
    public static final String TAG_APLI = MainActivity.class.getSimpleName();

    // UI
    private EditText etUrlDescarga;
    private Button btDescargar;

    // GESTIÓN DATOS DEL FICHERO
    String[] datosAlumnosNotas;

    // RECEPTOR DE ANUNCIOS PARA EL SERVICIO
    private ReceptorAvisosServicio receptorAvisosServicio;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initReferences();
        setListenersToButtons();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //Registro el receptor de Broadcast (BroadcastReceiver) para que la activity escuche los eventos que le envía
        //nuestro servicio
        receptorAvisosServicio = new ReceptorAvisosServicio();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ServicioLeerArchivoCSV.ACTION_ERROR_IO);
        intentFilter.addAction(ServicioLeerArchivoCSV.ACTION_ERROR_URL);
        intentFilter.addAction(ServicioLeerArchivoCSV.ACTION_FIN_CARGA_DATOS);
        registerReceiver(receptorAvisosServicio, intentFilter);

    }


    @Override
    protected void onPause() {
        super.onPause();
        //desregistro el BroadcastReceiver
        unregisterReceiver(receptorAvisosServicio);
    }


    /**
     * Método que obtiene las referencias a las vistas XML
     */
    private void initReferences() {
        etUrlDescarga = findViewById(R.id.etUrlFichero);
        btDescargar = findViewById(R.id.btDescargar);
    }

    /**
     * Método asigna el escuchador al botón
     */
    private void setListenersToButtons() {
        btDescargar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(etUrlDescarga.getText())) {
                    lanzarServicio(etUrlDescarga.getText().toString());
                }
            }
        });
    }


    /**
     * Método que lanza el servicio que descarga el CSV de la url que se le pasa
     *
     * @param urlDescarga url en formato string donde se encuentra el fichero a descargar
     */
    private void lanzarServicio(String urlDescarga) {
        //arranco el servicio, pasándole la url de descarga
        Intent svc = new Intent(this, ServicioLeerArchivoCSV.class);
        svc.putExtra(EXTRA_URI_CONEXION, urlDescarga);
        startService(svc);

    }

    /**
     * Método que muestra en el LogCat los datos que se le pasan
     *
     * @param datosAlumnosNotas String[] a mostrar en el log
     */
    private void mostrarEnLog(String[] datosAlumnosNotas) {
        for (String asignaturaNotaAlumno : datosAlumnosNotas) {
            Log.d(TAG_APLI, asignaturaNotaAlumno);
        }
    }


    ///////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////   RECEPTOR  AVISOS SERVICIO /////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////

    public class ReceptorAvisosServicio extends BroadcastReceiver {
        // CONSTANTES
        static final String EXTRA_KEY_DATOS_FICHERO = "extra_key_fichero";
        static final int COD_REQUEST_PENDING_INTENT = 0;

        // NOTIFICACIÓN
        private NotificationManager notificationManager;
        static final String CANA_ID = "canal_id";
        static final int NOTIFICACION_ID = 1;

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null) {
                switch (intent.getAction()) {
                    case ServicioLeerArchivoCSV.ACTION_ERROR_IO:
                        Toast.makeText(context, "ERROR DE LECTURA EN EL FICHERO", Toast.LENGTH_LONG).show();
                        return;
                    case ServicioLeerArchivoCSV.ACTION_ERROR_URL:
                        Toast.makeText(context, "ERROR URL INCORRECTA", Toast.LENGTH_LONG).show();
                        return;
                    case ServicioLeerArchivoCSV.ACTION_FIN_CARGA_DATOS:
                        datosAlumnosNotas = intent.getStringArrayExtra(ServicioLeerArchivoCSV.EXTRA_DATOS_CARGADOS);
                        mostrarEnLog(datosAlumnosNotas);
                        // lanzo la notificación
                        lanzarNotificacion(datosAlumnosNotas);
                        Toast.makeText(context, "FIN DESCARGA", Toast.LENGTH_SHORT).show();
                        return;

                }
            }

        }


        /**
         * Método que crea y lanza la notificación
         *
         * @param datos el contenido del fichero descargado en formato String[]
         */
        private void lanzarNotificacion(String[] datos) {
            notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                // Sería para versiones Oreo o superiores
                crearCanalNotificacion(notificationManager);
            }
            // creo un pendingIntent que será ejecutado al pulsar sobre la notificación y que me
            // lanzará la activity que se indica (se le pasan los datos para que esa nueva activity
            // pueda mostrarlos)
            PendingIntent pendingIntent = crearPendingIntent(datos);
            // creo la notificación
            Notification notification = crearNotificacion(notificationManager, pendingIntent);
            //lanzar notificacion
            notificationManager.notify(NOTIFICACION_ID, notification);

        }

        /**
         * Método que crea el canal que voy a usar para las notificaciones de mi app
         *
         * @param notificationManager es el servicio del sistema Android que gestiona y ejecuta las notificaciones
         */
        @RequiresApi(api = Build.VERSION_CODES.O)
        private void crearCanalNotificacion(NotificationManager notificationManager) {
            //creo un canal de notificaciones
            NotificationChannel canal = new NotificationChannel(CANA_ID, "mi canal", NotificationManager.IMPORTANCE_DEFAULT);
            canal.setDescription("Es el canal empleado para notificar el servicio de descarga");
            notificationManager.createNotificationChannel(canal);
        }


        /**
         * Método que crea la notificación
         *
         * @param notificationManager es el servicio del sistema Android que gestiona y ejecuta las notificaciones
         * @param intentPendiente     es el intent que se ejecutará al pulsar sobre la notificación
         */
        private Notification crearNotificacion(NotificationManager notificationManager, PendingIntent intentPendiente) {
            NotificationCompat.Builder notificacion =
                    new NotificationCompat.Builder(getApplicationContext(), CANA_ID)
                            .setSmallIcon(android.R.drawable.stat_sys_download)
                            .setContentTitle("DESCARGA FINALIZADA")
                            .setContentText("La descarga del fichero " + etUrlDescarga.getText().toString() + " ha finalizado. Pulse para verlo")
                            .setAutoCancel(true)
                            .setWhen(Calendar.getInstance().getTimeInMillis())
                            .setContentIntent(intentPendiente);
            return notificacion.build();
        }


        /**
         * Método que crea un PendingIntent que se ejecutará al pulsar sobre la notificación y
         * que abrirá una segunda activity para mostrar en ella los datos que se pasan como
         * parámetro.
         * <p>
         * Un PendingIntent es un Intent que especifica una acción que se llevará a cabo en el futuro.
         *
         * @param datos son los datos a mostrar en la otra activity (el fichero descargado)
         * @return el PendingIntent creado
         */
        private PendingIntent crearPendingIntent(String[] datos) {
            // Intent que quiero que se ejecute en el futuro (al pulsar sobre la notificación en este caso)
            Intent iMostrarFichero = new Intent(getApplicationContext(), MostrarFicheroActivity.class);
            iMostrarFichero.putExtra(EXTRA_KEY_DATOS_FICHERO, datos);
            // creo el PendingIntent
            PendingIntent intentPendiente = PendingIntent.getActivity(MainActivity.this,
                    COD_REQUEST_PENDING_INTENT,
                    iMostrarFichero, PendingIntent.FLAG_IMMUTABLE|PendingIntent.FLAG_UPDATE_CURRENT);
            return intentPendiente;
        }
    }
}