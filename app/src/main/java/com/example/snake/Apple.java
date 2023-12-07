package com.example.snake;

import android.content.Context;
import android.graphics.Point;

class Apple extends AbstractSpawnable {

    Apple(Context context, Point sr, int s) {
        super(context, sr, s, R.drawable.apple1);
    }

}
