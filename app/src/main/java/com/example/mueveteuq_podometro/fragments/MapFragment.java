package com.example.mueveteuq_podometro.fragments;


import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.mueveteuq_podometro.R;
import com.example.mueveteuq_podometro.activities.MapsActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Map;

import static android.content.Context.LOCATION_SERVICE;

/**
 * Fragmento del Mapa que carga funcionalidades de GPS, podómetro entre otros.
 */
public class MapFragment extends Fragment implements OnMapReadyCallback, LocationListener, View.OnClickListener {

    private View rootView;
    private MapView mapView;
    private GoogleMap gMap;

    private FloatingActionButton fab;
    private FloatingActionButton fabIniciarRecorrido;
    private FloatingActionButton fabTerminarRecorrido;

    private Location currentLocation;
    private LocationManager locationManager;
    private Marker marker;
    private CameraPosition cameraZoom;

    public MapFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_map, container, false);
        fab = rootView.findViewById(R.id.fab);
        fab.setOnClickListener(this);

        fabIniciarRecorrido = rootView.findViewById(R.id.fabIniciarRecorrido);
        fabTerminarRecorrido = rootView.findViewById(R.id.fabTerminarRecorrido);

        fabIniciarRecorrido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Si el GPS no está habilitado muestra la alerta.
                if (!verificarGPSEncendido()) {
                    mostrarAlertaActivarGPS();

                } else {

                }

                }
        });

        fabTerminarRecorrido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        return rootView;
    }

    /**
     * Aquí es cuando está el fragmento cargado y se van a crear las vistas
     *
     * @param view
     * @param savedInstanceState
     */
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        mapView = (MapView) rootView.findViewById(R.id.map);

        if (mapView != null) {
            mapView.onCreate(null);
            mapView.onResume();
            mapView.getMapAsync(this); //Recibe la implementacion de la interfaz
        }

        this.verificarGPSEncendido();

    }

    /**
     * Método que verifica si el GPS tiene señal
     */
    private boolean verificarGPSEncendido() {
        try {
            int gpsSignal = Settings.Secure.getInt(getActivity().getContentResolver(), Settings.Secure.LOCATION_MODE);

            if (gpsSignal == 0) {

                //El GPS no está activado
                this.mostrarAlertaActivarGPS();
                return false;
            } else {
                return true;
            }

        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
            return false;
        }

    }

    /**
     * Método que muestra un mensaje que indica que el GPS no esta activado y pregunta si desea
     * activarlo.
     */
    private void mostrarAlertaActivarGPS() {

        new AlertDialog.Builder(getContext())
                .setTitle("GPS sin señal")
                .setMessage("El GPS se encuentra desactivado. ¿Deseas activarlo?")
                .setPositiveButton("Activar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intento = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(intento);


                    }
                }).setNegativeButton("Cancelar", null).show();

    }

    @Override
    public void onResume() {
        super.onResume();
        this.verificarGPSEncendido();
    }

    /**
     * Método autogenerado de la interfaz OnMapReadyCallback, que permite realizar acciones sobre
     * el mapa cuando éste ya ha sido cargado.
     *
     * @param googleMap
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {

        gMap = googleMap;
        locationManager = (LocationManager) getContext().getSystemService(LOCATION_SERVICE);


        UiSettings uiSettings = gMap.getUiSettings();
        uiSettings.setMyLocationButtonEnabled(true);
        // uiSettings.setZoomControlsEnabled(true);


        //Método que hace una revisión de permisos antes de obtener la localizacion.

        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions

            requestPermissions(new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 0);   // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        } else {
            gMap.setMyLocationEnabled(true);
            gMap.getUiSettings().setMyLocationButtonEnabled(false);

            /**
             gMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
            @Override public boolean onMyLocationButtonClick() {
            Toast.makeText(getContext(),"Esto funciona", Toast.LENGTH_SHORT).show();
            return false;
            }
            });
             **/


            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 1, this);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1, this);

        }

    }


    /**
     * Método que hace zoom a la localizacion actual.
     * @param location
     */
    private void zoomToLocation(Location location) {

        gMap.setMinZoomPreference(17);  //Establecer zoom minimo del mapa
        gMap.setMaxZoomPreference(19);  //Establecer zoom maximo del mapa


        cameraZoom = new CameraPosition.Builder()
                .target(new LatLng(location.getLatitude(), location.getLongitude()))
                .zoom(10)       //Zoom a nivel de calles  -- Limite maximo->21
                .bearing(360)    //Orientación de la camara hacia el este    0 - 365°
                .tilt(30)       //Efecto 3D del mapa  0 - 90
                .build();

        //Da vida a las operaciones especificadas en el CameraPosition
        gMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraZoom));

    }

    @Override
    public void onLocationChanged(Location location) {
    //    Toast.makeText(getContext(), "Changed ->" + location.getProvider(), Toast.LENGTH_SHORT).show();
        crearOActualizarMarcador(location);
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    /**
     * Método que verifica que el GPS esté encendido. En caso de estarlo, lleva a la posicion actual del
     * usuario al darle clic.
     *
     * @param view
     */
    @Override
    public void onClick(View view) {

        //Si el GPS no está habilitado muestra la alerta.
        if (!this.verificarGPSEncendido()) {
            mostrarAlertaActivarGPS();

        } else {

            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    Activity#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for Activity#requestPermissions for more details.
                return;
            }

            //Aqui es donde se obtiene la localizacion actual y sus coordenadas.
            Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

            if (location == null) {
                location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            }

            currentLocation = location;

            if (currentLocation != null) {
                crearOActualizarMarcador(location);
                zoomToLocation(location);
            }

        }
    }


    /**
     * Método que crea el marcador de la posicion actual en caso de que no exista, y que actualiza el
     * marcador en caso de que exista.
     * @param location
     */
    private void crearOActualizarMarcador(Location location) {

        if (marker == null) {
            marker = gMap.addMarker(new MarkerOptions().position(new LatLng(location.getLatitude(), location.getLongitude())).draggable(true));

        } else {

            marker.setPosition(new LatLng(location.getLatitude(), location.getLongitude()));

        }
    }

}
