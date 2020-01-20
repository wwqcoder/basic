package test.annotation;

import org.apache.shiro.authc.Account;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @创建人 zhaojingen
 * @创建时间 2019/10/28
 * @描述
 */
@RestController
public class TestController {
    @RequestMapping("/test")
    public String test(){
        return "hello";
    }

    @Cacheable(value = "UserCache")//用来标记缓存查询 当标记在一个方法上时表示该方法是支持缓存的，当标记在一个类上时则表示该类所有的方法都是支持缓存的
    @CacheEvict(value="UserCache")//清空缓存
    public int getUserAge(){

        return 1;

    }
}
