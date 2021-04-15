package com.neiapp.workouttracker.model;

import androidx.annotation.Nullable;

public class ExerciseForTest extends ModelForTest {
    private final Exercise exercise;

    public ExerciseForTest(Exercise exercise) {
        this.exercise = exercise;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj == null) return false;
        Exercise otherExercise = (Exercise) obj;
        return nullOrEquals(exercise.getName(), otherExercise.getName())
                && nullOrEquals(exercise.getMuscles(), otherExercise.getMuscles());
    }
}
