package utils;

import java.io.File;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 *
 * @author oooooooooooldbi
 * @date 2025/4/19 10:38
 * @email bithesenior@163.com
 */

public class PackageScanner {
    public static List<Class<?>> scanPackage(String packageName, boolean isRecursion) {

        List<Class<?>> dtoClazz = new ArrayList();

        Set<String> classNames = null;
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        String path = packageName.replace(".", "/");

        URL url = loader.getResource(path);
        if (url != null) {
            String protocol = url.getProtocol();
            System.out.println("ppppppppprotocol:" + protocol);

            if (protocol.equals("file")) {
                System.out.println("指定file路径");
                classNames = getClassNameFromDir(url.getPath(), packageName, isRecursion);
            } else if (protocol.equals("jar")) {
                System.out.println("指定jar包");
                JarFile jarFile = null;
                try {
                    jarFile = ((JarURLConnection) url.openConnection()).getJarFile();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (jarFile != null) {
                    classNames = getClassNameFromJar(jarFile.entries(), packageName);
                }
            }
        } else {
            System.out.println("全包扫描");
            classNames = getClassNameFromJars(((URLClassLoader) loader).getURLs(), packageName);
        }

        if (classNames != null) {
            for (String className : classNames) {
                try {
                    Class<?> clazz = Class.forName(className);
                    dtoClazz.add(clazz);
                    //System.out.println("addddddddddddded:"+className);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }

        return dtoClazz;
    }

    /**
     * 从项目文件获取某包下有类
     *
     * @param filePath    文件路径
     * @param isRecursion 是否遍历子包
     * @return 类的完整名称
     */
    private static Set<String> getClassNameFromDir(String filePath, String packageName, boolean isRecursion) {

        Set<String> className = new HashSet<>();
        File file = new File(filePath);
        File[] files = file.listFiles();
        for (File childFile : files) {

            if (childFile.isDirectory()) {
                if (isRecursion) {
                    className.addAll(getClassNameFromDir(childFile.getPath(), packageName + "." + childFile.getName(), isRecursion));
                }
            } else {
                String fileName = childFile.getName();
                //endsWith() 方法用于测试字符串是否以指定的后�?结束�?  !fileName.contains("$") 文件名中不包? '$'
                if (fileName.endsWith(".class") && !fileName.contains("$")) {
                    className.add(packageName + "." + fileName.replace(".class", ""));
                }
            }
        }

        return className;
    }


    /**
     * @param jarEntries
     * @param packageName
     * @return
     */
    private static Set<String> getClassNameFromJar(Enumeration<JarEntry> jarEntries, String packageName) {
        Set<String> classNames = new HashSet();

        while (jarEntries.hasMoreElements()) {
            JarEntry jarEntry = jarEntries.nextElement();

            String entryName = jarEntry.getName().replace("/", ".");

            if (entryName.endsWith(".class") && entryName.contains(packageName) && !entryName.contains("$")) {
                System.out.println("entered if case entryName contains packageName");
                String replace = entryName.replace(".class", "");
                classNames.add(replace);
                System.out.println("addName:" + replace);
            }
        }

        return classNames;
    }

    /**
     * 从所有jar中搜索该包，并获取该包下�?有类
     *
     * @param urls        URL集合
     * @param packageName
     * @return 类的完整名称
     */
    private static Set<String> getClassNameFromJars(URL[] urls, String packageName) {

        Set<String> classNames = new HashSet<>();

        for (int i = 0; i < urls.length; i++) {
            String classPath = urls[i].getPath();
            //不必搜索classes文件�?
            if (classPath.endsWith("classes/")) {
                continue;
            }

            JarFile jarFile = null;
            try {
                jarFile = new JarFile(classPath.substring(classPath.indexOf("/")));
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (jarFile != null) {
                classNames.addAll(getClassNameFromJar(jarFile.entries(), packageName));
            }
        }

        for (String className : classNames) {
            System.out.println(className);
        }

        return classNames;
    }
}