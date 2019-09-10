package com.example.mueveteuq_podometro.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.mueveteuq_podometro.service.LocationService;

public class RestarService extends BroadcastReceiver {

    //Este metodo reinicia el servicio de GEOLOCALIZACION


    @Override
    public void onReceive(Context context, Intent intent) {


        Intent servicio = new Intent(context, LocationService.class);
        context.startService(servicio);
    }
}
