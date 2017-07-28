package com.thermodev.thegymnotebook;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;

/**
 * Created by user on 26-Jul-17.
 */

public class AddEditPlanActivity extends AppCompatActivity implements AddEditPlanActivityFragment.OnSaveClicked{
    private static final String TAG = "AddEditPlanActivity";

    ListView lv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.workout_plan_activity_add_edit);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        AddEditPlanActivityFragment fragment = new AddEditPlanActivityFragment();

        Bundle arguments = getIntent().getExtras();
//        arguments.putSerializable(Task.class.getSimpleName(), task);
        fragment.setArguments(arguments);

        lv = (ListView) findViewById(R.id.plan_add_edit_list_view);
//
//        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Log.d(TAG, "onItemClick: help!");
//            }
//        });
//        Log.d(TAG, "onCreate: Wha");
//

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment, fragment); // Use replace instead of Add. Doesn't require any text to replace it.
        fragmentTransaction.commit();

    }

    @Override
    public void onSaveClicked() {
        finish();
    }
}
