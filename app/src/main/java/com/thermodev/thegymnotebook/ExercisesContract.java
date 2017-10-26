package com.thermodev.thegymnotebook;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

import static com.thermodev.thegymnotebook.AppProvider.CONTENT_AUTHORITY;
import static com.thermodev.thegymnotebook.AppProvider.CONTENT_AUTHORITY_URI;

/**
 * Created by Thermolink on 17-Sep-17.
 */

public class ExercisesContract {
    static final String TABLE_NAME = "Exercises";

    // The Exercises Fields
    public static class Columns{
        public static final String _ID = BaseColumns._ID;
        public static final String EXERCISES_NAME = "Name";
        public static final String EXERCISES_SETS = "Sets";
        public static final String EXERCISES_REPS = "Reps";
        public static final String EXERCISES_WEIGHTS = "Weights";

        private Columns(){
            // Private Constructor to prevent accidental instantiation :)
        }

    }

    /**
     * The URI to access the Exercises Table.
     */
    public static final Uri CONTENT_URI = Uri.withAppendedPath(CONTENT_AUTHORITY_URI, TABLE_NAME);

    static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd." + CONTENT_AUTHORITY + "." + TABLE_NAME;
    static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd." + CONTENT_AUTHORITY + "." + TABLE_NAME;

    static Uri buildExerciseUri(long exerciseId){
        return ContentUris.withAppendedId(CONTENT_URI, exerciseId);
    }

    static long getExerciseId(Uri uri){
        return ContentUris.parseId(uri);
    }
}
