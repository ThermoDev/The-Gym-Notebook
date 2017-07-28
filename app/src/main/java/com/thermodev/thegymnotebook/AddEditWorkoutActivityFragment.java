package com.thermodev.thegymnotebook;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.Calendar;

/**
 * Created by user on 26-Jul-17.
 */

public class AddEditWorkoutActivityFragment extends Fragment implements DatePickerDialog.OnDateSetListener {
    private static final String TAG = "AddEditWorkoutActivityF";
    private TextView mDateTextView;
    private Button mDateButton;
    private Button mSaveButton;



    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Log.d(TAG, "onDateSet: " + year);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.workout_plan_fragment_add_edit, container, false);

        mDateButton = (Button) view.findViewById(R.id.add_edit_date_button);
        mDateTextView = (TextView) view.findViewById(R.id.addedit_date_textview);


        mDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), datePickerListener, year, month, day);
                datePickerDialog.show();
                int dayOfMonth = datePickerDialog.getDatePicker().getDayOfMonth();
                Log.d(TAG, "onClick: DayOfMonth Selected: " + dayOfMonth);
                Log.d(TAG, "onClick: Pressed");
            }
        });

        return view;
    }

// This will occur after the date has been picked.
    private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
            mDateTextView.setText(selectedDay + " / " + (selectedMonth + 1) + " / "
                    + selectedYear);
        }
    };
}
