package com.lilin.mwmw.loadProperties;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class PropertiesTest {


    @Autowired
    PropertiesBean propertiesBean;


    @RequestMapping(method = RequestMethod.POST,value = "/properties")
    public String  testProperties(){
       return propertiesBean.getKey2();
    }
}
