package test.日志.LogBack;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @创建人 zhaojingen
 * @创建时间 2019/10/12
 * @描述 LogBack 解决了 Log4J 不能使用占位符的问题，这使得阅读日志代码非常方便。除此之外，LogBack 比 Log4J 有更快的运行速度，更好的内部实现。并且 LogBack 内部集成
 *       了 SLF4J 可以更原生地实现一些日志记录的实现。
 */
public class LogBack {
    static final Logger logger = LoggerFactory.getLogger(LogBack.class);

    public static void main(String[] args) {

        logger.trace("Trace Level.");

        logger.debug("Debug Level.");

        logger.info("Info Level.{}","bbb");

        logger.warn("Warn Level.");

        logger.error("Error Level.");

    }
}
