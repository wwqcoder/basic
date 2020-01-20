package test.Proxy;

/**
 * @创建人 zhaojingen
 * @创建时间 2019/7/9
 * @描述
 */
public class TestCglib {

    public static void main(String[] args) {
        BookFacadeCglib cglib=new BookFacadeCglib();
        BookFacadeImpl bookCglib=(BookFacadeImpl)cglib.getInstance(new BookFacadeImpl());
        bookCglib.addBook();
    }
}
