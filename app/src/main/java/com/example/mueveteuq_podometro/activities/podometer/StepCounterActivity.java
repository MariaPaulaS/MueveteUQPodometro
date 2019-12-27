package com.example.mueveteuq_podometro.activities.podometer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mueveteuq_podometro.R;

public class StepCounterActivity extends AppCompatActivity implements SensorEventListener {

    private boolean running = false;
    private SensorManager sensorManager = null;
    private Sensor accel = null;
    private TextView stepsValue;
    private TextView ejeXtv;
    private TextView ejeYtv;
    private TextView ejeZtv;

    private double magnitudePrevia;

    private int valPasos = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_counter);


        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accel = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this, accel, SensorManager.SENSOR_DELAY_UI);


    }


    @Override
    protected void onResume() {
        super.onResume();
        running = true;

    //    Sensor stepsSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);

        if(accel == null){
            Toast.makeText(this, "No se ha detectado un sensor de pasos!", Toast.LENGTH_SHORT).show();
        }


        else{
            sensorManager.registerListener(this, accel, SensorManager.SENSOR_DELAY_UI);
        }


    }


    @Override
    protected void onPause() {
        super.onPause();
        running = false;

        sensorManager.unregisterListener(this);



    }

    @Override
    public void onSensorChanged(SensorEvent event) {


        Sensor mySensor = event.sensor;

        if (event.sensor.getType()==Sensor.TYPE_ACCELEROMETER){
            if (mySensor.getType() == Sensor.TYPE_ACCELEROMETER) {

                float x = event.values[0];
                float y = event.values[1];
                float z = event.values[2];

                stepsValue = findViewById(R.id.steps_value);

                ejeXtv = findViewById(R.id.ejeXtv);
                ejeYtv = findViewById(R.id.ejeYtv);
                ejeZtv = findViewById(R.id.ejeZtv);


                double magnitude = Math.sqrt( x*x + y*y + z*z);
                double magnitudeDelta = magnitude - magnitudePrevia;

                magnitudePrevia = magnitude;

                if(magnitudeDelta > 6){

                    valPasos++;

                    stepsValue.setText(""+valPasos);

                }




                /**

                //El eje x controla si el teléfono está derecho o torcido
         //       if(x>=0.100 && x<=3.000){

                    //El punto 9, 9.5 en el eje y es cuando la persona está de pie recta.
                    //El eje y controla si el teléfono está inclinado o no.
                    if(y>=6.000 && y<=8.000){

                        //El punto 0 en el eje z es cuando la persona está de pie recta.
                        //El eje z controla si el teléfono está inclinado o no. Cuando el eje y disminuye,
                        //el z aumenta.
                        if(z>=4.000 && z<=6.000){

                            valPasos++;


                            stepsValue.setText(""+valPasos);

                        }
                    }
           //     }

                 **/

                ejeXtv.setText("Eje X: " + x);
                ejeYtv.setText("Eje Y: " + y);
                ejeZtv.setText("Eje Z: " + z);
            }
        }



    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
