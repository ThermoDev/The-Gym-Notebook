package com.thermodev.thegymnotebook;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static com.thermodev.thegymnotebook.AddEditPlanFragment.tempWorkoutPlans;
import static com.thermodev.thegymnotebook.MainActivityFragment.workoutList;

/**
 * Created by Thermolink on 26-Jul-17.
 */

public class AddEditWorkoutFragment extends Fragment implements DatePickerDialog.OnDateSetListener {
    private static final String TAG = "AddEditWorkoutFragment";
    private Button mDateButton;
    private Button mAddButton;
    private Button mAddExerciseButton;
    private ListView mListView;
    private ExerciseArrayAdapter mExerciseArrayAdapter;
    List<Exercise> mExerciseList;
    private Calendar mCalendar;



    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Log.d(TAG, "onDateSet: " + year);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.workout_fragment_add_edit, container, false);
        //Enabling Options
        setHasOptionsMenu(true);

        mDateButton = (Button) view.findViewById(R.id.add_edit_date_button);
        mListView = (ListView) view.findViewById(R.id.add_edit_listview);
        mAddButton = (Button) view.findViewById(R.id.add_edit_commit_button);
        mAddExerciseButton = (Button) view.findViewById(R.id.add_edit_exercise_button);
        mExerciseList = new ArrayList<>();
        mCalendar = Calendar.getInstance();

        // Setting up Adapter
        mExerciseArrayAdapter = new ExerciseArrayAdapter(getContext(), R.layout.workout_plan_list_items, mExerciseList);
        mListView.setAdapter(mExerciseArrayAdapter);

        final WorkoutArrayAdapter myAdapter = new WorkoutArrayAdapter(getContext(), R.layout.workout_list_items, workoutList);

        mDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int year = mCalendar.get(Calendar.YEAR);
                int month = mCalendar.get(Calendar.MONTH);
                int day = mCalendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), datePickerListener, year, month, day);
                datePickerDialog.show();
                int dayOfMonth = datePickerDialog.getDatePicker().getDayOfMonth();

            }
        });
        mAddExerciseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Initializes count variable and if it's not null, we get the count from adapter, otherwise we set it to 0.
