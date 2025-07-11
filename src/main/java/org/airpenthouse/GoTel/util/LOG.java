package org.airpenthouse.GoTel.util;

import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;

public class LOG {
    private static final Log log = LogFactory.getLog(LOG.class);

    public static void info(String message) {
        log.info(message);
    }
}
