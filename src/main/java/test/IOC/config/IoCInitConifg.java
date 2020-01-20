package test.IOC.config;

/**
 * @创建人 zhaojingen
 * @创建时间 2019/10/28
 * @描述
 */
import test.IOC.annotion.MyIoc;
import test.IOC.core.MyBeanFactoryImpl;
import test.IOC.domain.BeanDefinition;
import org.reflections.Reflections;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import test.IOC.annotion.MyIoc;
import test.IOC.core.MyBeanFactoryImpl;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Order(value = 1)
public class IoCInitConifg implements CommandLineRunner {

    @Override
    public void run(String... args) {
        ConcurrentHashMap<String, BeanDefinition> concurrentHashMap = new ConcurrentHashMap<>();
        Set<String> beanNameSet = Collections.synchronizedSet(new HashSet<>());
        Reflections reflections = new Reflections();
        //获得项目中所有被MyIoc标记得类
        Set<Class<?>> typesAnnotatedWith = reflections.getTypesAnnotatedWith(MyIoc.class);
        //将其信息初始进自定义容器MyBeanFactory中
        for (Class clazz : typesAnnotatedWith) {
            BeanDefinition beanDefinition = new BeanDefinition();
            String className = clazz.getName();
            String superclassName = clazz.getSuperclass().getName();
            beanDefinition.setClassName(className);
            beanDefinition.setSuperNames(superclassName);
            beanDefinition.setAlias(getClassName(className));
            concurrentHashMap.put(className, beanDefinition);
            beanNameSet.add(className);
        }
        MyBeanFactoryImpl.setBeanDineMap(concurrentHashMap);
        MyBeanFactoryImpl.setBeanNameSet(beanNameSet);
    }

    private String getClassName(String beanClassName) {
        String className = beanClassName.substring(beanClassName.lastIndexOf(".") + 1);
        className = className.substring(0, 1).toLowerCase() + className.substring(1);
        return className;
    }
}