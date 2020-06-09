package com.uniquindio.mueveteuq.activities.podometer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;

import com.uniquindio.mueveteuq.activities.main.ContenedorInstruccionesActivity;
import com.uniquindio.mueveteuq.activities.login.HelloLoginActivity;
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



   //     getSupportActionBar().hide();






        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = preferences.edit();

        int bandera = Integer.parseInt(preferences.getString("bandera", "0"));

        if(bandera == 1){
            actualFragment = new MapFragment();
            changeFragment(actualFragment);


        }else{

            editor.putString("bandera", "1");
            editor.commit();
            Intent intent = new Intent(ZonaMapaActivity.this, ContenedorInstruccionesActivity.class);
            startActivity(intent);

            actualFragment = new MapFragment();
            changeFragment(actualFragment);

        }




    }

    private void changeFragment(Fragment fragmento){

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.contenedor_fragmento, fragmento)
                .commit();


    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if(keyCode == event.KEYCODE_BACK){



            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setTitle(R.string.alert);
            builder.setMessage(R.string.mensaje_abandono)
                    .setNegativeButton(R.string.cancel, null);

            builder.setCancelable(false);

            builder.setPositiveButton(R.string.aceptar, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    //Finaliza la actividad y va al menu inicio
                    finish();

                    //TODO: CAMBIAR A LA PANTALLA PRINCIPAL CUANDO ESTÉ LISTA
                    Intent intento = new Intent(ZonaMapaActivity.this, HelloLoginActivity.class);
                    startActivity(intento);
                }
            });

            builder.show();
        }

        return super.onKeyDown(keyCode, event);
    }
}
