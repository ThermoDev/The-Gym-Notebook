package com.thermodev.thegymnotebook;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by Thermolink on 17-Sep-17.
 * <p>
 * Provider for the Gym Notebook app. This is the only class that knows about {@link AppDatabase}
 */

public class AppProvider extends ContentProvider {
    private static final String TAG = "AppProvider";

    private AppDatabase mOpenHelper;

    private static final UriMatcher sUriMatcher = buildUriMatcher();

    static final String CONTENT_AUTHORITY = "com.thermodev.thegymnotebook.provider";
    public static final Uri CONTENT_AUTHORITY_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    private static final int EXERCISES = 100;
    private static final int EXERCISES_ID = 101;

    private static final int WORKOUTS = 200;
    private static final int WORKOUTS_ID = 201;

    private static final int WORKOUT_PLANS = 300;
    private static final int WORKOUT_PLANS_ID = 301;

    private static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);

        matcher.addURI(CONTENT_AUTHORITY, ExercisesContract.TABLE_NAME, EXERCISES);
        matcher.addURI(CONTENT_AUTHORITY, ExercisesContract.TABLE_NAME + "/#", EXERCISES_ID);

        matcher.addURI(CONTENT_AUTHORITY, WorkoutsContract.TABLE_NAME, WORKOUTS);
        matcher.addURI(CONTENT_AUTHORITY, WorkoutsContract.TABLE_NAME + "/#", WORKOUTS_ID);

