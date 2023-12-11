package com.example.snake;

import android.content.Context;
import android.content.SharedPreferences;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class GameState {
    private static volatile boolean mPaused = true;
    private static volatile boolean mPlaying = false;
    private static volatile boolean mGameOver = false;
    private static volatile boolean mGameStart = true;
    private static volatile boolean mPowerUp = false;
    private static final int maxHighScore = 3;
    private int mScore;

    private List<Integer> mHighScores;

    // This is how we will make all the high scores persist
    private SharedPreferences.Editor mEditor;

    private GameSound mSound;
    private boolean mIsBackgroundMusicPlaying = false;

    GameState(Context context){
        // Get the current high score
        SharedPreferences prefs;
        prefs = context.getSharedPreferences("HiScore",
                Context.MODE_PRIVATE);
        // Initialize the mEditor ready
        mEditor = prefs.edit();
        // Load high score from a entry in the file
        // labeled "hiscore"
        // if not available highscore set to zero 0
//        mHighScore = prefs.getInt("hi_score", 0);

        // Load previous high scores
        mHighScores = new ArrayList<>();
        for (int i = 0; i < maxHighScore; i++) {
            mHighScores.add(prefs.getInt("hi_score_" + i, 0));
        }

        // Sort the high scores in descending order
        Collections.sort(mHighScores, Collections.reverseOrder());

        mSound = new GameSound(context);
    }

    void startNewGame(){
        mScore = 0;
        mGameStart = true;
        resume();
    }

    public void endGame(){
        mGameOver = true;
        mPaused = true;

        if (mScore > mHighScores.get(maxHighScore - 1)) {
            mHighScores.set(maxHighScore - 1, mScore);
            // Save high scores
            saveHighScores();
        }

        if (mIsBackgroundMusicPlaying) {
            mSound.stopBackgroundMusic();
            mIsBackgroundMusicPlaying = false;
        }
    }

    private void saveHighScores() {
        // Sort the high scores in descending order
        Collections.sort(mHighScores, Collections.reverseOrder());

        // Save the high scores to SharedPreferences
        for (int i = 0; i < maxHighScore; i++) {
            mEditor.putInt("hi_score_" + i, mHighScores.get(i));
        }
        mEditor.apply();
    }

    List<Integer> getHighScores() {
        return mHighScores;
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
    void startPlaying(){
        mPlaying = true;
        if (mIsBackgroundMusicPlaying) {
            mSound.startBackgroundMusic();
            mIsBackgroundMusicPlaying = true;
        }
    }
    boolean getIsPlaying(){
        return mPlaying;
    }
    void eatenPowerUp(){
        mPowerUp = true;
    }
    void noPowerUp(){
        mPowerUp = false;
    }
    void pause(){
        mPaused = true;
        if (mIsBackgroundMusicPlaying) {
            mSound.stopBackgroundMusic();
            mIsBackgroundMusicPlaying = false;
       }
    }
    void startScreen(){
        mGameStart = true;
        mPaused = true;
        mGameOver = false;
        if (mIsBackgroundMusicPlaying) {
            mSound.stopBackgroundMusic();
            mIsBackgroundMusicPlaying = false;
        }
    }
    void resume(){
        mGameOver = false;
        mPaused = false;
        mPlaying = true;
        mGameStart = false;

        if (!mIsBackgroundMusicPlaying) {
            mSound.startBackgroundMusic();
            mIsBackgroundMusicPlaying = true;
        }
    }
    void stopEverything(){
        mPaused = true;
        mGameOver = true;
        mPlaying = false;

        if (mIsBackgroundMusicPlaying) {
            mSound.stopBackgroundMusic();
            mIsBackgroundMusicPlaying = false;
        }
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
    boolean getPowerUp(){
        return mPowerUp;
    }

}
