package com.example.mueveteuq_podometro.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.mueveteuq_podometro.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

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


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */

    /**
     * Recibe un objeto de googleMap.
     * @param googleMap
     * Permite añadir marcadores o hacer operaciones una vez el mapa esté preparado y ya se haya construido
     * por completo.
     */
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
}
