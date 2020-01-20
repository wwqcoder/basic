package test.rpc.demo01;

/**
 * @创建人 zhaojingen
 * @创建时间 2020/1/2
 * @描述
 */
public class HelloServiceImpl implements IHello {
    @Override
    public String sayHello(String string) {
        return "你好:".concat ( string );
    }
}
