package com.lvmama.push.util;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class DataSyncThreadPoolExecutor extends ThreadPoolExecutor{

	public DataSyncThreadPoolExecutor(int corePoolSize, int maximumPoolSize,
			long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
		super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
		// TODO Auto-generated constructor stub
	}
	
	private static DataSyncThreadPoolExecutor instance;
	
	
	
    public static DataSyncThreadPoolExecutor getInstance() {  
    	if (instance ==null){
    		synchronized (DataSyncThreadPoolExecutor.class) {
    			instance= new DataSyncThreadPoolExecutor(0, Integer.MAX_VALUE,  
                                      60L, TimeUnit.SECONDS,  
                                      new SynchronousQueue<Runnable>());
			}
    	}
    	return instance;
    }

}
