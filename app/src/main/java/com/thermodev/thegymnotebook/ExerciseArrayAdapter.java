package com.thermodev.thegymnotebook;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;

import java.util.ArrayList;

/**
 * Created by Thermodev on 27-Jul-17.
 */

public class ExerciseArrayAdapter extends ArrayAdapter<String> {
    private Button button;
    private Context context;
    private int layoutResourceId;


    public ExerciseArrayAdapter(@NonNull Context context, @LayoutRes int resource, @IdRes int textViewResourceId, @NonNull ArrayList<java.lang.String> objects) {
        super(context, resource, textViewResourceId, objects);
        this.context = context;
        this.layoutResourceId = resource;
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable final View convertView, @NonNull final ViewGroup parent) {
        View row = convertView;

        if(row == null){
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            row.setTag(button);
        }
        Button button = (Button) row.findViewById(R.id.plan_list_delete_button);
        if(button != null) {
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("", "onClick: Was Clicked: " + position);
                    remove(getItem(position));
                    Log.d("", "onClick: Remaining Activities: " + AddEditPlanActivityFragment.myArrayString.size());
                    notifyDataSetChanged();
                }
            });
        }
        notifyDataSetChanged();
        return row;
    }
}
