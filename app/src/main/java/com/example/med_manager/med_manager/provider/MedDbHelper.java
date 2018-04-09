package com.example.med_manager.med_manager.provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.med_manager.med_manager.provider.MedContract.MedEntry;

/**
 * Created by salabs on 03/04/2018.
 */

public class MedDbHelper extends SQLiteOpenHelper{
    // The database name
    private static final String DATABASE_NAME = "medmanager.db";

    // If you change the database schema, you must increment the database version
    private static final int DATABASE_VERSION = 2;

    // Constructor
    public MedDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        // Create a table to hold the plants data
        final String SQL_CREATE_MEDS_TABLE = "CREATE TABLE " + MedEntry.TABLE_NAME + " (" +
                MedEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                MedEntry.COLUMN_NAME + " TEXT NOT NULL, " +
                MedEntry.COLUMN_DESCRIPTION + " TEXT NOT NULL, " +
                MedEntry.COLUMN_FREQUENCY + " INTEGER NOT NULL, " +
                MedEntry.COLUMN_START_DATE + " TEXT NOT NULL, " +
                MedEntry.COLUMN_END_DATE + " TEXT NOT NULL)";

        sqLiteDatabase.execSQL(SQL_CREATE_MEDS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // For now simply drop the table and create a new one.
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MedEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
