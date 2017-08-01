package com.thermodev.thegymnotebook;

import java.util.List;

/**
 * Created by user on 01-Aug-17.
 */

public class WorkoutPlan {
    private String name;
    private String description;
    private List<Exercise> exercises;

    public WorkoutPlan(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Exercise> getExercises() {
        return exercises;
    }

    public void setExercises(List<Exercise> exercises) {
        this.exercises = exercises;
    }
}
