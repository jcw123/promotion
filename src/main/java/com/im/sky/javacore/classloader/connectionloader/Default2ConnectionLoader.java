package com.im.sky.javacore.classloader.connectionloader;


/**
 * @author jiangchangwei
 * @date 2020-9-21 下午 4:38
 **/
public class Default2ConnectionLoader extends ConnectionLoader {
    private final String PREFIX = "com.im.sky.javacore.classloader.connectionloader.name2";

    @Override
    protected Connection findConnection(String name) {
        String connectionName = PREFIX + "." + name;
        try {
            Class clz = Class.forName(connectionName);
            return (Connection) clz.newInstance();
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
