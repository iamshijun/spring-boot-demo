package com.kibou;

import java.util.concurrent.Future;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.kibou.appconfig.SpringFeatureCustomization;
import com.kibou.service.IndexService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class SpringFeatureTest2 {

	@Autowired
	private IndexService indexService;
	
	
	/**
	 * @see SpringFeatureCustomization
	 * @throws Throwable
	 */
	@Test
	public void testSchedule() throws Throwable{
		//just watch console
		Thread.sleep(10 * 1000);
	}
	
	/**
	 * 测试时 注意结合 @see SpringFeatureCustomization 和配置文件application.yaml
	 * @throws Throwable
	 */
	@Test
	public void testAsyncInvoke() throws Throwable{
		//可以通过看logger的输出-对应的线程是main还是其他线程名称e.g : TaskExecutor,即可看出是否开启了Async功能
		Future<String> future = indexService.getArticleByRevertedIndex("google");
		System.out.println("...get result...");
		System.out.println(future.get() + " , " + future.isDone());
	}
}
