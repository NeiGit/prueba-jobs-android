package com.neiapp.workouttracker.model;

import com.google.gson.Gson;

import org.bson.types.ObjectId;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Exercise extends RealmObject implements Jsonable {
    @PrimaryKey
    private ObjectId _id = new ObjectId();
    private String name;
    private RealmList<String> muscles;

    public Exercise() {
        muscles = new RealmList<>();
    }

    @Override
    public String toJson() {
        JSONObject json = new JSONObject();
        try {
            compute(json, "name", name);
            if (!muscles.isEmpty()) {
                Gson gson = new Gson();
                compute(json, "muscles", gson.toJson(muscles));
            }
            return json.toString();
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public ObjectId get_id() {
        return _id;
    }

    public void set_id(ObjectId _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RealmList<String> getMuscles() {
        return muscles;
    }

    public void setMuscles(RealmList<String> muscles) {
        this.muscles = muscles;
    }

    public static class Parser implements Parseable<Exercise> {
        @Override
        public Exercise fromString(String s) {
            try {
                JSONObject jsonObject = new JSONObject(s);

                Exercise exercise = new Exercise();

                Parseable.compute(() -> exercise.setName(jsonObject.getString("name")));

                if (jsonObject.has("muscles")) {
                    JSONArray jsonMuscles = new JSONArray(jsonObject.get("muscles").toString());
                    RealmList<String> muscles = new RealmList<>();
                    for (int i = 0; i < jsonMuscles.length(); i++) {
                        muscles.add(jsonMuscles.getString(i));
                    }
                    exercise.setMuscles(muscles);
                }

                return exercise;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
