package com.example.snake;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.SystemClock;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import java.util.ArrayList;
import android.util.Log;

class SnakeGame extends SurfaceView implements Runnable, ISnakeGameBroadcaster {

    // Objects for the game loop/thread
    private Thread mThread = null;
    // Control pausing between updates
    private long mNextFrameTime;
    // Array List for observers
    private ArrayList<InputObserver> inputObservers = new ArrayList();
    // UI controller class
    UIController mUIController;
    // Initialize Game State
    private GameState mGameState;
    // for playing sound effects
    private GameSound mSound;

    // The size in segments of the playable area
    private final int NUM_BLOCKS_WIDE = 40;
    private int mNumBlocksHigh;

    // Objects for drawing
    private Canvas mCanvas;
    private SurfaceHolder mSurfaceHolder;
    private Paint mPaint;
    // Initialize HUD object
    private HUD mHUD;

    // A snake ssss
    private Snake mSnake;
    // And an apple
    private Apple mApple;
    //Add a Bomb Apple
    private BombApple mBombApple;
    //add the new object bad apple
    private BadApple mBadApple;
    private ArrayList<IObstacle> obstacles;
    private ArrayList<IDrawable> gameObjects;
    long TARGET_FPS = 10;
    // used to calculate elapsed time
    long startTime, elapsedMilliSeconds;
    double elapsedSeconds;


    // This is the constructor method that gets called
    // from com.example.snake.SnakeActivity
    public SnakeGame(Context context, Point size) {
        super(context);
        mGameState = new GameState(context);
        //HUD is now a singleton class
        mHUD = HUD.getInstance(context, size, mGameState);
        mUIController = new UIController(this);

        // Work out how many pixels each block is
        int blockSize = size.x / NUM_BLOCKS_WIDE;
        // How many blocks of the same size will fit into the height
        mNumBlocksHigh = size.y / blockSize;

        // Initialize Sound Object
        mSound = new GameSound(context);


        // Initialize the drawing objects
        mSurfaceHolder = getHolder();
        mPaint = new Paint();

        // Call the constructors of our two game objects
        mApple = new Apple(context,
                new Point(NUM_BLOCKS_WIDE,
                        mNumBlocksHigh),
                blockSize);

        //Call Constructor of Bomb Apple
        mBombApple = new BombApple(context,
                new Point(NUM_BLOCKS_WIDE,
                        mNumBlocksHigh),
                blockSize);


        //call the constructor of the newly created bad apple
        mBadApple = new BadApple(context, new Point(NUM_BLOCKS_WIDE,
                mNumBlocksHigh),
                blockSize);

        mSnake = new Snake(context,
                new Point(NUM_BLOCKS_WIDE,
                        mNumBlocksHigh),
                blockSize);

        // put obstacles in array list
        obstacles = new ArrayList<>();
        obstacles.add(mBadApple);
        obstacles.add(mBombApple);

        // put game objects into array list
        gameObjects = new ArrayList<>();
        gameObjects.add(mSnake);
        gameObjects.add(mApple);
        gameObjects.add(mBadApple);
        gameObjects.add(mBombApple);

    }

    // To make SnakeGame a broadcaster for the observer design pattern
    public void addObserver(InputObserver observer){
        inputObservers.add(observer);
    }


    // Called to start a new game
    public void newGame() {

        // reset the snake
        mSnake.reset(NUM_BLOCKS_WIDE, mNumBlocksHigh);

        // Get the apple ready for dinner
        mApple.spawnApple();

        for(IObstacle o : obstacles){
            o.spawn();
            o.isOnScreen();
        }

        // Resets the Score and changes state variables
        mGameState.startNewGame();

        // Setup mNextFrameTime so an update can triggered
        mNextFrameTime = System.currentTimeMillis();

        // save game start time
        startTime = SystemClock.elapsedRealtime();
    }


    // Handles the game loop
    @Override
    public void run() {
        while (mGameState.getIsPlaying()) {
            if(!mGameState.getPaused()) {
                // Update 10 times a second
                if (updateRequired()) {
                    update();
                }
            }

            draw();
        }
    }


    // Check to see if it is time for an update
    public boolean updateRequired() {

        // Run at 10 frames per second
        TARGET_FPS = 10;
        // There are 1000 milliseconds in a second
        final long MILLIS_PER_SECOND = 1000;

        // Are we due to update the frame
        if(mNextFrameTime <= System.currentTimeMillis()){
            // Tenth of a second has passed

            // Setup when the next update will be triggered
            mNextFrameTime =System.currentTimeMillis()
                    + MILLIS_PER_SECOND / TARGET_FPS;

            // Return true so that the update and draw
            // methods are executed
            return true;
        }

        return false;
    }

