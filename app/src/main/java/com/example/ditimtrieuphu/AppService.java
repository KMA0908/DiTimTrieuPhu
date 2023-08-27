package com.example.ditimtrieuphu;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;

import androidx.annotation.Nullable;

/**
 * Minh: them service cho app
 */
public class AppService extends Service {
    private IBinder mBinder;
    private MediaPlayer mBackgroundMediaPlayer;
    private boolean mBgMusicUserSetting;

    @Override
    public void onCreate() {
        super.onCreate();
        mBinder = new AppServiceBinder();
        mBackgroundMediaPlayer = new MediaPlayer();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    /**
     * Bat dau phat mot sound moi
     * @param soundId: res cua sound
     * @param looping: loop nhac hay khong
     */
    public void startBackgroundMusic(int soundId, boolean looping) {
        setBackgroundMusic(soundId, looping);
        startBackgroundMusic();
    }

    /**
     * Bat dau phat nhac, su dung nhac da duoc set truoc do
     */
    public void startBackgroundMusic() {
        if (!mBackgroundMediaPlayer.isPlaying()) {
            mBackgroundMediaPlayer.start();
        }
    }

    public void setBgMusicUserSetting(boolean bgPlaying) {
        this.mBgMusicUserSetting = bgPlaying;
    }
    public boolean isBgMusicUserSetting() {
        return mBgMusicUserSetting;
    }

    public void pauseBackgroundMusic() {
        mBackgroundMediaPlayer.pause();
    }

    public void stopBackgroundMusic() {
        mBackgroundMediaPlayer.stop();
    }

    /**
     * Set file mp3 cho nhac nen
     */
    private void setBackgroundMusic(int soundId, boolean looping) {
        mBackgroundMediaPlayer = MediaPlayer.create(this, soundId);
        mBackgroundMediaPlayer.setLooping(looping);
    }

    public class AppServiceBinder extends Binder {
        public AppService getAppService() {
            return AppService.this;
        }
    }
}
