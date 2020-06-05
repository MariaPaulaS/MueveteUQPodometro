package com.uniquindio.mueveteuq.activities.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.uniquindio.mueveteuq.R;
import com.uniquindio.mueveteuq.activities.login.HelloLoginActivity;
import com.uniquindio.mueveteuq.activities.podometer.ZonaMapaActivity;
import com.uniquindio.mueveteuq.fragments.mainZone.AjustesFragment;
import com.uniquindio.mueveteuq.fragments.mainZone.HomeFragment;
import com.uniquindio.mueveteuq.fragments.mainZone.LeaderboardFragment;
import com.uniquindio.mueveteuq.fragments.mainZone.PreferenciasFragment;
import com.uniquindio.mueveteuq.fragments.mainZone.RewardContainerFragment;
import com.uniquindio.mueveteuq.fragments.mainZone.RewardsFragment;
import com.uniquindio.mueveteuq.fragments.mainZone.SearchFragment;
import com.uniquindio.mueveteuq.fragments.mainZone.TipsFragment;
import com.uniquindio.mueveteuq.fragments.mainZone.UsersFragment;
import com.uniquindio.mueveteuq.util.UtilsNetwork;

import java.util.Objects;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Fragment actualFragment;
    private Toolbar toolbar;
    private String activeFragment;
    private String valor;
    DrawerLayout mDrawerLayout;
    ActionBarDrawerToggle mDrawerToggle;
    private FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.include2);
        setSupportActionBar(toolbar);



         actualFragment = new HomeFragment();
         changeFragment(actualFragment);
         activeFragment = "HomeFragment";

        auth = FirebaseAuth.getInstance();
        mDrawerLayout = findViewById(R.id.drawerlayout);


        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.aceptar, R.string.activate);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();

        SharedPreferences preferencias = getSharedPreferences("busquedaPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor objetoEditor = preferencias.edit();
        objetoEditor.remove("cadenaBusqueda");
        objetoEditor.apply();

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

        searchItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                actualFragment.getFragmentManager().popBackStack();
                setSupportActionBar(toolbar);
                actualFragment = new SearchFragment();
                changeFragment(actualFragment);
                activeFragment = "SearchFragment";
                return false;
            }
        });


        EditText txtSearch = searchView.findViewById(androidx.appcompat.R.id.search_src_text);
        txtSearch.setHint("Busca un usuario");
        txtSearch.setHintTextColor(getResources().getColor(R.color.blanco_transparente));
        txtSearch.setTextColor(getResources().getColor(R.color.blanco));

        final SearchFragment searchFragment = new SearchFragment();
        assert manager != null;
        searchView.setSearchableInfo(manager.getSearchableInfo(getComponentName()));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {


            @Override
            public boolean onQueryTextSubmit(String s) {
                searchView.clearFocus();
                searchView.setQuery("", false);
                searchItem.collapseActionView();

                valor = s;


                enviarBusqueda(s);

                actualFragment.getFragmentManager().popBackStack();
                setSupportActionBar(toolbar);
                actualFragment = new SearchFragment();
                changeFragment(actualFragment);
                activeFragment = "SearchFragment";

                //searchFragment.getSearchResult(s);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {

                return false;
            }
        });

        return true;
    }


    private void changeFragment(Fragment fragmento) {

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.contenedor_fragmento_main, fragmento)
                .commit();
        actualFragment.getFragmentManager().popBackStack();


    }

    @Override
    public void onBackPressed() {

        if(activeFragment.equals("HomeFragment")){

            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);


        }


        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {

            mDrawerLayout.closeDrawer(GravityCompat.START);
            Toast.makeText(this, "Esto2", Toast.LENGTH_SHORT).show();

        } else {
            super.onBackPressed();
        }
    }

    public void enviarBusqueda(String s) {

        SharedPreferences preferences = getSharedPreferences("busquedaPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor objetoEditor = preferences.edit();
        objetoEditor.putString("cadenaBusqueda", s);
        objetoEditor.apply();
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();

        switch (id) {

            case R.id.home:

                actualFragment = new HomeFragment();
                changeFragment(actualFragment);
                activeFragment = "HomeFragment";
                break;

            case R.id.tips:
                actualFragment = new TipsFragment();
                changeFragment(actualFragment);
                activeFragment = "TipsFragment";
                break;

            case R.id.awards:
                actualFragment = new RewardContainerFragment();
                changeFragment(actualFragment);
                activeFragment = "RewardContainerFragment";
                break;

            case R.id.rankings:
                actualFragment = new LeaderboardFragment();
                changeFragment(actualFragment);
                activeFragment = "LeaderboardFragment";
                break;

            case R.id.options:
                actualFragment = new AjustesFragment();
                changeFragment(actualFragment);
                activeFragment = "AjustesFragment";
                break;

            case R.id.logout:
                auth.signOut();
                Intent intento1 = new Intent(this, HelloLoginActivity.class);
                startActivity(intento1);
                finish();
                break;
        }

        mDrawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return false;
    }
}