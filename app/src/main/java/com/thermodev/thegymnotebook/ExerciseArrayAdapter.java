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
import android.widget.EditText;
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

        if (row == null) {
            Log.d(TAG, "getView: CALLED IN IF");
            row = layoutInflater.inflate(layoutResourceId, parent, false);
//            Exercise exercise = new Exercise(getItem(position));
//            exerciseList.add(exercise);

            tvExerciseName = (TextView) row.findViewById(R.id.plan_list_add_edit_exercise);
            tvExerciseName.setText(getItem(position).getName());

            genExercise = new Exercise(getItem(position).getName());
            genExercise.setSets(0);
            genExercise.setReps(0);
            row.setTag(genExercise);

//            exerciseList.add(genExercise);
            Log.d(TAG, "getView: IF: " + position);
            Log.d(TAG, "getView: currentGetTag -  | Name: " + genExercise.getName() + " - Sets/Reps: " + genExercise.getSets() + "/" + genExercise.getReps());
//            Log.d(TAG, "getView: Parent " + parent.toString());

        } else {
            //TODO: Implement Else
            Log.d(TAG, "getView: CALLED IN ELSE: " + position);

            genExercise = (Exercise) row.getTag();

            Log.d(TAG, "getView: currentGetTag -  | Name: " + genExercise.getName() + " - Sets/Reps: " + genExercise.getSets() + "/" + genExercise.getReps());


            tvExerciseName = (TextView) row.findViewById(R.id.plan_list_add_edit_exercise);
            tvExerciseName.setText(getItem(position).getName());
//            exerciseList.get(position).setReps(genExercise.getReps());
//            exerciseList.get(position).setSets(genExercise.getSets());
        }
//
        Log.d(TAG, "getView: GenExercise:  " + genExercise.getName() + " - " + genExercise.getSets() + "/" + genExercise.getReps());
        Log.d(TAG, "getView: GetItem    " + position + "   : " + getItem(position).getName() + " - " + getItem(position).getSets() + "/" + getItem(position).getReps());


        EditText setsEdit = (EditText) row.findViewById(R.id.plan_list_sets_edit_text);
        EditText repsEdit = (EditText) row.findViewById(R.id.plan_list_reps_edit_text);


        //TODO: Determine if these lines are needed (Hint - Probs Not)
//        int repsNum = (repsEdit.getText().toString().equals("")) ? 0 : (Integer.parseInt(repsEdit.getText().toString()));
//        getItem(position).setReps(repsNum);
//
//        int setsNum = (repsEdit.getText().toString().equals("")) ? 0 : (Integer.parseInt(setsEdit.getText().toString()));
//        getItem(position).setSets(setsNum);


        Log.d(TAG, "getView: GenExercise:  " + genExercise.getName() + " - " + genExercise.getSets() + "/" + genExercise.getReps());
        Log.d(TAG, "getView: GetItem    " + position + "   : " + getItem(position).getName() + " - " + getItem(position).getSets() + "/" + getItem(position).getReps());

//        if (setsEdit != null) {
//            setsEdit.addTextChangedListener(new TextWatcher() {
//                @Override
//                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                    // Auto-generated method stub
//                    Log.d(TAG, "beforeTextChanged: NOT NUYLL NTUTA KSA TJAST ");
//                }
//
//                @Override
//                public void onTextChanged(CharSequence s, int start, int before, int count) {
//                    // Auto-generated method stub
//                }
//
//                @Override
//                public void afterTextChanged(Editable s) {
//                    Log.d(TAG, "afterTextChanged: " + s.toString());
////                    Exercise currentExercise = getItem(position);
//                    if (s.toString().length() >= 10) {
//                        genExercise.setSets(Integer.parseInt(s.toString().substring(0, 10)));
//                    } else if (s.toString().equals("")) {
//                        genExercise.setSets(0);
//                    } else {
//                        genExercise.setSets(Integer.parseInt(s.toString()));
//                        Log.d(TAG, "onClick: Reps: " + genExercise.getReps());
//                    }
//                }
//            });
//        }
//
//        if (repsEdit != null) {
////            repsEdit.setText(exerciseList.get(position).getReps());
//            repsEdit.addTextChangedListener(new TextWatcher() {
//                @Override
//                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                    // Auto-generated method stub
//                }
//
//                @Override
//                public void onTextChanged(CharSequence s, int start, int before, int count) {
//                    // Auto-generated method stub
//                }
//
//                @Override
//                public void afterTextChanged(Editable s) {
//                    Log.d(TAG, "afterTextChanged: " + s.toString());
////                    Exercise currentExercise =  getItem(position);
//                    if (s.toString().length() >= 10) {
//                        genExercise.setReps(Integer.parseInt(s.toString().substring(0, 10)));
//                    } else if (s.toString().equals("")) {
//                        genExercise.setReps(0);
//                    } else {
//                        genExercise.setReps(Integer.parseInt(s.toString()));
//                        Log.d(TAG, "onClick: Reps: " + genExercise.getReps());
//                    }
//                }
//            });
//        }

        Button deleteButton = (Button) row.findViewById(R.id.plan_list_delete_button);
        if (deleteButton != null) {
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setTitle("Are you sure you want to delete exercise: " + getItem(position).getName() + "...?");

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

                            clear();
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
