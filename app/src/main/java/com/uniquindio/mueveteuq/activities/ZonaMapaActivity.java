package com.uniquindio.mueveteuq.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import com.uniquindio.mueveteuq.fragments.mapZone.MapFragment;
import com.uniquindio.mueveteuq.R;

/**
 * Activity de la zona del mapa
 * Main activity al inicio del desarrollo
 * Contiene: 1. Mapa - 2. Reporte de resultados
 */
public class ZonaMapaActivity extends AppCompatActivity {

    //Permite llevar un control de que fragment estamos cargando en cada momento
    Fragment actualFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zona_mapa);

        actualFragment = new MapFragment();
        changeFragment(actualFragment);

       getSupportActionBar().hide();


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
