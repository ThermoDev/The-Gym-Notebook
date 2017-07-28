package com.thermodev.thegymnotebook;

import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;


/**
 * Created by user on 3/19/2017.
 */

class CursorRecyclerViewAdapter extends RecyclerView.Adapter<CursorRecyclerViewAdapter.TaskViewHolder> {
    private static final String TAG = "CursorRecyclerViewAdapt";
    private Cursor mCursor;
    private OnTaskClickListener mListener;

    interface OnTaskClickListener{
//        void onEditClick(Task task);
//        void onDeleteClick(Task task);
    }

    public CursorRecyclerViewAdapter(Cursor cursor, OnTaskClickListener listener) {
        Log.d(TAG, "CursorRecyclerViewAdapter - Constructor Called");
        mCursor = cursor;
        mListener = listener;
    }

    @Override
    public TaskViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.workout_plan_list_items, viewGroup, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final TaskViewHolder taskViewHolder, int i) {
        if((mCursor == null) || (mCursor.getCount() == 0)){
            Log.d(TAG, "onBindViewHolder - Providing Instructions");
//            taskViewHolder.name.setText(R.string.instructions_heading);
//            taskViewHolder.description.setText(R.string.instructions);
            taskViewHolder.editButton.setVisibility(View.GONE);
            taskViewHolder.deleteButton.setVisibility(View.GONE);
        }else{

            if(!mCursor.moveToPosition(i)){
                throw new IllegalStateException("Couldn't move cursor to Position: " + i);
            }

//            final Task task = new Task(mCursor.getLong(mCursor.getColumnIndex(TasksContract.Columns._ID)),
//                    mCursor.getString(mCursor.getColumnIndex(TasksContract.Columns.TASKS_NAME)),
//                    mCursor.getString(mCursor.getColumnIndex(TasksContract.Columns.TASKS_DESCRIPTION)),
//                    mCursor.getInt(mCursor.getColumnIndex(TasksContract.Columns.TASKS_SORTORDER)));

//            taskViewHolder.name.setText(task.getName());
//            taskViewHolder.description.setText(task.getDescription());

            taskViewHolder.editButton.setVisibility(View.VISIBLE);
            taskViewHolder.deleteButton.setVisibility(View.VISIBLE);

            View.OnClickListener buttonListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch( v.getId() ) {
//                        case R.id.tli_edit:
//                            if(mListener != null){
//                                mListener.onEditClick(task);
//                            }
//                            break;
//                        case R.id.tli_delete:
//                            if(mListener != null) {
//                                mListener.onDeleteClick(task);
//                            }
//                            break;
                        default:
                            Log.d(TAG, "onClick - Default Button?");
                            break;
                    }
                }
            };


            taskViewHolder.editButton.setOnClickListener(buttonListener);
            taskViewHolder.deleteButton.setOnClickListener(buttonListener);
        }
    }

    @Override
    public int getItemCount() {
        if( (mCursor == null) || (mCursor.getCount() == 0) ){
            return 1; // Populate a single cursor with instructions.
        }else{
            return mCursor.getCount();
        }
    }

    /**
     * Swap in New Cursor, returning the new cursor
     * The Returned old cursor is <em>not</em> closed.
     *
     *
     * @param newCursor The new Cursor to be used.
     * @return Returns the previously set cursor, or null if there wasn't one.
     * If the given new cursor is the same instance as the previously set Cursor,
     * null is also returned.
     */
    Cursor swapCursor(Cursor newCursor){
        if(newCursor == mCursor){
            return null;
        }

        final Cursor oldCursor = mCursor;
        mCursor = newCursor;
        if(newCursor != null){
            // Notify Observers of new cursor
            notifyDataSetChanged();
        }else{
            // Notify Observers of lack of cursor
            notifyItemRangeRemoved(0, getItemCount());
        }
        return oldCursor;
    }

    static class TaskViewHolder extends RecyclerView.ViewHolder {
        private static final String TAG = "TaskViewHolder";

        TextView name = null;
        TextView description = null;
        ImageButton editButton = null;
        ImageButton deleteButton = null;

        public TaskViewHolder(View itemView) {
            super(itemView);

            this.name = (TextView) itemView.findViewById(R.id.tli_name);
            this.description = (TextView) itemView.findViewById(R.id.tli_description);
//            this.editButton = (ImageButton) itemView.findViewById(R.id.tli_edit);
//            this.deleteButton = (ImageButton) itemView.findViewById(R.id.tli_delete);
        }
    }

}
