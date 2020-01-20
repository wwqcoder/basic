package test.日志.SLF4J整合JDKLog;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @创建人 zhaojingen
 * @创建时间 2019/10/12
 * @描述
 */
/****

 ** SLF4J + JDKLog

 **/
public class Slf4jJDKLog {
    final static Logger logger = LoggerFactory.getLogger(Slf4jJDKLog.class);

    public static void main(String[] args) {

        logger.trace("Trace Level.");

        logger.info("Info Level.");

        logger.warn("Warn Level.");

        logger.error("Error Level.");

    }
}
