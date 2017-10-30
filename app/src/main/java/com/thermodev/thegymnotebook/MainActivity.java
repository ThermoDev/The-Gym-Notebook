package com.thermodev.thegymnotebook;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity  implements WorkoutRecyclerViewAdapter.OnWorkoutClickListener,
        AppDialog.DialogEvents {
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
                workoutEditRequest(null);
                break;
            case R.id.action_show_about:
                Intent aboutIntent = new Intent(this, AboutActivity.class);
                startActivity(aboutIntent);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void planAddEditRequest(WorkoutPlan workoutPlan) {
        Log.d(TAG, "workoutEditRequest - Starts");
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

    private void workoutEditRequest(Workout workout) {
        Log.d(TAG, "workoutEditRequest - Starts");
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
        workoutEditRequest(workout);
    }

    @Override
    public void onDeleteClick(Workout workout) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(workout.getCalendar());

        String day = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.ENGLISH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        String month = calendar.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.ENGLISH);
        int year = calendar.get(Calendar.YEAR);
        // Creating workoutDate String, to display in dialog message.
        String workoutDate = day + " " + dayOfMonth + " - " + month + ", " + year;

        AppDialog appDialog = new AppDialog();
        Bundle args = new Bundle();

        args.putInt(AppDialog.DIALOG_ID, DIALOG_ID_DELETE);
        args.putString(AppDialog.DIALOG_MESSAGE, getString(R.string.deldialog_message, workoutDate));
        args.putInt(AppDialog.DIALOG_POSITIVE_RID, R.string.deldialog_positive_caption);

        args.putLong("WorkoutId", workout.getId());
        args.putString("ExerciseIds", workout.getExercises());
        appDialog.setArguments(args);
        appDialog.show(getFragmentManager(), null);
    }

    @Override
    public void onPositiveDialogResult(int dialogId, Bundle args) {
        Log.d(TAG, "onPositiveDialogResult - Called");
        switch (dialogId) {
            case DIALOG_ID_DELETE:
                // Deleting Exercises
                String exercisesFound = args.getString("ExerciseIds");
                // If we have found any exercises from the Cursor
                if (exercisesFound != null && !exercisesFound.equals("")) {
                    // Splits the found exercises by the "," split.
                    String[] exercisesIdSplitArray = exercisesFound.split(",");

                    for (String exerciseToParse : exercisesIdSplitArray) {
                        int exerciseID = Integer.parseInt(exerciseToParse);
                        getContentResolver().delete(ExercisesContract.buildExerciseUri(exerciseID), null, null);
                    }
                }

                // Deleting Workout
                Long workoutId = args.getLong("WorkoutId");
                if (BuildConfig.DEBUG && workoutId == 0) {
                    throw new AssertionError("Task ID is zero");
                }
                getContentResolver().delete(WorkoutsContract.buildWorkoutUri(workoutId), null, null);
                break;
            case DIALOG_ID_CANCEL_EDIT:
                // Edit is used, no action is required.
                break;
        }
    }

    @Override
    public void onNegativeDialogResult(int dialogId, Bundle args) {
        Log.d(TAG, "onNegativeDialogResult - Called");
        switch (dialogId) {
            case DIALOG_ID_DELETE:
                break;
            case DIALOG_ID_CANCEL_EDIT:
                finish();
                break;
        }
    }

    @Override
    public void onDialogCancelled(int dialogId) {
        Log.d(TAG, "onDialogCancelled - Called");
    }

}
