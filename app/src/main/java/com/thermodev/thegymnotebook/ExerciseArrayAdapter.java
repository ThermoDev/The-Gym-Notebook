package com.thermodev.thegymnotebook;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by Thermodev on 27-Jul-17.
 */

public class ExerciseArrayAdapter extends ArrayAdapter<Exercise> {
    private Context context;
    private int layoutResourceId;
    private final LayoutInflater layoutInflater;
    private List<Exercise> exerciseList;


    public List<Exercise> getExerciseList() {
        return exerciseList;
    }

    public ExerciseArrayAdapter(Context context, int resource, List<Exercise> exerciseList) {
        super(context, resource);
        this.layoutResourceId = resource;
        this.layoutInflater = LayoutInflater.from(context);
        this.exerciseList = exerciseList;
    }


    @Override
    public int getCount() {
        return super.getCount();
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable final View convertView, @NonNull final ViewGroup parent) {
        View row = convertView;
        final Exercise genExercise;
        TextView tvExerciseName;
        TextView tvReps;
        TextView tvSets;

        if (row == null) {
            Log.d(TAG, "getView: CALLED IN IF");
            row = layoutInflater.inflate(layoutResourceId, parent, false);
//            row.setTag(genExercise);

            Log.d(TAG, "getView: IF: " + position);
        } else {
            Log.d(TAG, "getView: CALLED IN ELSE: " + position);
        }
        // Sets TextViews
        tvExerciseName = (TextView) row.findViewById(R.id.plan_list_add_edit_exercise);
        tvExerciseName.setText(getItem(position).getName());

        tvSets = (TextView) row.findViewById(R.id.plan_list_sets_text_view);
        tvSets.setText(String.valueOf(getItem(position).getSets()));

        tvReps = (TextView) row.findViewById(R.id.plan_list_reps_text_view);
        tvReps.setText(String.valueOf(getItem(position).getReps()));


        Button deleteButton = (Button) row.findViewById(R.id.plan_list_delete_button);
        if (deleteButton != null) {
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    // Retrieves Exercise Name to Delete.
                    String exerciseName = getItem(position).getName();
                    String name = ( exerciseName.length() >= 12 ? (exerciseName.substring(0,12) + " ...?") : (exerciseName + " ?") );
                    builder.setTitle("Are you sure you want to delete exercise: " + name);

                    // Set up the buttons
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //Remove from Adapter
                            Log.d(TAG, "onClick: reps " + getItem(position).getReps());
                            getItem(position).setSets(0);
                            getItem(position).setReps(0);
                            Log.d(TAG, "onClick: reps " + getItem(position).getReps());
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
        return row;
    }


    public Exercise getExercise(int index) {
        return exerciseList.get(index);
    }

    @Nullable
    @Override
    public Exercise getItem(int position) {
        return super.getItem(position);
    }

    //TODO: Delete getMyExercise
//    public Exercise getMyExercise(View v) {
//        TextView tvName = (TextView) v.findViewById(R.id.plan_list_add_edit_exercise);
//        EditText etSets = (EditText) v.findViewById(R.id.plan_list_sets_edit_text);
//        EditText etReps = (EditText) v.findViewById(R.id.plan_list_reps_edit_text);
//        String name = tvName.getText().toString();
//        String sets = etSets.getText().toString();
//        String reps = etReps.getText().toString();
//        Exercise myExercise = new Exercise(name);
//        myExercise.setReps(Integer.parseInt(reps));
//        myExercise.setSets(Integer.parseInt(sets));
//        return myExercise;
//    }


}
