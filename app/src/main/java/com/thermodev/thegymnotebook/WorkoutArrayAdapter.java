package com.thermodev.thegymnotebook;

import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


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

    interface OnWorkoutClickListener{
        void onEditClick(Workout workout);
        void onDeleteClick(Workout workout);
    }

    public WorkoutArrayAdapter(Cursor cursor, OnWorkoutClickListener listener) {
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
            if(!mCursor.moveToPosition(position)){
                throw new IllegalStateException("Couldn't move cursor to Position: " + position);
            }
            String exercisesToSplit = mCursor.getString(mCursor.getColumnIndex(WorkoutsContract.Columns.WORKOUT_EXERCISES));
            String[] exercisesSplit = exercisesToSplit.split(",");

            //TODO: Figure out a way to use the exercise ID, to retrieve an Exercise from the database.
            String exercisesToDisplay = "";
            for(int i = 0; i < exercisesSplit.length; i++){
                exercisesToDisplay +=  mCursor.getString(mCursor.getColumnIndex(ExercisesContract.Columns._ID));
            }

            final Workout workout = new Workout(mCursor.getLong(mCursor.getColumnIndex(WorkoutsContract.Columns._ID)),
                    mCursor.getString(mCursor.getColumnIndex(WorkoutsContract.Columns.START_DATE)),
                    mCursor.getString(mCursor.getColumnIndex(WorkoutsContract.Columns.WORKOUT_EXERCISES)),
                    mCursor.getString(mCursor.getColumnIndex(WorkoutsContract.Columns.WORKOUT_DESCRIPTION)));
            View.OnClickListener buttonListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (v.getId()){
                        case R.id.workout_list_delete_button:
                            if(listener != null){
                                listener.onDeleteClick(workout);
                            }
                            break;
                        case R.id.workout_list_edit_button:
                            if(listener != null){
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
        public int getItemCount () {
            return 0;
        }

    /**
     * Swap in New Cursor, returning the new cursor
     * The Returned old cursor is <em>not</em> closed.
     *
     *
     * @param newCursor The new Cursor to be used.
     * @return Returns the previously set cursor, or null if there wasn't one.
     * If the given new cursor is the same instance as the previously set Cursor,
     * null is also returned.
     */
    Cursor swapCursor(Cursor newCursor){
        if(newCursor == mCursor){
            return null;
        }

        final Cursor oldCursor = mCursor;
        mCursor = newCursor;
        if(newCursor != null){
            // Notify Observers of new cursor
            notifyDataSetChanged();
        }else{
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
