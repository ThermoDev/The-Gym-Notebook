package com.thermodev.thegymnotebook;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
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
    private Button mAddEditButton;
    private Button mAddExerciseButton;
    private ListView mListView;
    private ExerciseArrayAdapter mExerciseArrayAdapter;
    List<Exercise> mExerciseList;
    private Calendar mCalendar;

    private Workout argumentWorkout;

    public enum FragmentMode {EDIT, ADD}

    private FragmentMode mFragmentMode;

    public static final int LOADER_ID = 0;

    interface OnSaveClicked {
        void onSaveClicked();
    }

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
        mAddEditButton = (Button) view.findViewById(R.id.add_edit_commit_button);
        mAddExerciseButton = (Button) view.findViewById(R.id.add_edit_exercise_button);
        mExerciseList = new ArrayList<>();
        mCalendar = Calendar.getInstance();

        // Setting up Adapter
        mExerciseArrayAdapter = new ExerciseArrayAdapter(getContext(), R.layout.workout_plan_list_items, mExerciseList);

        mListView.setAdapter(mExerciseArrayAdapter);

        Bundle arguments = getArguments();

        // If arguments were parsed to this fragment
        if (arguments != null) {
            Log.d(TAG, "onCreateView: Found arguments for Workout");
            // Create workout from the parsed arguments.
            argumentWorkout = (Workout) arguments.getSerializable(Workout.class.getSimpleName());
            if (argumentWorkout != null) {
                Log.d(TAG, "onCreateView: Workout was found. Editing the data.");
                mFragmentMode = FragmentMode.EDIT;
                mAddEditButton.setText(R.string.update_workout_button);

                // -Adding date-
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(argumentWorkout.getCalendar());

                int workoutDay = calendar.get(Calendar.DAY_OF_MONTH);
                int workoutMonth = calendar.get(Calendar.MONTH);
                int workoutYear = calendar.get(Calendar.YEAR);
                mDateButton.setText(workoutDay + " / " + (workoutMonth + 1) + " / " + workoutYear);
                mCalendar.set(workoutYear, workoutMonth, workoutDay);


                // -Adding Exercise-
                String exercises = argumentWorkout.getExercises();
                // If there were any exercises found.
                if (exercises != null) {
                    Log.d(TAG, "onCreateView: Exercises were found");
                    Log.d(TAG, "onCreateView: " + exercises);
                    String[] projection = {ExercisesContract.Columns._ID, ExercisesContract.Columns.EXERCISES_NAME,
                        ExercisesContract.Columns.EXERCISES_REPS, ExercisesContract.Columns.EXERCISES_SETS};

                    AppProvider appProvider = new AppProvider();

                    // -- Setting up the WHERE clause for CursorLoader, splitting id's already provided by "," in the db --

                    // Splits the found exercises by the "," split.
                    String[] exercisesIdSplitArray = exercises.split(",");

                    // Initialize empty string in exercisesToFind
                    String exercisesToFind = "";

                    // If the found split exercises ID is greater than one, meaning there is more than one item in the array:
                    if (exercisesIdSplitArray.length > 1) {

                        // If the split array of the ID
                        exercisesToFind = ExercisesContract.Columns._ID + " IN (" + exercises + ")";
                        Log.d(TAG, "onCreateView: ExerciseToFind " + exercisesToFind);

                    } else {
                        if (!exercises.isEmpty()) {
                            exercisesToFind = ExercisesContract.Columns._ID + " = " + exercises.replace(",", "");
                            Log.d(TAG, "onCreateView: ExerciseToFind " + exercisesToFind);
                        }
                    }
                    if(!exercisesToFind.equals("")){
                        // Creating a cursor loader
                        Cursor exerciseCursor = appProvider.query(ExercisesContract.CONTENT_URI, projection, exercisesToFind, null, null);

                        // If there is a currently usable exerciseCursor
                        if (exerciseCursor != null) {
                            // Checks if any Exercises were found
                            if (exerciseCursor.getCount() != 0) {
                                // Loops through all of the exercises found, then adds it to the exercise list.
                                while (exerciseCursor.moveToNext()) {
                                    Exercise exerciseToAdd = new Exercise(exerciseCursor.getString(exerciseCursor.getColumnIndex(ExercisesContract.Columns.EXERCISES_NAME)));
                                    exerciseToAdd.setSets(Integer.parseInt(exerciseCursor.getString(exerciseCursor.getColumnIndex(ExercisesContract.Columns.EXERCISES_SETS))));
                                    exerciseToAdd.setReps(Integer.parseInt(exerciseCursor.getString(exerciseCursor.getColumnIndex(ExercisesContract.Columns.EXERCISES_REPS))));
                                    mExerciseList.add(exerciseToAdd);
                                    mExerciseArrayAdapter.add(exerciseToAdd);
                                }
                                mExerciseArrayAdapter.notifyDataSetChanged();
                            }
                            Log.d(TAG, "onCreateView: Workout ID " + argumentWorkout.getId());
                            exerciseCursor.close();
                        } else {
                            Log.d(TAG, "onBindViewHolder: Exercise Cursor returned null");
                        }
                    }
                }
            }
        } else {
            Log.d(TAG, "onCreateView: No Arguments, adding new record.");
            // No Workout parsed, will be adding
            mFragmentMode = FragmentMode.ADD;
        }




        mDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int year = mCalendar.get(Calendar.YEAR);
                int month = mCalendar.get(Calendar.MONTH);
                int day = mCalendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), datePickerListener, year, month, day);
                datePickerDialog.show();
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

                                            // Adding values to newExercise object.
                                            newExercise.setSets(npSets.getValue());
                                            newExercise.setReps(npReps.getValue());

                                            //Adding exercise to exerciseList
                                            mExerciseList.add(newExercise);
                                            mExerciseArrayAdapter.add(newExercise);
                                            mExerciseArrayAdapter.notifyDataSetChanged();

                                            for (int i = 0; i < mExerciseArrayAdapter.getCount(); i++) {
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
        mAddEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Workout workout = new Workout(getId(), mCalendar.getTimeInMillis());
                if (!mExerciseList.isEmpty()) {
                    String description = "";
                    for (Exercise ex : mExerciseList) {
                        description += (ex.getName() + " : " + ex.getSets() + "/" + ex.getReps() + "\n");
                    }
                    workout.setDescription(description);
                }


                ContentResolver contentResolver = getActivity().getContentResolver();
                ContentValues values = new ContentValues();
                //Creating exerciseValues
                ContentValues exerciseValues = new ContentValues();
                String exercisesId = "";

                switch (mFragmentMode){
                    case ADD:
                        //Inserting into exerciseValues, each exercise
                        for (int i = 0; i < mExerciseList.size(); i++) {
                            exerciseValues.put(ExercisesContract.Columns.EXERCISES_NAME, mExerciseList.get(i).getName());
                            exerciseValues.put(ExercisesContract.Columns.EXERCISES_REPS, mExerciseList.get(i).getReps());
                            exerciseValues.put(ExercisesContract.Columns.EXERCISES_SETS, mExerciseList.get(i).getSets());
                            // Creating an exerciseUri with the returned URI from calling the contentResolver's insert() method.
                            Uri exerciseUri = contentResolver.insert(ExercisesContract.CONTENT_URI, exerciseValues);
                            exercisesId += ContentUris.parseId(exerciseUri);
                            // Adds commas if it is not the last item in the list
                            if (mExerciseList.size() - 1 != i) {
                                exercisesId += ",";
                            }
                            exerciseValues.clear();
                        }


                        Log.d(TAG, "onClick: Exercise ID: " + exercisesId);

                        values.put(WorkoutsContract.Columns.START_DATE, mCalendar.getTimeInMillis());
                        values.put(WorkoutsContract.Columns.WORKOUT_DESCRIPTION, workout.getDescription());
                        values.put(WorkoutsContract.Columns.WORKOUT_EXERCISES, exercisesId);

                        Uri uri = contentResolver.insert(WorkoutsContract.CONTENT_URI, values);

                        ContentUris.parseId(uri);
                        workoutList.add(workout);
                        Toast.makeText(getContext(), "Successfully added workout...", Toast.LENGTH_SHORT).show();
                        getActivity().finish();
                        break;
                    case EDIT:

                        // Deleting current exercises
                        String exercisesFound = argumentWorkout.getExercises();
                        // If we have found any exercises from the Cursor
                        if (exercisesFound != null && !exercisesFound.equals("")) {
                            Log.d(TAG, "onClick: Found Exercises");
                            // Splits the found exercises by the "," split.
                            String[] exercisesIdSplitArray = exercisesFound.split(",");

                            for (String exerciseToParse : exercisesIdSplitArray) {
                                int exerciseID = Integer.parseInt(exerciseToParse);
                                Log.d(TAG, "onClick: Exercise ID: " + exerciseID);
                                contentResolver.delete(ExercisesContract.buildExerciseUri(exerciseID), null, null);
                            }
                        }


                        for (int i = 0; i < mExerciseList.size(); i++) {
                            exerciseValues.put(ExercisesContract.Columns.EXERCISES_NAME, mExerciseList.get(i).getName());
                            exerciseValues.put(ExercisesContract.Columns.EXERCISES_REPS, mExerciseList.get(i).getReps());
                            exerciseValues.put(ExercisesContract.Columns.EXERCISES_SETS, mExerciseList.get(i).getSets());

                            Uri exerciseUri = contentResolver.insert(ExercisesContract.CONTENT_URI, exerciseValues);
                            exercisesId += ContentUris.parseId(exerciseUri);
                            // Adds commas if it is not the last item in the list
                            if (mExerciseList.size() - 1 != i) {
                                exercisesId += ",";
                            }
                            exerciseValues.clear();

                        }

                        values.put(WorkoutsContract.Columns.START_DATE, mCalendar.getTimeInMillis());
                        values.put(WorkoutsContract.Columns.WORKOUT_DESCRIPTION, workout.getDescription());
                        values.put(WorkoutsContract.Columns.WORKOUT_EXERCISES, exercisesId);


                        // Creating an exerciseUri with the returned URI from calling the contentResolver's insert() method.
                        if(argumentWorkout != null) {
                            Log.d(TAG, "onClick: Workout ID:" + argumentWorkout.getId());
                            int id = contentResolver.update(WorkoutsContract.buildWorkoutUri(argumentWorkout.getId()), values, null, null);
                        }
                        Toast.makeText(getContext(), "Successfully updated workout...", Toast.LENGTH_SHORT).show();
                        getActivity().finish();
                        values.clear();
                }


            }
        });

        return view;
    }

    // This will occur after the date has been picked.
    private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
            mDateButton.setText(selectedDay + " / " + (selectedMonth + 1) + " / " + selectedYear);
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
                } else {
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
