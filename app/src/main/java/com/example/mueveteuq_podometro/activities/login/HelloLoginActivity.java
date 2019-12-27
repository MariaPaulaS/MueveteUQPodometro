package com.example.mueveteuq_podometro.activities.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.mueveteuq_podometro.R;

public class HelloLoginActivity extends AppCompatActivity {


    private Button btnIniciarSesion;
    private Button btnRegistro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello_login);


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
}
