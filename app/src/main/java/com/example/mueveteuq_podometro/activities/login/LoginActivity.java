package com.example.mueveteuq_podometro.activities.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mueveteuq_podometro.R;
import com.example.mueveteuq_podometro.activities.MainActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class LoginActivity extends AppCompatActivity {

    private Button btnCrearCuenta;
    private Button btnIniciarSesion;
    private FirebaseAuth firebaseAuth;
    private EditText textoEmail;
    private EditText textoPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();


        getSupportActionBar().hide();

        textoEmail = findViewById(R.id.val_email);
        textoPassword = findViewById(R.id.val_password);
        btnIniciarSesion = findViewById(R.id.btn_iniciar_sesion);
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


        //Botón que inicia sesión y da paso a la pantalla de inicio
        btnIniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iniciarSesionUsuario();
            }
        });

    }


        private void iniciarSesionUsuario(){

        final String email = textoEmail.getText().toString().trim();
        final String password = textoPassword.getText().toString().trim();

        if(TextUtils.isEmpty(email)){
            Toast.makeText(this, "Por favor ingrese un e-mail válido.", Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(password)){
            Toast.makeText(this, "Por favor ingrese una contraseña.", Toast.LENGTH_SHORT).show();
            return;

        }

        if(password.length()<8){
            Toast.makeText(this, "La contraseña debe tener al menos 8 caracteres.", Toast.LENGTH_SHORT).show();
        }


        firebaseAuth.signInWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {

                Intent intento = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intento);


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(LoginActivity.this, "Ha ocurrido un error al iniciar sesión.", Toast.LENGTH_SHORT).show();
            }
        });



        }


}
