package com.thermodev.thegymnotebook;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by user on 29-Mar-17.
 */

public class AppDialog extends DialogFragment {
    public static final String TAG = "AppDialog";

    public static final String DIALOG_ID = "id";
    public static final String DIALOG_MESSAGE = "message";
    public static final String DIALOG_POSITIVE_RID = "positive_rid";
    public static final String DIALOG_NEGATIVE_RID = "negative_rid";

    /**
     * Dialogue's callback interface to notify user of selected results (delete confirmed, etc.)
     */
    interface DialogEvents {
        void onPositiveDialogResult(int dialogId, Bundle args);

        void onNegativeDialogResult(int dialogId, Bundle args);

        void onDialogCancelled(int dialogId);
    }

    private DialogEvents mDialogEvents;

    @Override
    public void onAttach(Context context) {
        Log.d(TAG, "onAttach - Penetrating onAttatch, Activity is: " + context.toString());
        super.onAttach(context);

        // Activities containing this fragment must implement its callbacks.
        if(!(context instanceof DialogEvents)) {
            throw new ClassCastException(context.toString() + " must implement AppDialog.DialogEvents interface");
        }

        mDialogEvents = (DialogEvents) context;
    }

    @Override
    public void onDetach() {
        Log.d(TAG, "onDetach - Penetrating");
        super.onDetach();

        // Reset the active callbacks interface, because we don't have an activity any longer.
        mDialogEvents = null;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Log.d(TAG, "onCreateDialog - Starts");
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        final Bundle arguments = getArguments();
        final int dialogId;
        String messageString;
        int positiveStringId;
        int negativeStringId;

        if(arguments != null){
            dialogId = arguments.getInt(DIALOG_ID);
            messageString = arguments.getString(DIALOG_MESSAGE);

            if(dialogId == 0 || messageString == null){
                throw new IllegalArgumentException("DIALOG_ID &/or DIALOG_MESSAGE not present within the bundle.");
            }

            positiveStringId = arguments.getInt(DIALOG_POSITIVE_RID);
            if(positiveStringId == 0){
                positiveStringId = R.string.ok;
            }
            negativeStringId = arguments.getInt(DIALOG_NEGATIVE_RID);
            if(negativeStringId == 0){
                negativeStringId = R.string.cancel;
            }
        }else{
            throw new IllegalArgumentException("Must pass DIALOG_ID & DIALOG_MESSAGE in the bundle :/");
        }

        builder.setMessage(messageString)
                .setPositiveButton(positiveStringId, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Callback Positive result method
                        if(mDialogEvents != null) {
                            mDialogEvents.onPositiveDialogResult(dialogId, arguments);
                        }
                    }
                })
                .setNegativeButton(negativeStringId, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        // callback negative result method
                        if(mDialogEvents != null) {
                            mDialogEvents.onNegativeDialogResult(dialogId, arguments);
                        }
                    }
                });

        return builder.create();
    }


    @Override
    public void onCancel(DialogInterface dialog) {
        Log.d(TAG, "onCancel - Starts");
        if(mDialogEvents != null){
            int dialogId = getArguments().getInt(DIALOG_ID);
            mDialogEvents.onDialogCancelled(dialogId);
        }
    }

}


























