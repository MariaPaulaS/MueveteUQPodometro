package com.example.mueveteuq_podometro.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class Database {

    //Esta clase utiliza el patron de diseño singleton, investigar sobre ello
    private static Database database;

    private SQLiteDatabase sqLiteDatabase;
    private Context context;

    private Database(Context context){

        this.context = context;
        //sqLiteDatabase = new PodometroHelper(context).getWritableDatabase();
    }


    public static Database getInstance(Context context){

        if (database != null)
            return database;

        return new Database(context);
    }

    public SQLiteDatabase getDatabase(){


        if (sqLiteDatabase != null && sqLiteDatabase.isOpen())
            return sqLiteDatabase;

        sqLiteDatabase = new PodometroHelper(context).getWritableDatabase();
        return sqLiteDatabase;
    }

    public  void close(){

        if (sqLiteDatabase.isOpen())
            sqLiteDatabase.close();
    }



}
