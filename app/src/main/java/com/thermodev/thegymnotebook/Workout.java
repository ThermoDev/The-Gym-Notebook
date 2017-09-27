package com.thermodev.thegymnotebook;

import java.io.Serializable;

/**
 * Created by user on 26-Jul-17.
 */

class Workout implements Serializable {
    public static final long serialVersionUID = 20170917;

    private long m_Id;
    private String mCalendar;
    private String mExercises;
    private String mDescription;

    public Workout(long id, String calendar) {
        this.m_Id = id;
        this.mCalendar = calendar;
    }

    public Workout(long m_Id, String calendar, String exercises, String description) {
        this.m_Id = m_Id;
        mCalendar = calendar;
        mExercises = exercises;
        mDescription = description;
    }

    public String getCalendar() {
        return mCalendar;
    }

    public void setCalendar(String calendar) {
        mCalendar = calendar;
    }

    public String getExercises() {
        return mExercises;
    }

    public void setExercises(String exercises) {
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
