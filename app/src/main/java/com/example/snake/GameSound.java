package com.example.snake;

import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.content.Context;
import java.io.IOException;

public class GameSound {
    // Initialize Sound Variables
    private SoundPool mSP;
    private int mEatID;
    private int mCrashID;

    private int mHurtID;



    GameSound(Context context){
        // Declare sound variables in constructor
        mEatID = -1;

        mCrashID = -1;


        mHurtID = -1;

        // Initialize the SoundPool upon creation of GameSound object
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build();

            mSP = new SoundPool.Builder()
                    .setMaxStreams(5)
                    .setAudioAttributes(audioAttributes)
                    .build();
        } else {
            mSP = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
        }
        try {
            AssetManager assetManager = context.getAssets();
            AssetFileDescriptor descriptor;

            // Prepare the sounds in memory
            descriptor = assetManager.openFd("get_apple.ogg");
            mEatID = mSP.load(descriptor, 0);

            descriptor = assetManager.openFd("snake_death.ogg");
            mCrashID = mSP.load(descriptor, 0);

            descriptor = assetManager.openFd("get_bad_apple.ogg");
            mHurtID = mSP.load(descriptor, 0);

        } catch (
                IOException e) {
            // Error
        }

    }

    void eatAppleSound() {
        mSP.play(mEatID, 1, 1, 0, 0, 1);
    }

    void deathSound(){
        mSP.play(mCrashID, 1, 1, 0, 0, 1);
    }


    void badAppleSound(){
        mSP.play(mHurtID, 1, 1, 0, 0, 1);
    }

}


