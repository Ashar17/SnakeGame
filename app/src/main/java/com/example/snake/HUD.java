package com.example.snake;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import java.util.ArrayList;

public class HUD implements IDrawable {
    // lets the HUD class hold an instance of itself
    private static HUD mHUD;
    private int mTextFormatting;
    private int mHudFormatting;
    private int mScreenHeight;
    private int mScreenWidth;
    private GameState gs;
    private ArrayList<Rect> controls;
    // declare array list index
    static int PAUSE = 0;

    // making the constructor private ensures only the HUD class can instantiate itself (singleton)
    private HUD(Point size, GameState gs){
        mScreenHeight = size.y;
        mScreenWidth = size.x;
        mTextFormatting = size.x / 50;
        mHudFormatting = size.x / 25;
        this.gs = gs;

        prepareControls();
    }

    // allows other classes to access this class
    static HUD getInstance(Context context, Point size, GameState gs){
        if(mHUD == null) {
            mHUD = new HUD(size, gs);
        }
        return mHUD;
    }

    void prepareControls(){
        int buttonWidth = mScreenWidth / 14;
        int buttonHeight = mScreenHeight / 12;
        int buttonPadding = mScreenWidth / 90;

        // preparing boundaries of button based on screen size
        Rect pause = new Rect (mScreenWidth - buttonPadding - buttonWidth, buttonPadding,
                mScreenWidth - buttonPadding, buttonPadding + buttonHeight);

        controls = new ArrayList<>();
        controls.add(PAUSE, pause);
    }

    @Override
    public void draw(Canvas canvas, Paint paint){
        // Draw the HUD
        paint.setColor(Color.argb(255,255,255,255));
        paint.setTextSize(mHudFormatting);

        // if game just opened (start screen)
        if(gs.getPaused() && gs.getGameStart()){
            paint.setTextSize(mTextFormatting * 5);
            canvas.drawText("PRESS PLAY", mScreenWidth /4, mScreenHeight / 2, paint);
        }
        // if game is paused
        if(gs.getPaused() && !gs.getGameOver() && !gs.getGameStart()){
            paint.setTextSize(mHudFormatting);
            canvas.drawText("High Score: " + gs.getHighScore(), mHudFormatting, mHudFormatting, paint);
            canvas.drawText("Score: " + gs.getScore(), mHudFormatting, mHudFormatting * 2, paint);
            paint.setTextSize(mTextFormatting * 5);
            canvas.drawText("PAUSED", mScreenWidth / 3, mScreenHeight /2 ,paint);
        }

        // if player has died
        if(gs.getPaused() && gs.getGameOver()) {
            paint.setTextSize(mHudFormatting);
            canvas.drawText("High Score: " + gs.getHighScore(), mHudFormatting, mHudFormatting, paint);
            canvas.drawText("Score: " + gs.getScore(), mHudFormatting, mHudFormatting * 2, paint);
            paint.setTextSize(mTextFormatting * 5);
            canvas.drawText("GAME OVER", mScreenWidth / 4, mScreenHeight / 2, paint);
        }
    }

    protected void drawControls(Canvas canvas, Paint paint){
        paint.setColor(Color.argb(255,255,255,255));

        for(Rect r : controls){
            canvas.drawRect(r.left, r.top, r.right, r.bottom, paint);
        }
    }
    ArrayList<Rect> getControls(){
        return controls;
    }

}