package cn.wwq.singleton;

import java.lang.reflect.Constructor;

/**
 *   但是，实际还是可以通过反射方式创建多个实例
 */
public class SingletonTest {
 
    public static void main(String[] args) throws Exception{
 
        Class clz = Class.forName("LazySingleton");
        // "true" indicates that the reflected object should suppress（废弃） Java language access checking when it is used
        Constructor constructor = clz.getDeclaredConstructor();
        constructor.setAccessible(true);
        Object obj1 = constructor.newInstance();
        Object obj2 = constructor.newInstance();
    }
}