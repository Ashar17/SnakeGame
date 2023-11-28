package com.example.snake;

public interface ISnakeGameBroadcaster {
    void addObserver(InputObserver observer);
    void newGame();
}
