package test.Object;

import java.util.Calendar;
import java.util.Date;

/**
 * @创建人 zhaojingen
 * @创建时间 2019/7/24
 * @描述 getClass返回的为class B 非 A
 */
public class ObjectTest {

    static final int N = 100000;
    public static void main(String[] args) {
        final Date date = new Date();
        A a = new B();
        System.out.println(a.getClass());
        {
            final long startTime = System.currentTimeMillis();
                       for (int i = 0; i < N; i++) {
                           Date date2 = (Date) date.clone();
                       }

                       final long endTime = System.currentTimeMillis();

                      System.out.println("clone:" + (endTime - startTime) + "ms");
        }

        {
            final long startTime = System.currentTimeMillis();
                       for (int i = 0; i < N; i++) {
                            final Calendar cal = Calendar.getInstance();
                            cal.setTime(date);
                           final Date date2 = cal.getTime();
                       }

                       final long endTime = System.currentTimeMillis();
                        System.out.println("Calender.setTime:" + (endTime - startTime) + "ms");
        }


    }
}

class A {
    static {
        System.out.println("初始化A");
    }
}

class B extends A{
    static {
        System.out.println("初始化B");
    }
}
