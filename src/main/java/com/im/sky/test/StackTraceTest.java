package com.im.sky.test;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.PropertyConfigurator;

public class StackTraceTest {

    private static final Log log = LogFactory.getLog(StackTraceTest.class);

    public static void main(String[] args) {
        PropertyConfigurator.configure("log4j2.xml");
        String s = "";
        for(int i = 0; i < 1; i++) {
            try {
                s = null;
                System.out.println(s.toUpperCase());
            }catch (Exception e) {
                log.error("hello", e);
            }
        }
    }
}
