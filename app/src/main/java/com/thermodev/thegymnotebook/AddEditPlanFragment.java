package com.thermodev.thegymnotebook;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.NumberPicker;

import java.util.ArrayList;

/**
 * Created by user on 26-Jul-17.
 */

public class AddEditPlanFragment extends Fragment implements DatePickerDialog.OnDateSetListener {
    private static final String TAG = "AddEditPlanFragment";
    //Initializing variables
    private Button mAddExerciseButton;
    private Button mSaveButton;
    private ListView mListView;
    private EditText mNameText;
    private EditText mDescriptionText;
    private ExerciseArrayAdapter mExerciseAdapter;
    public static ArrayList<Exercise> exerciseList;

    //TODO: Remove tempWorkoutPlans after a database is implemented.
    public static ArrayList<WorkoutPlan> tempWorkoutPlans = new ArrayList<>();

//    private CursorRecyclerViewAdapter mAdapter; // Add Adapter Reference

    // Overriding for OnDateSetListener
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

    }

    //Interface for saving
    interface OnSaveClicked {
        void onSaveClicked();
    }

    //Is Created on view
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        //Creates the view
        final View view = inflater.inflate(R.layout.workout_plan_fragment_add_edit, container, false);

        //Links the objects to the related layout components
        mAddExerciseButton = (Button) view.findViewById(R.id.plan_add_edit_exercise);
        mSaveButton = (Button) view.findViewById(R.id.plan_add_edit_save);
        mListView = (ListView) view.findViewById(R.id.plan_add_edit_list_view);
        mNameText = (EditText) view.findViewById(R.id.plan_add_edit_name);
        mDescriptionText = (EditText) view.findViewById(R.id.plan_add_edit_description);
        exerciseList = new ArrayList<>();


        //The adapter is created using previous arrays
        mExerciseAdapter = new ExerciseArrayAdapter(getContext(), R.layout.workout_plan_list_items, exerciseList);

        //Sets the mListView adapter using the ExerciseArrayAdapter class, and appending it to list_items
        mListView.setAdapter(mExerciseAdapter);


        //Listener for adding a workout
        mAddExerciseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Initializes count variable and if it's not null, we get the count from adapter, otherwise we set it to 0.
                int currentCount = (mListView.getAdapter() != null) ? mListView.getAdapter().getCount() : 0;
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Exercise Name ");

                // Sets up input.
                final EditText input = new EditText(getContext());
                // Specify the type of input expected;
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setView(input);


                // Set up the buttons
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Set-Up
                        final Exercise newExercise = new Exercise(input.getText().toString());
                        final View view = getActivity().getLayoutInflater().inflate(R.layout.number_picker, container, false);
                        final NumberPicker npSets = (NumberPicker) view.findViewById(R.id.number_picker_sets);
                        final NumberPicker npReps = (NumberPicker) view.findViewById(R.id.number_picker_reps);

                        // Set Min values
                        npSets.setMinValue(0);
                        npReps.setMinValue(0);

                        // Set Max Values
                        npSets.setMaxValue(50);
                        npReps.setMaxValue(50);

                        new AlertDialog.Builder(getActivity())
                                .setView(view)
                                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        newExercise.setSets(npSets.getValue());
                                        newExercise.setReps(npReps.getValue());

                                        //Adding exercise to exerciseList
                                        exerciseList.add(newExercise);
                                        mExerciseAdapter.add(newExercise);
                                        mExerciseAdapter.notifyDataSetChanged();

                                        int tempCount= mExerciseAdapter.getCount();
                                        for(int i = 0; i < tempCount; i++){
                                            Exercise ex = mExerciseAdapter.getItem(i);
                                            Log.d(TAG, "onClick: Name - " + ex.getName());
                                            Log.d(TAG, "onClick: Reps - " + ex.getReps());
                                            Log.d(TAG, "onClick: Sets - " + ex.getSets());
                                        }
                                    }
                                })
                                .setCancelable(false)
                                .create()
                                .show();
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


        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // mExerciseAdapter and exerciseList should be the same number.
//                Log.d(TAG, "onClick: " + mExerciseAdapter.getCount());
//                Log.d(TAG, "onClick: " + exerciseList.size());

                WorkoutPlan workoutPlan = new WorkoutPlan(mNameText.getText().toString());
                for(int i = 0; i < exerciseList.size(); i++){
                    Log.d(TAG, "onClick: " + exerciseList.get(i).getName()
                            + " : " + exerciseList.get(i).getSets()
                            + "/" + exerciseList.get(i).getReps());
                }
                if(!mDescriptionText.equals(null)){
                    workoutPlan.setDescription(mDescriptionText.getText().toString());
                }
                workoutPlan.setExercises(exerciseList);
                tempWorkoutPlans.add(workoutPlan);
                for(WorkoutPlan plan : tempWorkoutPlans){
                    Log.d(TAG, "onClick: Workout Plan " + plan.getName());
                    for(Exercise exercise : plan.getExercises()){
                        Log.d(TAG, "onClick: " + exercise.getName());
                    }

                }


                getActivity().finish();

            }
        });

        return view;

    }



}


