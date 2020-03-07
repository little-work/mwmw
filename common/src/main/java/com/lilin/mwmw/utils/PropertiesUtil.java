package com.lilin.mwmw.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;
import java.util.Set;

public class PropertiesUtil {


    public static void printAll() {
        Properties prop = new Properties();
        InputStream input = null;
        try {
            String filename = "ruleItem.properties";
            input = Thread.currentThread().getContextClassLoader().getResourceAsStream(filename);
            BufferedReader bf = new BufferedReader(new InputStreamReader(input,"UTF-8"));
            if (input == null) {
                System.out.println("Sorry, unable to find " + filename);
                return;
            }
            prop.load(bf);
            //方法一：
            Set<Object> keys = prop.keySet();//返回属性key的集合
            for (Object key : keys) {
                System.out.println("key:" + key.toString() + ",value:" + prop.get(key));
            }
            /*//方法二：
            Set<Entry<Object, Object>> entrys = prop.entrySet();//返回的属性键值对实体
            for (Entry<Object, Object> entry : entrys) {
                System.out.println("key:" + entry.getKey() + ",value:" + entry.getValue());
            }
            //方法三：
            Enumeration<?> e = prop.propertyNames();
            while (e.hasMoreElements()) {
                String key = (String) e.nextElement();
                String value = prop.getProperty(key);
                System.out.println("Key:" + key + ",Value:" + value);
            }*/
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    public static void main(String[] args) {
        new PropertiesUtil().printAll();
    }
}
