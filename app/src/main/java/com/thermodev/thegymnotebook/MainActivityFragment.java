package com.thermodev.thegymnotebook;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.security.InvalidParameterException;
import java.util.ArrayList;

import static com.thermodev.thegymnotebook.AddEditWorkoutFragment.LOADER_ID;

/**
 * Created by user on 26-Jul-17.
 */

// Extending fragment, as this is a fragement.
// Implementing LoaderMager.LoaderCallbacks<Cursor> to allow the Cursor to modify the View, which will work with the WorkoutRecyclerViewAdapter.
public class MainActivityFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{
    private static final String TAG = "MainActivityFragment";
    public static ArrayList<Workout> workoutList = new ArrayList<>();
    WorkoutRecyclerViewAdapter mAdapter;
    RecyclerView mWorkoutRecyclerView;

    // Initiates the Loader
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onActivityCreated - Starts");
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(LOADER_ID, null, this);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView - Starts");
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        mWorkoutRecyclerView = (RecyclerView) view.findViewById(R.id.workout_recyclerview);

        mWorkoutRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mAdapter = new WorkoutRecyclerViewAdapter(null, (WorkoutRecyclerViewAdapter.OnWorkoutClickListener) getActivity());

        mWorkoutRecyclerView.setAdapter(mAdapter);

        return view;
    }


    @Override
    public void onResume() {
        super.onResume();

        mAdapter.notifyDataSetChanged();
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle bundle) {
        Log.d(TAG, "onCreateLoader - Starts with ID: " + id);
        String[] projection = {WorkoutsContract.Columns._ID, WorkoutsContract.Columns.WORKOUT_NAME,
                WorkoutsContract.Columns.WORKOUT_DESCRIPTION, WorkoutsContract.Columns.START_DATE,
                WorkoutsContract.Columns.WORKOUT_EXERCISES};

        switch (id){
            case LOADER_ID:
                return new CursorLoader(getActivity(),
                        WorkoutsContract.CONTENT_URI,
                        projection,
                        null,
                        null,
                        null
                );
            default:
                throw new InvalidParameterException(TAG + ". onCreateLoader called with invalid ID: " + id);
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        Log.d(TAG, "onLoadFinished - Entering");
        mAdapter.swapCursor(cursor);
        int count = mAdapter.getItemCount();

        Log.d(TAG, "onLoadFinished - Count is: " + count);

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        Log.d(TAG, "onLoaderReset - Starts");
        mAdapter.swapCursor(null);
    }

}
