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

        final float distancia = preferencias.getFloat("distanciaFinal", 0);
        final int pasos = preferencias.getInt("pasosFinales", 0);
        final float calorias = preferencias.getFloat("caloriasFinales", 0);
        final String nicknameUsuario = "MariaTheCharmix"; //TODO: CAMBIAR POR USUARIO ACTUALMENTE LOGUEADO

        final int puntos = Math.round(calorias);
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

                race.setCalorias(calorias);
                race.setDistancia(distancia);
                race.setPasos(pasos);
                race.setPuntos(puntos);
                race.setNicknameUsuario(nicknameUsuario);

                records.whereEqualTo("nicknameUsuario", race.getNicknameUsuario()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if(task.isSuccessful()) {

                            for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {

                                String usuarioRecord = String.valueOf(document.getData().get("nicknameUsuario"));

                                //Si existía un record con anterioridad

                                if(nicknameUsuario.equals(usuarioRecord)){
                                    Race recordActual = (Race) document.getData();

                                    //Si los pasos de esta carrera son superiores a los del record guardado
                                    if (recordActual.getPasos() < race.getPasos()) {
                                        registrarRecord(race, nicknameUsuario);
                                        Toast.makeText(getApplicationContext(), "¡Felicidades! Has alcanzado un nuevo record", Toast.LENGTH_SHORT).show();

                                        //Si los pasos son inferiores
                                    }else{
                                        Log.d("tag", "No se ha alcanzado un record. Todo sigue igual");

                                    }
                                //Si no existía un record con anterioridad
                                } else {
                                    registrarRecord(race, nicknameUsuario);
                                    Toast.makeText(getApplicationContext(),"¡Felicidades! Has alcanzado un nuevo record", Toast.LENGTH_SHORT).show();


                                }

                            }
                        }
                    }
                });

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
        });

    }

    /**
     * Método que registra un record en Firebase cuando se alcanza uno
     * Caso 1: Cuando el registro de record del usuario es superado
     * Caso 2: Cuando no hay un record existente
     * @param race
     */
    public void registrarRecord(Race race, String nicknameUsuario){


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


    }
}
