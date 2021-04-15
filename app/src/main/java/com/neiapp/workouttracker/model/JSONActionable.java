package com.neiapp.workouttracker.model;

import org.json.JSONException;

@FunctionalInterface
public interface JSONActionable {
    void run() throws JSONException;
}
