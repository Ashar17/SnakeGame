package com.example.snake;

import android.content.Context;
import android.graphics.Point;

class BadApple extends AbstractApple implements IObstacle {

    private boolean isBadAppleOnScreen = false;
    private long badAppleStartTime;
    private final long BAD_APPLE_DURATION = 5000; // 5 seconds in milliseconds

    BadApple(Context context, Point sr, int s) {
        super(context, sr, s, R.drawable.badapple);
    }
    public void spawn(){
        spawnApple();
    }

    public void isOnScreen(){
        isBadAppleOnScreen = true;
        badAppleStartTime = System.currentTimeMillis();
    }

    @Override
    public void offScreen() {
        setOffScreen();
    }

    public void isNotOnScreen(){
        isBadAppleOnScreen = false;
    }

    public boolean getOnScreen(){
        return isBadAppleOnScreen;
    }

    public long getTime(){
        return badAppleStartTime;
    }

    public long getDuration(){
        return BAD_APPLE_DURATION;
    }
}