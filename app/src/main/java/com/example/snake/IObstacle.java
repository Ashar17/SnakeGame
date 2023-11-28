package com.example.snake;

public interface IObstacle {
    void spawn();
    void isOnScreen();
    void isNotOnScreen();
    boolean getOnScreen();
    long getTime();
    long getDuration();
}
