package com.example.snake;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;

public class HUD implements IDrawable {
    private int mTextFormatting;
    private int mHudFormatting;
    private int mScreenHeight;
    private int mScreenWidth;
    private GameState gs;

    HUD(Point size, GameState gs){
        mScreenHeight = size.y;
        mScreenWidth = size.x;
        mTextFormatting = size.x / 50;
        mHudFormatting = size.x / 25;
        this.gs = gs;
    }

    @Override
    public void draw(Canvas canvas, Paint paint){
        // Draw the HUD
        paint.setColor(Color.argb(255,255,255,255));
        paint.setTextSize(mHudFormatting);

        //if game just opened (start screen)
        if(gs.getPaused() && gs.getGameStart()){
            paint.setTextSize(mTextFormatting * 5);
            canvas.drawText("PRESS PLAY", mScreenWidth /4, mScreenHeight / 2, paint);
        }
        // if game is paused
        if(gs.getPaused() && !gs.getGameOver() && !gs.getGameStart()){
            paint.setTextSize(mTextFormatting * 5);
            canvas.drawText("RESUME", mScreenWidth / 4, mScreenHeight /2 ,paint);
        }

        // if player has died
        if(gs.getPaused() && gs.getGameOver()){
            paint.setTextSize(mHudFormatting);
            canvas.drawText("High Score: " + gs.getHighScore(), mHudFormatting,mHudFormatting, paint);
            canvas.drawText("Score: " + gs.getScore(), mHudFormatting,mHudFormatting * 2, paint);
            paint.setTextSize(mTextFormatting * 5);
            canvas.drawText("GAME OVER", mScreenWidth / 4, mScreenHeight /2 ,paint);
        }
    }
}