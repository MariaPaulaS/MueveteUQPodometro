package com.uniquindio.mueveteuq.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.uniquindio.mueveteuq.R;

import java.text.DecimalFormat;

public class ResultRaceActivity extends AppCompatActivity {

    Button btnGuardar;
    TextView tvResPasos;
    TextView tvResCalorias;
    TextView tvResDistancia;
    TextView tvPoints;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_race);

        getSupportActionBar().hide();

        btnGuardar = findViewById(R.id.btn_ok);
        tvResPasos = findViewById(R.id.txtResPasos);
        tvResCalorias = findViewById(R.id.txtResCalorias);
        tvResDistancia = findViewById(R.id.txtResDistancia);
        tvPoints = findViewById(R.id.txtResPoints);


        SharedPreferences preferencias= getSharedPreferences("pref", Context.MODE_PRIVATE);

        float distancia = preferencias.getFloat("distanciaFinal", 0);
        int pasos = preferencias.getInt("pasosFinales", 0);
        DecimalFormat formatear = new DecimalFormat("#.00");



        tvResDistancia.setText(formatear.format(distancia) + " m");

        tvResPasos.setText(pasos + "");

        SharedPreferences.Editor objetoEditor = preferencias.edit();
        objetoEditor.remove("distanciaFinal");
        objetoEditor.remove("pasosFinales");
        objetoEditor.apply();

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ResultRaceActivity.this, "¡Felicidades! Has terminado el recorrido", Toast.LENGTH_SHORT).show();

            }
        });

    }
}
