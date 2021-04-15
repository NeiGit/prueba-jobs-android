package com.neiapp.workouttracker.model;

import org.json.JSONException;
import org.json.JSONObject;

public interface Jsonable {
    String toJson();
    default void compute (JSONObject json, String key, Object value) throws JSONException {
        if (value != null) {
            json.put(key, value);
        }
    }
}
