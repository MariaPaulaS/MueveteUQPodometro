package com.example.mueveteuq_podometro.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.annotation.NonNull;

public class ViajeController {

    //TITULO, DESCRIPCION, ACTIVO, FECHA, KMRECORRIDOS


    public static void insert(@NonNull Context context, @NonNull String titulo, @NonNull String descripcion){


        SQLiteDatabase database = Database.getInstance(context).getDatabase();

        ContentValues values = new ContentValues();
        values.put("TITULO", titulo);
        values.put("DESCRIPCION", descripcion);
        values.put("ACTIVO", 1);
        values.put("KMRECORRIDOS", 0);

        database.insert("Viaje", null, values);
        database.close();
    }

    public static int getCurrentViaje(@NonNull Context context){

        SQLiteDatabase database = Database.getInstance(context).getDatabase();

        Cursor cursor = database.rawQuery("SELECT ID FROM VIAJE WHERE ACTIVO = 1 ORDER BY ID DESC", null);

        if (cursor == null){

            Log.i("Cursor", "El cursor es nulo");
            database.close();
            return -1;
        }


        int id = -1;
        if (cursor.moveToFirst()){

            id = cursor.getInt(0);
        }




        cursor.close();
        database.close();

        return id;
    }

    public static void endCurrentViaje(@NonNull Context context){

        SQLiteDatabase database = Database.getInstance(context).getDatabase();

        ContentValues values = new ContentValues();
        values.put("ACTIVO", "1");
        database.update("Viaje", values, "ACTIVO =?", new String[]{"1"});
        database.close();
    }

    public static void putKM(@NonNull Context context, float km){

        if (km < 0)
            return;

        SQLiteDatabase database = Database.getInstance(context).getDatabase();

        ContentValues values = new ContentValues();
        values.put("KMRECORRIDOS", km);
        database.update("Viaje", values, "ACTIVO =?", new String[]{"1"});
        database.close();
    }
}
