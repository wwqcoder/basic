package test.反射;

/**
 * @创建人 zhaojingen
 * @创建时间 2019/10/14
 * @描述
 */
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import java.lang.reflect.InvocationHandler;

class MyLibrary {
    public interface MyCallback {
        void doMyCallback();
    }
    public void mainMethod(MyCallback myCallback) {
        System.out.println("doing MyLibrary mainMethod...");
        myCallback.doMyCallback();
    }
}
class MyHandler implements InvocationHandler{
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("doing MyHandler invoke...");
        return null;
    }
}
class Test3 {
    public static void main(String[] args) throws Exception {
        Class<?> myLibraryClazz = Class.forName("test.反射.MyLibrary");//类
        Class<?> myCallbackClazz = Class.forName("test.反射.MyLibrary$MyCallback");//接口

        MyHandler myHandler = new MyHandler();//类
        MyLibrary.MyCallback myCallback = (MyLibrary.MyCallback)Proxy.newProxyInstance(
                Test3.class.getClassLoader(),//类加载器
                new Class[] { myCallbackClazz },//接口数组
                myHandler//为接口实现的对应具体方法
        );//为接口实例化对象
        Method method = myLibraryClazz.getDeclaredMethod("mainMethod", MyLibrary.MyCallback.class);//（类名，参数类型）
        method.invoke(myLibraryClazz.newInstance(), myCallback);//调用方法，（实例化对象，内部接口实现对象）
    }
}
