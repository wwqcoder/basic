package test.IOC.annotion;

/**
 * @创建人 zhaojingen
 * @创建时间 2019/10/28
 * @描述
 */
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface MyIoc {

}
