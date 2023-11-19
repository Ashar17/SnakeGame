package com.example.snake;

public interface SnakeGameBroadcaster {
    void addObserver(InputObserver observer);
    void newGame();
}
