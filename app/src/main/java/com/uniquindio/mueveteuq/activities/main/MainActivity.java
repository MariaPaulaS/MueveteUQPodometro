package com.uniquindio.mueveteuq.activities.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.uniquindio.mueveteuq.R;
import com.uniquindio.mueveteuq.activities.login.HelloLoginActivity;
import com.uniquindio.mueveteuq.activities.podometer.ZonaMapaActivity;

import java.util.Objects;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button btnMapa;
    private Button btnCerrarSesion;
    private TextView tvUsuario;
    private FirebaseAuth auth;
    private FirebaseFirestore db;
    private CollectionReference users;
    private String nickname;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnMapa = findViewById(R.id.btn_ir_mapa);
        btnCerrarSesion = findViewById(R.id.btn_cerrar_sesion);
        tvUsuario = findViewById(R.id.tv_nombre_usuario);
        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        users = db.collection("Users");

        btnMapa.setOnClickListener(this);
        btnCerrarSesion.setOnClickListener(this);

        getUserInfo();


    }

    @Override
    public void onClick(View view) {

        switch(view.getId()){

            case R.id.btn_ir_mapa:
                Intent intento = new Intent(MainActivity.this, ZonaMapaActivity.class);
                startActivity(intento);
                break;

            case R.id.btn_cerrar_sesion:

                auth.signOut();
                Intent intento1 = new Intent(MainActivity.this, HelloLoginActivity.class);
                startActivity(intento1);
                finish();

                break;
        }

    }


    private void getUserInfo(){

     //   String email = Objects.requireNonNull(auth.getCurrentUser()).getEmail();
        final SharedPreferences spr = getSharedPreferences("userCurrentPreferences", Context.MODE_PRIVATE);
        String emailCurrent = spr.getString("emailCurrentUser", "");

        users.whereEqualTo("email", emailCurrent).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                for(QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())){

                    nickname = document.getString("nickname");
                    tvUsuario.setText("¡Hola " + nickname + "!");
                    SharedPreferences.Editor objetoEditor = spr.edit();
                    objetoEditor.putString("currentUser", nickname);
                    objetoEditor.apply();


                }
                
            }
        });


    }



}
