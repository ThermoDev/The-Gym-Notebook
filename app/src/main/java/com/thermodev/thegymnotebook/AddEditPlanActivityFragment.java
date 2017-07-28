package com.thermodev.thegymnotebook;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by user on 26-Jul-17.
 */

public class AddEditPlanActivityFragment extends Fragment implements DatePickerDialog.OnDateSetListener {
    private static final String TAG = "AddEditPlanActivityFrag";
    //Initalizing variables
    private Button addWorkoutButton;
    private Button saveButton;
    private ListView listView;
    static ArrayList<String> myArrayString;
    private ExerciseArrayAdapter myAdapter;

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
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //Creates the view
        View view = inflater.inflate(R.layout.workout_plan_fragment_add_edit, container, false);

        //Links the objects to the related layout components
        addWorkoutButton = (Button) view.findViewById(R.id.plan_add_edit_exercise);
        saveButton = (Button) view.findViewById(R.id.plan_add_edit_save);
        listView = (ListView) view.findViewById(R.id.plan_add_edit_list_view);
        myArrayString  = new ArrayList<String>();

        //Listener for adding a workout
        addWorkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: Wow :)");
                //Initializes count variable and if it's not null, we get the count from adapter, otherwise we set it to 0
                int currentCount = (listView.getAdapter() != null) ? listView.getAdapter().getCount() : 0;

                myArrayString.add("Sets: ");

                //Turns the array into the ArrayString
                String[] myArray = myArrayString.toArray(new String[0]);
                //Builds the String array list using the created array
                myArrayString = new ArrayList<String>(Arrays.asList(myArray));
                //The adapter is created using previous arrays
                myAdapter = new ExerciseArrayAdapter(getContext(), R.layout.workout_plan_list_items, R.id.plan_list_sets, myArrayString);

                //Sets the listView adapter using the ExerciseArrayAdapter class, and appending it to list_items
                listView.setAdapter(myAdapter);
                Log.d(TAG, "onClick: " + listView.getAdapter().getCount());
            }
        });

        return view;

    }

}


