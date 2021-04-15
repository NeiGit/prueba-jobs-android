package com.neiapp.workouttracker.realm;

import io.realm.Realm;

public class RealmUtils {

    private Realm realm;

    public RealmUtils() {
        realm = Realm.getDefaultInstance();
    }

    public void run(RealmRunnable runnable) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(runnable::run);
    }

    public <T> T get(RealmSupplier<T> supplier) {
        Realm realm = Realm.getDefaultInstance();
        T instance = supplier.get(realm);
        return instance;
    }

    public void close() {
        realm.close();
    }
}
