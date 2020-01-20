package com.uniquindio.mueveteuq.activities.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.uniquindio.mueveteuq.R;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class HelloLoginActivity extends AppCompatActivity {


    private Button btnIniciarSesion;
    private Button btnRegistro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello_login);

        validarPermisos();


        getSupportActionBar().hide();

        btnIniciarSesion = findViewById(R.id.btn_go_login);

        btnRegistro = findViewById(R.id.btn_go_register);


        btnIniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intento = new Intent(view.getContext(), LoginActivity.class);
                startActivity(intento);
            }
        });


        btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intento = new Intent(view.getContext(), RegisterActivity.class);
                startActivity(intento);
            }
        });

    }


    /**
     * Método que verifica si la app tiene permisos para funcionar. Si no, los pide al momento de
     * la instalación.
     * Solo se piden una vez.
     * Funciona desde Android Malvavisco en adelante.
     */
    private void validarPermisos() {

        //Para version 5.
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return;
        }

        if (ContextCompat.checkSelfPermission(HelloLoginActivity.this, WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(HelloLoginActivity.this, CAMERA)
                != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(HelloLoginActivity.this, ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(HelloLoginActivity.this, ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted

            ActivityCompat.requestPermissions(HelloLoginActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION, CAMERA, WRITE_EXTERNAL_STORAGE}, 1);


        }
    }


    /**
     * Método para verificar conexión a internet
     */
    private void verificarConexion(){

        ConnectivityManager manager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activityNetwork = manager.getActiveNetworkInfo();


    }

}
