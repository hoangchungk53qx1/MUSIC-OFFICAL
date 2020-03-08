package com.example.phatnhac.view;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.phatnhac.R;
import com.example.phatnhac.adapter.AudioAdaper;
import com.example.phatnhac.model.Audio;
import com.example.phatnhac.service.PlayerService;

import java.util.ArrayList;

public class HouseActivity extends AppCompatActivity {

    private RecyclerView recyclerListAudio;
    private EditText searchAudio;
    private AudioAdaper audioAdaper;
    private ArrayList<Audio> arrayAudio = new ArrayList<>();
    private ArrayList<Audio> arrayAudioSave = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_house);

        initView();
        createDemo();
        createRecycler();
        initEvents();
    }

    private void initEvents() {
        searchAudio.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(s)){
                    arrayAudio.clear();
                    arrayAudio.addAll(arrayAudioSave);
                    audioAdaper.notifyDataSetChanged();
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    private void createDemo() {
        Audio audio = new Audio();
        audio.setName("Hơn Cả Yêu");
        audio.setAuthor("Đức Phúc");
        audio.setSinger("Đức Phúc");
        audio.setId("185845");
        audio.setLink("https://firebasestorage.googleapis.com/v0/b/autoconnectwifi-c33ea.appspot.com/o/Hon%20Ca%20Yeu%20-%20Duc%20Phuc.mp3?alt=media&token=cddb9870-70d4-4232-ab2f-92171c65b1a1");
        audio.setImage("https://photo-resize-zmp3.zadn.vn/w240_r1x1_jpeg/cover/a/9/e/d/a9ed142c215560ab45f6b2b433907f90.jpg");
        audio.setDate(System.currentTimeMillis() - 100000);

        Audio audio1 = new Audio();
        audio1.setName("Tình Đẹp Đến Mấy Cũng Tàn");
        audio1.setAuthor("Việt");
        audio1.setSinger("Việt");
        audio1.setId("185846");
        audio1.setLink("https://firebasestorage.googleapis.com/v0/b/autoconnectwifi-c33ea.appspot.com/o/Tinh%20Dep%20Den%20May%20Cung%20Tan%20-%20Viet.mp3?alt=media&token=c4312845-5f78-4f61-9ee7-cc691a27d2b7");
        audio1.setImage("https://data.chiasenhac.com/data/cover/114/113231.jpg");
        audio1.setDate(System.currentTimeMillis() - 150000);

        Audio audio2 = new Audio();
        audio2.setName("Tướng Quân");
        audio2.setAuthor("Đình Dũng");
        audio2.setSinger("Nhật Phong");
        audio2.setId("185847");
        audio2.setLink("https://firebasestorage.googleapis.com/v0/b/autoconnectwifi-c33ea.appspot.com/o/Tuong%20Quan%20-%20Nhat%20Phong%20%5B128kbps_MP3%5D.mp3?alt=media&token=cd38f848-b898-4137-88e2-ad127aa0df45");
        audio2.setImage("https://data.chiasenhac.com/data/cover/108/107676.jpg");
        audio2.setDate(System.currentTimeMillis() - 156000);

        arrayAudio.add(audio);
        arrayAudio.add(audio1);
        arrayAudio.add(audio2);
        arrayAudio.add(audio);
        arrayAudio.add(audio1);
        arrayAudio.add(audio2);
        arrayAudio.add(audio);
        arrayAudio.add(audio1);
        arrayAudio.add(audio2);
        arrayAudio.add(audio);
        arrayAudio.add(audio1);
        arrayAudio.add(audio2);
        arrayAudio.add(audio);
        arrayAudio.add(audio1);
        arrayAudio.add(audio2);
        arrayAudio.add(audio);
        arrayAudio.add(audio1);
        arrayAudio.add(audio2);
        arrayAudio.add(audio);
        arrayAudio.add(audio1);
        arrayAudio.add(audio2);
        arrayAudio.add(audio);
        arrayAudio.add(audio1);
        arrayAudio.add(audio2);
        arrayAudio.add(audio);
        arrayAudio.add(audio1);
        arrayAudio.add(audio2);
        arrayAudio.add(audio);
        arrayAudio.add(audio1);
        arrayAudio.add(audio2);
        arrayAudio.add(audio);
        arrayAudio.add(audio1);
        arrayAudio.add(audio2);
        arrayAudio.add(audio);
        arrayAudio.add(audio1);
        arrayAudio.add(audio2);
        arrayAudio.add(audio);
        arrayAudio.add(audio1);
        arrayAudio.add(audio2);
        arrayAudio.add(audio);
        arrayAudio.add(audio1);
        arrayAudio.add(audio2);

        arrayAudioSave.addAll(arrayAudio);
    }

    @Override
    protected void onStart() {
        super.onStart();

        Intent intent = new Intent(this, PlayerService.class);
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
        }
        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    private void createRecycler() {
        recyclerListAudio.setHasFixedSize(true);
        recyclerListAudio.setLayoutManager(new LinearLayoutManager(this));
        audioAdaper = new AudioAdaper(arrayAudio);
        recyclerListAudio.setAdapter(audioAdaper);
    }

    private void initView() {
        recyclerListAudio = findViewById(R.id.recyclerListAudio);
        searchAudio       = findViewById(R.id.searchAudio);
    }

    public void onClickSearch(View view) {
        String nameSearch = searchAudio.getText().toString();

        if (!TextUtils.isEmpty(nameSearch)){
            arrayAudio.clear();

            for (int i=0; i < arrayAudioSave.size(); i++){
                if (arrayAudioSave.get(i).getName().toLowerCase().contains(nameSearch.toLowerCase())){
                    arrayAudio.add(arrayAudioSave.get(i));
                }
            }
            audioAdaper.notifyDataSetChanged();
        }
    }
}
