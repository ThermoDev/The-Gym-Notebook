package com.thermodev.thegymnotebook;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

/**
 * Created by user on 26-Jul-17.
 */

public class AddEditPlanActivity extends AppCompatActivity implements AddEditPlanFragment.OnSaveClicked{
    private static final String TAG = "AddEditPlanActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.workout_plan_activity_add_edit);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        AddEditPlanFragment fragment = new AddEditPlanFragment();

        Bundle arguments = getIntent().getExtras();
        fragment.setArguments(arguments);

//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Log.d(TAG, "onItemClick: help!");
//            }
//        });
//        Log.d(TAG, "onCreate: Wha");
//

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment, fragment).commit();
    }

    @Override
    public void onSaveClicked() {
        finish();
    }
}
