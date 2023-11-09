package com.example.snake;

import android.graphics.Rect;
import android.view.MotionEvent;
import java.util.ArrayList;
public interface InputObserver {
    void handleInput (MotionEvent event, GameState sg, ArrayList<Rect> controls);
}
