package com.thermodev.thegymnotebook;

import java.io.Serializable;

/**
 * Created by user on 01-Aug-17.
 */

class WorkoutPlan implements Serializable {
    public static final long serialVersionUID = 20170917;

    private long m_Id;
    private String mName;
    private String mDescription;
    private String mExercises;

    public WorkoutPlan(long id, String name, String description) {
        this.m_Id = id;
        this.mName = name;
        this.mDescription = description;
    }

    public WorkoutPlan(long id, String name, String description, String exercises) {
        this.m_Id = id;
        this.mName = name;
        this.mDescription = description;
        this.mExercises = exercises;
    }

    public String getName() {
        return mName;
    }

    public String getDescription() {
        return mDescription;
    }

    public String getExercises() {
        return mExercises;
    }

    public long getId() {
        return m_Id;
    }

    public void setId(long id) {
        this.m_Id = id;
    }


    public long getM_Id() {
        return m_Id;
    }

    public void setM_Id(long m_Id) {
        this.m_Id = m_Id;
    }

    public void setName(String name) {
        mName = name;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public void setExercises(String exercises) {
        mExercises = exercises;
    }
}
