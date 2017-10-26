package com.thermodev.thegymnotebook;

import java.io.Serializable;
import java.util.List;

/**
 * Created by user on 01-Aug-17.
 */

class WorkoutPlan implements Serializable {
    public static final long serialVersionUID = 20170917;

    private long m_Id;
    private String mName;
    private String mDescription;
    private List<Exercise> mExercises;

    public WorkoutPlan(long id, String name, String description) {
        this.m_Id = id;
        this.mName = name;
        this.mDescription = description;
    }

    public WorkoutPlan(long id, String name, String description, List<Exercise> exercises) {
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

    public List<Exercise>  getExercises() {
        return mExercises;
    }

    public long getId() {
        return m_Id;
    }

    public void setId(long id) {
        this.m_Id = id;
    }

    public void setName(String name) {
        mName = name;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public void setExercises(List<Exercise>  exercises) {
        mExercises = exercises;
    }
}
