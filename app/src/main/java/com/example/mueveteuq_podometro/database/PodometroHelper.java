package com.example.mueveteuq_podometro.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.mueveteuq_podometro.util.Constant;

public class PodometroHelper extends SQLiteOpenHelper {


    private static final  String VIAJE = "CREATE TABLE VIAJE(ID INTEGER PRIMARY KEY AUTOINCREMENT, TITULO TEXT, DESCRIPCION TEXT, ACTIVO INTEGER, FECHA DATETIME, KMRECORRIDOS REAL);";
    private static final  String RECORRIDO = "CREATE TABLE RECORRIDO(ID INTEGER PRIMARY KEY AUTOINCREMENT, VIAJE_ID INTEGER, LAT NUMERIC, LONG NUMERIC, FECHA DATETIME);";

    public PodometroHelper(@Nullable Context context) {
        super(context, Constant.DBNAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL(VIAJE);
        sqLiteDatabase.execSQL(RECORRIDO);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
