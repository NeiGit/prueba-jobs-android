package com.neiapp.workouttracker.realm;

import io.realm.Realm;

public interface RealmSupplier<T> {
    T get(Realm realm);
}
