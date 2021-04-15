package com.neiapp.workouttracker.model;

import android.app.Instrumentation;
import android.content.Context;

import androidx.test.core.app.ApplicationProvider;

import org.bson.types.ObjectId;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import io.realm.internal.RealmCore;
import io.realm.log.RealmLog;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(RobolectricTestRunner.class)
public class WorkoutTypeTest {
    private Realm realm;

    @Before public void setUp() {
        Context context = ApplicationProvider.getApplicationContext();
        Realm.init(context);
        RealmConfiguration config =
                new RealmConfiguration.Builder().inMemory().name("test-realm").build();
        realm = Realm.getInstance(config);

    }

    @After
    public void tearDown() {
//        realm.close();
    }

    @Test
    @DisplayName("persist WorkoutType | ok")
    public void persistWorkoutTypeOk() {
//        final String name = "HIIT";
//        realm.executeTransaction(new Realm.Transaction() {
//            @Override
//            public void execute(Realm realm) {
//                WorkoutType workoutType = realm.createObject(WorkoutType.class, new ObjectId());
//                workoutType.setName(name);
//            }
//        });

//        WorkoutType workoutType = realm.where(WorkoutType.class).equalTo("name", name).findFirst();
//        assertThat(workoutType).isNotNull();
//        assertThat(workoutType.getName()).isEqualTo(name);
    }

}