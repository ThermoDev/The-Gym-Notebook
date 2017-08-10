package com.thermodev.thegymnotebook;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by Thermodev on 27-Jul-17.
 */

public class ExerciseSetArrayAdapter extends ArrayAdapter<Exercise> {
    private Button button;
    private Context context;
    private int layoutResourceId;
    private final LayoutInflater layoutInflater;
    private List<Exercise> exerciseList;


    public ExerciseSetArrayAdapter(Context context, int resource,List<Exercise> exerciseList) {
        super(context, resource);
        this.context = context;
        this.layoutResourceId = resource;
        this.layoutInflater = LayoutInflater.from(context);
        this.exerciseList = exerciseList;
    }


    @Override
    public int getCount() {
        return super.getCount();
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable final View convertView, @NonNull final ViewGroup parent) {
        View row = convertView;
        final ViewHolder viewHolder;
        if(row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            viewHolder = new ViewHolder(row);
            row.setTag(viewHolder);
            Exercise exercise = new Exercise(getItem(position).getName());
            exerciseList.add(exercise);
            Log.d(TAG, "getView - getItem: " + getItem(position));
        }else{
            viewHolder = (ViewHolder) row.getTag();
        }

        TextView exerciseName = (TextView) row.findViewById(R.id.plan_list_add_edit_exercise);
        exerciseName.setText(getItem(position).getName());


        Exercise currentExercise = exerciseList.get(position);
        if(currentExercise.getReps() != 0){
            String string = currentExercise.getReps() + "";
            viewHolder.tvReps.setText(string);
        }
        if(currentExercise.getSets() != 0){
            String string = currentExercise.getSets() + "";
            viewHolder.tvSets.setText(string);
        }




        Button deleteButton = (Button) row.findViewById(R.id.plan_list_delete_button);
        if (deleteButton != null) {
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setTitle("Are you sure you want to delete exercise: " + getItem(position) + "?");

                    // Set up the buttons
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //Remove from Adapter
                            remove(getItem(position));

                            //Remove from list
                            exerciseList.remove(position);

                            Log.d(TAG, "onClick - Remaining ExerciseList: " + exerciseList.size());
                            notifyDataSetChanged();
                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });

                    builder.show();

                }
            });
        }
        notifyDataSetChanged();
        return row;
    }


    public Exercise getExercise(int index){
        return exerciseList.get(index);
    }

    @Nullable
    @Override
    public Exercise getItem(int position) {
        return super.getItem(position);
    }


    private class ViewHolder{
        final TextView tvName;
        final TextView tvReps;
        final TextView tvSets;

        public ViewHolder(View v) {
            this.tvName = (TextView) v.findViewById(R.id.plan_list_add_edit_exercise);
            this.tvReps = (TextView) v.findViewById(R.id.plan_list_reps_text_view);
            this.tvSets = (TextView) v.findViewById(R.id.plan_list_sets_text_view);
        }
    }
}
