package cn.wwq.singleton;

/**
 *  三、【懒汉模式】多线程安全单例模式实例三(使用同步方法-双重检查锁Double-Checked Lock)
 *
 * 双重检查的原因：可能会有多个线程同时同步块外的if判断语句，
 * 不进行双重检查就会导致创建多个实例。
 *
 * 同时因为singleton = new LazySingleton()不是原子操作，而是分为3个步骤进行：
 *
 * ①给singleton分配内存
 * ②在空间内创建对象即初始化
 * ③将singleton变量指向分配的内存空间
 * 但是在 JVM 的即时编译器中存在指令重排序的优化。
 * 也就是说上面的②和③的顺序是不能保证的，最终的执行顺序可能是 ①②③也可能是 ①③②。
 * 如果是后者，则在③执行完毕、②未执行之前，被线程二抢占了，
 * 这时 instance 已经是非 null 了（但却没有初始化），所以线程二会直接返回 instance，
 * 然后使用，然后就会报错。
 * 解决办法就是要将 singleton变量声明成 volatile 就可以了，
 * 因为volatile有禁止指令重排的特性。
 */
public class LazySingleton {
    private volatile static LazySingleton singleton = null;
 
    private LazySingleton() {
    }
 
    public static LazySingleton getSingleton() {
        if (singleton == null) {
            synchronized (LazySingleton.class) {
                if (singleton == null)
                    singleton = new LazySingleton();
            }
        }
 
        return singleton;
    }
}