package com.thermodev.thegymnotebook;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
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

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;
import static com.thermodev.thegymnotebook.AddEditPlanActivityFragment.myArrayString;

/**
 * Created by Thermodev on 27-Jul-17.
 */

public class ExerciseArrayAdapter extends ArrayAdapter<String> {
    private Button button;
    private Context context;
    private int layoutResourceId;
    private List<String> exercises;
    private List<Exercise> exerciseList = new ArrayList<>();


    public List<Exercise> getExerciseList() {
        return exerciseList;
    }

    public ExerciseArrayAdapter(@NonNull Context context, @LayoutRes int resource, @IdRes int textViewResourceId, @NonNull ArrayList<java.lang.String> objects) {
        super(context, resource, textViewResourceId, objects);
        this.context = context;
        this.layoutResourceId = resource;
        this.exercises = objects;
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable final View convertView, @NonNull final ViewGroup parent) {
        View row = convertView;

        if(row == null){
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            row.setTag(button);
            Exercise exercise = new Exercise(getItem(position));
            exerciseList.add(exercise);
        }else{
            Log.d(TAG, "getView: ");
        }

        TextView exerciseName = (TextView) row.findViewById(R.id.plan_list_add_edit_exercise);

        EditText setsEdit = (EditText) row.findViewById(R.id.plan_list_sets_edit_text);
        if(setsEdit != null) {
            exerciseList.get(position).getSets();
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
                    if (!s.toString().equals("") && s.toString().length() < 10) {
                        exerciseList.get(position).setSets(Integer.parseInt(s.toString()));
                        Log.d(TAG, "onClick: Sets: " + exerciseList.get(position).getSets());
                    } else {
                        //TODO: Implement what to do if the length is greater than 10, or is empty
                        exerciseList.get(position).setSets(Integer.parseInt(s.toString().substring(0, 9)));
                    }
                }
            });
        }
        EditText repsEdit = (EditText) row.findViewById(R.id.plan_list_reps_edit_text);
        if(repsEdit != null) {
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
                    if (!s.toString().equals("") && s.toString().length() < 10) {
                        exerciseList.get(position).setReps(Integer.parseInt(s.toString()));
                        Log.d(TAG, "onClick: Reps: " + exerciseList.get(position).getReps());
                    } else {
                        //TODO: Implement what to do if the length is greater than 10, or is empty
                        exerciseList.get(position).setReps(Integer.parseInt(s.toString().substring(0, 10)));
                    }
                }
            });
        }

        exerciseName.setText(getItem(position));
        Log.d(TAG, "getView: "+ getItem(position));
        Button deletebutton = (Button) row.findViewById(R.id.plan_list_delete_button);
        if(deletebutton != null) {
            deletebutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setTitle("Are you sure you want to delete exercise: " + getItem(position) + "?");

                    // Set up the buttons
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
//                    Log.d("", "onClick: Was Clicked: " + position);
                            remove(getItem(position));
                            Log.d("", "onClick: Remaining Activities: " + myArrayString.size());
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


    @Nullable
    @Override
    public String getItem(int position) {
        return super.getItem(position);
    }
}
