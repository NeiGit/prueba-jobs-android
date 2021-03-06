package com.neiapp.workouttracker.model;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class Set implements Jsonable {
    private String title;
    private Exercise exercise;
    private Integer reps;
    private Float weight;
    private Float rest; // seconds
    private Float distance; // meters
    private Float time;
    private String comment;
    private List<Set> sets;

    public Set() {
        this.sets = new ArrayList<>();
    }

    public List<String> getExercises() {
        List<String> list = new ArrayList<>();
        list.add(exercise.getName());
        return list;
    }

    public List<String> getMuscles() {
        return exercise.getMuscles();
    }

    @Override
    public String toJson() {
        JSONObject json = new JSONObject();
        try {
            compute(json, "title", title);
            compute(json, "exercise", exercise.toJson());
            compute(json, "reps", reps);
            compute(json, "weight", weight);
            compute(json, "rest", rest);
            compute(json, "distance", distance);
            compute(json, "time", time);
            compute(json, "comment", comment);
            if (!this.sets.isEmpty()) {
                Gson gson = new Gson();
                String setsJson = gson.toJson(sets.stream().map(Set::toJson).collect(Collectors.toList()));
                compute(json, "sets", setsJson);
            }
            return json.toString();
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public void addSet(Set set) {
        this.sets.add(set);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Exercise getExercise() {
        return exercise;
    }

    public void setExercise(Exercise exercise) {
        this.exercise = exercise;
    }

    public Integer getReps() {
        return reps;
    }

    public void setReps(Integer reps) {
        this.reps = reps;
    }

    public Float getWeight() {
        return weight;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
    }

    public Float getRest() {
        return rest;
    }

    public void setRest(Float rest) {
        this.rest = rest;
    }

    public Float getDistance() {
        return distance;
    }

    public void setDistance(Float distance) {
        this.distance = distance;
    }

    public Float getTime() {
        return time;
    }

    public void setTime(Float time) {
        this.time = time;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public List<Set> getSets() {
        return sets;
    }

    public void setSets(List<Set> sets) {
        this.sets = sets;
    }

    public static class Parser implements Parseable<Set> {
        @Override
        public Set fromString(String s) {
            try {
                JSONObject jsonObject = new JSONObject(s);

                Set set = new Set();
                Parseable.compute(() -> set.setTitle(jsonObject.getString("title")));
                Parseable.compute(() -> set.setComment(jsonObject.get("comment").toString()));
                Parseable.compute(() -> set.setRest(Float.parseFloat(jsonObject.get("rest").toString())));
                Parseable.compute(() -> set.setDistance(Float.parseFloat(jsonObject.get("distance").toString())));
                Parseable.compute(() -> set.setWeight(Float.parseFloat(jsonObject.get("weight").toString())));
                Parseable.compute(() -> set.setTime(Float.parseFloat(jsonObject.get("time").toString())));
                Parseable.compute(() -> set.setExercise(new Exercise.Parser().fromString(jsonObject.getString("exercise"))));
                Parseable.compute(() -> set.setReps(Integer.parseInt(jsonObject.get("reps").toString())));

                if (jsonObject.has("sets")) {
                    JSONArray jsonSets = new JSONArray(jsonObject.get("sets").toString());
                    List<Set> sets = new ArrayList<>();
                    for (int i=0; i < jsonSets.length(); i++){
                        sets.add(fromString(jsonSets.getString(i)));
                    }
                    set.setSets(sets);
                }

                return set;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
