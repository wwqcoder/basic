package test.日志.Log4J;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * @创建人 zhaojingen
 * @创建时间 2019/10/12
 * @描述
 * Log4J 是 Apache 的一个日志开源框架，有多个分级（DEBUG/INFO/WARN/ERROR）记录级别，可以很好地将不同日志级别的日志分开记录，极大地方便了日志的查看。
 *
 * Log4J 有 1.X 版本和 2.X 版本，现在官方推荐使用 2.X 版本，2.X 版本在架构上进行了一些升级，配置文件也发生了一些变化。
 *
 *  但 Log4J 本身也存在一些缺点，比如不支持使用占位符，不利于代码阅读等缺点
 */
public class Log4jLog {
    public static void main(String args[]) {

        Logger logger = LogManager.getLogger(Log4jLog.class);

        logger.debug("Debug Level");

        logger.info("Info Level");

        logger.warn("Warn Level");

        logger.error("Error Level");

    }
}