    // check if it's time for difficulty to increase
    public boolean checkTime(){
        // calculate time
        elapsedMilliSeconds = SystemClock.elapsedRealtime() - startTime;
        elapsedSeconds = elapsedMilliSeconds / 1000.0;

        // if it's been twenty seconds, then increase difficulty
        if (elapsedSeconds > 20){
            return true;
        }
        return false;
    }


    // Update all the game objects
    public void update() {

        // Move the snake
        mSnake.move();

        // Did the head of the snake eat the apple?
        if(mSnake.checkDinner(mApple.getLocation())){
//            long updatedFps = TARGET_FPS *10;
//            updateRequired(updatedFps);

            // This reminds me of Edge of Tomorrow.
            // One day the apple will be ready!
            mApple.spawnApple();

            // Add to Score
            mGameState.increaseScore();

            // Play a sound
            mSound.eatAppleSound();
        }

        // Did the head of the snake eat the bad apple?
        if(mSnake.checkFoodPoisoning(mBadApple.getLocation())){

            mBadApple.spawn();

            // starts counting how long apple is on screen
            mBadApple.isOnScreen();

            // Subtract from score
            mGameState.decreaseScore();

            // Play a sound
            mSound.badAppleSound();
        }

        // Did the snake die? or did it collide with bomb apple?
        if (mSnake.detectDeath(mGameState.getScore()) || mSnake.checkExplosion(mBombApple.getLocation())) {
            mSound.deathSound();
            mGameState.endGame();
        }

        // check if it's time to respawn all obstacles
        for (IObstacle o : obstacles){
            if(o.getOnScreen() && (System.currentTimeMillis() - o.getTime() >= o.getDuration())) {
                o.spawn();
                o.isOnScreen();
            }
        }

        // is it time to increase difficulty?
        if(checkTime()){
            // TO DO: increase speed of snake

            // TO DO: spawn more apples or obstacles


            Log.i("snakeGame", Double.toString(elapsedSeconds));

            // reset start time
            startTime = SystemClock.elapsedRealtime();
        }

    }


    // Do all the drawing
    public void draw() {
        // Get a lock on the mCanvas
        if (mSurfaceHolder.getSurface().isValid()) {
            mCanvas = mSurfaceHolder.lockCanvas();

            // Fill the screen with a color
            mCanvas.drawColor(Color.argb(255, 26, 128, 182));

            // Set the size and color of the mPaint for the text
            mPaint.setColor(Color.argb(255, 255, 255, 255));
            mPaint.setTextSize(120);

            // Draw all game objects
            for(IDrawable d : gameObjects){
                d.draw(mCanvas, mPaint);
            }

            if(!mGameState.getPaused()) {
                // Draw the score
                mCanvas.drawText("" + mGameState.getScore(), 20, 120, mPaint);
                // Draw pause control
                mHUD.drawControls(mCanvas, mPaint);
            } else {
                // Draw some text while paused
                mHUD.draw(mCanvas, mPaint);
            }


            // Unlock the mCanvas and reveal the graphics for this frame
            mSurfaceHolder.unlockCanvasAndPost(mCanvas);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_UP:
                if (mGameState.getPaused() && mGameState.getGameStart()){
                    mGameState.resume();
                    newGame();

                    // Don't want to process snake direction for this tap
                    return true;
                }
                // if game is just paused, don't start new game !
                else if (mGameState.getPaused() && !mGameState.getGameOver() && !mGameState.getGameStart()){
                    mGameState.resume();

                    // Don't want to process snake direction for this tap as well
                    return true;
                }
                else if (mGameState.getPaused() && mGameState.getGameOver() && !mGameState.getGameStart()) {
                    // when game over screen is displayed, enable HUD buttons
                    for (InputObserver o : inputObservers) {
                        o.handleInput(motionEvent, mGameState, mHUD.getGameOverControls());
                    }
                    return true;
                }

                // when player touches HUD controls, handle input
                for (InputObserver o : inputObservers) {
                    o.handleInput(motionEvent, mGameState, mHUD.getControls());
                }

                if (mGameState.getPaused()){
                    // prevents pause button from updating snake direction
                    return true;
                }

                // Let the Snake class handle the input
                mSnake.switchHeading(motionEvent);
                break;

            default:
                break;

        }
        return true;
    }


    // Stop the thread
    public void pause() {
        mGameState.stopEverything();
        try {
            mThread.join();
        } catch (InterruptedException e) {
            // Error
        }
    }


    // Start the thread
    public void resume() {
        mGameState.startPlaying();
        mThread = new Thread(this);
        mThread.start();
    }

}
