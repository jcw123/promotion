package com.im.sky.netty.zs.util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ClassTypeUtils {

    private final static Map<String, Class> classMap = new ConcurrentHashMap<>();

    private final static Map<Class, String> typeStrMap = new ConcurrentHashMap<>();

    public static Class[] getClasses(String[] typeStrs) throws RuntimeException {
        if (typeStrs == null || typeStrs.length == 0) {
            return new Class[0];
        } else {
            Class[] classes = new Class[typeStrs.length];
            for (int i = 0; i < typeStrs.length; i++) {
                classes[i] = getClass(typeStrs[i]);
            }
            return classes;
        }
    }

    public static Class getClass(String typeStr) throws RuntimeException {
        try {
            Class cls = classMap.get(typeStr);
            if (cls == null) {
                if ("void".equals(typeStr)) cls = void.class;
                else if ("boolean".equals(typeStr)) cls = boolean.class;
                else if ("short".equals(typeStr)) cls = short.class;
                else if ("byte".equals(typeStr)) cls = byte.class;
                else if ("char".equals(typeStr)) cls = char.class;
                else if ("int".equals(typeStr)) cls = int.class;
                else if ("long".equals(typeStr)) cls = long.class;
                else if ("float".equals(typeStr)) cls = float.class;
                else if ("double".equals(typeStr)) cls = double.class;
                else {
                    String jvmName = canonicalNameToJvmName(typeStr);
                    cls = ClassLoaderUtils.forName(typeStr);
                }
                classMap.put(typeStr, cls);
            }
            return cls;
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String[] getTypeStrs(Class[] types) {
        if (types == null || types.length == 0) {
            return new String[0];
        } else {
            String[] strings = new String[types.length];
            for (int i = 0; i < types.length; i++) {
                strings[i] = getTypeStr(types[i]);
            }
            return strings;
        }
    }

    public static String getTypeStr(Class clazz) {
        String typeStr = typeStrMap.get(clazz);
        if (typeStr == null) {
            if (clazz.isArray()) {
                String name = clazz.getName(); // 原始名字：[Ljava.lang.String;
                typeStr = jvmNameToCanonicalName(name); // java.lang.String[]
            } else {
                typeStr = clazz.getName();
            }
            typeStrMap.put(clazz, typeStr);
        }
        return typeStr;
    }

   private static String canonicalNameToJvmName(String typeStr) {
       boolean isArray = typeStr.endsWith("[]");
       if(isArray) {
           String t = "";
           while(isArray) {
               typeStr = typeStr.substring(0, typeStr.length() - 2);
               t += "[";
               isArray = typeStr.endsWith("[]");
           }
           if("boolean".equals(typeStr)) typeStr = t + "Z";
           else if("byte".equals(typeStr)) typeStr = t + "B";
           else if("char".equals(typeStr)) typeStr = t + "C";
           else if("double".equals(typeStr)) typeStr = t + "D";
           else if("float".equals(typeStr)) typeStr = t + "F";
           else if("int".equals(typeStr)) typeStr = t + "I";
           else if("long".equals(typeStr)) typeStr = t + "J";
           else if("short".equals(typeStr)) typeStr = t + "S";
           else typeStr = t + "L" + typeStr + ";";
       }
       return typeStr;
   }

    private static String jvmNameToCanonicalName(String jvmName) {
        boolean isarray = jvmName.charAt(0) == '[';
        if (isarray) {
            String cnName = ""; // 计数，看上几维数组
            int i = 0;
            for (; i < jvmName.length(); i++) {
                if (jvmName.charAt(i) != '[') {
                    break;
                }
                cnName += "[]";
            }
            String componentType = jvmName.substring(i);
            if ("Z".equals(componentType)) cnName = "boolean" + cnName;
            else if ("B".equals(componentType)) cnName = "byte" + cnName;
            else if ("C".equals(componentType)) cnName = "char" + cnName;
            else if ("D".equals(componentType)) cnName = "double" + cnName;
            else if ("F".equals(componentType)) cnName = "float" + cnName;
            else if ("I".equals(componentType)) cnName = "int" + cnName;
            else if ("J".equals(componentType)) cnName = "long" + cnName;
            else if ("S".equals(componentType)) cnName = "short" + cnName;
            else cnName = componentType.substring(1,componentType.length()-1) + cnName; // 对象的 去掉L
            return cnName;
        }
        return jvmName;
    }
}
