package cn.wwq.singleton;

/**
 *  四、【懒汉模式】多线程安全单例模式实例四（
 *  使用静态内部类《Effective Java》上所推荐的）
 * 外部类被加载时内部类并不需要立即加载内部类，内部类不被加载则不需要进行类初始化，
 * 因此单例对象在外部类被加载了以后不占用内存。
 */
public class Singleton {
    private static class SingletonHolder {
        public static final Singleton instance = new Singleton();
    }
 
    public static Singleton getInstance() {
        return SingletonHolder.instance;
    }
}