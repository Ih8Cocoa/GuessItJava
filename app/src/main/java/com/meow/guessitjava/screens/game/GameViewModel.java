package com.meow.guessitjava.screens.game;

import android.os.CountDownTimer;
import android.text.format.DateUtils;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

// (1) The GameFragment class should only deal with drawing elements on the screen
// and handling on-click events, while the ViewModel handles data processing and persistence
// This helps with separation of concern, and allow the data to survive config changes
public final class GameViewModel extends ViewModel {
    private final long DONE = 0;
    private final long ONE_SEC = 1000;
    private final long COUNTDOWN_TIME = 60000;
    private final int COUNTDOWN_PANIC_SECONDS = 10;
    private final CountDownTimer timer;

    // (14) Now, let's change these fields to use LiveData. This helps automate UI changes
    // in the Fragment
    /*// default word and score
    private String word = "";
    private int score = 0;*/

    private final MutableLiveData<String> word = new MutableLiveData<>();
    private final MutableLiveData<Integer> score = new MutableLiveData<>(0);

    private final MutableLiveData<Long> currentTime = new MutableLiveData<>(COUNTDOWN_TIME);

    // define a custom transformation of LiveData that converts the timer to String
    private final LiveData<String> currentTimeString = Transformations.map(
            currentTime, DateUtils::formatElapsedTime
    );

    // Setup a Buzzer LiveData to store buzzer event
    private final MutableLiveData<BuzzType> buzzEvent = new MutableLiveData<>();

    // (17) The difference between LiveData and MutableLiveData is that
    // MutableLiveData allows both read and write operations, while
    // LiveData is read-only. And we don't want the fragment to write anything
    // to our LiveData
    public LiveData<String> getWord() {
        return word;
    }

    public LiveData<Integer> getScore() {
        return score;
    }

    public LiveData<String> getCurrentTimeString() {
        return currentTimeString;
    }

    // (25) Let's make a LiveData variable representing the gameFinish event
    private final MutableLiveData<Boolean> gameFinishedEvent = new MutableLiveData<>(false);

    LiveData<Boolean> getGameFinishedEvent() {
        return gameFinishedEvent;
    }

    // expose read-only LiveData to public
    public LiveData<BuzzType> getBuzzEvent() {
        return buzzEvent;
    }

    // lateinit variables and binding
    private LinkedList<String> wordList;

    public GameViewModel() {
        // call resetList when the ViewModel is created
        resetList();
        timer = makeTimer(
                time -> {
                    currentTime.setValue(time / ONE_SEC);
                    // Keep buzzing
                    if (time / ONE_SEC < COUNTDOWN_PANIC_SECONDS) {
                        buzzEvent.setValue(BuzzType.COUNTDOWN_PANIC);
                    }
                }, () -> {
                    currentTime.setValue(DONE);
                    buzzEvent.setValue(BuzzType.GAME_OVER);
                    gameFinishedEvent.setValue(true);
                });
        timer.start();
    }

    public void onSkip() {
        // (15) update the code to use LiveData
        Integer oldScore = score.getValue();
        if (oldScore != null) {
            int newScore = oldScore - 1;
            score.setValue(newScore);
        }
        // score--;
        nextWord();
    }

    public void onCorrect() {
        // (15) update the code to use LiveData
        Integer oldScore = score.getValue();
        if (oldScore != null) {
            int newScore = oldScore + 1;
            score.setValue(newScore);
        }
        // score++;
        // Don't forget to buzz
        buzzEvent.setValue(BuzzType.CORRECT);
        nextWord();
    }

    private void resetList() {
        final List<String> abstractList = Arrays.asList(
                "queen", "hospital", "basketball", "cat", "change", "snail", "soup", "calendar",
                "sad", "desk", "guitar", "home", "railway", "zebra", "jelly", "car", "crow",
                "trade", "bag", "roll", "bubble"
        );
        // shuffle the list
        Collections.shuffle(abstractList);
        // The abstractList is constant-sized, and does not support remove operation
        // so make a new list
        wordList = new LinkedList<>(abstractList);
        // (30) I gave up on numbering lmao
        nextWord();
    }

    private void nextWord() {
        // Select and remove a word from the list
        if (wordList.isEmpty()) {
            // (26) signaling the gameFinished event
            // gameFinishedEvent.setValue(true);
            // gameFinished();
            resetList();
        }
        final String newWord = wordList.removeFirst();
        word.setValue(newWord);
        // (12) these lines are no longer needed,
        // since they're already called in the on-click listener
        /*updateWordText();
        updateScoreText();*/
    }

    public void onGameFinishCompleted() {
        // (28) the event is triggered, set the LiveData back to false
        gameFinishedEvent.setValue(false);
    }

    private CountDownTimer makeTimer(Observer<Long> onTick, Runnable onFinish) {
        // quickly make a new CountDownTimer from lambda expressions?
        return new CountDownTimer(COUNTDOWN_TIME, ONE_SEC) {
            @Override
            public void onTick(long millisUntilFinished) {
                onTick.onChanged(millisUntilFinished);
            }

            @Override
            public void onFinish() {
                onFinish.run();
            }
        };
    }

    // call this when the vibration is finished
    void onBuzzCompleted() {
        buzzEvent.setValue(BuzzType.NO_BUZZ);
    }

    // override onCleared to cancel the timer
    @Override
    protected void onCleared() {
        super.onCleared();
        timer.cancel();
    }
}
