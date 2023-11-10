package com.example.snake;

import android.graphics.Point;
import android.graphics.Rect;
import android.view.MotionEvent;
import java.util.ArrayList;

public class UIController implements InputObserver {
    public UIController(SnakeGameBroadcaster b){
        b.addObserver(this);
    }

    @Override
    public void handleInput(MotionEvent event, GameState gs, ArrayList<Rect> buttons){
        int i = event.getActionIndex();
        int x = (int) event.getX(i);
        int y = (int) event.getY(i);
        int eventType = event.getAction() & MotionEvent.ACTION_MASK;

        if(eventType == MotionEvent.ACTION_UP || eventType == MotionEvent.ACTION_POINTER_UP){
            if(buttons.get(HUD.PAUSE).contains(x, y)){
                // player pressed the pause button, respond based on game state

                // if the game is not paused
                if(!gs.getPaused()) {
                    gs.pause();
                }
                // if game over start new game
                else if(gs.getGameOver()){
                    gs.startNewGame();
                }
                // paused and not game over
                else if(gs.getPaused() && !gs.getGameOver()){
                    gs.resume();
                }
            }
        }
    }
}
