package com.thermodev.thegymnotebook;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by Thermodev on 27-Jul-17.
 */

public class ExerciseArrayAdapter extends ArrayAdapter<String> {
    private Button button;
    private Context context;
    private int layoutResourceId;
    private List<Exercise> exerciseList;


    public List<Exercise> getExerciseList() {
        return exerciseList;
    }

    public ExerciseArrayAdapter(Context context, int resource, int textViewResourceId, List<Exercise> exerciseList) {
        super(context, resource, textViewResourceId);
        this.context = context;
        this.layoutResourceId = resource;
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
        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            row.setTag(button);
            Exercise exercise = new Exercise(getItem(position));
            exerciseList.add(exercise);
            Log.d(TAG, "getView - getItem: " + getItem(position));
        } else {
            Log.d(TAG, "getView: called in else ");
        }

        TextView exerciseName = (TextView) row.findViewById(R.id.plan_list_add_edit_exercise);
        exerciseName.setText(getItem(position));



        EditText setsEdit = (EditText) row.findViewById(R.id.plan_list_sets_edit_text);
        if (setsEdit != null) {
            setsEdit.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    // Auto-generated method stub
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    // Auto-generated method stub
                }

                @Override
                public void afterTextChanged(Editable s) {
                    Log.d(TAG, "afterTextChanged: " + s.toString());
                    Exercise currentExercise = exerciseList.get(position);
                    if (s.toString().length() >= 10) {
                        currentExercise.setSets(Integer.parseInt(s.toString().substring(0, 10)));
                    } else if (s.toString().equals("")) {
                        currentExercise.setSets(0);
                    } else {
                        currentExercise.setSets(Integer.parseInt(s.toString()));
                    }
                }
            });
        }
        EditText repsEdit = (EditText) row.findViewById(R.id.plan_list_reps_edit_text);
        if (repsEdit != null) {
//            repsEdit.setText(exerciseList.get(position).getReps());
            repsEdit.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    // Auto-generated method stub
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    // Auto-generated method stub
                }

                @Override
                public void afterTextChanged(Editable s) {
                    Log.d(TAG, "afterTextChanged: " + s.toString());
                    Exercise currentExercise = exerciseList.get(position);
                    if (s.toString().length() >= 10) {
                        currentExercise.setReps(Integer.parseInt(s.toString().substring(0, 10)));
                    } else if (s.toString().equals("")) {
                        currentExercise.setReps(0);
                    } else {
                        currentExercise.setReps(Integer.parseInt(s.toString()));
                        Log.d(TAG, "onClick: Reps: " + exerciseList.get(position).getReps());
                    }
                }
            });
        }

        Button deleteButton = (Button) row.findViewById(R.id.plan_list_delete_button);
        if (deleteButton != null) {
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setTitle("Are you sure you want to delete exercise: " + getItem(position) + "?");

                    // Set up the buttons
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //Remove from Adapter
                            remove(getItem(position));

                            //Remove from list
                            exerciseList.remove(position);

                            Log.d(TAG, "onClick - Remaining ExerciseList: " + exerciseList.size());
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
        notifyDataSetChanged();
        return row;
    }


    public Exercise getExercise(int index){
        return exerciseList.get(index);
    }

    @Nullable
    @Override
    public String getItem(int position) {
        return super.getItem(position);
    }
}
