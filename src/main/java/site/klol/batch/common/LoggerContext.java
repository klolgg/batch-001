package site.klol.batch.common;

import org.slf4j.Logger;

public class LoggerContext {
    private static final ThreadLocal<Logger> contextLogger = new ThreadLocal<>();

    public static void setLogger(Logger logger) {
        contextLogger.set(logger);
    }

    public static Logger getLogger() {
        return contextLogger.get();
    }

    public static void clear() {
        contextLogger.remove();
    }
}
