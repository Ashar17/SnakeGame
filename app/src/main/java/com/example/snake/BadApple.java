package com.example.snake;

import android.content.Context;
import android.graphics.Point;

import java.util.Random;

class BadApple extends AbstractApple {

    BadApple(Context context, Point sr, int s) {
        super(context, sr, s, R.drawable.badapple);
    }
}