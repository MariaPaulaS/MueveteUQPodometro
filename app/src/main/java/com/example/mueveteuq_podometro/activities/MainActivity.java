package com.example.mueveteuq_podometro.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.mueveteuq_podometro.R;
import com.example.mueveteuq_podometro.fragments.MapFragment;
import com.example.mueveteuq_podometro.fragments.WelcomeFragment;

public class MainActivity extends AppCompatActivity {

    //Permite llevar un control de que fragment estamos cargando en cada momento
    Fragment actualFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*actualFragment = new MapFragment();
        changeFragment(actualFragment);*/

        startActivity(new Intent(MainActivity.this, MapsActivity.class));
        finish();


    }

    private void changeFragment(Fragment fragmento){

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.contenedor_fragmento, fragmento)
                .commit();


    }

/**

 //Metodo que permite inflar el menu con las opciones del menu

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /* Metodo que permite crear eventos para cada una de las opciones

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){

            case R.id.menu_bienvenida:

                actualFragment=new WelcomeFragment();
                break;

            case R.id.menu_mapa:

                actualFragment=new MapFragment();
                break;

        }
        changeFragment(actualFragment);
        return super.onOptionsItemSelected(item);
    }

    **/

    /**
     * Método para la transaccion del fragment
     * @param fragmento
     */

}
