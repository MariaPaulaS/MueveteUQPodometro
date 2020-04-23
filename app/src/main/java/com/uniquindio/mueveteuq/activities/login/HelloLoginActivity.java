package com.uniquindio.mueveteuq.activities.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
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

import com.google.firebase.auth.FirebaseAuth;
import com.uniquindio.mueveteuq.R;
import com.uniquindio.mueveteuq.activities.main.MainActivity;
import com.uniquindio.mueveteuq.util.Utilities;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

/**
 * Actividad que recibe al usuario y le da la opcion de crear cuenta o registrarse
 * Configurar para opcion de invitado
 * @author MariaTheCharmix
 */
public class HelloLoginActivity extends AppCompatActivity {


    private Button btnIniciarSesion;
    private Button btnRegistro;
    private FirebaseAuth auth;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello_login);

        toolbar = findViewById(R.id.toolbar);
    //    Utilities.validarPermisos(this, HelloLoginActivity.this);
    //    getActionBar().hide();
        //getSupportActionBar().hide();

        btnIniciarSesion = findViewById(R.id.btn_go_login);

        btnRegistro = findViewById(R.id.btn_go_register);
        auth = FirebaseAuth.getInstance();


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




    @Override
    protected void onResume() {
        super.onResume();

        //Validar permisos de la app
        Utilities.validarPermisos(this, HelloLoginActivity.this);


    }

    /**
     * Si el usuario no ha cerrado sesión llévame a la pantalla principal
     */
    @Override
    protected void onStart() {
        super.onStart();

        if(auth.getCurrentUser()!=null){

            startActivity(new Intent(HelloLoginActivity.this, MainActivity.class));

        }
    }

}
