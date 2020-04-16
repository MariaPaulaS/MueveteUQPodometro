package com.uniquindio.mueveteuq.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.uniquindio.mueveteuq.R;
import com.uniquindio.mueveteuq.activities.login.HelloLoginActivity;
import com.uniquindio.mueveteuq.models.Race;
import com.uniquindio.mueveteuq.util.UtilsNetwork;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ResultRaceActivity extends AppCompatActivity {

    Button btnGuardar;
    TextView tvResPasos;
    TextView tvResCalorias;
    TextView tvResDistancia;
    TextView tvPoints;

    //Crea una instancia a la base de datos
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference races;
    private CollectionReference records;

    Race race = new Race();
    private String usuarioRecord;
    private long pasosRecord;
    private float distancia;
    private float calorias;
    private int pasos;
    private String nicknameUsuario;
    private int puntos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_race);

        getSupportActionBar().hide();
        races = db.collection("Races");
        records = db.collection("Records");


        btnGuardar = findViewById(R.id.btn_ok);
        tvResPasos = findViewById(R.id.txtResPasos);
        tvResCalorias = findViewById(R.id.txtResCalorias);
        tvResDistancia = findViewById(R.id.txtResDistancia);
        tvPoints = findViewById(R.id.txtResPoints);


        SharedPreferences preferencias= getSharedPreferences("pref", Context.MODE_PRIVATE);

        distancia = preferencias.getFloat("distanciaFinal", 0);
        pasos = preferencias.getInt("pasosFinales", 0);
        calorias = preferencias.getFloat("caloriasFinales", 0);
        nicknameUsuario = "Seikken"; //TODO: CAMBIAR POR USUARIO ACTUALMENTE LOGUEADO

        puntos = Math.round(calorias);
        DecimalFormat formatear = new DecimalFormat("#.00");



        tvResDistancia.setText(formatear.format(distancia) + " m");

        tvResPasos.setText(pasos + "");

        tvResCalorias.setText(calorias + "");

        tvPoints.setText(puntos + "");

        SharedPreferences.Editor objetoEditor = preferencias.edit();
        objetoEditor.remove("distanciaFinal");
        objetoEditor.remove("pasosFinales");
        objetoEditor.remove("caloriasFinales");
        objetoEditor.apply();

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                registrarCarrera();


    }

        });

    }




    public void registrarCarrera(){


        race.setCalorias(calorias);
        race.setDistancia(distancia);
        race.setPasos(pasos);
        race.setPuntos(puntos);
        race.setNicknameUsuario(nicknameUsuario);


        if(UtilsNetwork.isOnline(this)){


            verificarRecord();

            races.document().set(race)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d("tag", "Nota de desarrolladora: ¡Se ha guardado la carrera con éxito!");
                            Intent intento = new Intent(ResultRaceActivity.this, HelloLoginActivity.class);
                            startActivity(intento);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w("tag", "Error escribiendo documento", e);
                        }
                    });


        }





    }


    /**
     * Método que registra un record en Firebase cuando se alcanza uno
     * Caso 1: Cuando el registro de record del usuario es superado
     * Caso 2: Cuando no hay un record existente
     *
     */
    public void verificarRecord(){

        records.whereEqualTo("nicknameUsuario", nicknameUsuario)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {


                        if (task.isSuccessful()) {

                            for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {

                                usuarioRecord = String.valueOf(document.getData().get("nicknameUsuario"));
                                pasosRecord = (long) document.getData().get("pasos");

                                Toast.makeText(ResultRaceActivity.this, "usuario: " + usuarioRecord, Toast.LENGTH_SHORT).show();



                                if(usuarioRecord!=null){

                                    records.document(nicknameUsuario).set(race).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Log.d("tag", "Nota de desarrolladora: ¡Se ha alcanzado un nuevo record!");

                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.w("tag", "Error escribiendo documento", e);

                                        }
                                    });

                                    Toast.makeText(ResultRaceActivity.this, R.string.record, Toast.LENGTH_SHORT).show();


                                }

                                else if(pasosRecord < (long) pasos){

                                    records.document(nicknameUsuario).set(race).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Log.d("tag", "Nota de desarrolladora: ¡Se ha alcanzado un nuevo record!");

                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.w("tag", "Error escribiendo documento", e);

                                        }
                                    });

                                    Toast.makeText(ResultRaceActivity.this, R.string.record, Toast.LENGTH_SHORT).show();

                                }


                                Log.d("tag", document.getId() + " => " + document.getData());
                            }
                        } else {
                            Log.d("tag", "Error getting documents: ", task.getException());
                        }
                    }
                });


    }
}
