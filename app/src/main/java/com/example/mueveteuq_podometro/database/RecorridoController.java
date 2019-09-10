package com.example.mueveteuq_podometro.database;


//Esta clase es un controlador con metodos estaticos para realizar CRUD sobre la tabla recorrido

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

public class RecorridoController {


    //VIAJE_ID, LAT, LONG, FECHA

    public static void insert(Context context, int viaje_id, Location location){

        insert(context, viaje_id, location.getLatitude(), location.getLongitude());
    }

    public static void insert(Context context,int viaje_id, double lat, double lon){


        if (context == null || viaje_id < 1)
            return;

        SQLiteDatabase database = Database.getInstance(context).getDatabase();

        ContentValues values = new ContentValues();
        values.put("VIAJE_ID", viaje_id);
        values.put("LONG", lon);
        values.put("LAT", lat);

        database.insert("", null, values);
        Log.i("Recorrido", "Insertado con exito");

        database.close();
    }

    public static List<LatLng> getRecorrido(Context context, int viaje_id){

        List<LatLng> list = new ArrayList<>();

        if (viaje_id < 1)
            return list;

        SQLiteDatabase database = Database.getInstance(context).getDatabase();

        Cursor cursor = database.rawQuery("SELECT LAT, LONG FROM RECORRIDO WHERE VIAJE_ID =?", new String[]{Integer.toString(viaje_id)});

        if (cursor == null)
            return list;

        if (cursor.moveToFirst()){

            do {

                list.add(new LatLng(cursor.getDouble(0), cursor.getDouble(1)));

            }while (cursor.moveToNext());
        }

        cursor.close();
        database.close();

        return list;
    }
}
