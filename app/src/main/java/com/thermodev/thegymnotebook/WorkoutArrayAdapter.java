package com.thermodev.thegymnotebook;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;


/**
 * Created by user on 17-Aug-17.
 */
public class WorkoutArrayAdapter extends ArrayAdapter<Workout> {
    private static final String TAG = "WorkoutArrayAdapter";
    private Context context;
    private int layoutResourceId;
    private final LayoutInflater layoutInflater;
    private List<Workout> mWorkouts;

    private TextView tvWorkoutDate;
    private TextView tvWorkoutDescription;
    private Button deleteButton;
    private Button editButton;


    public WorkoutArrayAdapter(Context context, int resource, List<Workout> workoutList) {
        super(context, resource);
        this.layoutResourceId = resource;
        this.layoutInflater = LayoutInflater.from(context);
        this.mWorkouts = workoutList;
    }

    @Override
    public int getCount() {
        return super.getCount();
    }


    @NonNull
    @Override
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
        final String workoutDate = cal.getDisplayName(Calendar.DAY_OF_WEEK ,Calendar.LONG, Locale.getDefault()) + " "
                + cal.get(Calendar.DAY_OF_MONTH) + " - "
                +  cal.getDisplayName(Calendar.MONTH ,Calendar.LONG, Locale.getDefault())
                + " - " + cal.get(Calendar.YEAR);


        tvWorkoutDate.setText(workoutDate );
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
        if(editButton != null){
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


}
