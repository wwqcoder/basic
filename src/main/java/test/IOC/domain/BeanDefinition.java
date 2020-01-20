package test.IOC.domain;

/**
 * @创建人 zhaojingen
 * @创建时间 2019/10/28
 * @描述
 */

import lombok.Data;
import test.IOC.annotion.MyIocUse;

/**
 * @program: springBootPractice
 * @description:
 * @author: hu_pf
 * @create: 2019-01-22 20:46
 **/
@Data
public class BeanDefinition {

    @MyIocUse
    private String className;
    private String alias;
    private String superNames;
}
