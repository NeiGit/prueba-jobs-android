package com.neiapp.workouttracker.model;

import com.google.gson.Gson;

import org.bson.types.ObjectId;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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
