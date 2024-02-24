package com.breeze.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoggerWrapper {

    private final Logger logger;

    private LoggerWrapper(Class<?> clazz) {
        logger = LogManager.getLogger(clazz);
    }

    public static LoggerWrapper getLogger(Class<?> clazz) {
        return new LoggerWrapper(clazz);
    }

    public void info(Object message) {
        logger.info(message);
    }

    public void info(String message, Object... params) {
        logger.info(message, params);
    }

    public void error(Object message) {
        logger.error(message);
    }
}
