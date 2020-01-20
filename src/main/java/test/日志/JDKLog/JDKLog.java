package test.日志.JDKLog;

import java.util.logging.Logger;

/**
 * @创建人 zhaojingen
 * @创建时间 2019/10/12
 * @描述 JDKLog 的有点是使用非常简单，直接在 JDK 中就可以使用。但 JDKLog
 * 功能比较太过于简单，不支持占位符显示，拓展性比较差，所以现在用的人也很少
 */
public class JDKLog {
    public static void main( String[] args )

    {

        Logger logger = Logger.getLogger("JDKLog");

        logger.info("Hello World.");

    }
}
