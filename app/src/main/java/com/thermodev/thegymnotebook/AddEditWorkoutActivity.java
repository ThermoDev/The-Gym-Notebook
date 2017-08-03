package com.thermodev.thegymnotebook;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

/**
 * Created by user on 26-Jul-17.
 */

public class AddEditWorkoutActivity extends AppCompatActivity {
//    private Button addWorkoutButton;
    private ExerciseArrayAdapter mExerciseArrayAdapter;


    private static final String TAG = "AddEditWorkoutActivity";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.workout_activity_add_edit);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        AddEditWorkoutActivityFragment fragment = new AddEditWorkoutActivityFragment();
        Bundle arguments = getIntent().getExtras();
        fragment.setArguments(arguments);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment, fragment).commit();


//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Log.d(TAG, "onItemClick: help!");
//            }
//        });
        Log.d(TAG, "onCreate: Wha");
    }


}
