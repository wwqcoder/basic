package test.IOC.domain;

/**
 * @创建人 zhaojingen
 * @创建时间 2019/10/28
 * @描述
 */
import test.IOC.annotion.MyIoc;
import lombok.Data;

/**
 * @program: springBootPractice
 * @description:
 * @author: hu_pf
 * @create: 2019-01-23 20:25
 **/
@MyIoc
@Data
public class User {
    private Student student;
}
