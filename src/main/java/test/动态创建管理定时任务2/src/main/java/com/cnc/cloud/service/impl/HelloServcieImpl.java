package test.动态创建管理定时任务2.src.main.java.com.cnc.cloud.service.impl;

import org.springframework.stereotype.Service;
import test.动态创建管理定时任务2.src.main.java.com.cnc.cloud.service.HelloService;


@Service("helloService")
public class HelloServcieImpl implements HelloService {

	@Override
	public void sayHello() {
		System.out.println("hello world, i am quartz");
		
	}

}
