package test.动态创建管理定时任务.service;

/**
 * @创建人 zhaojingen
 * @创建时间 2019/10/29
 * @描述
 */
import org.springframework.stereotype.Service;

@Service
public class HelloService {
    public void sayHello(String aa, Integer bb) {
        System.out.format("%s %d", aa, bb);
    }
}
