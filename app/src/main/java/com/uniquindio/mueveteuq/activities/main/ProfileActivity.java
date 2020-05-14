package com.uniquindio.mueveteuq.activities.main;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.uniquindio.mueveteuq.R;
import com.uniquindio.mueveteuq.models.Race;

public class ProfileActivity extends AppCompatActivity {

    private String nickname;
    private String email;
    private Race record;

    TextView tvNickname;
    TextView tvEmail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        tvNickname = findViewById(R.id.tv_nickname_profile);
        tvEmail = findViewById(R.id.tv_correo_profile);


        Intent intento = getIntent();
        nickname = intento.getStringExtra("nicknameProfile");
        email = intento.getStringExtra("emailProfile");

        tvNickname.setText(nickname);
        tvEmail.setText(email);

        //TODO: OnBackPressed: finish activity

    }








}
