package com.example.snake;

import android.content.Context;
import android.graphics.Point;

class BombApple extends AbstractApple {

    BombApple(Context context, Point sr, int s) {
        super(context, sr, s, R.drawable.bombapple);
    }
}
