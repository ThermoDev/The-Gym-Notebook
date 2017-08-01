package com.thermodev.thegymnotebook;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.DatePicker;

import java.util.Calendar;
//
///**
// * Created by user on 26-Jul-17.
// */
//
public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener{
//
//    private TextView mDateButton;
//
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
//        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
//
//        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }
//
    public void onDateSet(DatePicker view, int year, int month, int day) {
//        // Do something with the date chosen by the user
//        mDateButton = (TextView) view.findViewById(R.id.add_edit_date_button);
//        mDateButton.setText(year + month + day);
    }
}