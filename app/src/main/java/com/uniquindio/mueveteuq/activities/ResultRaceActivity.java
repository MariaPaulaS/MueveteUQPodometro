package com.uniquindio.mueveteuq.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.uniquindio.mueveteuq.R;
import com.uniquindio.mueveteuq.models.Race;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

public class ResultRaceActivity extends AppCompatActivity {

    Button btnGuardar;
    TextView tvResPasos;
    TextView tvResCalorias;
    TextView tvResDistancia;
    TextView tvPoints;

    //Crea una instancia a la base de datos
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference races;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_race);

        getSupportActionBar().hide();
        races = db.collection("Races");


        btnGuardar = findViewById(R.id.btn_ok);
        tvResPasos = findViewById(R.id.txtResPasos);
        tvResCalorias = findViewById(R.id.txtResCalorias);
        tvResDistancia = findViewById(R.id.txtResDistancia);
        tvPoints = findViewById(R.id.txtResPoints);


        SharedPreferences preferencias= getSharedPreferences("pref", Context.MODE_PRIVATE);

        final float distancia = preferencias.getFloat("distanciaFinal", 0);
        final int pasos = preferencias.getInt("pasosFinales", 0);
        final float calorias = preferencias.getFloat("caloriasFinales", 0);

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


                Race race = new Race();
                race.setCalorias(calorias);
                race.setDistancia(distancia);
                race.setPasos(pasos);
                race.setPuntos(puntos);
                race.setNicknameUsuario("MariaTheCharmix"); //TODO: CAMBIAR POR USUARIO ACTUALMENTE LOGUEADO

               races.document().set(race)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d("tag", "Nota de desarrolladora: ¡Se ha guardado la carrera con éxito!");
                                Toast.makeText(ResultRaceActivity.this, "¡Felicidades! Has terminado el recorrido", Toast.LENGTH_SHORT).show();

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
}
