package com.wangpan.kidplan;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class MediaPlayerUtil {
    private static String TAG = CommonUtils.COMMON_TAG + "MediaPlayerUtil";
    private MediaPlayer mMediaPlayer = new MediaPlayer();
    private static MediaPlayerUtil mMediaPlayUtil = new MediaPlayerUtil();
    private MediaPlayerStateCallback mCallback;
    private Timer mTimer;
    private TimerTask mTimerTask;

    private MediaPlayerUtil(){}
    public static MediaPlayerUtil getInstance() {
        return mMediaPlayUtil;
    }
    public interface MediaPlayerStateCallback {
        void onPrepared(int duration, int curPosition);
        void onProgress(int curPosition);
    }

    public void setMediaPlayerStateCallback(MediaPlayerStateCallback callback) {
        mCallback = callback;
    }

    public void startPlay(Context context, Uri uri) {
        Log.d(TAG, "startPlay, uri:" + uri);
        try {
            mMediaPlayer.setDataSource(context.getApplicationContext(), uri);
            mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mMediaPlayer.prepareAsync();
            mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(final MediaPlayer mediaPlayer) {
                    Log.d(TAG, "mediaPlayer.getDuration():"+mMediaPlayer.getDuration());
                    mCallback.onPrepared(mMediaPlayer.getDuration(), mMediaPlayer.getCurrentPosition());
                    mMediaPlayer.start();
                }
            });
            mMediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                @Override
                public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
                    Log.e(TAG, "onError. " + i + " " + i1);
                    return false;
                }
            });

            //play progress
            mTimer = new Timer();
            mTimerTask = new TimerTask(){

                @Override
                public void run() {
                    mCallback.onProgress(mMediaPlayer.getCurrentPosition());
                }
            };
            mTimer.schedule(mTimerTask, 0, 10);
        } catch (IOException e) {
            Log.e(TAG, "exp.", e);
            e.printStackTrace();
        }
    }

    public void stopPlay() {
        if (mMediaPlayer != null && mMediaPlayer.isPlaying()) {
            mMediaPlayer.stop();
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }
}
