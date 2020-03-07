package com.lilin.mwmw.controller;

import com.lilin.mwmw.bo.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.util.Base64;

@RestController
@Slf4j
public class ReturnStringController {

    //@Autowired
   // UserServiceConsumeImpl userServiceConsumeImpl;

    @RequestMapping(value = "test",method = RequestMethod.GET)
    public String test(){
        log.info("开始处理");
        return "测试返回类型是String的";
    }
        @RequestMapping(value = "test2",method = RequestMethod.POST)
    public User test2(@RequestBody @Validated User user){
        User user2=new User();
        user2.setUsername("lilin");
        return user2;
        //return userServiceConsumeImpl.getUserById(id).getName();
    }

    public static void base64ToFile(String base64, String filePath, String fileName) {
        File file = null;
        // 创建文件目录
        File dir = new File(filePath);
        if (!dir.exists() && !dir.isDirectory()) {
            dir.mkdirs();
        }
        BufferedOutputStream bos = null;
        FileOutputStream fos = null;
        try {
            byte[] bytes = Base64.getDecoder().decode(base64);
            file = new File(filePath + File.separator + fileName);
            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            bos.write(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public static void main(String[] args) throws Exception {
        FileInputStream in=new FileInputStream(new File("C:\\base64.txt"));
        String str="";
        // size 为字串的长度 ，这里一次性读完
        int size=in.available();
        byte[] buffer=new byte[size];
        in.read(buffer);
        in.close();
        str=new String(buffer);
        str=str.substring(str.lastIndexOf(",")+1);
        System.out.println(str);
        base64ToFile(str,"c:\\","picture.png");
        System.out.println();
    }
}