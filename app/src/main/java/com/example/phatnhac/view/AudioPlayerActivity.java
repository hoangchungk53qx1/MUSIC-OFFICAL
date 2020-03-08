package com.example.phatnhac.view;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.phatnhac.R;
import com.example.phatnhac.callback.OnPlayingListener;
import com.example.phatnhac.model.Audio;
import com.example.phatnhac.service.PlayerService;

import java.text.SimpleDateFormat;

import de.hdodenhof.circleimageview.CircleImageView;

public class AudioPlayerActivity extends AppCompatActivity implements OnPlayingListener {

    private CircleImageView imgAvatar;
    private SeekBar sbProgress;
    private ImageView imgPrevious,imgPlay,imgNext;
    private TextView txtName,txtSinger,txtPositionTime,txtTotalTime;
    private Audio audio;
    private Handler handler = new Handler();
    private int count;
    private PlayerService playerService;
    private SimpleDateFormat simpleDateFormat;

    private boolean isPlaying = false;
    private boolean isPlayerCreate = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_player);

        initView();
        initData();
        initEvents();
    }

    private void initEvents() {
        sbProgress.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                playerService.changePostion(seekBar.getProgress());
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void initData() {
        // Nhận Audio từ HouseActivity
        audio = (Audio) getIntent().getSerializableExtra("audio");

        // Gán hình ảnh lên ImageView
        Glide.with(this)
                .load(audio.getImage())
                .into(imgAvatar);

        // Gán tên và tên ca sỹ lên TextView
        txtName.setText(audio.getName());
        txtSinger.setText("Ca sỹ : " + audio.getSinger());
    }

    @Override
    protected void onStart() {
        super.onStart();

        Intent intent = new Intent(this,PlayerService.class);
        bindService(intent,serviceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();

        unbindService(serviceConnection);
    }

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            PlayerService.SampleBinder sampleBinder = (PlayerService.SampleBinder) service;
            playerService = sampleBinder.getBinder();
            playerService.setOnPlayingListener(AudioPlayerActivity.this);
            playerService.play(audio.getLink());
            isPlaying = true;
            runRotateImage.run();

            Glide.with(getApplicationContext())
                    .load(R.drawable.ic_pause_black_24dp)
                    .into(imgPlay);
        }
        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    private Runnable runRotateImage = new Runnable() {
        @Override
        public void run() {
            imgAvatar.setRotation(count);
            count++;

            if (count == 361){
                count = 0;
            }

            handler.postDelayed(this,20);
        }
    };

    @SuppressLint("SimpleDateFormat")
    private void initView() {
        imgAvatar = findViewById(R.id.imgAvatar);
        sbProgress = findViewById(R.id.sbProgress);
        imgPrevious = findViewById(R.id.imgPrevious);
        imgPlay = findViewById(R.id.imgPlay);
        imgNext = findViewById(R.id.imgNext);
        txtName = findViewById(R.id.txtName);
        txtSinger = findViewById(R.id.txtSinger);
        txtPositionTime = findViewById(R.id.txtPositionTime);
        txtTotalTime = findViewById(R.id.txtTotalTime);

        simpleDateFormat = new SimpleDateFormat("mm:ss");
    }

    public void onClickPlayAndPause(View view) {
        if (isPlaying){
            isPlaying = false;
            playerService.pause();
            handler.removeCallbacks(runRotateImage);

            Glide.with(this)
                    .load(R.drawable.ic_play_arrow_black_24dp)
                    .into(imgPlay);
        }else {
            isPlaying = true;
            runRotateImage.run();

            Glide.with(this)
                    .load(R.drawable.ic_pause_black_24dp)
                    .into(imgPlay);
            playerService.resume();
        }
    }

    @Override
    public void getTotal(int total) {
        sbProgress.setMax(total);
        txtTotalTime.setText(simpleDateFormat.format(total));
        Log.d("ehet4ht4h",total + "");
    }

    @Override
    public void positionPlaying(int positon) {
        sbProgress.setProgress(positon);
        txtPositionTime.setText(simpleDateFormat.format(positon));
    }

    @Override
    public void onComplete() {
        handler.removeCallbacks(runRotateImage);
    }
}
