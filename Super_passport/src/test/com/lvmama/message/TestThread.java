package com.lvmama.message;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

public class TestThread implements Runnable{

	private int x;
	
	
	private TestThread(int x) {
		super();
		this.x = x;
	}


	@Override
	public void run() {
//		try {
//			Thread.sleep(1000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		System.out.println(Thread.currentThread().getName()+"    number:"+x+"   "+System.currentTimeMillis());
	}

	
	public static void main(String[] args) {
		ExecutorService executor =Executors.newScheduledThreadPool(5);
		
		for(int i=0;i<1000;i++){
			executor.submit(new TestThread(i));
			System.out.println("fffffffff");
		}
		executor.shutdown();
	}
}
