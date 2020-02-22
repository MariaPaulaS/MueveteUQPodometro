package com.uniquindio.mueveteuq.util;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.app.Activity;
import android.os.Environment;
import android.provider.MediaStore;

import com.uniquindio.mueveteuq.R;

import java.io.File;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class Utilities {

    static ProgressDialog progressDialog;

    /**
     * Método que verifica si la app tiene permisos para funcionar. Si no, los pide al momento de
     * la instalación.
     * Solo se piden una vez.
     * Funciona desde Android Malvavisco en adelante.
     */
    public static void validarPermisos(Context context, Activity activity) {

        //Para version 5.
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return;
        }

        if (ContextCompat.checkSelfPermission(context, WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(context, CAMERA)
                != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(context, ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(context, ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted

            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION, CAMERA, WRITE_EXTERNAL_STORAGE}, 1);
        }


        //Si faltan dos permisos

        //1. Si falta el permiso de escritura de archivos y de GPS

       else if (ContextCompat.checkSelfPermission(context, WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(context, ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(context, ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted

            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION, WRITE_EXTERNAL_STORAGE}, 1);
        }


        //2. Si falta el permiso de escritura de archivos y cámara

        else if (ContextCompat.checkSelfPermission(context, WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(context, CAMERA) != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted

            ActivityCompat.requestPermissions(activity, new String[]{CAMERA, WRITE_EXTERNAL_STORAGE}, 1);
        }


        //3. Si falta el permiso de cámara y GPS


        else if (ContextCompat.checkSelfPermission(context, CAMERA) != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(context, ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(context, ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted

            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION, CAMERA}, 1);
        }

        //Si solo falta un permiso

        //1. Si falta el permiso de escritura de archivos
       else if (ContextCompat.checkSelfPermission(context, WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(activity, new String[]{WRITE_EXTERNAL_STORAGE}, 1);
        }

        //2. Si falta el permiso de GPS
        else if (ContextCompat.checkSelfPermission(context, ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(context, ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {


            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION}, 1);

        }


        //3. Si falta el permiso de la cámara
        else if (ContextCompat.checkSelfPermission(context, CAMERA) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(activity, new String[]{CAMERA}, 1);
        }


    }


    /**
     * Método para inicializar progress bar
     * @param context
     */
    public static void init(Context context) {
       progressDialog = new ProgressDialog(context);
    }

    /**
     * Método para mostrar progress bar
     */
    public static void showProgressBar() {

        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_dialog);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

    }

    /**
     * Método para finalizar progress bar
     */
    public static void dismissProgressBar(){

        progressDialog.dismiss();
    }



}
