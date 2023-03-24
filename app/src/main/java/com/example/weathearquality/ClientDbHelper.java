package com.example.weathearquality;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ClientDbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = "weatherQuality.db";

    public final String SQL_CREATE_USER =
            "CREATE TABLE utilisateur (idUser INTEGER PRIMARY KEY AUTOINCREMENT, utilisateur TEXT, motdepasse TEXT);";

    public final String SQL_CREATE_HIST =
            "CREATE TABLE historique (idHistorique INTEGER PRIMARY KEY AUTOINCREMENT, utilisateur TEXT, ville TEXT, date TEXT);";

    public final String SQL_DELETE_USER =
            "DROP TABLE IF EXISTS utilisateur";

    public final String SQL_DELETE_HIST =
            "DROP TABLE IF EXISTS historique";

    public ClientDbHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_USER);
        db.execSQL(SQL_CREATE_HIST);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_USER);
        db.execSQL(SQL_DELETE_HIST);
        onCreate(db);
    }
}
