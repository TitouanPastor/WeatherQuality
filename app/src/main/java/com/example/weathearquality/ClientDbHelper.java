package com.example.weathearquality;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ClientDbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "wheathearQuality.db";

    public final String SQL_CREATE =
            "CREATE TABLE utilisateur (id INTEGER PRIMARY KEY AUTOINCREMENT, utilisateur TEXT, motdepasse TEXT);";

    public final String SQL_DELETE =
            "DROP TABLE IF EXISTS utilisateur";

    public ClientDbHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE);
        onCreate(db);
    }
}
