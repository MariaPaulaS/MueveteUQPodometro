package com.example.mueveteuq_podometro.activities;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.widget.ListPopupWindow;
import android.widget.Toast;

import com.example.mueveteuq_podometro.R;
import com.example.mueveteuq_podometro.listener.OnDrawListener;
import com.example.mueveteuq_podometro.service.LocationService;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, ServiceConnection {

    private GoogleMap mMap;
    private LocationService service;
    private Polyline polyline;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be use
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        //Obtiene el mapa asincronamente -cuando sea el momento, por motivos de memoria, rendimiento, etc.
        mapFragment.getMapAsync(this);
    }

    private void startLocationService() {

        Intent service = new Intent(getApplicationContext(), LocationService.class);
        stopService(service);
        startService(service);
    }

    @Override
    protected void onResume() {
        super.onResume();

        //Pregunto si el usuario tiene permisos no lo le pido permisos para que pueda continuar con el uso de la app

        if (needsActiveLocation()){

            getLocationDialog().show();
            return;
        }

        if (!checkPermission(Manifest.permission.ACCESS_FINE_LOCATION) || !checkPermission(Manifest.permission.ACCESS_FINE_LOCATION)){

            getPermissions(2, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION);
            return;
        }

        startLocationService();
        connectionService();
    }

    private Dialog getLocationDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(MapsActivity.this);

        builder.setTitle(R.string.alert);
        builder.setMessage(R.string.message)
                .setNegativeButton(R.string.cancel, null);

        builder.setCancelable(false);

        builder.setPositiveButton(R.string.activate, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        });

        return builder.create();
    }

    private boolean needsActiveLocation() {

        LocationManager manager = (LocationManager) getSystemService(LOCATION_SERVICE);

        return manager.isProviderEnabled(LocationManager.GPS_PROVIDER) || manager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    private void getPermissions(int i, String ... permissions) {


        ActivityCompat.requestPermissions(this, permissions, i);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED){

            startLocationService();
            connectionService();

        }
    }

    private boolean checkPermission(String permission) {

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
            return true;

        return ActivityCompat.checkSelfPermission(getApplicationContext(), permission) == PackageManager.PERMISSION_GRANTED;
    }

    private void connectionService(){

        Intent service = new Intent(getApplicationContext(), LocationService.class);
        bindService(service, this, BIND_AUTO_CREATE);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setMinZoomPreference(17);  //Establecer zoom minimo del mapa
        mMap.setMaxZoomPreference(19);  //Establecer zoom maximo del mapa

        // Add a marker in Sydney and move the camera
        //Crea la latitud y longitud de Sydney
        LatLng sydney = new LatLng(-34, 151);
        LatLng calarca = new LatLng(4.515634087347382, -75.64803600311281);
        //Añade un marcador en Sydney y le da un título al marcador
        mMap.addMarker(new MarkerOptions().position(calarca).title("Yo vivo aqui"));

        CameraPosition camera=new CameraPosition.Builder()
                .target(calarca)
                .zoom(10)       //Zoom a nivel de calles  -- Limite maximo->21
                .bearing(360)    //Orientación de la camara hacia el este    0 - 365°
                .tilt(30)       //Efecto 3D del mapa  0 - 90
                .build();

        //Da vida a las operaciones especificadas en el CameraPosition
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(camera));

        //Mueve la cámara hacia la latitud y longitud de Sydney
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(calarca));

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                Toast.makeText(MapsActivity.this,"Latitud: " +latLng.latitude
                        + " y longitud: "+latLng.longitude, Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {

        Toast.makeText(getApplicationContext(), "Servicio Conectado", Toast.LENGTH_SHORT).show();

        //Llamar metodos de la siguiente manera para que todo marche bien

        //Obtengo enlace del servicio
        LocationService.ConnexionService connexionService = (LocationService.ConnexionService) iBinder;

        //Obtengo instancia del servicio y empiezo la comunicacion
        service = connexionService.getCurrentService();

        if (mMap != null) {

           polyline  =  mMap.addPolyline(new PolylineOptions());
        }


        service.setOnDrawListener(new OnDrawListener() {
            @Override
            public void onDraw(LatLng latLng) {

                if (polyline == null)
                    return;

                List<LatLng> points = polyline.getPoints();
                points.add(latLng);

                polyline.setPoints(points);
            }
        });

        service.refresh();
        service.startRacer("Carrera", "prueba no1");

    }

    @Override
    public void onServiceDisconnected(ComponentName componentName) {

        if (service != null)
            service.finishRacer();
    }
}
