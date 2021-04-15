package com.neiapp.workouttracker.realm;

import io.realm.Realm;

@FunctionalInterface
public interface RealmRunnable {
    void run(Realm realm);
}
