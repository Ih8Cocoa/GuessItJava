package com.meow.guessitjava.screens.score;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.meow.guessitjava.R;
import com.meow.guessitjava.databinding.FragmentScoreBinding;


/**
 * A simple {@link Fragment} subclass.
 */
public class ScoreFragment extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // initiate data binding
        final FragmentScoreBinding binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_score, container, false
        );

        // get the score arguments, and set it to the score text
        final Bundle bundle = getArguments();
        if (bundle != null) {
            final ScoreFragmentArgs args = ScoreFragmentArgs.fromBundle(bundle);
            int score = args.getScore();
            String scoreStr = Integer.toString(score);
            binding.scoreText.setText(scoreStr);
        }

        // set the onPlayAgain action
        final NavDirections restartDirection = ScoreFragmentDirections.actionRestart();
        View.OnClickListener listener = Navigation.createNavigateOnClickListener(restartDirection);
        binding.playAgainButton.setOnClickListener(listener);

        return binding.getRoot();
    }
}
