package com.im.sky.log;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.ConfigurationSource;
import org.apache.logging.log4j.core.config.Configurator;
import org.apache.logging.log4j.core.config.LoggerConfig;
import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.util.Map;

public class LogTest {
    public static void main(String[] args) throws Exception {
        URL url= ClassLoader.getSystemResource("log4j2.xml");
        ConfigurationSource source = new ConfigurationSource(new FileInputStream(new File(url.getPath())),url);
        Configurator.initialize(null, source);
        Map<String, LoggerConfig> loggers = LoggerContext.getContext().getConfiguration().getLoggers();
        Logger logger = LogManager.getLogger("test.v1");
        logger.isDebugEnabled();
        logger.isInfoEnabled();
    }
}
