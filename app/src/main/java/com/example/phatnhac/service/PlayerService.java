package com.example.phatnhac.service;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.example.phatnhac.callback.OnPlayingListener;

import java.io.IOException;

public class PlayerService extends Service {

    private static MediaPlayer mediaPlayer;
    private OnPlayingListener onPlayingListener;
    private Handler handler = new Handler();

    private IBinder binder = new SampleBinder();

    public class SampleBinder extends Binder{

        public PlayerService getBinder(){
            return PlayerService.this;
        }
    }

    public void setOnPlayingListener(OnPlayingListener onPlayingListener){
        this.onPlayingListener = onPlayingListener;
    }

    private Runnable currentPositionListener = new Runnable() {
        @Override
        public void run() {
            onPlayingListener.positionPlaying(mediaPlayer.getCurrentPosition());
            handler.postDelayed(this,1000);
        }
    };

    public void play(String path){
        if (mediaPlayer == null) {
            mediaPlayer = new MediaPlayer();
            try {
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                mediaPlayer.setDataSource(path);
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        onPlayingListener.onComplete();
                        handler.removeCallbacks(currentPositionListener);
                    }
                });
                mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        mp.start();
                        onPlayingListener.getTotal(mp.getDuration());
                        handler.postDelayed(currentPositionListener, 0);
                    }
                });
                mediaPlayer.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            cancel();
            play(path);
        }
    }

    private void cancel(){
        if (mediaPlayer != null){
            mediaPlayer.reset();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    public void pause(){
        if (mediaPlayer != null){
            if (mediaPlayer.isPlaying()){
                mediaPlayer.pause();
                handler.removeCallbacks(currentPositionListener);
            }
        }
    }

    public void resume(){
        if (mediaPlayer != null){
            if (!mediaPlayer.isPlaying()){
                mediaPlayer.start();
                handler.postDelayed(currentPositionListener,0);
            }
        }
    }

    public void changePostion(int time){
        if (mediaPlayer != null){
            mediaPlayer.seekTo(time);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }
}
