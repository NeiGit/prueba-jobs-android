package com.neiapp.workouttracker.model;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.List;

import io.realm.RealmList;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class SetTest {

    @Test
    @DisplayName("serialize / deserialize set | all values | OK")
    public void testSerializationAllValues() {
        Set expectedSet = buildSet("set-title");

        String jsonSet = expectedSet.toJson();

        assertThat(jsonSet).isNotBlank();

        Set actualSet = new Set.Parser().fromString(jsonSet);

        assertThat(new SetForTest(actualSet)).isEqualTo(expectedSet);
    }

    @Test
    @DisplayName("serialize / deserialize set | only some values | OK")
    public void testSerializationSomeValues() {
        int reps = 10;
        String setComment = "set-comment";
        float weight = 20f;

        Exercise exercise = new Exercise();
        exercise.setName("Push ups");

        Set expectedSet = new Set();
        expectedSet.setExercise(exercise);
        expectedSet.setReps(reps);
        expectedSet.setWeight(weight);
        expectedSet.setComment(setComment);

        String jsonSet = expectedSet.toJson();

        assertThat(jsonSet).isNotBlank();

        Set actualSet = new Set.Parser().fromString(jsonSet);

        assertThat(new SetForTest(actualSet)).isEqualTo(expectedSet);
        assertThat(expectedSet.getDistance()).isNull();
        assertThat(expectedSet.getTime()).isNull();
        assertThat(expectedSet.getTitle()).isNull();
        assertThat(expectedSet.getRest()).isNull();
        assertThat(expectedSet.getExercise().getMuscles()).isEmpty();
    }

    @Test
    @DisplayName("serialize / deserialize set | two levels of set nesting | all values OK")
    public void testSerializationTwoLevelsOfNesting() {
        Set expectedSetParent = buildSet("setParent");

        Set setChild1OfParent = buildSet("setChild1OfParent");
        Set setChild2OfParent = buildSet("setChild2OfParent");

        Set setChild1OfChild2 = buildSet("setChild1OfChild2");
        Set setChild2OfChild2 = buildSet("setChild2OfChild2");

        setChild2OfParent.addSet(setChild1OfChild2); // before adding to parent

        expectedSetParent.addSet(setChild1OfParent);
        expectedSetParent.addSet(setChild2OfParent);

        setChild2OfParent.addSet(setChild2OfChild2); // after adding to parent

        String setParentJson = expectedSetParent.toJson();
        assertThat(setParentJson).isNotBlank();

        Set actualSetParent = new Set.Parser().fromString(setParentJson);
        List<Set> setsChildrenOfParent = actualSetParent.getSets();

        assertThat(setsChildrenOfParent).hasSize(2);
        assertThat(new SetForTest(setChild1OfParent)).isEqualTo(setsChildrenOfParent.get(0));
        assertThat(new SetForTest(setChild2OfParent)).isEqualTo(setsChildrenOfParent.get(1));

        List<Set> setsChildrenOfChild2 = setsChildrenOfParent.get(1).getSets();
        assertThat(setsChildrenOfChild2).hasSize(2);
        assertThat(new SetForTest(setsChildrenOfChild2.get(0))).isEqualTo(setChild1OfChild2);
        assertThat(new SetForTest(setsChildrenOfChild2.get(1))).isEqualTo(setChild2OfChild2);
    }

    private Set buildSet(String title) {
        int reps = 10;
        String setComment = "set-comment";
        float weight = 20f;
        float time = 400f;
        float distance = 100f;
        float rest = 120f;

        Exercise exercise = new Exercise();
        exercise.setName("Push ups");
        RealmList<String> muscles = new RealmList<>("Chest", "Biceps", "Tricep");
        exercise.setMuscles(muscles);

        Set set = new Set();
        set.setExercise(exercise);
        set.setReps(reps);
        set.setWeight(weight);
        set.setTime(time);
        set.setDistance(distance);
        set.setTitle(title);
        set.setRest(rest);
        set.setComment(setComment);

        return set;
    }
}