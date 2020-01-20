package com.uniquindio.mueveteuq.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.uniquindio.mueveteuq.service.LocationService;

public class RestarService extends BroadcastReceiver {

    //Este metodo reinicia el servicio de GEOLOCALIZACION


    @Override
    public void onReceive(Context context, Intent intent) {


        Toast.makeText(context, "Reiniciando servicio", Toast.LENGTH_LONG).show();
        Intent servicio = new Intent(context, LocationService.class);
        context.startService(servicio);
    }
}
