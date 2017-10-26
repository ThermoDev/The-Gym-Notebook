package com.thermodev.thegymnotebook;

import java.io.Serializable;

/**
 * Created by user on 31-Jul-17.
 */

public class Exercise implements Serializable {
    public static final long serialVersionUID = 20170917;

    private long m_Id;
    private String name;
    private int sets;
    private int reps;
    private int weights;

    public Exercise( String name) {
        this.name = name;
        this.sets = 0;
        this.reps = 0;
        this.weights = 0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSets() {
        return sets;
    }

    public void setSets(int sets) {
        this.sets = sets;
    }

    public int getReps() {
        return reps;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }

    public long getId() {
        return m_Id;
    }

    public void setId(long id) {
        this.m_Id = id;
    }

    public int getWeights() {
        return weights;
    }

    public void setWeights(int weights) {
        this.weights = weights;
    }

}
