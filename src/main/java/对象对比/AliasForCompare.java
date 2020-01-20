package 对象对比;

/**
 * @创建人 zhaojingen
 * @创建时间 2019/10/28
 * @描述
 */
import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AliasForCompare {

    String describe() default "";
}
