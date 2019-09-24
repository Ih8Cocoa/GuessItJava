package com.meow.guessitjava.screens.game;

enum BuzzType {
    CORRECT(new long[] {100, 100, 100, 100, 100, 100}),
    GAME_OVER(new long[] {0, 150}),
    COUNTDOWN_PANIC(new long[] {0, 400}),
    NO_BUZZ(new long[] {0});

    public final long[] buzzPattern;

    BuzzType(long[] pattern) {
        buzzPattern = pattern;
    }
}