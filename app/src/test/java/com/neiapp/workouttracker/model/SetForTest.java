package com.neiapp.workouttracker.model;

import androidx.annotation.Nullable;

import java.util.List;
import java.util.stream.Collectors;

public class SetForTest extends ModelForTest {
    private final Set set;

    public SetForTest(Set set) {
        this.set = set;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        Set otherSet = (Set) obj;
        List<SetForTest> setForTests = set.getSets().stream().map(SetForTest::new).collect(Collectors.toList());

        return nullOrEquals(set.getComment(), otherSet.getComment()) &&
                nullOrEquals(set.getDistance(), otherSet.getDistance()) &&
                nullOrEquals(set.getReps(), otherSet.getReps()) &&
                nullOrEquals(set.getRest(), otherSet.getRest()) &&
                nullOrEquals(set.getTime(), otherSet.getTime()) &&
                nullOrEquals(set.getTitle(), otherSet.getTitle()) &&
                nullOrEquals(set.getWeight(), otherSet.getWeight()) &&
                otherSet.getSets().stream().allMatch(s -> setForTests.stream().anyMatch(sft -> sft.equals(s))) &&
                nullOrEquals(new ExerciseForTest(set.getExercise()), set.getExercise());
    }
}
