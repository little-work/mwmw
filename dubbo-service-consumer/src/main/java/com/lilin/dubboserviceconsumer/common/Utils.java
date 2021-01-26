package com.lilin.dubboserviceconsumer.common;

/**
 * @author lilin
 * @version 1.0
 * @date 2020-07-13 10:59
 */
public class Utils {

    /**
     * 将毫秒转换成00:00:00:000这样的格式
     * @param ms
     * @return
     */
    public static String formatTime(Long ms) {
        Integer ss = 1000;
        Integer mi = ss * 60;
        Integer hh = mi * 60;
        Integer dd = hh * 24;

        Long day = ms / dd;
        Long hour = (ms - day * dd) / hh;
        Long minute = (ms - day * dd - hour * hh) / mi;
        Long second = (ms - day * dd - hour * hh - minute * mi) / ss;
        Long milliSecond = ms - day * dd - hour * hh - minute * mi - second * ss;

        StringBuffer sb = new StringBuffer();
        if (day > 0) {
            sb.append(day + "天");
        }
        if (hour > 0) {
            sb.append(hour + "小时");
        }
        if (minute > 0) {
            sb.append(minute + ":");
        }
        if (second > 0) {
            sb.append(second + ":");
        }
        if (milliSecond > 0) {
            sb.append(milliSecond + "");
        }
        return sb.toString();

    }
}
