package com.uniquindio.mueveteuq.activities.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

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
import android.widget.Toast;

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
import com.uniquindio.mueveteuq.fragments.mainZone.HomeFragment;
import com.uniquindio.mueveteuq.fragments.mainZone.UsersFragment;
import com.uniquindio.mueveteuq.util.UtilsNetwork;

import java.util.Objects;

public class MainActivity extends AppCompatActivity{

    private Fragment actualFragment;
    private Toolbar toolbar;
    private String activeFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.include2);
        setSupportActionBar(toolbar);


            actualFragment = new HomeFragment();
            changeFragment(actualFragment);
            activeFragment = "HomeFragment";
        }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);

        SearchManager manager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

        final MenuItem searchItem = menu.findItem(R.id.item_buscar_persona);
        final MenuItem peopleItem = menu.findItem(R.id.item_usuarios);
        final SearchView searchView = (SearchView) searchItem.getActionView();

        peopleItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {

                actualFragment.getFragmentManager().popBackStack();
                setSupportActionBar(toolbar);
                actualFragment = new UsersFragment();
                changeFragment(actualFragment);
                activeFragment = "UsersFragment";
                return false;
            }
        });


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


    private void changeFragment(Fragment fragmento){

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.contenedor_fragmento_main, fragmento)
                .commit();
        actualFragment.getFragmentManager().popBackStack();



    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        /**
        switch (activeFragment){


            case "UsersFragment":
                actualFragment = new HomeFragment();
                changeFragment(actualFragment);
                activeFragment = "HomeFragment";
                break;

            default:
                break;
        }

    }
         **/
}
}