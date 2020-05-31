package com.lilin.mwmw.utils;


import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class MyFileUtils {


    /**
     * 修改文件名
     *
     * @param path
     * @param containsString
     * @param newString
     */
    public static void recursiveTraversalFolder(String path, String containsString, String newString) {
        File folder = new File(path);
        if (folder.exists()) {
            File[] fileArr = folder.listFiles();
            if (null == fileArr || fileArr.length == 0) {
                System.out.println("文件夹是空的!");
                return;
            } else {
                File newDir = null;//文件所在文件夹路径+新文件名
                String fileName = null;//旧文件名
                String fileNameSuffix;
                File parentPath = new File("");//文件所在父级路径
                for (File file : fileArr) {
                    if (file.isDirectory()) {//是文件夹，继续递归，如果需要重命名文件夹，这里可以做处理
                        System.out.println("文件夹:" + file.getAbsolutePath() + "，继续递归！");
                        recursiveTraversalFolder(file.getAbsolutePath(), containsString, newString);
                    } else {//是文件，判断是否需要重命名
                        fileName = file.getName();
                        fileNameSuffix = fileName.substring(fileName.indexOf(".") + 1);
                        System.out.println(fileName);

                        parentPath = file.getParentFile();
                        if (fileName.contains(containsString)) {//文件名包含需要被替换的字符串
                            newDir = new File(parentPath + "/" + newString + fileNameSuffix);//文件所在文件夹路径+新文件名
                            file.renameTo(newDir);//重命名
                            System.out.println("修改后：" + newDir);
                        }
                    }
                }
            }
        } else {
            System.out.println("文件不存在!");
        }
    }


    /**
     * 获取某包下所有类
     *
     * @param packageName 包名
     * @return 类的完整名称
     */
    public static Set<String> getClassName(String packageName) {
        Set<String> classNames = null;
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        String packagePath = packageName.replace(".", "/");

        URL url = loader.getResource(packagePath);
        if (url != null) {
            String protocol = url.getProtocol();
            if (protocol.equals("file")) {
                classNames = getClassNameFromDir(url.getPath(), packageName, false);
            }
        }
        return classNames;
    }


    /**
     * 从项目文件获取某包下所有类
     *
     * @param filePath    文件路径
     * @param isRecursion 是否遍历子包
     * @return 类的完整名称
     */
    private static Set<String> getClassNameFromDir(String filePath, String packageName, boolean isRecursion) {
        Set<String> className = new HashSet<String>();
        File file = new File(filePath);
        File[] files = file.listFiles();
        for (File childFile : files) {
            if (childFile.isDirectory()) {
                if (isRecursion) {
                    className.addAll(getClassNameFromDir(childFile.getPath(), packageName + "." + childFile.getName(), isRecursion));
                }
            } else {
                String fileName = childFile.getName();
                if (fileName.endsWith(".class") && !fileName.contains("$")) {
                    className.add(packageName + "." + fileName.replace(".class", ""));
                }
            }
        }

        return className;
    }


    /**
     * 根据给定的数据文件生成文件
     *
     * @param list
     */
    public static void generateFile(String tempPath, String dataPath, String resultFilePath) {
        List<String> templateFileList;
        List<String> dataFileList;
        List<String> writeList;
        List listAll = new ArrayList();
        String head = "{\n" +
                "  \"respCode\": \"000000\",\n" +
                "  \"respMsg\": \"查询成功！\",\n" +
                "  \"recordResult\":0,\n" +
                "   \"List\":{\n" +
                "     \"Detail\":[";

        String tail = "\t]\n" +
                "}\n" +
                "}";
        listAll.add(head);
        try {
            //读取模板文件
            templateFileList = FileUtils.readLines(new File(tempPath));
            dataFileList = FileUtils.readLines(new File(dataPath));
            //生成过多少个借款人信息
            for (int i = 0; i < dataFileList.size(); i++) {
                writeList = new ArrayList();
                //对每一行数据进行在分割
                Map<String, String> map = dataHandle(dataFileList.get(i));

                for (String ss : templateFileList) {
                    for (Map.Entry<String, String> entry : map.entrySet()) {
                        String key = "\"" + entry.getKey() + "\"";
                        String val = entry.getValue();
                        //读取模板文件
                        if (ss.contains(key)) {
                            ss = myReplace(ss, key, val);
                        }
                    }
                    writeList.add(ss);
                }
                listAll.addAll(writeList);
            }
            //去除最后的,号
            finalHandle(listAll);
            //生成最终的文件
            listAll.add(tail);
            FileUtils.writeLines(new File(resultFilePath), listAll);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * String转Map
     *
     * @param data
     * @return
     */
    public static Map<String, String> dataHandle(String data) {
        Map<String, String> map = new LinkedHashMap<>();
        String[] strArr = data.split(":");
        for (String str : strArr) {
            String[] kv = str.split("=");
            if (kv.length > 1) {
                map.put(kv[0], kv[1]);
            } else {
                map.put(kv[0], "");
            }
        }
        return map;
    }

    /**
     * 替换字符串中的值
     *
     * @param ss
     * @param oldStr
     * @param newStr
     * @return
     */
    public static String myReplace(String ss, String oldStr, String newStr) {
        int startSub = ss.indexOf(oldStr) + oldStr.length() + 3;
        String result = "";
        //说明要替换的值为空
        if (startSub + 2 == ss.length()) {
            result = ss.substring(0, startSub) + newStr + "\",";
        } else {
            String o = ss.substring(startSub, ss.length() - 2);
            result = ss.replace(o, newStr);
        }
        return result;
    }

    /**
     * 去除list数组中最后一个元素的最后一个字符
     *
     * @param list
     */
    public static void finalHandle(List<String> list) {
        String lastStr = list.get(list.size() - 1);
        lastStr = lastStr.substring(0, lastStr.length() - 1);
        list.remove(list.size() - 1);
        list.add(lastStr);
    }

    public static void main(String[] args) throws Exception {
        //模板文件地址
        String tempPath = "C:\\Users\\Administrator\\Desktop\\新建文件夹\\第三批造数据\\TemplateFile.txt";

        //要生成文件的数据
        //String dataPath="C:\\Users\\Administrator\\Desktop\\readyData\\dataBefore.txt";
        String dataPath = "C:\\Users\\Administrator\\Desktop\\新建文件夹\\第三批造数据\\dataAfter.txt";

        //最终生成的文件
        //String resultFilePath="C:\\Users\\Administrator\\Desktop\\readyData\\fileBefore.txt";
        String resultFilePath = "C:\\Users\\Administrator\\Desktop\\新建文件夹\\第三批造数据\\fileAfter.txt";
        generateFile(tempPath, dataPath, resultFilePath);

    }

}
