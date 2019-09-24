package com.meow.guessitjava.screens.score;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;

import com.meow.guessitjava.R;
import com.meow.guessitjava.databinding.FragmentScoreBinding;

/**
 * A simple {@link Fragment} subclass.
 */
public final class ScoreFragment extends Fragment {
    // create the viewModel and viewModelFactory
    private ScoreViewModel viewModel;

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
            // initialize ViewModelFactory and ViewModel
            final ScoreViewModelFactory factory = new ScoreViewModelFactory(score);
            viewModel = ViewModelProviders.of(this, factory).get(ScoreViewModel.class);

            // set the ViewModel inside the XML and lifecycleOwner
            binding.setScoreViewModel(viewModel);
            binding.setLifecycleOwner(this);
        }

        setupObservers();

        return binding.getRoot();
    }

    private void setupObservers() {
        final Observer<Boolean> observePlayAgain = playAgain -> {
            if (playAgain != null && playAgain) {
                final NavDirections restartDirection = ScoreFragmentDirections.actionRestart();
                NavHostFragment.findNavController(this).navigate(restartDirection);
                viewModel.onPlayAgainCompleted();
            }
        };
        viewModel.getEventPlayAgain().observe(this, observePlayAgain);
    }
}
