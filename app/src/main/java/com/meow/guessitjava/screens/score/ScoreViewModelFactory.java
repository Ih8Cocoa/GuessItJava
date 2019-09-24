package com.meow.guessitjava.screens.score;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public final class ScoreViewModelFactory implements ViewModelProvider.Factory {
    private final int finalScore;

    public ScoreViewModelFactory(final int finalScore) {
        this.finalScore = finalScore;
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        final boolean isAssignable = modelClass.isAssignableFrom(ScoreViewModel.class);
        if (isAssignable) {
            return (T) new ScoreViewModel(finalScore);
        }
        throw new IllegalArgumentException("Incorrect ViewModel class");
    }
}
