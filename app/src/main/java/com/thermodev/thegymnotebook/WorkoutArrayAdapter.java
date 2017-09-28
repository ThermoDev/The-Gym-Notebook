package com.thermodev.thegymnotebook;

import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.Arrays;


/**
 * Created by Thermolink on 17-Aug-17.
 */
class WorkoutArrayAdapter extends RecyclerView.Adapter<WorkoutArrayAdapter.WorkoutViewHolder> {
    private static final String TAG = "WorkoutArrayAdapter";
//    private Context context;
//    private int layoutResourceId;
//    private final LayoutInflater layoutInflater;
//    private List<Workout> mWorkouts;

    private OnWorkoutClickListener listener;
    private Cursor mCursor;

    interface OnWorkoutClickListener {
        void onEditClick(Workout workout);

        void onDeleteClick(Workout workout);
    }

    public WorkoutArrayAdapter(Cursor cursor, OnWorkoutClickListener listener) {
        Log.d(TAG, "WorkoutArrayAdapter: Constructor Called");
        this.mCursor = cursor;
        this.listener = listener;
    }

    //TODO: DELETE THIS COMMENT
/*
    @NonNull
    public View getView(final int position, @Nullable final View convertView, @NonNull final ViewGroup parent) {
        View row = convertView;

        if (row == null) {
            Log.d(TAG, "getView: CALLED IN IF");
            row = layoutInflater.inflate(layoutResourceId, parent, false);
        } else {
            Log.d(TAG, "getView: CALLED IN ELSE: " + position);
        }
        tvWorkoutDate = (TextView) row.findViewById(R.id.workout_list_date);
        tvWorkoutDescription = (TextView) row.findViewById(R.id.workout_list_description);
        deleteButton = (Button) row.findViewById(R.id.workout_list_delete_button);
        editButton = (Button) row.findViewById(R.id.workout_list_edit_button);

        Calendar cal = getItem(position).getCalendar();
        // Sets the workout date as a string.
        final String workoutDate = cal.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault()) + " "
                + cal.get(Calendar.DAY_OF_MONTH) + " - "
                + cal.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault())
                + " - " + cal.get(Calendar.YEAR);


        tvWorkoutDate.setText(workoutDate);
        tvWorkoutDescription.setText(getItem(position).getDescription());

        if (deleteButton != null) {
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    // Retrieves Exercise Name to Delete.
                    builder.setTitle("Delete Workout?");
                    builder.setMessage("Are you sure you want to delete the Workout on: " + workoutDate
                            + ".\n\n" + getItem(position).getDescription());

                    // Set up the buttons
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //Remove from Adapter
                            getItem(position).setCalendar(null);
                            getItem(position).setExercises(null);
                            getItem(position).setDescription(null);
                            remove(getItem(position));
                            notifyDataSetChanged();
                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });

                    builder.show();

                }
            });
        }
        if (editButton != null) {
            editButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent detailIntent = new Intent(getContext(), AddEditWorkoutActivity.class);
                    if (getItem(position) != null) {
                        Workout workout = getItem(position);
                        detailIntent.putExtra(Workout.class.getSimpleName(), workout);
                        getContext().startActivity(detailIntent);
                    }

                }
            });
        }
        return row;
    }
    */

