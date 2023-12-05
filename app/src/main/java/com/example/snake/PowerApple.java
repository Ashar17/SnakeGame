package com.example.snake;

import android.content.Context;
import android.graphics.Point;

class PowerApple extends AbstractApple implements IObstacle {

    private boolean isPowerAppleOnScreen = false;
    private long powerAppleStartTime;
    private long powerUpDuration;
    private final long POWER_APPLE_DURATION = 8000; // 8 seconds in milliseconds
    private final long RESPAWN_DURATION = 12000;

    PowerApple(Context context, Point sr, int s) {
        super(context, sr, s, R.drawable.powerapple);
    }
    public void spawn(){
        spawnApple();
    }
    public void isOnScreen(){
        isPowerAppleOnScreen = true;
        powerAppleStartTime = System.currentTimeMillis();
    }
    public void eatenPowerUp(){
        powerUpDuration = System.currentTimeMillis();
        powerAppleStartTime = System.currentTimeMillis();
    }
    @Override
    public void offScreen() {
        setOffScreen();
    }
    public void isNotOnScreen(){
        isPowerAppleOnScreen = false;
    }
    public boolean getOnScreen(){
        return isPowerAppleOnScreen;
    }
    public long getTime(){
        return powerAppleStartTime;
    }
    public long getEatenTime(){
        return powerUpDuration;
    }
    public long getPowerUpDuration(){
        return POWER_APPLE_DURATION;
    }
    public long getDuration(){
        return RESPAWN_DURATION;
    }
}