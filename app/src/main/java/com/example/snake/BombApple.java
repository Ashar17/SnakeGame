package com.example.snake;

import android.content.Context;
import android.graphics.Point;

class BombApple extends AbstractApple implements IObstacle {

    private boolean isBombAppleOnScreen = false;
    private long bombAppleStartTime;
    private final long BOMB_APPLE_DURATION = 9000;

    BombApple(Context context, Point sr, int s) {
        super(context, sr, s, R.drawable.bombapple);
    }

    public void spawn(){
        spawnApple();
    }
    public void isOnScreen(){
        isBombAppleOnScreen = true;
        bombAppleStartTime = System.currentTimeMillis();
    }

    @Override
    public void offScreen() {
        setOffScreen();
    }

    public void isNotOnScreen(){
        isBombAppleOnScreen = false;
    }

    public boolean getOnScreen(){
        return isBombAppleOnScreen;
    }

    public long getTime(){
        return bombAppleStartTime;
    }

    public long getDuration(){
        return BOMB_APPLE_DURATION;
    }
}
