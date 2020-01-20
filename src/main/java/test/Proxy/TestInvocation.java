package test.Proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @创建人 zhaojingen
 * @创建时间 2019/7/9
 * @描述
 */
public class TestInvocation implements InvocationHandler {
    private Object target;

    public Object bind(Object target){
        this.target = target;
        return  Proxy.newProxyInstance(target.getClass().getClassLoader(),target.getClass().getInterfaces(),this);

    }
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object invoke = method.invoke(target, args);
        return  invoke;
    }
}
