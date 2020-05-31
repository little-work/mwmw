package com.lilin.mwmw.designpattern.adapter;

public class MediaAdapter implements MediaPlayer{


    private AdvancedMediaPlayer advancedMediaPlayer;



    @Override
    public void playerMp3(String audioType, String fileName) {
        if(audioType.equals("2")){
            advancedMediaPlayer=new Mp4MediaPlayer();
            advancedMediaPlayer.playerMp4(fileName);
        }else if(audioType.equals("3")){
            advancedMediaPlayer=new Mp5MediaPlayer();
            advancedMediaPlayer.playerMp5(fileName);
        }
    }
}
