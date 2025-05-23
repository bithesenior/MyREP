package Utils;

import java.lang.reflect.Field;

/**
 * @author: oooooooooldbi
 * @date: 2025/4/22 17:19
 * @email: bithesenior@163.com
 */
public class SetMethodDefaultValue {

    static String setString ="set";

    static String point ="\\.";

    public static String setMethodDefaultValue(Field field) {

        String name = field.getName();
        StringBuilder stringBuilder = new StringBuilder(setString);
        stringBuilder.append(Character.toUpperCase(name.charAt(0))).append(name.substring(1));
        return stringBuilder.toString();

    }
    public static String classNameDefaultValue(Class clz) {

        String name = clz.getName();
        String[] split = name.split(point);
        int length = split.length;

        String className = split[length - 1];

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(Character.toLowerCase(className.charAt(0))).append(className.substring(1));
        return stringBuilder.toString();

    }

}
