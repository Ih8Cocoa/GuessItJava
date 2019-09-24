package com.meow.guessitjava.screens.score;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public final class ScoreViewModel extends ViewModel {
    // pls send help
    private final MutableLiveData<Boolean> eventPlayAgain = new MutableLiveData<>(false);
    private final MutableLiveData<Integer> score;

    public ScoreViewModel(final int finalScore) {
        score = new MutableLiveData<>(finalScore);
    }

    public LiveData<Boolean> getEventPlayAgain() {
        return eventPlayAgain;
    }

    public LiveData<Integer> getScore() {
        return score;
    }

    public void onPlayAgain() {
        eventPlayAgain.setValue(true);
    }

    public void onPlayAgainCompleted() {
        eventPlayAgain.setValue(false);
    }
}
