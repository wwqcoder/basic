package test.Proxy;

/**
 * @创建人 zhaojingen
 * @创建时间 2019/7/9
 * @描述
 */
public class TestProxy {
    public static void main(String[] args) {
        BookFacadeProxy proxy = new BookFacadeProxy();
        BookFacade bookProxy = (BookFacade) proxy.bind(new BookFacadeImpl());
        bookProxy.addBook();
    }
}