//                int currentCount = (mListView.getAdapter() != null) ? mListView.getAdapter().getCount() : 0;

                final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Exercise Name ");

                // Sets up input.
                final EditText input = new EditText(getContext());
                // Specify the type of input expected;
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setView(input);

                // Set up the buttons
                builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if (input.getText().toString().isEmpty()) {
                            new AlertDialog.Builder(getActivity())
                                    .setMessage(R.string.no_input_specified)
                                    .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                        }
                                    }).show();
                        } else {
                            // Set-Up
//                            final Exercise newExercise = new Exercise(input.getText().toString());
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

                                            Exercise newExercise = new Exercise(input.getText().toString());

                                            // Adding values to database

                                            newExercise.setSets(npSets.getValue());
                                            newExercise.setReps(npReps.getValue());

                                            //Adding exercise to exerciseList
                                            mExerciseList.add(newExercise);
                                            mExerciseArrayAdapter.add(newExercise);
                                            mExerciseArrayAdapter.notifyDataSetChanged();

                                            int tempCount = mExerciseArrayAdapter.getCount();
                                            for (int i = 0; i < tempCount; i++) {
                                                Exercise ex = mExerciseArrayAdapter.getItem(i);
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
        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Successfully added workout...", Toast.LENGTH_SHORT).show();
                Workout workout = new Workout( getId(), mCalendar);
                if(!mExerciseList.isEmpty()) {
                    workout.setExercises(mExerciseList);
                    String description = "";
                    for(Exercise ex : mExerciseList){
                        description += (ex.getName() + " : " + ex.getSets() + "/" + ex.getReps() + "\n");
                    }
                    workout.setDescription(description);
                }


                ContentResolver contentResolver = getActivity().getContentResolver();
                ContentValues values = new ContentValues();
                //Creating exerciseValues
                ContentValues exerciseValues = new ContentValues();
                String exercisesId = "";
                //Inserting into exerciseValues, each exercise
                for(Exercise ex : workout.getExercises()){
                    exerciseValues.put(ExercisesContract.Columns.EXERCISES_NAME, ex.getName() );
                    exerciseValues.put(ExercisesContract.Columns.EXERCISES_REPS, ex.getReps() );
                    exerciseValues.put(ExercisesContract.Columns.EXERCISES_SETS, ex.getSets() );
                    // Creating an exerciseUri with the returned URI from calling the contentResolver's insert() method.
                    Uri exerciseUri = contentResolver.insert(ExercisesContract.CONTENT_URI, exerciseValues);
                    exercisesId += ContentUris.parseId(exerciseUri) +",";
                    exerciseValues.clear();
                }

                Log.d(TAG, "onClick: Exercise ID: " + exercisesId);

                values.put(WorkoutsContract.Columns.START_DATE, mCalendar.getTime().toString());
                values.put(WorkoutsContract.Columns.WORKOUT_DESCRIPTION, workout.getDescription());
                values.put(WorkoutsContract.Columns.WORKOUT_EXERCISES, exercisesId);

                Uri uri = contentResolver.insert(WorkoutsContract.CONTENT_URI, values);

                ContentUris.parseId(uri);

                myAdapter.add(workout);
                workoutList.add(workout);
                getActivity().finish();
            }
        });

        return view;
    }

    // This will occur after the date has been picked.
    private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
            mDateButton.setText(selectedDay + " / " + (selectedMonth + 1) + " / "
                    + selectedYear);
            mCalendar.set(selectedYear, selectedMonth, selectedDay);
        }
    };

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_add_edit_workout, menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.menu_add_plan:
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
                LayoutInflater inflater = getLayoutInflater(getArguments());
                View convertView = (View) inflater.inflate(R.layout.exercise_list_view, null);
                alertDialog.setView(convertView);
                alertDialog.setTitle("Select From Plan");
                ListView dialogListView = (ListView) convertView.findViewById(R.id.exercise_list_view);
                ArrayList<String> planList = new ArrayList<String>();
                if (!tempWorkoutPlans.isEmpty()) {
                    for (WorkoutPlan currentPlan : tempWorkoutPlans) {
                        planList.add(currentPlan.getName());
                        Log.d(TAG, "onOptionsItemSelected - currentPlan: " + currentPlan.getName());
                    }
                }else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage(R.string.no_plans_found)
                            .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // FIRE ZE MISSILES!
                                }
                            }).show();
                    return false;
                }
                String plans[] = planList.toArray(new String[0]);

                ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, plans);
                dialogListView.setAdapter(adapter);

                final Dialog myDialog = alertDialog.show();

                dialogListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Log.d(TAG, "onItemClick - Postion: " + position);
//                        mExerciseList = tempWorkoutPlans.get(position).getExercises();
//
//                        for(Exercise e : tempWorkoutPlans.get(position).getExercises()){
//                            Log.d(TAG, "onItemClick - Workout Plan Name: " + e.getName());
//                        }
                        for (Exercise exercise : mExerciseList) {
//                            mExerciseList.add(exercise);
                            mExerciseArrayAdapter.add(exercise);
                            Log.d(TAG, "onItemClick: Name: " + exercise.getName() + " sets: " + exercise.getSets());
                        }
                        // Sets the listView adapter using the ExerciseArrayAdapter class, and appending it to list_items
                        mListView.setAdapter(mExerciseArrayAdapter);
                        mExerciseArrayAdapter.notifyDataSetChanged();
                        myDialog.dismiss();
                    }
                });
                break;

            case R.id.menu_clear:

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage(R.string.confirm_clear)
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                mExerciseArrayAdapter.clear();
                                mExerciseArrayAdapter.notifyDataSetChanged();
                                mCalendar.clear();
                                mDateButton.setText("");
                                Toast.makeText(getContext(), "Successfully Cleared...", Toast.LENGTH_SHORT).show();
                            }
                        }).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .show();
               break;

            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }


}
