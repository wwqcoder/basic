package test.日志.SLF4J整合Log4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import test.日志.SLF4J整合JDKLog.Slf4jJDKLog;

/**
 * @创建人 zhaojingen
 * @创建时间 2019/10/12
 * @描述
 */
public class Slf4jLog4j {
    final static Logger logger = LoggerFactory.getLogger(Slf4jLog4j.class);

    public static void main(String[] args) {

        logger.trace("Trace Level.");

        logger.info("Info Level.");

        logger.warn("Warn Level.");

        logger.error("Error Level.");

    }
}
