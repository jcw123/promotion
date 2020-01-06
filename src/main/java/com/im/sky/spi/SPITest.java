package com.im.sky.spi;

import sun.misc.Service;

import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * @author jiangchangwei
 * @date 2020-1-2 下午 4:47
 **/
public class SPITest {
    public static void main(String[] args) {
        Iterator<SPIService> iterator = Service.providers(SPIService.class);
        ServiceLoader<SPIService> spiServices = ServiceLoader.load(SPIService.class);
        while(iterator.hasNext()) {
            iterator.next().execute();
        }

        Iterator<SPIService> iterator1 = spiServices.iterator();
        while (iterator1.hasNext()) {
            iterator1.next().execute();
        }
    }
}
