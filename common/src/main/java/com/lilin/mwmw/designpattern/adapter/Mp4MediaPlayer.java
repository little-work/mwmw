package com.lilin.mwmw.designpattern.adapter;

public class Mp4MediaPlayer implements AdvancedMediaPlayer{
    @Override
    public void playerMp4(String fileName) {
        System.out.println("高级功能MP4播放器-播放"+fileName);
    }

    @Override
    public void playerMp5(String fileName) {
        //do nothing
    }
}
