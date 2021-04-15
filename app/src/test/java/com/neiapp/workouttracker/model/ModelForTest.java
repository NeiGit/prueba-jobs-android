package com.neiapp.workouttracker.model;

public class ModelForTest {
    protected boolean nullOrEquals(Object o1, Object o2) {
        if (o1 != null && o2 != null) {
            return o1.equals(o2);
        } else return o1 == null && o2 == null;
    }
}
