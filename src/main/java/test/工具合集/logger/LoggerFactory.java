package test.工具合集.logger;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @创建人 zhaojingen
 * @创建时间 2019/8/8
 * @描述
 */
public class LoggerFactory {
    private static final ConcurrentMap<String, Logger> LOGGERS = new ConcurrentHashMap<String, Logger>();

    public static Logger getLogger() {
        return new Logger(LoggerFactory.class);
    }

    public static Logger getLogger(String className) {
        Logger logger = LOGGERS.get(className);
        if (logger == null) {
            LOGGERS.putIfAbsent(className, new Logger(className));
            logger = LOGGERS.get(className);
        }
        return logger;
    }

    public static Logger getLogger(Class clazz) {
        return getLogger(clazz.getName());
    }
}
