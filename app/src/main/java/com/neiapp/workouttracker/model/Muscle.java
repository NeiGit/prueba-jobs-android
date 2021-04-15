package com.neiapp.workouttracker.model;

import org.bson.types.ObjectId;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Muscle extends RealmObject {
    @PrimaryKey
    private ObjectId _id = new ObjectId();
    private String name;

    public Muscle() {}

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
}
