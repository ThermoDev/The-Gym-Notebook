package com.thermodev.thegymnotebook;

import android.app.ListActivity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

/**
 * Created by user on 26-Jul-17.
 */

public class AddEditWorkoutActivity extends ListActivity {
    ListView lv = (ListView) findViewById(R.id.plan_add_edit_list_view);
    //initialize adapter


    private Button addWorkoutButton;

    private static final String TAG = "AddEditWorkoutActivity";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "onItemClick: help!");
            }
        });
        Log.d(TAG, "onCreate: Wha");
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Log.d(TAG, "onListItemClick: Clickay?ww");
    }
}
