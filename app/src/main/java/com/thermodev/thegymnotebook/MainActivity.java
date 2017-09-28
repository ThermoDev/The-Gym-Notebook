package com.thermodev.thegymnotebook;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity  implements WorkoutArrayAdapter.OnWorkoutClickListener {
    private static final String TAG = "MainActivity";
    public static final int DIALOG_ID_DELETE = 1;
    public static final int DIALOG_ID_CANCEL_EDIT = 2;

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
                workoutAddEditRequest(null);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void planAddEditRequest(WorkoutPlan workoutPlan) {
        Log.d(TAG, "workoutAddEditRequest - Starts");
        {

            // Starts the detail activity for the selected Item
            Intent detailIntent = new Intent(this, AddEditPlanActivity.class);
            if (workoutPlan != null) {
                detailIntent.putExtra(WorkoutPlan.class.getSimpleName(), workoutPlan);
                startActivity(detailIntent);
            } else {
                startActivity(detailIntent);
            }

        }
    }

    private void workoutAddEditRequest(Workout workout) {
        Log.d(TAG, "workoutAddEditRequest - Starts");
        {
            Intent detailIntent = new Intent(this, AddEditWorkoutActivity.class);
            if (workout != null) {
                detailIntent.putExtra(Workout.class.getSimpleName(), workout);
                startActivity(detailIntent);
            }else{
                startActivity(detailIntent);
            }
        }
    }


    @Override
    public void onEditClick(Workout workout) {
        workoutAddEditRequest(workout);
    }

    @Override
    public void onDeleteClick(final Workout workout) {
        AppDialog appDialog = new AppDialog();
        Bundle args = new Bundle();

        args.putInt(AppDialog.DIALOG_ID, DIALOG_ID_DELETE);
        args.putString(AppDialog.DIALOG_MESSAGE, getString(R.string.deldialog_message, workout.getId(), workout.getCalendar()));
        args.putInt(AppDialog.DIALOG_POSITIVE_RID, R.string.deldialog_positive_caption);

        args.putLong("WorkoutId", workout.getId());

        appDialog.setArguments(args);
        appDialog.show(getFragmentManager(), null);
    }
}
