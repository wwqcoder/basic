package test.IOC.domain;

/**
 * @创建人 zhaojingen
 * @创建时间 2019/10/28
 * @描述
 */

import lombok.Data;
import test.IOC.annotion.MyIoc;
import test.IOC.annotion.MyIocUse;

/**
 * @program: springBootPractice
 * @description:
 * @author: hu_pf
 * @create: 2019-01-24 20:43
 **/
@MyIoc
@Data
public class MyIocUseDemo {

    @MyIocUse
    private User user;
}
