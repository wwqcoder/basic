package test.IOC.core;

/**
 * @创建人 zhaojingen
 * @创建时间 2019/10/28
 * @描述
 */
public interface MyBeanFactory {

    Object getBeanByName(String name) throws Exception;
}