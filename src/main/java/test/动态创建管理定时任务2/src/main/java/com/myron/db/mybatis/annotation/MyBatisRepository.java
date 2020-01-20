package test.动态创建管理定时任务2.src.main.java.com.myron.db.mybatis.annotation;

import org.springframework.stereotype.Repository;

/**
 * 自定义注解 
 * 被@MyBatisRepository注解的Dao接口才会被扫描生成mybaties MapperFactoryBean的代理类
 * @author Administrator
 *
 */
@Repository
public @interface MyBatisRepository {
	String value() default "";
}
