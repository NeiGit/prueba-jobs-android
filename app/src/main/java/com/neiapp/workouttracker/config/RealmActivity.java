package com.neiapp.workouttracker.config;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import lombok.SneakyThrows;

public class RealmActivity extends Application {
    @SneakyThrows
    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        String realmName = "Workout Tracker - DB";
        RealmConfiguration config = new RealmConfiguration.Builder()
                .name(realmName)
                .schemaVersion(0)
                .deleteRealmIfMigrationNeeded()
                .inMemory()
                .allowWritesOnUiThread(true)
                .build();
        Realm.setDefaultConfiguration(config);
        RealmTest realmTest = new RealmTest();
        realmTest.run();
        realmTest.destroy();
        realmTest = null;
    }
}
