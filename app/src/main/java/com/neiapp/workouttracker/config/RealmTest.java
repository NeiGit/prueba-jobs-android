package com.neiapp.workouttracker.config;

import android.text.TextUtils;

import com.neiapp.workouttracker.model.Exercise;
import com.neiapp.workouttracker.model.Muscle;
import com.neiapp.workouttracker.model.Set;
import com.neiapp.workouttracker.model.Workout;
import com.neiapp.workouttracker.model.WorkoutType;
import com.neiapp.workouttracker.realm.RealmUtils;

import org.bson.types.ObjectId;

import java.util.Date;

import io.realm.RealmList;

public class RealmTest {
    RealmUtils realm;
    public void run() {
        realm = new RealmUtils();
        ensureWorkoutType();
        ensureExercise();
        ensureMuscle();
        ensureWorkoutAndSets();
        ensureWorkoutAndSetWithinASet();
        showSuccessMessage();
        realm.close();
    }

    private void ensureWorkoutAndSets() {
        int reps = 10;
        String setComment = "set-comment";
        float weight = 20f;
        float time = 400f;
        float distance = 100f;
        String setTitle = "test-set";
        float rest = 120f;

        Exercise exercise = new Exercise();
        exercise.setName("Push ups");
        RealmList<String> muscles = new RealmList<>("Chest", "Biceps", "Tricep");
        exercise.setMuscles(muscles);

        Set s = new Set();
        s.setExercise(exercise);
        s.setReps(reps);
        s.setWeight(weight);
        s.setTime(time);
        s.setDistance(distance);
        s.setTitle(setTitle);
        s.setRest(rest);
        s.setComment(setComment);

        ObjectId id = new ObjectId();
        String workoutTitle = "workout-title";
        String workoutComment = "workout-comment";
        Date date = new Date();

        Workout w = new Workout();
        w.set_id(id);
        w.setTitle(workoutTitle);
        w.addRound(s);
        w.setComment(workoutComment);
        w.setDate(date);

        realm.run(r -> r.insert(w));
        Workout workout = realm.get(r -> r.where(Workout.class).equalTo("_id", id)).findFirst();
        assertThat(workout != null);
        assertThat(workout.getComment().equals(workoutComment));
        assertThat(workout.getTitle().equals(workoutTitle));
        assertThat(workout.getRounds().size() == 1);
        assertThat(workout.getExercises().equals(exercise.getName()));
        assertThat(workout.getMuscles().equals(TextUtils.join(",", muscles)));

        Set set = workout.getRounds().get(0);

        assertThat(set.getTitle().equals(setTitle));
        assertThat(set.getComment().equals(setComment));
        assertThat(set.getDistance() == distance);
        assertThat(set.getExercise().getName().equals(exercise.getName()));
        assertThat(set.getReps() == reps);
        assertThat(set.getRest() == rest);
        assertThat(set.getTime() == time);
        assertThat(set.getWeight() == weight);
        assertThat(set.getExercise().getName().equals("Push ups"));
        assertThat(set.getMuscles().containsAll(muscles));
    }

    private void ensureWorkoutAndSetWithinASet() {

    }

    private void showSuccessMessage() {
        breaks();
        System.out.println("******** REALM IS WORKING FINE ********");
        breaks();
    }

    private void ensureWorkoutType() {
        realm.run(r -> {
            WorkoutType wt = r.createObject(WorkoutType.class, new ObjectId());
            wt.setName("HIIT");
        });

        WorkoutType workoutType = realm.get(r ->
            r.where(WorkoutType.class).equalTo("name", "HIIT").findFirst());

        assertThat(workoutType != null);
        assertThat(workoutType.getName().equals("HIIT"));
    }

    private void ensureExercise() {
        realm.run(r -> {
            Exercise e = r.createObject(Exercise.class, new ObjectId());
            e.setName("Push ups");
            e.setMuscles(new RealmList<>("Chest", "Biceps", "Tricep"));
        });

        Exercise exercise = realm.get(r -> r.where(Exercise.class).equalTo("name", "Push ups")).findFirst();

        assertThat(exercise != null);
        assertThat(exercise.getName().equals("Push ups"));
        assertThat(exercise.getMuscles().size() == 3);
        assertThat(exercise.getMuscles().containsAll(new RealmList<>("Chest", "Biceps", "Tricep")));
    }

    private void ensureMuscle() {
        realm.run(r -> {
            Muscle e = r.createObject(Muscle.class, new ObjectId());
            e.setName("Biceps");
        });

        Muscle muscle = realm.get(r -> r.where(Muscle.class).equalTo("name", "Biceps")).findFirst();

        assertThat(muscle != null);
        assertThat(muscle.getName().equals("Biceps"));
    }

    private void assertThat(boolean result) {
        if (!result) {
            breaks();
            throw new RuntimeException("------- REALM IS BROKEN --------");
        }
    }

    private void breaks() {
        System.out.println("*");
        System.out.println("    *");
        System.out.println("         *");
    }

    public void destroy() throws Throwable {
        finalize();
    }
}
