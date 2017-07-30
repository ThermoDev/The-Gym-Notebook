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

//                myArrayString.add("Sets: ");

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Exercise Name: ");

                // Set up the input
                final EditText input = new EditText(getContext());
                // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setView(input);

                // Set up the buttons
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // If we currently have a usable adapter
                        if(myAdapter != null){
                            myAdapter.add(input.getText().toString());
                        }else { // Else, Create a new adapter
                            //Adds the input to the array
                            myArrayString.add(input.getText().toString());

                            //Turns the array into the ArrayList
                            String[] myArray = myArrayString.toArray(new String[0]);

                            //Builds the String array list using the created array
                            myArrayString = new ArrayList<>(Arrays.asList(myArray));

                            //The adapter is created using previous arrays
                            myAdapter = new ExerciseArrayAdapter(getContext(), R.layout.workout_plan_list_items, R.id.plan_list_add_edit_exercise, myArrayString);
                            //Sets the listView adapter using the ExerciseArrayAdapter class, and appending it to list_items
                            listView.setAdapter(myAdapter);
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

//                Log.d(TAG, "onClick: " + listView.getAdapter().getCount());
            }
        });


        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int i = 0; i < myAdapter.getCount(); i++){
//                    Log.d(TAG, "onClick: " + myAdapter.getItem(i));
                    Log.d(TAG, "onClick: " +myAdapter.getExerciseList().get(i).getName()
                            + " : " + myAdapter.getExerciseList().get(i).getSets()
                            + "/" + myAdapter.getExerciseList().get(i).getReps());
                }
            }
        });

        return view;

    }

}


