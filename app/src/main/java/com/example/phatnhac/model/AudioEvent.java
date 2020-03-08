package com.example.phatnhac.model;

public class AudioEvent {

    private int type;
    private String audio;

    public AudioEvent(int type) {
        this.type = type;
    }

    public AudioEvent() {
    }

    public String getAudio() {
        return audio;
    }

    public void setAudio(String audio) {
        this.audio = audio;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
