package com.lilin.mwmw.springMVC.hander;

import com.lilin.mwmw.springMVC.annotation.MyController;
import com.lilin.mwmw.springMVC.annotation.MyRequestMapping;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class HandlerMethod {


    public Map<String, Method> map=new HashMap<>();


    /**
     * 根据Controller和RequestMapping上面的注解生成URL和对应方法的映射
     * @param instanceMap
     */
    public void setMapping(Map<String, Object> instanceMap){
        if (instanceMap.size() <= 0)
            return;
        //遍历实例map 存放有MyController和MyService注解的类的实例
        for (Map.Entry<String, Object> entry : instanceMap.entrySet()) {
            //如果实例被MyController注解所注解
            if (entry.getValue().getClass().isAnnotationPresent(MyController.class)) {
                MyController controller = (MyController) entry.getValue().getClass().getAnnotation(MyController.class);
                //获得这个类上面MyController直接的值
                String ctvalue = controller.value();
                //或者这个类实例里面所有的方法
                Method[] methods = entry.getValue().getClass().getMethods();
                for (Method method : methods) {
                    //如果方法被MyRequestMapping注解所注解
                    if (method.isAnnotationPresent(MyRequestMapping.class)) {
                        MyRequestMapping rm = (MyRequestMapping) method.getAnnotation(MyRequestMapping.class);
                        //或者这个方法上面MyRequestMapping注解上面的值
                        String rmvalue = rm.value();
                        /**
                         * 拼接MyController和MyRequestMapping的值作为键，方法作为值
                         */
                        map.put("/" + ctvalue + "/" + rmvalue, method);
                    } else {
                        continue;
                    }
                }
            } else {
                continue;
            }
        }
    }


}
