package com.iflytek.client;

import lombok.Data;

@Data
public class IatSessionParam {

    //会话id
    private String sid;
    //音频编码格式
    private String aue;
    //返回结果格式
    private String rst;
    //转写结果编码  utf8，gbk
    private String rse;
    //静音时长，如果音频中静音的时长超过词数值，引擎会自动断开连接
    private String eos;
    //引擎参数，是否获得中间结果  1、wpgs：实时返回中间结果 2、当参数为空时不返回中间结果
    private String dwa;
    //引擎参数，多候选（句子级）
    private String wbest;
    //引擎参数，是否只有人名多候选
    private String wbestonlyper;
    //采样率
    private String rate;
    //采用;分割的热词，为utf-8编码
    private String hotword;
    //引擎透传的参数，由;连接的引擎参数键值对 Key1=value1;key2=value2
    private String engine_param;
    //开启 pgs 时返回结果的格式
    private String pgs_mode;
    //词语属性开关，返回结果中包含wp 字段
    private String openwp;
    //音频数据信息
    private String samplesInfo;

}
