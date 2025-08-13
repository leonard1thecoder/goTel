package org.airpenthouse.GoTel.util;

import org.apache.juli.logging.LogFactory;

public class Log {
    private static final org.apache.juli.logging.Log log = LogFactory.getLog(Log.class);

    public static void info(String message) {
        log.info(message);
    }
}
