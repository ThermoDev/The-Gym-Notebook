package com.thermodev.thegymnotebook;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.action_add_workout_plan:
                planAddEditRequest(null);
                break;
            case R.id.action_add_workout:
                workoutAddEditRequest();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void planAddEditRequest(Workout workout) {
        Log.d(TAG, "workoutAddEditRequest - Starts");
        {
            // Starts the detail activity for the selected Item
            Intent detailIntent = new Intent(this, AddEditPlanActivity.class);
            if (workout != null) {
                detailIntent.putExtra(Workout.class.getSimpleName(), workout);
                startActivity(detailIntent);
            } else {
                startActivity(detailIntent);
            }

        }
    }

    private void workoutAddEditRequest() {
        Log.d(TAG, "workoutAddEditRequest - Starts");
        {
            // Starts the detail activity for the selected Item
            Intent detailIntent = new Intent(this, AddEditWorkoutActivity.class);
            startActivity(detailIntent);
        }
    }


}
