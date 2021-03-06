package com.uniquindio.mueveteuq.activities.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

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
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.uniquindio.mueveteuq.R;
import com.uniquindio.mueveteuq.util.Utilities;
import com.uniquindio.mueveteuq.util.UtilsNetwork;

public class ResetPasswordActivity extends AppCompatActivity {

    private EditText textoEmail;
    private Button btnRecovery;
    private String email = "";


    private FirebaseAuth mAuth;
    private ConstraintLayout cl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

      //  getSupportActionBar().hide();

        textoEmail = findViewById(R.id.val_email);
        btnRecovery = findViewById(R.id.btn_recovery);
        cl = findViewById(R.id.constraint_activity_reset_password);
        mAuth = FirebaseAuth.getInstance();

        btnRecovery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                resetPassword();
            }
        });

    }


    public void resetPassword() {

        email = textoEmail.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {

            Toast.makeText(this, "Por favor ingrese un correo electrónico", Toast.LENGTH_SHORT).show();
            return;
        }

        Utilities.init(this);
        Utilities.showProgressBar();

        if (UtilsNetwork.isOnline(this)) {


            mAuth.setLanguageCode("es");
            mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {

                    if (task.isSuccessful()) {


                        Utilities.dismissProgressBar();

                        Snackbar.make(cl, R.string.recovery_password,
                                Snackbar.LENGTH_SHORT).show();

                        Intent intento = new Intent(ResetPasswordActivity.this, HelloLoginActivity.class);
                        startActivity(intento);
                        finish();

                    } else {
                        Utilities.dismissProgressBar();

                        Snackbar.make(cl, R.string.not_recovery_password,
                                Snackbar.LENGTH_SHORT).show();

                    }

                }
            });
        } else {
            Utilities.dismissProgressBar();
            Snackbar.make(cl, R.string.connection_missing,
                    Snackbar.LENGTH_SHORT).show();

            return;
        }


    }


    @Override
    protected void onResume() {
        super.onResume();

        if (UtilsNetwork.isOnline(this)) {

        } else {
            Snackbar.make(cl, R.string.connection_missing,
                    Snackbar.LENGTH_SHORT).show();
            return;
        }


    }
}
