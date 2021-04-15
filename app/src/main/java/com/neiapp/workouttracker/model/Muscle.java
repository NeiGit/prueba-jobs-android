package com.neiapp.workouttracker.model;

import org.bson.types.ObjectId;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Muscle extends RealmObject {
    @PrimaryKey
    private ObjectId _id = new ObjectId();
    private String name;

    public Muscle() {}
}
