package test.日志.SLF4J整合LogBack;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import test.日志.SLF4J整合Log4j.Slf4jLog4j;

/**
 * @创建人 zhaojingen
 * @创建时间 2019/10/12
 * @描述
 */
public class SLF4JLogBack {
    final static Logger logger = LoggerFactory.getLogger(SLF4JLogBack.class);

    public static void main(String[] args) {

        logger.trace("Trace Level.{}{}{}","aa","bb","cc");

        logger.info("Info Level.{}{}{}","aa","bb","cc");

        logger.warn("Warn Level.");

        logger.error("Error Level.");

    }
}
