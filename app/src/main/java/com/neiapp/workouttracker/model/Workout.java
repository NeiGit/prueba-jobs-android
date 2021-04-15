package com.neiapp.workouttracker.model;

import android.text.TextUtils;

import com.neiapp.workouttracker.realm.RealmUtils;

import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.Index;
import io.realm.annotations.PrimaryKey;

public class Workout extends RealmObject {
    @PrimaryKey
    private ObjectId _id = new ObjectId();

    private String title;
    private Date date;
    private String type;
    private String comment;
    private RealmList<String> rounds;

    // indexed fields for query
    @Index
    private String exercises;

    @Index
    private String muscles;

    public Workout() {
        this.rounds = new RealmList<>();
        this.exercises = "";
        this.muscles = "";
    }

    public void setExercises(List<String> exercises) {
        this.exercises = buildStringFromList(exercises);
    }

    public void setMuscles(List<String> muscles) {
        this.muscles = buildStringFromList(muscles);
    }

    private static String buildStringFromList(List<String> list) {
        return TextUtils.join(",", list);
    }

    public void addRound(Set s) {
        rounds.add(s.toJson());

        List<String> exercises = s.getExercises();
        exercises.stream().forEach(e -> {
            if(!this.exercises.contains(e)) addExercise(e);
        });

        List<String> muscles = s.getMuscles();
        muscles.stream().forEach(m -> {
            if (!this.muscles.contains(m)) addMuscle(m);
        });
    }

    private void addExercise(String exercise) {
        this.exercises = concat(this.exercises, exercise);
    }

    private void addMuscle(String muscle) {
        this.muscles = concat(this.muscles, muscle);
    }

    private String concat(String base, String addition) {
        if (TextUtils.isEmpty(base)) return addition;
        else return base + "," + addition;
    }

    public List<Set> getRounds() {
        Set.Parser parser = new Set.Parser();
        return rounds.stream().map(parser::fromString).collect(Collectors.toList());
    }

    public ObjectId get_id() {
        return _id;
    }

    public void set_id(ObjectId _id) {
        this._id = _id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setRounds(RealmList<String> rounds) {
        this.rounds = rounds;
    }

    public String getExercises() {
        return exercises;
    }

    public void setExercises(String exercises) {
        this.exercises = exercises;
    }

    public String getMuscles() {
        return muscles;
    }

    public void setMuscles(String muscles) {
        this.muscles = muscles;
    }
}
