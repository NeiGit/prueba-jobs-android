package com.neiapp.workouttracker.model;

import org.json.JSONException;

public interface Parseable<T> {
    T fromString(String s);

    static void compute(JSONActionable actionable) {
        try {
            actionable.run();
        } catch (JSONException e) {
            // ignore
        }
    }
}
