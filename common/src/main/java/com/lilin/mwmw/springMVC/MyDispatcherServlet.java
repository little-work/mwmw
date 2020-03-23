package com.lilin.mwmw.springMVC;

import com.lilin.mwmw.springMVC.annotation.MyController;
import com.lilin.mwmw.springMVC.annotation.MyQuatifier;
import com.lilin.mwmw.springMVC.annotation.MyRequestMapping;
import com.lilin.mwmw.springMVC.annotation.MyService;
import com.lilin.mwmw.springMVC.controller.SpringmvcController;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyDispatcherServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    List<String> packageNames = new ArrayList<String>();
    // 所有类的实例，key是注解的value,value是所有类的实例
    Map<String, Object> instanceMap = new HashMap<String, Object>();
    //映射表（客户访问的路径-具体的执行方法）
    Map<String, Object> handerMap = new HashMap<String, Object>();


    public void init(ServletConfig config) throws ServletException {
        // 包扫描,获取包中的文件
        scanPackage("com.lilin.mwmw.springMVC");
        try {
            filterAndInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 建立映射关系
        handerMap();
        // 实现注入
        ioc();
    }

    private void ioc() {
        if (instanceMap.isEmpty())
            return;
        for (Map.Entry<String, Object> entry : instanceMap.entrySet()) {
            // 拿到里面的所有属性
            Field fields[] = entry.getValue().getClass().getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);// 可访问私有属性
                //如果属性上面有MyQuatifier注解的话
                if (field.isAnnotationPresent(MyQuatifier.class)) ;
                MyQuatifier quatifier = field.getAnnotation(MyQuatifier.class);
                String value = quatifier.value();
                field.setAccessible(true);
                try {
                    //根据MyQuatifier注解上面的值 来给属性赋值
                    field.set(entry.getValue(), instanceMap.get(value));
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        SpringmvcController find = (SpringmvcController) instanceMap.get("find");
        System.out.print(find);
    }

    /**
     * 根据扫描的包的全限类名 创建实例放到容器中
     *
     * @throws Exception
     */
    private void filterAndInstance() throws Exception {
        if (packageNames.size() <= 0) {
            return;
        }
        for (String className : packageNames) {
            Class<?> cName = Class.forName(className.replace(".class", "").trim());
            //如果类上面有MyController注解的话
            if (cName.isAnnotationPresent(MyController.class)) {
                //创建这个类的实例
                Object instance = cName.newInstance();
                MyController controller = (MyController) cName.getAnnotation(MyController.class);
                String key = controller.value();
                //键为注解的值 ，值为被注解的实例
                instanceMap.put(key, instance);
            } else if (cName.isAnnotationPresent(MyService.class)) {
                Object instance = cName.newInstance();
                MyService service = (MyService) cName.getAnnotation(MyService.class);
                String key = service.value();
                //键为注解的值 ，值为被注解的实例
                instanceMap.put(key, instance);
            } else {
                continue;
            }
        }
    }

    /**
     * 扫描包下的所有文件
     *
     * @param Package
     */
    private void scanPackage(String Package) {
        // 将所有的.转义获取对应的路径
        URL url = this.getClass().getClassLoader().getResource("/" + replaceTo(Package));
        String pathFile = url.getFile();
        File file = new File(pathFile);
        String fileList[] = file.list();
        for (String fileName : fileList) {
            File eachFile = new File(pathFile + fileName);
            //如果是文件夹  则递归
            if (eachFile.isDirectory()) {
                scanPackage(Package + "." + eachFile.getName());
            } else {
                //将全限类名放到list中  例如：com.lilin.mwmw.springMVC.MyDispatcherServlet
                packageNames.add(Package + "." + eachFile.getName());
            }
        }
    }

    private String replaceTo(String path) {
        return path.replaceAll("\\.", "/");
    }

    /**
     * 建立映射关系
     */
    private void handerMap() {
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
                        handerMap.put("/" + ctvalue + "/" + rmvalue, method);
                    } else {
                        continue;
                    }
                }
            } else {
                continue;
            }

        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String url = req.getRequestURI();
        String context = req.getContextPath();
        String path = url.replace(context, "");
        //根据客户访问的路径 找到对应的方法，前面已经做好了
        Method method = (Method) handerMap.get(path);
        SpringmvcController controller = (SpringmvcController) instanceMap.get(path.split("/")[1]);
        try {
            method.invoke(controller, new Object[]{req, resp, null});
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

}
