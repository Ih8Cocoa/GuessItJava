package com.meow.guessitjava.screens.game;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.os.VibrationEffect;
import android.os.Vibrator;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.meow.guessitjava.R;
import com.meow.guessitjava.databinding.FragmentGameBinding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public final class GameFragment extends Fragment {
    // (4) Move all of these to the ViewModel
    /*// default word and score
    private String word = "";
    private int score = 0;

    // lateinit variables and binding
    private LinkedList<String> wordList;*/

    // (2) Add the ViewModel field to the fragment
    private GameViewModel viewModel;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState
    ) {
        // setup data binding
        final FragmentGameBinding binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_game, container, false
        );

        // (3) construct the gameViewModel here
        viewModel = ViewModelProviders.of(this).get(GameViewModel.class);

        // set the ViewModel variable inside XML file
        binding.setGameViewModel(viewModel);
        // also set the binding's lifecycleOwner to this fragment
        binding.setLifecycleOwner(this);

        // (5) Move this line to the ViewModel's constructor
        // resetList();
        // (23) setup LiveData observation
        setupObservers();

        return binding.getRoot();
    }

    private void setupObservers() {
        // (27) setup observer for the gameFinishedEvent
        final Observer<Boolean> gameFinishedObserver = finished -> {
            // if the game is finished, call gameFinished()
            if (finished) {
                gameFinished();
                // (29) tell the ViewModel that the event is finished
                viewModel.onGameFinishCompleted();
            }
        };
        final Observer<BuzzType> buzzEventObserver = buzzType -> {
            if (buzzType != BuzzType.NO_BUZZ) {
                buzz(buzzType);
                viewModel.onBuzzCompleted();
            }
        };
        // make the fragment observe the data
        viewModel.getGameFinishedEvent().observe(this, gameFinishedObserver);
        viewModel.getBuzzEvent().observe(this, buzzEventObserver);
    }

    // Start buzzing
    private void buzz(final BuzzType buzzType) {
        final Activity activity = getActivity();
        if (activity == null) {
            return;
        }
        // lmao vibrator haha
        final Vibrator buzzer = (Vibrator) activity.getSystemService(Context.VIBRATOR_SERVICE);
        if (buzzer == null) {
            return;
        }
        // Execute depending on the API level
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            final VibrationEffect vibe =
                    VibrationEffect.createWaveform(buzzType.buzzPattern, -1);
            buzzer.vibrate(vibe);
        } else {
            buzzer.vibrate(buzzType.buzzPattern, -1);
        }
    }

    private void gameFinished() {
        // (22) get the score from the ViewModel
        Integer score = viewModel.getScore().getValue();
        if (score == null) {
            // (23) quick null check
            score = 0;
        }
        // get the destination
        final NavDirections target =
                GameFragmentDirections.actionGameFragmentToScoreFragment(score);
        // set the score for the target, then begin navigation
        NavHostFragment.findNavController(this).navigate(target);
    }



    // (4) Move this to the ViewModel
    /*private void resetList() {
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
        nextWord();
    }

    private void nextWord() {
        // Select and remove a word from the list
        if (wordList.isEmpty()) {
            gameFinished();
        } else {
            word = wordList.removeFirst();
        }
        updateWordText();
        updateScoreText();
    }*/

    // (24) delete this method
    /*private void updateWordText() {
        // (10) get the word from ViewModel
        final String word = viewModel.getWord();
        binding.wordText.setText(word);
    }*/

    // (21) delete this method
    /*private void updateScoreText() {
        // (11) Get the score from the ViewModel
        final int score = viewModel.getScore();
        final String scoreStr = Integer.toString(score);
        binding.scoreText.setText(scoreStr);
    }*/

    // (6) move these 2 methods to the ViewModel, and call it from the ViewModel directly
    // inside the on-click listener
    /*private void onSkip() {
        score--;
        nextWord();
    }

    private void onCorrect() {
        score++;
        nextWord();
    }*/
}
