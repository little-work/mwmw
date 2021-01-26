package com.lilin.mwmw.bo;


import lombok.Data;
import org.springframework.stereotype.Component;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
public class User {

    //这个min  max不是位数  是数字在4-20之间
    @Min(4)
    @Max(20)
    private int id;

    @DecimalMin(value = "2")
    private int age;

    @NotBlank(message = "用户名不能为空")
    private String username;
    @NotBlank(message = "用户名不能为空")
    private String password;
    private String address;

    private Integer userType;
    private Date createTime;
    private Date lastUpdateTime;


    private void init() {
        System.out.println("调用User类的初始化方法 ");
    }

    private void destory() {
        System.out.println("容器关闭的时候");
    }


}