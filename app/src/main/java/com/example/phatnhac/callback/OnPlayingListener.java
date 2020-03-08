package com.example.phatnhac.callback;

public interface OnPlayingListener {
    // *1 là kiểu dữ liệu nhận về
    // *2 là tên phương thức
    // *3 là kiểu dữ liệu muốn gửi
    void getTotal(int total);
    void positionPlaying(int positon);
    void onComplete();
}
