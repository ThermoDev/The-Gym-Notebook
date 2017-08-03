package com.thermodev.thegymnotebook;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
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
                    for (WorkoutPlan tempPlan : tempWorkoutPlans) {
                        planList.add(tempPlan.getName());
                    }
                }
                String plans[] = planList.toArray(new String[0]);

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, plans);
                dialogListView.setAdapter(adapter);

                final Dialog myDialog = alertDialog.show();

                dialogListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Log.d(TAG, "onItemClick - Postion: " + position);
                        //TODO: Move List outside of listener scope so it can be edited
                        List<Exercise> selectedExercises = new ArrayList<Exercise>();
//                                tempWorkoutPlans.get(position).getExercises();

                        //TODO: SET-UP Adapter so it can be used outside of this listener scope as well.
                        //The adapter is created using previous arrays
                        myAdapter = new ExerciseSetArrayAdapter(getContext(), R.layout.workout_plan_list_items, R.id.plan_list_add_edit_exercise, selectedExercises);

                        //Sets the listView adapter using the ExerciseArrayAdapter class, and appending it to list_items
                        mListView.setAdapter(myAdapter);

                        for (Exercise exercise : tempWorkoutPlans.get(position).getExercises()) {
                            selectedExercises.add(exercise);
                            myAdapter.add(exercise.getName());
                        }

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
