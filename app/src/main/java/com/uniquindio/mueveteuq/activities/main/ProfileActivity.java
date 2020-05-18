package com.uniquindio.mueveteuq.activities.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.uniquindio.mueveteuq.R;
import com.uniquindio.mueveteuq.models.Race;

import java.util.Objects;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class ProfileActivity extends AppCompatActivity {

    private String nickname;
    private String email;
    private Race record;

    TextView tvNickname;
    TextView tvEmail;
    TextView tvPasos;
    TextView tvCalorias;
    TextView tvDistancia;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        tvNickname = findViewById(R.id.tv_nickname_profile);
        tvEmail = findViewById(R.id.tv_correo_profile);
        tvPasos = findViewById(R.id.tv_record_pasos);
        tvCalorias = findViewById(R.id.tv_record_calorias);
        tvDistancia = findViewById(R.id.tv_record_distancia);


        Intent intento = getIntent();
        nickname = intento.getStringExtra("nicknameProfile");
        email = intento.getStringExtra("emailProfile");


        tvNickname.setText(nickname);
        tvEmail.setText(email);

        getUserRecord(nickname);
        //TODO: OnBackPressed: finish activity

    }


    public void getUserRecord(final String nickname) {

        //Get current user

        final FirebaseUser fUser = FirebaseAuth.getInstance().getCurrentUser();

        //Get path of database named "Users" content users info
        CollectionReference records = FirebaseFirestore.getInstance().collection("Records");

        //Get all data of collection

        records.whereEqualTo("nicknameUsuario", nickname).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if (task.isSuccessful()) {

                    for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {


                            int pasos = Integer.parseInt(document.getData().get("pasos").toString());


                            float calorias = Float.parseFloat(document.getData().get("calorias").toString());
                            float distancia = Float.parseFloat(document.getData().get("distancia").toString());


                                imprimirCarrera(pasos, calorias, distancia);


                    }

                } else {

                    Log.d(TAG, "Error getting documents: ", task.getException());
                }
            }
        }

        );


    }

    private void imprimirCarrera(int pasos, float calorias, float distancia) {

        tvPasos.setText("Pasos: " + pasos);
        tvCalorias.setText("Calorias: " + calorias);
        tvDistancia.setText("Distancia: " + distancia);
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
}
