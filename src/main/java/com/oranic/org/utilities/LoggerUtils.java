package com.oranic.org.utilities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class LoggerUtils {
    public static <T> Logger getLogger(Class<T> clazz) {
        return LoggerFactory.getLogger(clazz);
    }
}
