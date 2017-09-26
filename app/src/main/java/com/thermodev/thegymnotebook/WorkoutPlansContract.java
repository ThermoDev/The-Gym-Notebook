package com.thermodev.thegymnotebook;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

import static com.thermodev.thegymnotebook.AppProvider.CONTENT_AUTHORITY;
import static com.thermodev.thegymnotebook.AppProvider.CONTENT_AUTHORITY_URI;

/**
 * Created by Thermolink on 17-Sep-17.
 */

public class WorkoutPlansContract {
    static final String TABLE_NAME = "Workouts";

    // The WorkoutPlans Fields
    public static class Columns {
        public static final String _ID = BaseColumns._ID;
        public static final String WORKOUT_NAME = "Name";
        public static final String WORKOUT_DESCRIPTION = "Description";
        public static final String EXCERISE_ID = "ExerciseID";

        private Columns(){
            // Private Constructor to prevent accidental instantiation :)
        }

    }

    /**
     * The URI to access the WorkoutPlans Table.
     */
    public static final Uri CONTENT_URI = Uri.withAppendedPath(CONTENT_AUTHORITY_URI, TABLE_NAME);

    static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd." + CONTENT_AUTHORITY + "." + TABLE_NAME;
    static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd." + CONTENT_AUTHORITY + "." + TABLE_NAME;

    static Uri buildWorkoutPlanUri(long workoutId){
        return ContentUris.withAppendedId(CONTENT_URI, workoutId);
    }

    static long getWorkoutPlanId(Uri uri){
        return ContentUris.parseId(uri);
    }
}
