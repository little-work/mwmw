package com.lilin.mwmw.designpattern.adapter;

public class MediaPlayerImpl implements MediaPlayer{



    @Override
    public void playerMp3(String audioType, String fileName) {
            if(audioType.equals("1")){
                System.out.println("MP3播放器-播放"+fileName);
            }else{
                new MediaAdapter().playerMp3(audioType,fileName);
            }
    }
}
