package cn.wwq.singleton;

/**
 * 二、【懒汉模式】多线程安全单例模式实例二(使用同步方法)
 * 缺点：一次锁住了整个方法，粒度有些大。改进--只在实例化语句加锁
 */
public class LazySingletonOne {
    private static LazySingletonOne singleton = null;
 
    private LazySingletonOne() {
    }
 
    public static synchronized LazySingletonOne getSingleton() {
        if (singleton == null)
            singleton = new LazySingletonOne();
        return singleton;
    }
}