package com.uniquindio.mueveteuq.fragments.mainZone;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.uniquindio.mueveteuq.R;
import com.uniquindio.mueveteuq.activities.login.HelloLoginActivity;
import com.uniquindio.mueveteuq.activities.main.MainActivity;
import com.uniquindio.mueveteuq.activities.podometer.ZonaMapaActivity;
import com.uniquindio.mueveteuq.util.UtilsNetwork;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private Button btnMapa;
    private TextView tvUsuario;
    private FirebaseAuth auth;
    private FirebaseFirestore db;
    private CollectionReference users;
    private String nickname;
    private String password;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        btnMapa = view.findViewById(R.id.btn_ir_mapa);
        tvUsuario = view.findViewById(R.id.tv_nombre_usuario);
        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        users = db.collection("Users");
        btnMapa.setOnClickListener(this);

        getUserInfo();


        return view;
    }


    /**
     * Método que obtiene la información del usuario actual (nickname)
     */
    private void getUserInfo(){

        //   String email = Objects.requireNonNull(auth.getCurrentUser()).getEmail();
        final SharedPreferences spr = this.getActivity().getSharedPreferences("userCurrentPreferences", Context.MODE_PRIVATE);
        String emailCurrent = spr.getString("emailCurrentUser", "");

        if(UtilsNetwork.isOnline(this.getActivity())){
            users.whereEqualTo("email", emailCurrent).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {

                    for(QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())){

                        nickname = document.getString("nickname");
                        tvUsuario.setText("¡Hola " + nickname + "!");
                        password = document.getString("password");
                        SharedPreferences.Editor objetoEditor = spr.edit();
                        objetoEditor.putString("currentUser", nickname);
                        objetoEditor.putString("passwordCurrentUser", password);
                        objetoEditor.apply();


                    }

                }
            });
        }


    }



    /**
     * Método que almacena los eventos de todos los botones
     * -ojala hubiera sabido esto antes-
     * @param view
     */
    @Override
    public void onClick(View view) {

        switch(view.getId()){

            case R.id.btn_ir_mapa:
                Intent intento = new Intent(getActivity(), ZonaMapaActivity.class);
                startActivity(intento);
                break;

        }

    }



}
