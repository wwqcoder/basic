package test.annotation;

/**
 * @创建人 zhaojingen
 * @创建时间 2019/7/8
 * @描述
 */
import lombok.*;

/**
 * Created by Trace on 2018/5/19.<br/>
 * DESC: 测试类
 */
@SuppressWarnings("unused")
public class TestClass {

    public static void main(String[] args) {

    }

    @AllArgsConstructor(access = AccessLevel.PUBLIC)
    @RequiredArgsConstructor(access = AccessLevel.PACKAGE)
    @NoArgsConstructor(staticName = "of",access = AccessLevel.PRIVATE)
    @ToString(of = "{name,age}",exclude = "{friednly}")
    @Getter(value = AccessLevel.PUBLIC)
    @Setter(value = AccessLevel.PUBLIC)
    public static class Person {
        @NonNull
        private String name;
        private int age;
        private boolean friendly;
    }


    public static class Animal {
        private String name;
        private int age;
        @Getter @Setter private boolean funny;
    }

}
