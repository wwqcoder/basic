package test.lambda表达式;

import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * @创建人 zhaojingen
 * @创建时间 2019/10/15
 * @描述
 */
public class LambdaTest {
    /**
     * 无参数，无返回值
     */
    @Test
    public void test1(){
        Runnable r = new Runnable() {
            @Override
            public void run() {
                System.out.println("线程启动了！！");
            }
        };
        r.run();
    }

    @Test
    public void test2() {
        Runnable r2 = ()-> System.out.println("线程2启动了");
        r2.run();
    }

    /**
     * 有一个参数，并且无返回值
     */
    @Test
    public void test3() {
        //这个e就代表所实现的接口的方法的参数，
        Consumer<String> consumer = e->System.out.println("ghijhkhi"+e);
        Consumer<String> consumer2 = System.out::println;
        consumer.accept("woojopj");
        consumer2.accept("dsfasdfs");
    }
    /**
     * 有两个以上的参数，有返回值，并且 Lambda 体中有多条语句
     */
    @Test
    public void test4() {
        //Lambda 体中有多条语句，记得要用大括号括起来
        Comparator<Integer> com = (x, y) -> {
            System.out.println("函数式接口");
            return Integer.compare(x, y);
        };
        int compare = com.compare(100, 244);
        System.out.println(compare);
    }

    @Test
    public void test6() {
        Supplier<String> supplier = ()->"532323".substring(0, 2);
        System.out.println(supplier.get());
        //特殊的lambda
        Supplier<String> supplier2 = String::new;
        String ss = supplier2.get();
        System.out.println(ss);
    }
    @Test
    public void test7() {
        Function<String, String> function = (x)->x.substring(0, 2);
        System.out.println(function.apply("我是中国人"));
    }
    @Test
    public void test8() {
        Predicate<String> predicate = (x)->x.length()>5;
        System.out.println(predicate.test("12345678"));
        System.out.println(predicate.test("123"));
    }
    @Test
    public void test12() {
        Comparator<Integer> comparator = (x,y)->Integer.compare(x, y);
        Comparator<Integer> comparator1 = Integer::compare;
        int compare = comparator.compare(1, 2);
        int compare2 = comparator1.compare(1, 2);
        System.out.println("compare:"+compare);
        System.out.println("compare2:"+compare2);

    }

}
