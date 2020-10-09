package com.im.sky.spi.demo1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * @author jiangchangwei
 * @date 2020-9-18 上午 11:52
 **/
public class ServiceLoader {

    private static final String path = "spi/spi_test";

    public static <M> M load(Class<M> clz) throws IOException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        URL url;
        if(classLoader != null) {
            url = classLoader.getResource(path);
        }else {
            url = ClassLoader.getSystemClassLoader().getResource(path);
        }
        if(url != null) {
            BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
            String s;
            Class childClz;
            while((s = br.readLine()) != null) {
                childClz = Class.forName(s);
                Class[] clzs = childClz.getInterfaces();
                if(clzs != null && clzs.length > 0) {
                    boolean isFind = false;
                    for(Class tmp : clzs) {
                        if(tmp == clz) {
                            isFind = true;
                            break;
                        }
                    }
                    if(isFind) {
                        return clz.cast(childClz.newInstance());
                    }
                }
            }
        }
        return null;
    }
}
