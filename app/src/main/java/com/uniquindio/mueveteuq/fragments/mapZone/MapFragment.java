package com.uniquindio.mueveteuq.fragments.mapZone;


import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.uniquindio.mueveteuq.activities.PhotoActivity;
import com.uniquindio.mueveteuq.activities.ZonaMapaActivity;
import com.uniquindio.mueveteuq.classes.StepDetector;
import com.uniquindio.mueveteuq.listener.OnDrawListener;
import com.uniquindio.mueveteuq.listener.StepListener;
import com.uniquindio.mueveteuq.service.LocationService;
import com.uniquindio.mueveteuq.R;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;


import java.io.File;
import java.text.DecimalFormat;
import java.util.List;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.BIND_AUTO_CREATE;
import static android.content.Context.LOCATION_SERVICE;

/**
 * Fragmento del Mapa que carga funcionalidades de GPS, podómetro entre otros.
 * <p>
 * <p>
 * Prueba Podometro
 * 4. Codigo principal
 * Desde donde se comienzan a grabar las lecturas y mostrar el resultado al usuario.
 */
public class MapFragment extends Fragment implements OnMapReadyCallback, LocationListener, View.OnClickListener, ServiceConnection, SensorEventListener, StepListener {

    /**
     * Atributos del mapa
     */
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
    private LocationService service;
    private Polyline polyline;
    private TextView location;
    private boolean status = false;
    private boolean permisoConcedido = false;
    private Location initialLocation;
    private Location anteriorLocation;
    private Location actualLocation;
    private float distancia = 0;
    private float distanciaAcum = 0;


    float puntoVerde;

    /**
     * Atributos del podometro
     */
    TextView pasostv;
    TextView numeroDistancia;
    TextView numeroCalorias;
    private SensorManager sensorManager = null;
    private Sensor accel = null;
    private double magnitudePrevia;
    private int valPasos = 0;
    private StepDetector simpleStepDetector;
    private static final String TEXT_NUM_STEPS = "Numero de pasos: ";
    private int numSteps;


    //Variable para el botón del podometro que activa el sensor
    private boolean running = false;

    public MapFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //--------------Limpiando preferencias de carrera anterior---------

        SharedPreferences preferencias= this.getActivity().getSharedPreferences("preferencias", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=preferencias.edit();
        editor.remove("estadoFoto");
        editor.remove("corriendo");
        editor.commit();

        distancia = 0;
        distanciaAcum = 0;

        // Obtain the SupportMapFragment and get notified when the map is ready to be use
        /**
         SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
         //Obtiene el mapa asincronamente -cuando sea el momento, por motivos de memoria, rendimiento, etc.
         mapFragment.getMapAsync(this);
         **/


        //-----------Inflando la vista------------------

        if (polyline != null) {
            polyline.remove();
        }


        rootView = inflater.inflate(R.layout.fragment_map, container, false);

        //-----------Inflate para el sensor-----------

        sensorManager = (SensorManager) getContext().getSystemService(Context.SENSOR_SERVICE);
        accel = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this, accel, SensorManager.SENSOR_DELAY_UI);
        simpleStepDetector = new StepDetector();
        simpleStepDetector.registerListener(this);
        pasostv = rootView.findViewById(R.id.numPasostv);


        // -----------Inflate para el mapa-----------


        fab = rootView.findViewById(R.id.fab);

        fab.setOnClickListener(this);

        puntoVerde = BitmapDescriptorFactory.HUE_GREEN;
        location = rootView.findViewById(R.id.map_postition);
        fabIniciarRecorrido = rootView.findViewById(R.id.fabIniciarRecorrido);
        fabTerminarRecorrido = rootView.findViewById(R.id.fabTerminarRecorrido);
        numeroDistancia = rootView.findViewById(R.id.numDistancia);


