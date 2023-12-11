package com.example.snake;

import android.content.res.AssetFileDescriptor;
import android.content.Context;
import java.io.IOException;
import android.media.MediaPlayer;

public class GameSound {
    private MediaPlayer mMediaPlayer;
    private Context mContext;

    GameSound(Context context) {
        mContext = context;
    }

    void eatAppleSound() {
        playSound("get_apple.ogg");
    }

    void deathSound() {
        playSound("snake_death.ogg");
    }

    void badAppleSound() {
        playSound("get_bad_apple.ogg");
    }

    void bombSound() { playSound("get_bomb.ogg"); }

    void startBackgroundMusic() {
        if (mMediaPlayer == null) {
            try {
                AssetFileDescriptor descriptor = mContext.getAssets().openFd("snake_music.mp3");
                mMediaPlayer = new MediaPlayer();
                mMediaPlayer.setDataSource(descriptor.getFileDescriptor(), descriptor.getStartOffset(), descriptor.getLength());
                descriptor.close();

                mMediaPlayer.setLooping(true);
                mMediaPlayer.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (!mMediaPlayer.isPlaying()) {
            //mMediaPlayer.setVolume(1f, 1f);  // Set volume before starting
            mMediaPlayer.start();
        }
    }

    void stopBackgroundMusic() {
        if (mMediaPlayer != null && mMediaPlayer.isPlaying()) {
            mMediaPlayer.pause();
        }
    }

    private void playSound(String fileName) {
        if (mMediaPlayer == null) {
            mMediaPlayer = new MediaPlayer();
        }

        try {
            mMediaPlayer.reset();
            AssetFileDescriptor descriptor = mContext.getAssets().openFd(fileName);
            mMediaPlayer.setDataSource(descriptor.getFileDescriptor(), descriptor.getStartOffset(), descriptor.getLength());
            descriptor.close();

            mMediaPlayer.prepare();
            mMediaPlayer.start();
        } catch (IOException | IllegalStateException e) {
            // Handle exceptions
        }
    }
}


