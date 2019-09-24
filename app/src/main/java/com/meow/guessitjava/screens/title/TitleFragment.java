package com.meow.guessitjava.screens.title;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.meow.guessitjava.R;
import com.meow.guessitjava.databinding.FragmentTitleBinding;


/**
 * A simple {@link Fragment} subclass.
 */
public class TitleFragment extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // inflate the layout
        final FragmentTitleBinding binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_title, container, false
        );

        // set the on-click listener for the button that navigates to the playing screen
        final NavDirections toGameScreen = TitleFragmentDirections.actionTitleFragmentToGameFragment();
        final OnClickListener listener = Navigation.createNavigateOnClickListener(toGameScreen);
        binding.playButton.setOnClickListener(listener);

        return binding.getRoot();
    }

}
