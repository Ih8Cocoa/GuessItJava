package com.meow.guessitjava.screens.game;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

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
    // default word and score
    private String word = "";
    private int score = 0;

    // lateinit variables and binding
    private LinkedList<String> wordList;
    private FragmentGameBinding binding;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState
    ) {
        // setup data binding
        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_game, container, false
        );

        // bind the correct and skip button to the correct method
        binding.correctButton.setOnClickListener(view -> onCorrect());
        binding.skipButton.setOnClickListener(view -> onSkip());
        resetList();

        return binding.getRoot();
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
    }

    private void gameFinished() {
        // get the destination
        final NavDirections target =
                GameFragmentDirections.actionGameFragmentToScoreFragment(score);
        // set the score for the target, then begin navigation
        NavHostFragment.findNavController(this).navigate(target);
    }

    private void updateWordText() {
        binding.wordText.setText(word);
    }

    private void updateScoreText() {
        final String scoreStr = Integer.toString(score);
        binding.scoreText.setText(scoreStr);
    }

    private void onSkip() {
        score--;
        nextWord();
    }

    private void onCorrect() {
        score++;
        nextWord();
    }
}
