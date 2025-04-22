package Utils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 *
 * @author oooooooooooldbi
 * @date 2025/4/19 10:38
 * @email bithesenior@163.com
 */

public class PackageScanner {
    public static List<Class<?>> scanPackage(String packageName) {
        List<Class<?>> classes = new ArrayList<>();
        try {
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            assert classLoader != null;
            String path = packageName.replace('.', '/');
            Enumeration<URL> resources = classLoader.getResources(path);
            List<File> directories = new ArrayList<>();
            while (resources.hasMoreElements()) {
                URL resource = resources.nextElement();
                directories.add(new File(resource.getFile()));
            }
            for (File directory : directories) {
                File[] files = directory.listFiles();
                assert files != null;
                for (File file : files) {
                    if (file.isFile() && file.getName().endsWith(".class")) {
                        String className = file.getName().substring(0, file.getName().length() - 6);
                        try {
                            Class<?> cls = classLoader.loadClass(packageName + '.' + className);
                            classes.add(cls);
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return classes;
    }

}