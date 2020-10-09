package com.im.sky.javacore.classloader.connectionloader;

/**
 * @author jiangchangwei
 * @date 2020-9-21 下午 4:46
 **/
public class Test {

    public static void main(String[] args) {
        DefaultConnectionLoader defaultConnectionLoader = new DefaultConnectionLoader();
        Default2ConnectionLoader default2ConnectionLoader = new Default2ConnectionLoader();
        default2ConnectionLoader.setParent(defaultConnectionLoader);
        Connection connection = default2ConnectionLoader.loadConnection("Connection2");
        connection.mark();
    }
}
