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
       switch(id) {
           case R.id.action_add_workout:
               workoutEditRequest(null);
       }

        return super.onOptionsItemSelected(item);
    }

    private void workoutEditRequest(Workout workout) {
        Log.d(TAG, "taskEditRequest - Starts");
        {
            Log.d(TAG, "taskEditRequest: In Single-Pane Mode (PHONE) ");
            // In Single-Pane mode, starts the detail activity for the selected Item Id.sqlite
            Intent detailIntent = new Intent(this, AddEditPlanActivity.class);
            if (workout != null) {
                detailIntent.putExtra(Workout.class.getSimpleName(), workout);
                startActivity(detailIntent);
            } else {
                startActivity(detailIntent);
            }

        }
    }

}
