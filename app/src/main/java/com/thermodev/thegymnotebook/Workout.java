package com.thermodev.thegymnotebook;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by user on 26-Jul-17.
 */

class Workout implements Serializable{
    private Date date;
    private List<Exercise> exercises;
}
