package com.lilin.mwmw.utils;


import java.io.File;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

public class FileUtils {


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

}
