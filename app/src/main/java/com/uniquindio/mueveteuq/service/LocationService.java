package com.uniquindio.mueveteuq.service;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;

import androidx.core.content.ContextCompat;

import com.uniquindio.mueveteuq.database.RecorridoController;
import com.uniquindio.mueveteuq.database.ViajeController;
import com.uniquindio.mueveteuq.listener.OnDrawListener;
import com.uniquindio.mueveteuq.util.Constant;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;


//Este servicio solo se iniciara si y solo si el usuario ya ha dado permisos de localizacion sobre la app
public class LocationService extends Service{

    private LocationManager locationManager;
    private int currentRacer;
    private OnDrawListener onDrawListener;
    private LocationListener gps;//, network;

    @Override
    public void onCreate() {
        super.onCreate();

        currentRacer    = getStorageRacer();
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)

            return;

        gps = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

                LocationService.this.onLocationChanged(location);
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {}
            @Override
            public void onProviderEnabled(String s) {}
            @Override
            public void onProviderDisabled(String s) {}
        };

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5, 5, gps);


        /*network = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

                LocationService.this.onLocationChanged(location);
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {}
            @Override
            public void onProviderEnabled(String s) {}
            @Override
            public void onProviderDisabled(String s) {}};*/


        //locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5, 5, network);
    }

    @Override
    public IBinder onBind(Intent intent) {

        ConnexionService connexionService = new ConnexionService();
        return connexionService;
    }

    public void startRacer(String title, String description){

        if (currentRacer != -1)
            return;


        ViajeController.insert(getApplicationContext(), title, description);
        currentRacer = ViajeController.getCurrentViaje(getApplicationContext());


    }

    public void setOnDrawListener(OnDrawListener onDrawListener) {
        this.onDrawListener = onDrawListener;
    }

    //Este metodo retorna todas las posiciones asociadas a una carrera
    public void refresh(){

        if (currentRacer < 0 || onDrawListener == null)
            return;


        List<LatLng> positions = RecorridoController.getRecorrido(getApplicationContext(), currentRacer);

        for (LatLng postion:
             positions) {

            onDrawListener.onDraw(postion);
        }
    }

    public void finishRacer(){

        if (currentRacer == -1)
            return;

        ViajeController.endCurrentViaje(getApplicationContext());
        currentRacer = -1;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        return START_NOT_STICKY;
    }


    //Metodo para invocar nuevamente el servicio y elimino los listener y mas

    private void restarService(){

        //Guardo valores por default como numero de carrera actual
        removeLocationListener();
        storageRacer();
        //Creo intent para llamar a el broadCast que reiniciara el servicio
        Intent intent = new Intent(Constant.MYACTION);
        sendBroadcast(intent);
    }

    private void removeLocationListener() {


        if (gps != null){

            //locationManager.removeUpdates(network);
            locationManager.removeUpdates(gps);
        }
    }

    private int getStorageRacer(){

        SharedPreferences preferences = getSharedPreferences(Constant.SHARED, Context.MODE_PRIVATE);
        return preferences.getInt(Constant.CURRENT_RACE, -1);
    }

    private void storageRacer() {

        SharedPreferences preferences = getSharedPreferences(Constant.SHARED, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(Constant.CURRENT_RACE, currentRacer);
        editor.apply();
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        restarService();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        restarService();
    }

    //Metodos referentes a location listner


    public void onLocationChanged(Location location) {

        if (currentRacer != -1){


            RecorridoController.insert(getApplicationContext(), currentRacer, location);

            if (onDrawListener != null){

                onDrawListener.onDraw(new LatLng(location.getLatitude(), location.getLongitude()));
            }
        }

    }


    //Clase para realizar el enlace con los componentes (Actividades, fragments etc) con este servicio

    public class ConnexionService extends Binder {


        public ConnexionService(){}

        //Este metodo regresa la instancia al servicio actual

        public LocationService getCurrentService(){

            return LocationService.this;
        }

    }

}
