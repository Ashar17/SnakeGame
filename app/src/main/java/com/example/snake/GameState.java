package com.example.snake;

import android.content.Context;
import android.content.SharedPreferences;

public class GameState {
    private static volatile boolean mPaused = true;
    private static volatile boolean mPlaying = false;
    private static volatile boolean mGameOver = false;
    private static volatile boolean mGameStart = true;
    private int mScore;
    private int mHighScore;
    // This is how we will make all the high scores persist
    private SharedPreferences.Editor mEditor;

    GameState(SnakeGame sg, Context context){
        // Get the current high score
        SharedPreferences prefs;
        prefs = context.getSharedPreferences("HiScore",
                Context.MODE_PRIVATE);
        // Initialize the mEditor ready
        mEditor = prefs.edit();
        // Load high score from a entry in the file
        // labeled "hiscore"
        // if not available highscore set to zero 0
        mHighScore = prefs.getInt("hi_score", 0);
    }

    void startNewGame(){
        mScore = 0;
        mGameStart = true;
        resume();
    }

    public void endGame(){
        mGameOver = true;
        mPaused = true;
        if(mScore > mHighScore){
            mHighScore = mScore;
            // Save high score
            mEditor.putInt("hi_score", mHighScore);
            mEditor.commit();
        }
    }

    void increaseScore(){
        mScore++;
    }
    void decreaseScore(){
        mScore--;
    }
    int getScore(){
        return mScore;
    }
    int getHighScore(){
        return mHighScore;
    }
    void startPlaying(){
        mPlaying = true;
    }
    boolean getIsPlaying(){
        return mPlaying;
    }
    void pause(){
        mPaused = true;
    }
    void resume(){
        mGameOver = false;
        mPaused = false;
        mPlaying = true;
    }
    void stopEverything(){
        mPaused = true;
        mGameOver = true;
        mPlaying = false;
    }
    boolean getPaused(){
        return mPaused;
    }
    boolean getGameOver(){
        return mGameOver;
    }
    boolean getGameStart() {
        return mGameStart;
    }
    void setGameStart(boolean g){
        mGameStart = g;
    }
}
