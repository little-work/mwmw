package com.lilin.mwmw;

import com.lilin.mwmw.utils.FileUtils;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainTest {


    public static void main(String[] args) {
        System.out.println("qewq".substring(1,"qewq".length()));
    }

    public static boolean ifStringContainNumber(String content) {
        if (content.contains("==")) {
            return false;
        }
        boolean flag = false;
        Pattern p = Pattern.compile(".*\\d+.*");
        Matcher m = p.matcher(content);
        if (m.matches()) {
            flag = true;
        }
        return flag;
    }
}