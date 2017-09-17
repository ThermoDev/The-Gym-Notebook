package com.thermodev.thegymnotebook;

import java.util.Calendar;

import java.io.Serializable;
import java.util.List;

/**
 * Created by user on 26-Jul-17.
 */

class Workout implements Serializable {
    public static final long serialVersionUID = 20170917;

    private long m_Id;
    private Calendar mCalendar;
    private List<Exercise> mExercises;
    private String mDescription;

    public Workout(long id, Calendar calendar) {
        this.m_Id = id;
        this.mCalendar = calendar;
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
    public long getId() {
        return m_Id;
    }

    public void setId(long id) {
        this.m_Id = id;
    }
}
