package com.thermodev.thegymnotebook;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static com.thermodev.thegymnotebook.AddEditPlanActivityFragment.tempWorkoutPlans;

/**
 * Created by user on 26-Jul-17.
 */

public class AddEditWorkoutActivityFragment extends Fragment implements DatePickerDialog.OnDateSetListener {
    private static final String TAG = "AddEditWorkoutActivityF";
    private Button mDateButton;
    private Button mAddButton;
    private ListView mListView;
    private ExerciseSetArrayAdapter myAdapter;
    List<Exercise> mExerciseList;



    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Log.d(TAG, "onDateSet: " + year);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.workout_fragment_add_edit, container, false);
        //Enabling Options
        setHasOptionsMenu(true);

        mDateButton = (Button) view.findViewById(R.id.add_edit_date_button);
        mListView = (ListView) view.findViewById(R.id.add_edit_listview);
        mAddButton = (Button) view.findViewById(R.id.add_edit_commit_button);
        mExerciseList = new ArrayList<>();

        //Setting up Adapter
        myAdapter = new ExerciseSetArrayAdapter(getContext(), R.layout.workout_plan_list_items, mExerciseList);


        mDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), datePickerListener, year, month, day);
                datePickerDialog.show();
                int dayOfMonth = datePickerDialog.getDatePicker().getDayOfMonth();
            }
        });
        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Successfully Added Entry!", Toast.LENGTH_SHORT).show();
            }
        });


        return view;
    }

    // This will occur after the date has been picked.
    private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
//            mDateTextView.setText(selectedDay + " / " + (selectedMonth + 1) + " / "
//                    + selectedYear);
            mDateButton.setText(selectedDay + " / " + (selectedMonth + 1) + " / "
                    + selectedYear);
        }
    };

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_add_edit_workout, menu);
        return;

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
                        Log.d(TAG, "onOptionsItemSelected: wow " + currentPlan.getName());
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
                        mExerciseList = tempWorkoutPlans.get(position).getExercises();

                        for(Exercise e : tempWorkoutPlans.get(position).getExercises()){
                            Log.d(TAG, "onItemClick - Workout Plan Name: " + e.getName());
                        }

                        /* TODO: Remove logging */
                        /* Log.d(TAG, "onItemClick: =========================");
                        if (!tempWorkoutPlans.isEmpty()) {
                            for (WorkoutPlan currentPlan : tempWorkoutPlans) {
                                Log.d(TAG, "onOptionsItemSelected: wow " + currentPlan.getName());
                                for(Exercise ex : currentPlan.getExercises()) {
                                    Log.d(TAG, "onItemClick: name: " + ex.getName());
                                    Log.d(TAG, "onItemClick:  sets/reps" +ex.getSets() + "/" +ex.getSets());
                                }
                                Log.d(TAG, "onOptionsItemSelected: " + currentPlan.getExercises());
                            }
                        }
                        Log.d(TAG, "onItemClick: ========================="); */

                        for (Exercise exercise : mExerciseList) {
//                            mExerciseList.add(exercise);
                            myAdapter.add(exercise);
                            Log.d(TAG, "onItemClick: Name: " + exercise.getName() + " sets: " + exercise.getSets());
                        }

                        // Sets the listView adapter using the ExerciseArrayAdapter class, and appending it to list_items
                        mListView.setAdapter(myAdapter);
                        myAdapter.notifyDataSetChanged();
                        myDialog.dismiss();
                    }
                });


                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }


}
