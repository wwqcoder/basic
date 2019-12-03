package cn.wwq.singleton;

/**
 * 一、【饿汉模式】- 多线程安全单例模式实例一(不使用同步锁)
 * 缺点：对象在没有使用之前就已经初始化了。这就可能带来潜在的性能问题：
 * 如果这个对象很大呢？没有使用这个对象之前，就把它加载到了内存中去是一种巨大的浪费。
 * 另外，当系统中这样的类较多时，会使得启动速度变慢 。
 */
public class EagerSingleton {
    private static final EagerSingleton singleton = new EagerSingleton();
 
    private EagerSingleton() {
    }
 
    public static EagerSingleton getSingleton() {
        return singleton;
    }
}