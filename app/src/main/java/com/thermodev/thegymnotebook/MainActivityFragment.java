package com.thermodev.thegymnotebook;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by user on 26-Jul-17.
 */

public class MainActivityFragment extends Fragment {
    private static final String TAG = "MainActivityFragment";
    public static ArrayList<Workout> workoutList = new ArrayList<>();
    WorkoutArrayAdapter mAdapter;
    ListView mWorkoutListView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView - Starts");
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        mWorkoutListView = (ListView) view.findViewById(R.id.workout_list_view);
        mAdapter = new WorkoutArrayAdapter(getContext(), R.layout.workout_list_items, workoutList);

        mWorkoutListView.setAdapter(mAdapter);

        TextView tvWorkout = (TextView) view.findViewById(R.id.current_workout);
        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        mAdapter.clear();
        if(!workoutList.isEmpty()) {
            for (Workout w : workoutList) {
                mAdapter.add(w);
            }
        }

        mAdapter.notifyDataSetChanged();
    }
}
