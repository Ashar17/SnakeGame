package com.example.snake;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import java.util.ArrayList;

public class HUD implements IDrawable {
    // lets the HUD class hold an instance of itself
    Context context;
    private static HUD mHUD;
    private int mTextFormatting;
    private int mHudFormatting;
    private int mScreenHeight;
    private int mScreenWidth;
    private Bitmap bitmapScreen;
    private GameState gs;
    private ArrayList<Rect> controls, gameOverControls;
    // declare array list index
    static int PAUSE = 0; // index for in-game controls
    static int RESTART = 0; // index for game over screen
    static int RETURNBUTTON = 1; // index for game over screen

    // making the constructor private ensures only the HUD class can instantiate itself (singleton)
    private HUD(Context context, Point size, GameState gs){
        this.context = context;
        mScreenHeight = size.y;
        mScreenWidth = size.x;
        mTextFormatting = size.x / 50;
        mHudFormatting = size.x / 30;
        this.gs = gs;

        // prep artwork
        // Load the image to the bitmap
        bitmapScreen = BitmapFactory.decodeResource(context.getResources(), R.drawable.start_screen);
        // Resize the bitmap
        bitmapScreen = Bitmap.createScaledBitmap(bitmapScreen, mScreenWidth, mScreenHeight, false);

        prepareControls();
    }

    // allows other classes to access this class
    static HUD getInstance(Context context, Point size, GameState gs){
        if(mHUD == null) {
            mHUD = new HUD(context, size, gs);
        }
        return mHUD;
    }

    void prepareControls(){
        int buttonWidth = mScreenWidth / 14;
        int buttonHeight = mScreenHeight / 12;
        int buttonPadding = mScreenWidth / 90;

        // preparing boundaries of pause button based on screen size
        Rect pause = new Rect (mScreenWidth - buttonPadding - buttonWidth, buttonPadding,
                mScreenWidth - buttonPadding, buttonPadding + buttonHeight);

        // preparing boundaries of restart button based on screen size
        Rect restart = new Rect ((mScreenWidth / 5) - 10, ((mScreenHeight / 5) * 4) - 160,
                (mScreenWidth / 5) + 450, ((mScreenHeight / 5) * 4) - 40);

        // preparing boundaries of return button based on screen size
        Rect returnButton = new Rect ((mScreenWidth / 5) * 3 - 10, ((mScreenHeight / 5) * 4) - 160,
                (mScreenWidth / 5) * 3 + 410, ((mScreenHeight / 5) * 4) - 40);

        controls = new ArrayList<>();
        controls.add(PAUSE, pause);

        gameOverControls = new ArrayList<>();
        gameOverControls.add(RESTART, restart);
        gameOverControls.add(RETURNBUTTON, returnButton);
    }

    @Override
    public void draw(Canvas canvas, Paint paint){
        // Draw the HUD
        paint.setColor(Color.argb(255,255,255,255));
        paint.setTextSize(mHudFormatting);

        // if game just opened (start screen)
        if(gs.getPaused() && gs.getGameStart()){
            canvas.drawBitmap(bitmapScreen, 0, 0, paint);
        }
        // if game is paused
        if(gs.getPaused() && !gs.getGameOver() && !gs.getGameStart()){
            paint.setTextSize(mHudFormatting);
            // Display the top three high scores
            for (int i = 0; i < gs.getHighScores().size(); i++) {
                int score = gs.getHighScores().get(i);
                canvas.drawText("Top " + (i + 1) + ": " + score, mHudFormatting, mHudFormatting * (i + 1), paint);
            }

            canvas.drawText("Current Score: " + gs.getScore(), mHudFormatting, mHudFormatting * (gs.getHighScores().size() + 1), paint);
            paint.setTextSize(mTextFormatting * 5);
            canvas.drawText("PAUSED", mScreenWidth / 3, mScreenHeight /2 ,paint);
        }

        // if player has died
        if(gs.getPaused() && gs.getGameOver()) {
            paint.setTextSize(mHudFormatting);
            // Display the top three high scores here as well
            for (int i = 0; i < gs.getHighScores().size(); i++) {
                int score = gs.getHighScores().get(i);
                canvas.drawText("Top " + (i + 1) + ": " + score, mHudFormatting, mHudFormatting * (i + 1), paint);
            }
            canvas.drawText("Current Score: " + gs.getScore(), mHudFormatting, mHudFormatting * (gs.getHighScores().size() + 1), paint);
            paint.setTextSize(mTextFormatting * 5);
            canvas.drawText("GAME OVER", mScreenWidth / 5, mScreenHeight / 2, paint);
            drawGameOverScreen(canvas, paint);
        }
    }

    protected void drawControls(Canvas canvas, Paint paint){
        paint.setColor(Color.argb(130,255,255,255));

        for(Rect r : controls){
            canvas.drawRect(r.left, r.top, r.right, r.bottom, paint);
        }

        paint.setColor(Color.argb(255,0,0,0));
        paint.setTextSize((float) (mHudFormatting / 1.40));
        //display pause button text
        canvas.drawText("Pause", mScreenWidth - (mScreenWidth / 90) - (mScreenWidth / 14),
                (mScreenWidth / 90) + (mScreenHeight / 14), paint);
    }

    protected void drawGameOverScreen(Canvas canvas, Paint paint){
        paint.setColor(Color.argb(155,255,255,255));

        for (Rect r: gameOverControls){
            canvas.drawRect(r.left, r.top, r.right, r.bottom, paint);
        }

        paint.setTextSize(mTextFormatting * 3);
        paint.setColor(Color.argb(255,0,0,0));
        //display restart button text
        canvas.drawText("Restart", mScreenWidth / 5, ((mScreenHeight / 5) * 4) - 50, paint);

        //display return button text
        canvas.drawText("Return", (mScreenWidth / 5) * 3, ((mScreenHeight / 5) * 4) - 50, paint);
    }

    ArrayList<Rect> getControls(){
        return controls;
    }

    ArrayList<Rect> getGameOverControls() {
        return gameOverControls;
    }
}