        fabIniciarRecorrido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (verificarGPSEncendido()) {
                    fab.callOnClick();

                }
                else{

                    retornarPuntoInicio();

                    Intent intento = new Intent(view.getContext(), PhotoActivity.class);
                    startActivity(intento);

        //            Toast.makeText(view.getContext(),"Es necesario tomar una foto para iniciar el recorrido.", Toast.LENGTH_SHORT).show();
                }

            }
        });

        fabTerminarRecorrido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (service != null && status && running) {

                    Intent intento = new Intent(view.getContext(), PhotoActivity.class);
                    startActivity(intento);

                }


              
            }
        });

        return rootView;
    }

    /**
     * Método que retorna la posición inicial del recorrido
     */

    public void retornarPuntoInicio(){

        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }


        initialLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        if (initialLocation == null) {
            initialLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        }




    }

    /**
     * Método que permite iniciar el recorrido
     */

    public void iniciarRecorrido() {
        //Si el GPS no está habilitado muestra la alerta.


        if (service == null) {
            Toast.makeText(getContext(), "El servicio es nulo", Toast.LENGTH_SHORT).show();
        }

        else {

            if (status == false) {                              //Si el status es falso
                ponerMarcadorInicioFin();
                status = true;

                running = true;

                numSteps = 0;
                sensorManager.registerListener(MapFragment.this, accel, SensorManager.SENSOR_DELAY_FASTEST);


                if (service != null) {

                    service.startRacer("Titulo de prueba", "Descripcion de prueba");


                }
            }

        }

    }


    /**
     * Método que permite terminar el recorrido
     */
    public void terminarRecorrido() {


        //Si el servicio esta corriendo y el estado es true
        if (service != null && status && running) {



            running = false;
            ponerMarcadorInicioFin();
            sensorManager.unregisterListener(MapFragment.this);
            service.finishRacer();

            Toast.makeText(getContext(), "¡Felicidades! Has terminado el recorrido", Toast.LENGTH_SHORT).show();


        }

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


    private void startLocationService() {

        Intent service = new Intent(getContext(), LocationService.class);
        getContext().stopService(service);
        getContext().startService(service);
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


        if (!checkPermission(Manifest.permission.ACCESS_FINE_LOCATION) || !checkPermission(Manifest.permission.ACCESS_FINE_LOCATION)) {

            getPermissions(2, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION);
            return;
        }

        //Pregunto si el usuario tiene permisos no lo le pido permisos para que pueda continuar con el uso de la app


        //    else if (verificarGPSEncendido()){

        //        mostrarAlertaActivarGPS().show();
        //        return;
        //    }

        startLocationService();
        connectionService();

        mapView = rootView.findViewById(R.id.map);

        if (mapView != null) {
            mapView.onCreate(null);
            mapView.onResume();
            mapView.getMapAsync(this); //Recibe la implementacion de la interfaz
        }

        this.verificarGPSEncendido();

    }

    /**
     * Método que muestra un mensaje que indica que el GPS no esta activado y pregunta si desea
     * activarlo.
     * <p>
     * ANTES ERA VOID
     */
    private Dialog mostrarAlertaActivarGPS() {


        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

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

    @Override
    public void onResume() {
        super.onResume();

        if (!checkPermission(Manifest.permission.ACCESS_FINE_LOCATION) || !checkPermission(Manifest.permission.ACCESS_FINE_LOCATION)) {

            getPermissions(2, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION);
            return;
        }

        startLocationService();
        connectionService();

        SharedPreferences preferencias= this.getActivity().getSharedPreferences("preferencias", Context.MODE_PRIVATE);
        String estadoFoto = preferencias.getString("estadoFoto", "");
        String corriendo = preferencias.getString("corriendo", "");


        if(estadoFoto.equals("1") && !corriendo.equals("si")){

            iniciarRecorrido();

            SharedPreferences.Editor objetoEditor = preferencias.edit();
            objetoEditor.putString("corriendo", "si");
            objetoEditor.remove("estadoFoto");
            objetoEditor.apply();
            fabIniciarRecorrido.setVisibility(View.INVISIBLE);

        }

        //Actualizar los datos del SharedPreferences
        corriendo = preferencias.getString("corriendo", "");
        estadoFoto = preferencias.getString("estadoFoto", "");

        if (corriendo.equals("si")) {

            if(estadoFoto.equals("1")){

                terminarRecorrido();
                fabIniciarRecorrido.setVisibility(View.VISIBLE);

                //Resetea los datos de la carrera.
                SharedPreferences.Editor objetoEditor = preferencias.edit();
                objetoEditor.remove("corriendo");
                objetoEditor.remove("estadoFoto");
                objetoEditor.apply();

            }

        }



        }


    /**
     * Método que hace zoom a la localizacion actual.
     *
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


        calcularMetrosRecorridos(location);

        crearOActualizarMarcador(location);
    }


    /**
     * Método que calcula la cantidad de metros recorridos por la persona -independientemente de si esta
     * se devuelve o no-.
     * @param location
     */
    private void calcularMetrosRecorridos(Location location){

        imprimirDistancia(distanciaAcum);


        if(distanciaAcum == 0 && initialLocation!=null){
            distancia = location.distanceTo(initialLocation);
            distanciaAcum += distancia;

            anteriorLocation = initialLocation;
        }

        else if(location!=initialLocation && initialLocation!=null){



            actualLocation = location;

            distancia = calcularDistancia(anteriorLocation, location);

            distanciaAcum += distancia;


            if(calcularDistancia(initialLocation, anteriorLocation) > calcularDistancia(initialLocation, location)){

                distanciaAcum +=  (calcularDistancia(initialLocation, anteriorLocation) - calcularDistancia(initialLocation, location));


            }

            anteriorLocation = actualLocation;


        }

        imprimirDistancia(distanciaAcum);




    }

    /**
     * Método que calcula la distancia cada que cambia la posición del marcador
     */
    private float calcularDistancia(Location initialL, Location finalL){


        float distancia = finalL.distanceTo(initialL);
        return distancia;
    }

    /**
     * Método que formatea el decimal para que solo tenga dos numeros decimales, y lo setea en la
     * vista.
     * @param val
     */
    private void imprimirDistancia(float val){
        DecimalFormat formatear = new DecimalFormat("#.00");
        numeroDistancia.setText(formatear.format(val) + " m");
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

        if (polyline != null && !running) {
            polyline.remove();
        }


        if (verificarGPSEncendido()) {
            mostrarAlertaActivarGPS().show();

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
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, this);

                if (location == null) {
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 0, this);
                    location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

                }
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
     *
     * @param location
     */
    private void crearOActualizarMarcador(Location location) {

        if (marker == null) {
            marker = gMap.addMarker(new MarkerOptions().position(new LatLng(location.getLatitude(), location.getLongitude())).draggable(true));

        } else {
            marker.setPosition(new LatLng(location.getLatitude(), location.getLongitude()));

        }
    }




    /**
     * Metodo onServiceConnected de la interfaz ServiceConnection
     *
     * @param componentName
     * @param iBinder
     */
    @Override
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {


        Toast.makeText(getContext(), "Servicio Conectado", Toast.LENGTH_SHORT).show();

        //Llamar metodos de la siguiente manera para que todo marche bien

        //Obtengo enlace del servicio
        LocationService.ConnexionService connexionService = (LocationService.ConnexionService) iBinder;

        //Obtengo instancia del servicio y empiezo la comunicacion
        service = connexionService.getCurrentService();

        if (gMap != null) {

            polyline = gMap.addPolyline(new PolylineOptions().color(Color.BLUE));
        }


        service.setOnDrawListener(new OnDrawListener() {

            @Override
            public void onDraw(LatLng latLng) {


                location.setText("Latitude: " + latLng.latitude + "Longitude: " + latLng.longitude);

                CameraPosition camera = new CameraPosition.Builder()
                        .target(latLng)
                        .zoom(10)       //Zoom a nivel de calles  -- Limite maximo->21
                        .bearing(360)    //Orientación de la camara hacia el este    0 - 365°
                        .tilt(30)       //Efecto 3D del mapa  0 - 90
                        .build();

                //Da vida a las operaciones especificadas en el CameraPosition
                gMap.animateCamera(CameraUpdateFactory.newCameraPosition(camera));

                if (polyline == null)
                    return;

                List<LatLng> points = polyline.getPoints();
                points.add(latLng);

                polyline.setPoints(points);
            }
        });

        service.refresh();

    }

    /**
     * Metodo onServiceDisconnected de la interfaz ServiceConnection
     *
     * @param componentName
     */
    @Override
    public void onServiceDisconnected(ComponentName componentName) {

    }

    private void ponerMarcadorInicioFin() {

        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        //Aqui es donde se obtiene la localizacion actual y sus coordenadas.
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        if (location == null) {
            location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        }

        currentLocation = location;

        if (currentLocation != null) {

            gMap.addMarker(new MarkerOptions().position(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude())).icon(BitmapDescriptorFactory.defaultMarker(puntoVerde)));
        }

    }

    /**
     * needsActiveLocation
     *
     * @return
     */
    private boolean verificarGPSEncendido() {

        LocationManager manager = (LocationManager) getContext().getSystemService(LOCATION_SERVICE);

        permisoConcedido = !manager.isProviderEnabled(LocationManager.GPS_PROVIDER) || !manager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        return permisoConcedido;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {

            startLocationService();
            connectionService();

        }
    }


    private void getPermissions(int i, String... permissions) {


        ActivityCompat.requestPermissions(getActivity(), permissions, i);
    }


    private boolean checkPermission(String permission) {

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
            return true;

        return ActivityCompat.checkSelfPermission(getActivity().getApplicationContext(), permission) == PackageManager.PERMISSION_GRANTED;
    }

    /**
     *
     **/

    private void connectionService() {

        Intent service = new Intent(getContext(), LocationService.class);
        getContext().bindService(service, this, BIND_AUTO_CREATE);
    }

    /**
     * @Override public void onPause() {
     * super.onPause();
     * getContext().unbindService(this);
     * }
     **/

    protected void createLocationRequest() {
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(5000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }


    //------------------------------Zona del StepCounter-----------------------------


    /**
     * Método que se activa cada que el sensor detecta un cambio
     *
     * @param sensorEvent
     */
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

        if (running) {


            if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                simpleStepDetector.updateAccel(sensorEvent.timestamp, sensorEvent.values[0],
                        sensorEvent.values[1], sensorEvent.values[2]);
            }


        }


        /**

         if (sensorEvent != null) {

         float x = sensorEvent.values[0];
         float y = sensorEvent.values[1];
         float z = sensorEvent.values[2];

         double magnitude = Math.sqrt(x * x + y * y + z * z);
         double magnitudeDelta = magnitude - magnitudePrevia;

         magnitudePrevia = magnitude;

         if (magnitudeDelta > 6) {

         valPasos++;
         }

         pasostv.setText("" + valPasos);
         }
         **/
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {


    }

    /**
     * Método que determina si un usuario está caminando de verdad.
     * Calcula una primera localizacion, y si en los tres segundos siguientes la localización es la
     * misma pero el valor de pasos esta subiendo, está haciendo trampa.
     * Si detecta trampa lanza una alerta
     *
     * @return
     */
    public void detectarTramposos() {

        int pasosActuales = 0;
        int postPasos = 0;

        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        //Aqui es donde se obtiene la localizacion actual y sus coordenadas.

        pasosActuales = valPasos;
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        if (location == null) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, this);

            if (location == null) {
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 0, this);
                location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

            }
        }


        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        postPasos = valPasos;
        Location postLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);


        if (postLocation == null) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, this);

            if (postLocation == null) {
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 0, this);
                postLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

            }
        }

        //Hay que comparar si las coordenadas son iguales, no los objetos
        //Si la ubicacion no ha cambiado en cinco segundos
        if (location.equals(postLocation)) {

            //Pero los pasos si
            if (postPasos > pasosActuales) {

                valPasos = 0;

                mostrarAlertaTramposos();

            }

        }

    }


    /**
     * Lanza la alerta al usuario haciendo trampa. Se detiene el contador y se reinicia el servicio.
     *
     * @return
     */

    public Dialog mostrarAlertaTramposos() {


        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        builder.setTitle(R.string.titleCheat);
        builder.setMessage(R.string.cheat)
                .setNegativeButton(R.string.cancel, null);

        builder.setCancelable(false);

        return builder.create();

    }


    @Override
    public void step(long timeNs) {

        numSteps++;
        pasostv.setText(numSteps + "");

    }


}

