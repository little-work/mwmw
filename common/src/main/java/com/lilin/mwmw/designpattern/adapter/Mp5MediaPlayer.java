package com.lilin.mwmw.designpattern.adapter;

public class Mp5MediaPlayer implements AdvancedMediaPlayer{
    @Override
    public void playerMp4(String fileName) {
        //do nothing
    }

    @Override
    public void playerMp5(String fileName) {
        System.out.println("高级功能MP5播放器-播放"+fileName);
    }
}
