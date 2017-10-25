package com.thermodev.thegymnotebook;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Thermolink on 17-Sep-17.
 *
 * Database Class for the Application.w
 *
 * The only class that should use this is {@link AppProvider}.
 */

class AppDatabase extends SQLiteOpenHelper {
    private static final String TAG = "AppDatabase";

    public static final String DATABASE_NAME = "TheGymNotebook.db";
    public static final int DATABASE_VERSION = 1;

    // Implement AppDatabase as a Singleton
    private static AppDatabase instance = null;

    private AppDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.d(TAG, "TheGymNotebook Constructor Called.");
    }

    /**
     *
     * Get an instance of the app's singleton database helper object
     *
     * @param context the content providers context.
     * @return a SQLite database helper object
     */
    static AppDatabase getInstance(Context context) {
        if(instance == null) {
            Log.d(TAG, "getInstance: creating new instance");
            instance = new AppDatabase(context);
        }

        return instance;
    }

    /**
     * This is used to instantiate the databases.
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "onCreate: starts");
        String sSQL;    // Use a string variable to facilitate logging

        sSQL = "CREATE TABLE " + ExercisesContract.TABLE_NAME + " ("
                + ExercisesContract.Columns._ID + " INTEGER PRIMARY KEY NOT NULL, "
                + ExercisesContract.Columns.EXERCISES_NAME + " TEXT NOT NULL, "
                + ExercisesContract.Columns.EXERCISES_SETS + " INTEGER, "
                + ExercisesContract.Columns.EXERCISES_REPS + " INTEGER);";
        db.execSQL(sSQL);

        sSQL = "CREATE TABLE " + WorkoutsContract.TABLE_NAME + " ("
                + WorkoutsContract.Columns._ID + " INTEGER PRIMARY KEY NOT NULL, "
                + WorkoutsContract.Columns.START_DATE + " TEXT NOT NULL, "
                + WorkoutsContract.Columns.WORKOUT_NAME+ " TEXT, "
                + WorkoutsContract.Columns.WORKOUT_DESCRIPTION+ " TEXT, "
                + WorkoutsContract.Columns.WORKOUT_EXERCISES + " INTEGER, "
                + "FOREIGN KEY (" + WorkoutsContract.Columns.WORKOUT_EXERCISES + ") REFERENCES " + ExercisesContract.TABLE_NAME + "("+ ExercisesContract.Columns._ID+ ")" + ");";
        db.execSQL(sSQL);

        sSQL = "CREATE TABLE " + WorkoutPlansContract.TABLE_NAME + "("
                + WorkoutPlansContract.Columns._ID + " INTEGER PRIMARY KEY NOT NULL, "
                + WorkoutPlansContract.Columns.WORKOUT_NAME + " TEXT NOT NULL, "
                + WorkoutPlansContract.Columns.WORKOUT_DESCRIPTION + " TEXT, "
                + WorkoutPlansContract.Columns.WORKOUT_EXERCISES + " INTEGER, "
                + "FOREIGN KEY (" + WorkoutPlansContract.Columns.WORKOUT_EXERCISES + ") REFERENCES " + ExercisesContract.TABLE_NAME + "("+ ExercisesContract.Columns._ID+ ")" + ");";

        db.execSQL(sSQL);

        Log.d(TAG, "onCreate: ends");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(TAG, "onUpgrade: starts");
        switch(oldVersion) {
            case 1:
                // Add any code to upgrade the logic from v.1 .
                break;
            default:
                throw new IllegalStateException("onUpgrade() with unknown newVersion: " + newVersion);
        }
        Log.d(TAG, "onUpgrade: end");
    }
}
