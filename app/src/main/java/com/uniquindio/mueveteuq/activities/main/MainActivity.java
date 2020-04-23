package com.uniquindio.mueveteuq.activities.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.uniquindio.mueveteuq.util.UtilsNetwork;

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

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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

    /**
     * Método que almacena los eventos de todos los botones
     * -ojala hubiera sabido esto antes-
     * @param view
     */
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


    /**
     * Método que obtiene la información del usuario actual (nickname)
     */
    private void getUserInfo(){

     //   String email = Objects.requireNonNull(auth.getCurrentUser()).getEmail();
        final SharedPreferences spr = getSharedPreferences("userCurrentPreferences", Context.MODE_PRIVATE);
        String emailCurrent = spr.getString("emailCurrentUser", "");

        if(UtilsNetwork.isOnline(this)){
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);

        SearchManager manager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

        final MenuItem searchItem = menu.findItem(R.id.item_buscar_persona);
        final SearchView searchView = (SearchView) searchItem.getActionView();


        EditText txtSearch = searchView.findViewById(androidx.appcompat.R.id.search_src_text);
        txtSearch.setHint("Busca un usuario");
        txtSearch.setHintTextColor(getResources().getColor(R.color.blanco_transparente));
        txtSearch.setTextColor(getResources().getColor(R.color.blanco));


        assert manager != null;
        searchView.setSearchableInfo(manager.getSearchableInfo(getComponentName()));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchView.clearFocus();
                searchView.setQuery("", false);
                searchItem.collapseActionView();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return true;
    }
}
