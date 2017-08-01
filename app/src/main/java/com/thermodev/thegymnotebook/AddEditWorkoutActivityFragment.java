package com.thermodev.thegymnotebook;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
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

import static com.thermodev.thegymnotebook.AddEditPlanActivityFragment.tempWorkoutPlans;

/**
 * Created by user on 26-Jul-17.
 */

public class AddEditWorkoutActivityFragment extends Fragment implements DatePickerDialog.OnDateSetListener {
    private static final String TAG = "AddEditWorkoutActivityF";
    private Button mDateButton;
    private Button mAddButton;
    private Button mAddPlanButton;
    private Button mSaveButton;
    private ListView listView;
    private ExerciseArrayAdapter myAdapter;


    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Log.d(TAG, "onDateSet: " + year);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.workout_fragment_add_edit, container, false);

        mDateButton = (Button) view.findViewById(R.id.add_edit_date_button);
        listView = (ListView) view.findViewById(R.id.add_edit_listview);
        mAddButton = (Button) view.findViewById(R.id.add_edit_commit_button);
        mAddPlanButton = (Button) view.findViewById(R.id.add_edit_workout_plan);

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
        mAddPlanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
                LayoutInflater inflater = getLayoutInflater(getArguments());
                View convertView = (View) inflater.inflate(R.layout.exercise_list_view, null);
                alertDialog.setView(convertView);
                alertDialog.setTitle("Select From Plan");
                ListView lv = (ListView) convertView.findViewById(R.id.exercise_list_view);
                String names[] = {"wow", "steve"};
                ArrayList<String> planList = new ArrayList<String>();
                if(!tempWorkoutPlans.isEmpty()) {
                    for (WorkoutPlan tempPlan : tempWorkoutPlans) {
                        planList.add(tempPlan.getName());
                        Log.d(TAG, "onClick - " + tempPlan.getName());
                    }
                }
                String plans[] = planList.toArray(new String[0]);

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, plans);
                lv.setAdapter(adapter);

                final Dialog myDialog = alertDialog.show();

                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Log.d(TAG, "onItemClick - Postion: " + position);
                        myDialog.dismiss();
                    }
                });


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
}
