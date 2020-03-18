package com.lilin.mwmw.bo;

import lombok.Data;

@Data
public class Login {



    private String userName;
    private String password;

    public Login(){
        this.password="ewqewq";
    }
}
