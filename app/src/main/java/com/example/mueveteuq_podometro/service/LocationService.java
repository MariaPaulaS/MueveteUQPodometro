package com.example.mueveteuq_podometro.service;

import android.Manifest;
import android.app.IntentService;
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

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.example.mueveteuq_podometro.database.RecorridoController;
import com.example.mueveteuq_podometro.database.ViajeController;
import com.example.mueveteuq_podometro.listener.OnDrawListener;
import com.example.mueveteuq_podometro.util.Constant;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;


//Este servicio solo se iniciara si y solo si el usuario ya ha dado permisos de localizacion sobre la app
public class LocationService extends IntentService implements LocationListener{

    private LocationManager locationManager;
    private int currentRacer;
    private OnDrawListener onDrawListener;

    public LocationService(String name) {

        super(name);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        currentRacer    = getStorageRacer();
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        ConnexionService connexionService = new ConnexionService();
        return connexionService;
    }

    public void startRacer(String title, String description){

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

        ViajeController.endCurrentViaje(getApplicationContext());
        currentRacer = -1;
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            return;

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5, 5, this);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5, 5, this);

    }

    //Metodo para invocar nuevamente el servicio y elimino los listener y mas

    private void restarService(){

        //Guardo valores por default como numero de carrera actual

        storageRacer();
        removeLocationListener();
        //Creo intent para llamar a el broadCast que reiniciara el servicio
        Intent intent = new Intent(Constant.MYACTION);
        sendBroadcast(intent);
    }

    private void removeLocationListener() {


        if (locationManager != null)
            locationManager.removeUpdates(this);
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
        restarService();
    }

    //Metodos referentes a location listner


    @Override
    public void onLocationChanged(Location location) {

        if (currentRacer > 0){

            RecorridoController.insert(getApplicationContext(), currentRacer, location);

            if (onDrawListener != null){

                onDrawListener.onDraw(new LatLng(location.getLatitude(), location.getLongitude()));
            }
        }

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {}
    @Override
    public void onProviderEnabled(String s) {}
    @Override
    public void onProviderDisabled(String s) {}


    //Clase para realizar el enlace con los componentes (Actividades, fragments etc) con este servicio

    public class ConnexionService extends Binder {


        public ConnexionService(){}

        //Este metodo regresa la instancia al servicio actual

        public LocationService getCurrentService(){

            return LocationService.this;
        }

    }

}
