package com.uniquindio.mueveteuq.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.uniquindio.mueveteuq.R;

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

        btnGuardar = findViewById(R.id.btn_ok);
        tvResPasos = findViewById(R.id.txtResPasos);
        tvResCalorias = findViewById(R.id.txtResCalorias);
        tvResDistancia = findViewById(R.id.txtResDistancia);
        tvPoints = findViewById(R.id.txtResPoints);
    }
}
