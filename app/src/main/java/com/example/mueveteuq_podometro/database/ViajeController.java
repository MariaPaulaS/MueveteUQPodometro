package com.example.mueveteuq_podometro.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

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
        database.close();
    }

    public static int getCurrentViaje(@NonNull Context context){

        SQLiteDatabase database = Database.getInstance(context).getDatabase();

        Cursor cursor = database.rawQuery("SELECT ID FROM VIAJE WHERE ACTIVO = 1 ORDER BY ID DESC", null);

        if (cursor == null){

            database.close();
            return -1;
        }


        if (cursor.moveToFirst())
            return cursor.getInt(0);


        database.close();

        return -1;
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