//        matcher.addURI(CONTENT_AUTHORITY, WorkoutPlansContract.TABLE_NAME, WORKOUT_PLANS);
//        matcher.addURI(CONTENT_AUTHORITY, WorkoutPlansContract.TABLE_NAME + "/#", WORKOUT_PLANS_ID);

        return matcher;
    }

    @Override
    public boolean onCreate() {
        mOpenHelper = AppDatabase.getInstance(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        final int match = sUriMatcher.match(uri);
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();

        switch (match) {
            case EXERCISES:
                queryBuilder.setTables(ExercisesContract.TABLE_NAME);
                break;

            case EXERCISES_ID:
                queryBuilder.setTables(ExercisesContract.TABLE_NAME);
                long exerciseId = ExercisesContract.getExerciseId(uri);
                queryBuilder.appendWhere(ExercisesContract.Columns._ID + " = " + exerciseId);
                break;

            case WORKOUTS:
                queryBuilder.setTables(WorkoutsContract.TABLE_NAME);
                break;

            case WORKOUTS_ID:
                queryBuilder.setTables(WorkoutsContract.TABLE_NAME);
                long workoutId = WorkoutsContract.getWorkoutId(uri);
                queryBuilder.appendWhere(WorkoutsContract.Columns._ID + " = " + workoutId);
                break;

//            case WORKOUT_PLANS:
//                queryBuilder.setTables(WorkoutPlansContract.TABLE_NAME);
//                break;

//            case WORKOUT_PLANS_ID:
//                queryBuilder.setTables(WorkoutPlansContract.TABLE_NAME);
//                long workoutPlanId = WorkoutPlansContract.getWorkoutPlanId(uri);
//                queryBuilder.appendWhere(WorkoutPlansContract.Columns._ID + " = " + workoutPlanId);
//                break;

            default:
                throw new IllegalArgumentException("Unable to identify URI: " + uri);
        }

        if(mOpenHelper == null){
            mOpenHelper = AppDatabase.getInstance(getContext());
        }
        SQLiteDatabase db = mOpenHelper.getReadableDatabase();
//        return queryBuilder.query(db, projection, selection, selectionArgs, null, null, sortOrder);
        Cursor cursor = queryBuilder.query(db, projection, selection, selectionArgs, null, null, sortOrder);
        if(getContext() != null) {
            cursor.setNotificationUri(getContext().getContentResolver(), uri);
        }
        return cursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case EXERCISES:
                return ExercisesContract.CONTENT_TYPE;

            case EXERCISES_ID:
                return ExercisesContract.CONTENT_ITEM_TYPE;

            case WORKOUTS:
                return WorkoutsContract.CONTENT_TYPE;

            case WORKOUTS_ID:
                return WorkoutsContract.CONTENT_ITEM_TYPE;
//
//            case WORKOUT_PLANS:
//                return DurationsContract.WorkoutPlans.CONTENT_TYPE;
//
//            case WORKOUT_PLANS_ID:
//                return DurationsContract.WorkoutPlans.CONTENT_ITEM_TYPE;

            default:
                throw new IllegalArgumentException("Unable to identify URI: " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final int match = sUriMatcher.match(uri);
        Log.d(TAG, "insert with match: " + match);

        final SQLiteDatabase db;

        Uri returnUri;
        long recordId;
        Log.d(TAG, "insert: VALUES " + values.toString());

        switch (match) {
            case EXERCISES:
                db = mOpenHelper.getWritableDatabase();
                recordId = db.insert(ExercisesContract.TABLE_NAME, null, values);
                if (recordId >= 0) {
                    returnUri = ExercisesContract.buildExerciseUri(recordId);
                } else {
                    throw new android.database.SQLException("Failed to insert into " + uri.toString());
                }
                break;

            case WORKOUTS:
                db = mOpenHelper.getWritableDatabase();
                recordId = db.insert(WorkoutsContract.TABLE_NAME, null, values);
                if (recordId >= 0) {
                    returnUri = WorkoutsContract.buildWorkoutUri(recordId);
                } else {
                    throw new android.database.SQLException("Failed to insert into " + uri.toString());
                }
                break;

            case WORKOUT_PLANS:
//                db = mOpenHelper.getWritableDatabase();
//                recordId = db.insert(WorkoutPlansContract.TABLE_NAME, null, values);
//                if(recordId >=0) {
//                    returnUri = WorkoutPlansContract.Workouts.buildWorkoutUri(recordId);
//                } else {
//                    throw new android.database.SQLException("Failed to insert into " + uri.toString());
//                }
//                break;

            default:
                throw new IllegalArgumentException("Unknown uri: " + uri);
        }

        if (recordId >= 0) {
            // something was inserted
            Log.d(TAG, "insert: Setting notifyChanged with " + uri);
            getContext().getContentResolver().notifyChange(uri, null);
        } else {
            Log.d(TAG, "insert: nothing inserted");
        }

        Log.d(TAG, "Exiting insert: " + returnUri);
        return returnUri;

    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        Log.d(TAG, "update called with uri " + uri);
        final int match = sUriMatcher.match(uri);
        Log.d(TAG, "match is " + match);

        final SQLiteDatabase db;
        int count;

        String selectionCriteria;

        switch (match) {
            case EXERCISES:
                db = mOpenHelper.getWritableDatabase();
                count = db.delete(ExercisesContract.TABLE_NAME, selection, selectionArgs);
                break;

            case EXERCISES_ID:
                db = mOpenHelper.getWritableDatabase();
                long exerciseId = ExercisesContract.getExerciseId(uri);
                selectionCriteria = ExercisesContract.Columns._ID + " = " + exerciseId;

                if ((selection != null) && (selection.length() > 0)) {
                    selectionCriteria += " AND (" + selection + ")";
                }
                count = db.delete(ExercisesContract.TABLE_NAME, selectionCriteria, selectionArgs);
                break;

            case WORKOUTS:
                db = mOpenHelper.getWritableDatabase();
                count = db.delete(WorkoutsContract.TABLE_NAME, selection, selectionArgs);
                break;

            case WORKOUTS_ID:
                db = mOpenHelper.getWritableDatabase();
                long workoutId = WorkoutsContract.getWorkoutId(uri);
                selectionCriteria = WorkoutsContract.Columns._ID + " = " + workoutId;

                if((selection != null) && (selection.length()>0)) {
                    selectionCriteria += " AND (" + selection + ")";
                }
                count = db.delete(WorkoutsContract.TABLE_NAME, selectionCriteria, selectionArgs);
                break;

//            case WORKOUT_PLANS:
//                db = mOpenHelper.getWritableDatabase();
//                count = db.delete(WorkoutPlansContract.TABLE_NAME, selection, selectionArgs);
//                break;
//
//            case WORKOUT_PLANS_ID:
//                db = mOpenHelper.getWritableDatabase();
//                long workoutPlanId = WorkoutPlansContract.getWorkoutId(uri);
//                selectionCriteria = WorkoutPlansContract.Columns._ID + " = " + workoutPlanId;
//
//                if((selection != null) && (selection.length()>0)) {
//                    selectionCriteria += " AND (" + selection + ")";
//                }
//                count = db.delete(WorkoutPlansContract.TABLE_NAME, selectionCriteria, selectionArgs);
//                break;
            default:
                throw new IllegalArgumentException("Unknown uri: " + uri);
        }

        if (count > 0) {
            // something was deleted
            Log.d(TAG, "delete: Setting notifyChange with " + uri);
            getContext().getContentResolver().notifyChange(uri, null);
        } else {
            Log.d(TAG, "delete - It's okay, nothing was Deleted");
        }

        Log.d(TAG, "Exiting delete & returning " + count);
        return count;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        Log.d(TAG, "update called with uri " + uri);
        final int match = sUriMatcher.match(uri);
        Log.d(TAG, "match is " + match);

        final SQLiteDatabase db;
        int count;

        String selectionCriteria;

        switch (match) {

            case EXERCISES:
                db = mOpenHelper.getWritableDatabase();
                count = db.update(ExercisesContract.TABLE_NAME, values, selection, selectionArgs);
                break;

            case EXERCISES_ID:
                db = mOpenHelper.getWritableDatabase();
                long exerciseId = ExercisesContract.getExerciseId(uri);
                selectionCriteria = ExercisesContract.Columns._ID + " = " + exerciseId;

                if ((selection != null) && (selection.length() > 0)) {
                    selectionCriteria += " AND (" + selection + ")";
                }
                count = db.update(ExercisesContract.TABLE_NAME, values, selectionCriteria, selectionArgs);
                break;

            case WORKOUTS:
                db = mOpenHelper.getWritableDatabase();
                count = db.update(WorkoutsContract.TABLE_NAME, values, selection, selectionArgs);
                break;

            case WORKOUTS_ID:
                db = mOpenHelper.getWritableDatabase();
                long workoutId = WorkoutsContract.getWorkoutId(uri);
                selectionCriteria = WorkoutsContract.Columns._ID + " = " + workoutId;

                if((selection != null) && (selection.length()>0)) {
                    selectionCriteria += " AND (" + selection + ")";
                }
                count = db.update(WorkoutsContract.TABLE_NAME, values, selectionCriteria, selectionArgs);
                break;

//            case WORKOUT_PLANS:
//                db = mOpenHelper.getWritableDatabase();
//                count = db.update(WorkoutPlansContract.TABLE_NAME, values, selection, selectionArgs);
//                break;
//
//            case WORKOUT_PLANS_ID:
//                db = mOpenHelper.getWritableDatabase();
//                long workoutPlanId = WorkoutPlansContract.getWorkoutId(uri);
//                selectionCriteria = WorkoutPlansContract.Columns._ID + " = " + workoutPlanId;
//
//                if((selection != null) && (selection.length()>0)) {
//                    selectionCriteria += " AND (" + selection + ")";
//                }
//                count = db.update(WorkoutPlansContract.TABLE_NAME, values,  selectionCriteria, selectionArgs);
//                break;

            default:
                throw new IllegalArgumentException("Unknown uri: " + uri);
        }

        if (count > 0) {
            // something was deleted
            Log.d(TAG, "update: Setting notifyChange with " + uri);
            getContext().getContentResolver().notifyChange(uri, null);
        } else {
            Log.d(TAG, "update: nothing updated");
        }

        Log.d(TAG, "Exiting update, returning " + count);
        return count;
    }


}
