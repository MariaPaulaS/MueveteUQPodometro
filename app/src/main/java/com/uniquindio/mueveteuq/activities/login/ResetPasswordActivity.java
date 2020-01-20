package com.uniquindio.mueveteuq.activities.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.uniquindio.mueveteuq.R;

public class ResetPasswordActivity extends AppCompatActivity {

    private EditText textoEmail;
    private Button btnRecovery;
    private String email="";
    private ProgressDialog progressDialog;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        getSupportActionBar().hide();

        textoEmail = findViewById(R.id.val_email);
        btnRecovery = findViewById(R.id.btn_recovery);

        mAuth = FirebaseAuth.getInstance();

        btnRecovery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                resetPassword();
            }
        });

    }


    public void resetPassword(){

        email = textoEmail.getText().toString().trim();

        if(TextUtils.isEmpty(email)){

            Toast.makeText(this, "Por favor ingrese un correo electrónico", Toast.LENGTH_SHORT).show();
            return;
        }

        init();
        showProgressBar();

        mAuth.setLanguageCode("es");
        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if(task.isSuccessful()){


                    progressDialog.dismiss();
                    Toast.makeText(ResetPasswordActivity.this, "Se ha enviado un correo para recuperar tu contraseña. Si no ves nada, por favor revisa tu carpeta de spam.", Toast.LENGTH_SHORT).show();

                    Intent intento = new Intent(ResetPasswordActivity.this, HelloLoginActivity.class);
                    startActivity(intento);
                    finish();

                }


                else{
                    progressDialog.dismiss();
                    Toast.makeText(ResetPasswordActivity.this, "No se ha podido enviar el correo de recuperación.", Toast.LENGTH_SHORT).show();


                }

            }
        });


    }


    /**
     * Método que muestra la progress bar
     * Version 1: Version por defecto -comentada-
     * Version 2: Version personalizada
     *
     */
    public void showProgressBar(){
        //     progressDialog.setCancelable(false);
        //     progressDialog.setMessage("Espera un momento...");
        //     progressDialog.show();


        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_dialog);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

    }


    private void init(){
        this.progressDialog = new ProgressDialog(this);
    }


}
