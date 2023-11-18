package com.example.snake;

import android.util.Log;
import android.graphics.Rect;
import android.view.MotionEvent;
import java.util.ArrayList;

public class UIController implements InputObserver {
    private SnakeGameBroadcaster sg;
    public UIController(SnakeGameBroadcaster b){
        b.addObserver(this);
        this.sg = b;
    }

    @Override
    public void handleInput(MotionEvent event, GameState gs, ArrayList<Rect> buttons){
        int i = event.getActionIndex();
        int x = (int) event.getX(i);
        int y = (int) event.getY(i);
        int eventType = event.getAction() & MotionEvent.ACTION_MASK;

        if(eventType == MotionEvent.ACTION_UP || eventType == MotionEvent.ACTION_POINTER_UP){
            if(buttons.get(HUD.PAUSE).contains(x, y)){
                // if the game is not paused, pause it
                if(!gs.getPaused()) {
                    Log.i("UIController", "pause");
                    gs.pause();
                }
            }
            if (buttons.get(HUD.RESTART).contains(x, y)){
                // player pressed restart button, start new game
                 if(gs.getGameOver()){
                     gs.resume();
                     sg.newGame();
                 }
            }
        }
    }
}
