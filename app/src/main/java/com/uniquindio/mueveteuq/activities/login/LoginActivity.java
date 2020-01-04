package com.uniquindio.mueveteuq.activities.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.uniquindio.mueveteuq.R;
import com.uniquindio.mueveteuq.activities.ZonaMapaActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Activity que permite el ingreso al usuario a la aplicación
 */
public class LoginActivity extends AppCompatActivity {

    private Button btnCrearCuenta;
    private Button btnIniciarSesion;
    private FirebaseAuth firebaseAuth;
    private EditText textoEmail;
    private EditText textoPassword;
    private TextView resetPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();


        getSupportActionBar().hide();

        textoEmail = findViewById(R.id.val_email);
        textoPassword = findViewById(R.id.val_password);
        resetPassword = findViewById(R.id.tvolvidarcontrasena);
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


        resetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intento = new Intent(view.getContext(), ResetPasswordActivity.class);
                startActivity(intento);
                finish();
            }
        });

    }




    private void iniciarSesionUsuario() {

        final String email = textoEmail.getText().toString().trim();
        final String password = textoPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Por favor ingrese un e-mail válido.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Por favor ingrese una contraseña.", Toast.LENGTH_SHORT).show();
            return;

        }

        if (password.length() < 8) {
            Toast.makeText(this, "La contraseña debe tener al menos 8 caracteres.", Toast.LENGTH_SHORT).show();
        }


        firebaseAuth.signInWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {

                Intent intento = new Intent(LoginActivity.this, ZonaMapaActivity.class);
                startActivity(intento);


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(LoginActivity.this, "Ha ocurrido un error al iniciar sesión. Por favor revisa tus datos y vuelve a intentarlo.", Toast.LENGTH_SHORT).show();
            }
        });


    }


}
