package test.IOC;

/**
 * @创建人 zhaojingen
 * @创建时间 2019/10/28
 * @描述
 */
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import test.IOC.core.MyBeanFactoryImpl;
import test.IOC.domain.Student;
import test.IOC.domain.User;

@SpringBootApplication
public class IocApplication {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(IocApplication.class, args);
        MyBeanFactoryImpl beanFactory = new MyBeanFactoryImpl();
        User user1 = (User) beanFactory.getBeanByName("test.IOC.domain.User");
        User user2 = (User) beanFactory.getBeanByName("test.IOC.domain.User");
        Student student1 = user1.getStudent();
        Student student2 = user1.getStudent();
        Student student3 = (Student) beanFactory.getBeanByName("test.IOC.domain.Student");
        System.out.println(user1);
        System.out.println(user2);
        System.out.println(student1);
        System.out.println(student2);
        System.out.println(student3);
    }
}
