package com.thermodev.thegymnotebook;

import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Locale;


/**
 * Created by Thermolink on 17-Aug-17.
 */
class WorkoutRecyclerViewAdapter extends RecyclerView.Adapter<WorkoutRecyclerViewAdapter.WorkoutViewHolder> {
    private static final String TAG = "WorkoutRecyclerViewAdap";
//    private Context context;
//    private int layoutResourceId;
//    private final LayoutInflater layoutInflater;
//    private List<Workout> mWorkouts;

    private OnWorkoutClickListener mOnWorkoutListener;
    private Cursor mCursor;

    interface OnWorkoutClickListener {
        void onEditClick(Workout workout);
        void onDeleteClick(Workout workout);
    }

    public WorkoutRecyclerViewAdapter(Cursor cursor, OnWorkoutClickListener listener) {
        Log.d(TAG, "WorkoutRecyclerViewAdapter: Constructor Called");
        this.mCursor = cursor;
        this.mOnWorkoutListener = listener;
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

            // Setting this to visible, if there is a usable curosr, and the count of it is greater than one.
            workoutHolder.editButton.setVisibility(View.VISIBLE);
            workoutHolder.deleteButton.setVisibility(View.VISIBLE);



            // TODO: Move this code that retrieves exercises..
//            String exercisesFound = mCursor.getString(mCursor.getColumnIndex(WorkoutsContract.Columns.WORKOUT_EXERCISES));
//            // If we have found any exercises from the Cursor
//            if (exercisesFound != null) {
//                // The projection for which the columns of the table will be looked at within the CursorLoader
//                String[] projection = {ExercisesContract.Columns._ID, ExercisesContract.Columns.EXERCISES_NAME,
//                        ExercisesContract.Columns.EXERCISES_REPS, ExercisesContract.Columns.EXERCISES_SETS};
//                AppProvider appProvider = new AppProvider();
//
//                // -- Setting up the WHERE clause for CursorLoader, splitting id's already provided by "," in the db --
//
//                // Splits the found exercises by the "," split.
//                String[] exercisesIdSplitArray = exercisesFound.split(",");
//
//                // Initialize empty string in exercisesToFind
//                String exercisesToFind = "";
//
//                // If the found split exercises ID is greater than one, meaning there is more than one item in the array:
//                if(exercisesIdSplitArray.length > 1){
//                    String exerciseIds = "";
//                    for(int i = 0; i < exercisesIdSplitArray.length; i++){
//                        exerciseIds += exercisesIdSplitArray[i];
//                    }
//                    // If the split array of the ID
//                    if(exerciseIds.split(",").length > 1) {
//                        exercisesToFind = ExercisesContract.Columns._ID + " IN (" + exerciseIds + ")";
//                    }
//                }else {
//                    if(!exercisesFound.isEmpty()){
//                        exercisesToFind = ExercisesContract.Columns._ID + " = " + exercisesFound.replace(",", "") + "";
//                    }else{
//                        exercisesToFind = null;
//                    }
//                }
//
//                // Creating a cursor loader
//                Cursor exerciseCursor = appProvider.query( ExercisesContract.CONTENT_URI ,projection, exercisesToFind, null, null);
//
//                // If there is a currently usable exerciseCursor
//                if(exerciseCursor != null){
//                    // Checks if any Exercises were found
//                    if(exerciseCursor.getCount() != 0) {
//                        // moveToFirst() to move the cursor to the first element.
//                        exerciseCursor.moveToFirst();
//                        Log.d(TAG, "onBindViewHolder: Exercise cursor Count: " + exerciseCursor.getCount());
//                        // moveToFirst() to move the cursor to the first element.
//                        exerciseCursor.moveToFirst();
//                        // Loops through all of the exercises found, then logs them.
//                        while(exerciseCursor.moveToNext()) {
//                            Log.d(TAG, "onBindViewHolder: ID: " + exerciseCursor.getString(exerciseCursor.getColumnIndex(ExercisesContract.Columns._ID))
//                                    + " | Name: " + exerciseCursor.getString(exerciseCursor.getColumnIndex(ExercisesContract.Columns.EXERCISES_NAME)));
//                            Log.d(TAG, "onBindViewHolder: Reps/Sets: " + exerciseCursor.getString(exerciseCursor.getColumnIndex(ExercisesContract.Columns.EXERCISES_REPS))
//                                    + "/" + exerciseCursor.getString(exerciseCursor.getColumnIndex(ExercisesContract.Columns.EXERCISES_SETS)));
//                        }
//                    }
//                    exerciseCursor.close();
//                }else{
//                    Log.d(TAG, "onBindViewHolder: Exercise Cursor returned null");
//                }
//            }

            final Workout workout = new Workout(mCursor.getLong(mCursor.getColumnIndex(WorkoutsContract.Columns._ID)),
                    mCursor.getLong(mCursor.getColumnIndex(WorkoutsContract.Columns.START_DATE)),
                    mCursor.getString(mCursor.getColumnIndex(WorkoutsContract.Columns.WORKOUT_EXERCISES)),
                    mCursor.getString(mCursor.getColumnIndex(WorkoutsContract.Columns.WORKOUT_DESCRIPTION)));

            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(workout.getCalendar());

            workoutHolder.tvWorkoutDescription.setText(workout.getDescription());
            String day = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.ENGLISH);
            int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
            String month = calendar.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.ENGLISH);
            int year = calendar.get(Calendar.YEAR);

            String workoutDate = day + " " + dayOfMonth + " - " + month + ", " + year;

            workoutHolder.tvWorkoutDate.setText(workoutDate);

            View.OnClickListener buttonListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (v.getId()) {
                        case R.id.workout_list_delete_button:
                            if (mOnWorkoutListener != null) {
                                mOnWorkoutListener.onDeleteClick(workout);
                            }
                            break;
                        case R.id.workout_list_edit_button:
                            if (mOnWorkoutListener != null) {
                                mOnWorkoutListener.onEditClick(workout);
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
