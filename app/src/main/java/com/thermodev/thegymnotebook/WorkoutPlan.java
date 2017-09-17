package com.thermodev.thegymnotebook;

import java.io.Serializable;
import java.util.List;

/**
 * Created by user on 01-Aug-17.
 */

public class WorkoutPlan implements Serializable {
    public static final long serialVersionUID = 20170917;

    private long m_Id;
    private final String mName;
    private final String mDescription;
    private final List<Exercise> mExercises;

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

    public List<Exercise> getExercises() {
        return mExercises;
    }

    public long getId() {
        return m_Id;
    }

    public void setId(long id) {
        this.m_Id = id;
    }
}
