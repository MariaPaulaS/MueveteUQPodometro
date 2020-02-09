package com.uniquindio.mueveteuq.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;

import com.uniquindio.mueveteuq.activities.login.LoginActivity;
import com.uniquindio.mueveteuq.fragments.mapZone.MapFragment;
import com.uniquindio.mueveteuq.R;

import java.io.File;

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

        Intent intent = new Intent(ZonaMapaActivity.this, ContenedorInstruccionesActivity.class);
        startActivity(intent);



    }

    private void changeFragment(Fragment fragmento){

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.contenedor_fragmento, fragmento)
                .commit();


    }



/**

 //Metodo que permite inflar el menu_contenedor_instrucciones con las opciones del menu_contenedor_instrucciones

    @Override
    public boolean onCreateOptionsMenu(Menu menu_contenedor_instrucciones) {

        getMenuInflater().inflate(R.menu_contenedor_instrucciones.menu_contenedor_instrucciones, menu_contenedor_instrucciones);
        return super.onCreateOptionsMenu(menu_contenedor_instrucciones);
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
