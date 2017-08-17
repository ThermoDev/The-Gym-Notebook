package com.thermodev.thegymnotebook;

import java.util.Calendar;

import java.io.Serializable;
import java.util.List;

/**
 * Created by user on 26-Jul-17.
 */

class Workout implements Serializable{
    private Calendar mCalendar;
    private List<Exercise> mExercises;
    private String mDescription;

    public Workout(Calendar calendar) {
        mCalendar = calendar;
    }

    public Calendar getCalendar() {
        return mCalendar;
    }

    public void setCalendar(Calendar calendar) {
        mCalendar = calendar;
    }

    public List<Exercise> getExercises() {
        return mExercises;
    }

    public void setExercises(List<Exercise> exercises) {
        mExercises = exercises;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }
}
