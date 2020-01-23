
package com.uniquindio.mueveteuq.activities.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.uniquindio.mueveteuq.models.User;
import com.uniquindio.mueveteuq.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.uniquindio.mueveteuq.util.UtilsNetwork;

/**
 * Activity de registro de usuario
 * Tiene conexión con Firebase, usa Authentication y Realtime Database al mismo tiempo.
 * Permite registrar un usuario mientras que a su vez lo añade a la base de datos usando un modelo.
 * @author MariaTheCharmix
 */
public class RegisterActivity extends AppCompatActivity {

    /**
     * Atributos de vistas
     */
    private Button btnLogin;
    private Button btnRegistrar;
    private ProgressDialog progressDialog;
    private EditText textoCorreo;
    private EditText textoPassword;
    private EditText textoNickname;
    private EditText textoVerifyPassword;
    private ConstraintLayout cl;

    /**
     * Atributos de Firebase y Database
     */
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase db;
    private DatabaseReference users;




    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        firebaseAuth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
        users = db.getReference("Users");

        getSupportActionBar().hide();

        btnLogin = findViewById(R.id.btn_login);
        btnRegistrar = findViewById(R.id.btn_register);
        cl = findViewById(R.id.constraint_activity_register);
        textoCorreo = findViewById(R.id.val_email);
        textoPassword = findViewById(R.id.val_password);
        textoNickname = findViewById(R.id.val_nickname);
        textoVerifyPassword = findViewById(R.id.val_verify_password);


        //Boton por si el usuario ya tiene una cuenta existente
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intento = new Intent(view.getContext(), LoginActivity.class);
                startActivity(intento);
                finish();
            }
        });


        //Boton para registrar el usuario
        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                registrarUsuario();

            }
        });


    }


    private void init(){
        this.progressDialog = new ProgressDialog(this);
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


    /**
     * Método que valida que los datos sean correctos y
     * si es así permite el registro.
     */
    private void registrarUsuario() {

        final String email = textoCorreo.getText().toString().trim();
        final String password = textoPassword.getText().toString().trim();
        String verifyPassword = textoVerifyPassword.getText().toString().trim();
        final String nickname = textoNickname.getText().toString().trim();

        //El nickname no puede estar vacío
        if (TextUtils.isEmpty(nickname)) {
            Toast.makeText(this, "Por favor ingrese un nickname", Toast.LENGTH_SHORT).show();
            return;
        }

        //El email no puede estar vacío
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Por favor ingrese un email", Toast.LENGTH_SHORT).show();
            return;
        }

        //La contraseña no puede estar vacía
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Por favor ingrese una contraseña", Toast.LENGTH_SHORT).show();
            return;
        }

        //La verificación de contraseña no puede estar vacía
        if (TextUtils.isEmpty(verifyPassword)) {
            Toast.makeText(this, "Por favor repita la contraseña", Toast.LENGTH_SHORT).show();
            return;
        }

        //La contraseña no puede ser menor de 8 caracteres
        if (password.length() < 8) {
            Toast.makeText(this, "La contraseña debe tener al menos 8 caracteres", Toast.LENGTH_SHORT).show();
            return;
        } 

        //Las contraseñas deben coincidir
        if (!password.equals(verifyPassword)) {
            Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
            return;
        }



        init();
        showProgressBar();

        if(UtilsNetwork.isOnline(this)){





        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        //Guardar usuario en la base de datos
                        User user = new User();
                        user.setEmail(email);
                        user.setNickname(nickname);
                        user.setPassword(password);

                        //Usa el nickname como llave.
                        users.child(user.getNickname()).setValue(user)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {


                                        progressDialog.dismiss();
                                        Toast.makeText(RegisterActivity.this, "¡El usuario ha sido registrado con éxito!", Toast.LENGTH_SHORT).show();


                                        //Método contra dedos temblorosos -que oprimen doble-.
                                        Intent intento = new Intent(RegisterActivity.this, HelloLoginActivity.class);
                                        startActivity(intento);
                                        finish();

                                    }
                                }).addOnFailureListener(new OnFailureListener() { //Error interno - ¿del usuario?
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                progressDialog.dismiss();


                                Snackbar.make(cl, R.string.error_user_data,
                                        Snackbar.LENGTH_SHORT).show();


                            }
                        });


                    }
                }).addOnFailureListener(new OnFailureListener() {      //Error externo - ¿de la base de datos?
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();

                Snackbar.make(cl, R.string.error_user_data,
                        Snackbar.LENGTH_SHORT).show();


            }
        });
        }
        else{
            progressDialog.dismiss();
            Snackbar.make(cl, R.string.connection_missing,
                    Snackbar.LENGTH_SHORT).show();
            return;
        }


    }


    /**
     * Método para controlar el evento del botón de atrás
     * Lanza un mensaje al usuario si trata de abandonar la vista de registro con datos ingresados
     * @param keyCode
     * @param event
     * @return
     */

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        final String email = textoCorreo.getText().toString().trim();
        final String password = textoPassword.getText().toString().trim();
        String verifyPassword = textoVerifyPassword.getText().toString().trim();
        final String nickname = textoNickname.getText().toString().trim();


        if (keyCode == event.KEYCODE_BACK) {


            if(!TextUtils.isEmpty(email) || !TextUtils.isEmpty(password) || !TextUtils.isEmpty(verifyPassword) || !TextUtils.isEmpty(nickname)){

                new AlertDialog.Builder(RegisterActivity.this)
                        .setTitle("¡Espera!")
                        .setMessage("Aun hay datos aquí. ¿Seguro que quieres irte?")
                        .setPositiveButton("Adelante", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intento = new Intent(RegisterActivity.this, HelloLoginActivity.class);
                                startActivity(intento);
                                finish();


                            }
                        }).setNegativeButton("Me quedo", null).show();

            }

        }


        return super.onKeyDown(keyCode, event);
    }


    @Override
    protected void onResume() {
        super.onResume();

        if(UtilsNetwork.isOnline(this)){

        }

        else{
            Snackbar.make(cl, R.string.connection_missing,
                    Snackbar.LENGTH_SHORT).show();
            return;
        }




    }
}
