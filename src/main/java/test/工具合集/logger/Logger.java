package test.工具合集.logger;

/**
 * @创建人 zhaojingen
 * @创建时间 2019/8/8
 * @描述
 */
public class Logger extends LoggerBase{
    public Logger() {
        super(Logger.class);
    }

    public Logger(String cls) {
        super(cls);
    }

    @SuppressWarnings("unchecked")
    public Logger(Class cls) {
        super(cls);
    }
}