    @Override
    public WorkoutViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.workout_list_items, parent, false);
        return new WorkoutViewHolder(view);
    }

    @Override
    public void onBindViewHolder(WorkoutViewHolder workoutHolder, int position) {
        if ((mCursor == null) || (mCursor.getCount() == 0)) {
            Log.d(TAG, "onBindViewHolder - Providing Instructions");
            workoutHolder.tvWorkoutDate.setText(R.string.empty_description);
            workoutHolder.tvWorkoutDescription.setText(R.string.instructions);
            workoutHolder.editButton.setVisibility(View.GONE);
            workoutHolder.deleteButton.setVisibility(View.GONE);
        } else {
            if (!mCursor.moveToPosition(position)) {
                throw new IllegalStateException("Couldn't move cursor to Position: " + position);
            }

            String exercisesFound = mCursor.getString(mCursor.getColumnIndex(WorkoutsContract.Columns.WORKOUT_EXERCISES));
            // If we have found any exercises from the Cursor
            if (exercisesFound != null) {
                // The projection for which the columns of the table will be looked at within the CursorLoader
                String[] projection = {ExercisesContract.Columns._ID, ExercisesContract.Columns.EXERCISES_NAME,
                        ExercisesContract.Columns.EXERCISES_REPS, ExercisesContract.Columns.EXERCISES_SETS};

                // Setting up the WHERE clause for CursorLoader, splitting id's already provided by "," in the db

                AppProvider appProvider = new AppProvider();

                // Splits the found exercises by the ",".
                String[] exercisesIdSplitArray = exercisesFound.split(",");

                Log.d(TAG, "onBindViewHolder:  ExerciseIdSplitArray" + Arrays.toString(exercisesIdSplitArray));

                Log.d(TAG, "onBindViewHolder: ExerciseIdSplitArray Count: " + exercisesIdSplitArray.length);

                String exerciseToFind = "";

                if(exercisesIdSplitArray.length > 1){
                    String exerciseIds = "";
                    for(int i = 0; i < exercisesIdSplitArray.length; i++){
                        // Replaces the last found array item and replaces the "," with "" for the database.
                        Log.d(TAG, "onBindViewHolder: ExerciseIdSplitArray ITEM " + exercisesIdSplitArray[i]);
                        Log.d(TAG, "onBindViewHolder: INDEX " + i);
                        Log.d(TAG, "onBindViewHolder: ExerciseIdSplitArray LENGTH " + exercisesIdSplitArray.length);
                        exerciseIds += exercisesIdSplitArray[i];
                    }
                    if(exerciseIds.split(",").length > 1) {
                        exerciseToFind = ExercisesContract.Columns._ID + " IN (" + exerciseIds + ")";
                    }
                    Log.d(TAG, "onBindViewHolder: Exercises To Find: " + exerciseToFind);
                }else {
                    exerciseToFind = ExercisesContract.Columns._ID + " = " + exercisesFound.replace(",", "") + "";
                }

                // Creating a cursor loader
                Cursor exerciseCursor = appProvider.query( ExercisesContract.CONTENT_URI ,projection, exerciseToFind, null, null);

                // If there is a currently usable exerciseCursor
                if(exerciseCursor != null){
                    // Checks if any Exercises were found
                    if(exerciseCursor.getCount() != 0) {

                        // moveToFirst() to move the cursor to the first element.
                        exerciseCursor.moveToFirst();
                        Log.d(TAG, "onBindViewHolder: Exercise cursor Count: " + exerciseCursor.getCount());
                        // moveToFirst() to move the cursor to the first element.
                        exerciseCursor.moveToFirst();
                        // Loops through all of the exercises found, then logs them.
                        while(exerciseCursor.moveToNext()) {
                            Log.d(TAG, "onBindViewHolder: ID: " + exerciseCursor.getString(exerciseCursor.getColumnIndex(ExercisesContract.Columns._ID)));
                            Log.d(TAG, "onBindViewHolder: Name: " + exerciseCursor.getString(exerciseCursor.getColumnIndex(ExercisesContract.Columns.EXERCISES_NAME)));
                            Log.d(TAG, "onBindViewHolder: Reps: " + exerciseCursor.getString(exerciseCursor.getColumnIndex(ExercisesContract.Columns.EXERCISES_REPS)));
                            Log.d(TAG, "onBindViewHolder: Sets: " + exerciseCursor.getString(exerciseCursor.getColumnIndex(ExercisesContract.Columns.EXERCISES_SETS)));
                        }
                    }
                    exerciseCursor.close();
                }else{
                    Log.d(TAG, "onBindViewHolder: Exercise Cursor returned null");
                }
            }
//            String[] myExerciseIdArray  = exercisesFound.split(",");


//            for(int i = 0; i < myExerciseIdArray.length; i++){
//                Log.d(TAG, "onBindViewHolder: Exercise ID: " + myExerciseIdArray[i]);
//            }


            final Workout workout = new Workout(mCursor.getLong(mCursor.getColumnIndex(WorkoutsContract.Columns._ID)),
                    mCursor.getString(mCursor.getColumnIndex(WorkoutsContract.Columns.START_DATE)),
                    mCursor.getString(mCursor.getColumnIndex(WorkoutsContract.Columns.WORKOUT_EXERCISES)),
                    mCursor.getString(mCursor.getColumnIndex(WorkoutsContract.Columns.WORKOUT_DESCRIPTION)));
            View.OnClickListener buttonListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (v.getId()) {
                        case R.id.workout_list_delete_button:
                            if (listener != null) {
                                listener.onDeleteClick(workout);
                            }
                            break;
                        case R.id.workout_list_edit_button:
                            if (listener != null) {
                                listener.onEditClick(workout);
                            }
                            break;
                        default:
                            Log.d(TAG, "onClick: Default ");
                            break;
                    }
                }
            };

            workoutHolder.editButton.setOnClickListener(buttonListener);
            workoutHolder.deleteButton.setOnClickListener(buttonListener);
        }
    }

    @Override
    public int getItemCount() {
        if ((mCursor == null) || (mCursor.getCount() == 0)) {
            return 1; // Populate a single cursor with instructions.
        } else {
            return mCursor.getCount();
        }
    }

    /**
     * Swap in New Cursor, returning the new cursor
     * The Returned old cursor is <em>not</em> closed.
     *
     * @param newCursor The new Cursor to be used.
     * @return Returns the previously set cursor, or null if there wasn't one.
     * If the given new cursor is the same instance as the previously set Cursor,
     * null is also returned.
     */
    Cursor swapCursor(Cursor newCursor) {
        if (newCursor == mCursor) {
            return null;
        }

        final Cursor oldCursor = mCursor;
        mCursor = newCursor;
        if (newCursor != null) {
            // Notify Observers of new cursor
            notifyDataSetChanged();
        } else {
            // Notify Observers of lack of cursor
            notifyItemRangeRemoved(0, getItemCount());
        }
        return oldCursor;
    }


    /**
     * WorkoutViewHolder
     * Will be used by this class to set the required items in a row.
     */
    static class WorkoutViewHolder extends RecyclerView.ViewHolder {
        private static final String TAG = "WorkoutViewHolder";

        TextView tvWorkoutDate = null;
        TextView tvWorkoutDescription = null;
        Button editButton = null;
        Button deleteButton = null;

        public WorkoutViewHolder(View row) {
            super(row);

            tvWorkoutDate = (TextView) row.findViewById(R.id.workout_list_date);
            tvWorkoutDescription = (TextView) row.findViewById(R.id.workout_list_description);
            deleteButton = (Button) row.findViewById(R.id.workout_list_delete_button);
            editButton = (Button) row.findViewById(R.id.workout_list_edit_button);

        }
    }

}
