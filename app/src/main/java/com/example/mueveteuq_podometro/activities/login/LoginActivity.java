package com.example.mueveteuq_podometro.activities.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.mueveteuq_podometro.R;
import com.google.firebase.auth.FirebaseAuth;


public class LoginActivity extends AppCompatActivity {

    private Button btnCrearCuenta;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();


        getSupportActionBar().hide();


        btnCrearCuenta = findViewById(R.id.btnCrearCuenta);

        //Redirige a la vista de registro si el usuario no tiene una cuenta
        btnCrearCuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intento = new Intent(view.getContext(), RegisterActivity.class);
                startActivity(intento);
                finish();
            }
        });


    }



}
