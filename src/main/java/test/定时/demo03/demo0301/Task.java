package test.定时.demo03.demo0301;

import cn.hutool.core.date.DateTime;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * @创建人 zhaojingen
 * @创建时间 2019/12/31
 * @描述  配置文件实现
 */
@Component
public class Task {
    @Scheduled(cron="0/5 * *  * * ? ")   //每5秒执行一次
    public void execute(){
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(sdf.format(DateTime.now().toTimeStr())+"*********B任务每5秒执行一次进入测试");
    }
}
