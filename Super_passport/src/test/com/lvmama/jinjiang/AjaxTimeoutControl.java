package com.lvmama.jinjiang;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 
 * 同时只能一个操作进行
 * 
 * @author linkai
 *
 */
public class AjaxTimeoutControl {
	private static AjaxTimeoutControl instance;
	
	public static final int RUNSTART_START = 1;
	public static final int RUNSTART_END = 0;
	
	public static final int RUNRESULT_RUN = 2;
	public static final int RUNRESULT_SUCCESS = 1;
	public static final int RUNRESULT_FAILED = 0;
	
	public static final String RUNSTART_KEY = "runStart";
	public static final String RUNNUM_KEY = "runNum";
	
	private int runStatus;
	private int runNum;
	private Map<Integer, Integer> runMap;
	private Lock lock;
	
	private AjaxTimeoutControl(){}
	
	public static AjaxTimeoutControl getInstance() {
		if (instance == null) {
			synchronized (AjaxTimeoutControl.class) {
				if (instance == null) {
					instance = new AjaxTimeoutControl();
					instance.init();
				}
			}
		}
		return instance;
	}
	
	private void init() {
		runStatus = RUNSTART_END;
		runNum = 1;
		runMap = new HashMap<Integer, Integer>();
		lock = new ReentrantLock();
	}
	
	public Map<String, Integer> start() {
		lock.lock();
		try {
			Map<String, Integer> p = new HashMap<String, Integer>();
			// 如果为开始运行，则运行
			if (runStatus == RUNSTART_END) {
				p.put(RUNSTART_KEY, RUNSTART_END);
				
				runStatus = RUNSTART_START;
				runMap.put(runNum, RUNRESULT_RUN);
				
				// 已经运行
			} else {
				p.put(RUNSTART_KEY, RUNSTART_START);
			}
			p.put(RUNNUM_KEY, runNum);
			return p;
		} finally {
			lock.unlock();
		}
	}
	
	public void end(boolean isSuccess) {
		lock.lock();
		try {
			runStatus = RUNSTART_END;
			if (isSuccess) {
				runMap.put(runNum, RUNRESULT_SUCCESS);
			} else {
				runMap.put(runNum, RUNRESULT_FAILED);
			}
			runNum++;
			if (runNum > 200) {
				runNum = 1;
			}
		} finally {
			lock.unlock();
		}
	}
	
	public int lookStatus(int runNum) {
		lock.lock();
		try {
			return runMap.get(runNum);
		} finally {
			lock.unlock();
		}
	}
}
