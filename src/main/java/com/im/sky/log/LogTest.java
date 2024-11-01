package com.im.sky.log;

import com.alibaba.fastjson.JSON;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.*;

import java.io.File;
import java.io.FileInputStream;
import java.net.URI;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class LogTest {

    private static final ThreadPoolExecutor EXECUTOR = new ThreadPoolExecutor(10, 10, 0, TimeUnit.SECONDS, new LinkedBlockingQueue<>());

    public static void main(String[] args) throws Exception {
        URL url= ClassLoader.getSystemResource("log4j2.xml");
        ConfigurationSource source = new ConfigurationSource(new FileInputStream(new File(url.getPath())),url);
        LoggerContext loggerContext =  Configurator.initialize(null, source);
//        Configuration configuration = loggerContext.getConfiguration();
//        Map<String, Appender> appenderMap = configuration.getAppenders();
//        System.out.println(JSON.toJSONString(appenderMap.keySet()));
        Logger logger = LogManager.getLogger("test.v1");
        parallelRun(logger);

    }

    private static void parallelRun(Logger logger) throws Exception {
        for(;;) {
            CompletableFuture.runAsync(() -> {
                logger.error("2");
            }, EXECUTOR);
            CompletableFuture.runAsync(() -> {
                logger.error("22");
            }, EXECUTOR);
        }
    }
}
