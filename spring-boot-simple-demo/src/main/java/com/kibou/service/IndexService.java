package com.kibou.service;

import java.util.concurrent.Future;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

@Service
public class IndexService {
	
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Async
	public void index(){
		logger.info("Ready to reindex");
		long start = System.nanoTime();
		try {
			TimeUnit.SECONDS.sleep(ThreadLocalRandom.current().nextInt(5, 10));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		long timeElipsed = TimeUnit.SECONDS.convert(System.nanoTime() - start, TimeUnit.NANOSECONDS);
		logger.info("Reindex finished with time elipsed : " + timeElipsed + "/s");
	} 
	
	@Async
	public Future<String> getArticleByRevertedIndex(String word){
//		Thread.yield();
		logger.info("Ready to getArticle");
		long start = System.nanoTime();
		try {
			TimeUnit.SECONDS.sleep(ThreadLocalRandom.current().nextInt(5, 10));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		long timeElipsed = TimeUnit.SECONDS.convert(System.nanoTime() - start, TimeUnit.NANOSECONDS);
		logger.info("Get article finished with time elipsed : " + timeElipsed + "/s");
		return new AsyncResult<String>(new String("Article with word :" + word));
	}
}
