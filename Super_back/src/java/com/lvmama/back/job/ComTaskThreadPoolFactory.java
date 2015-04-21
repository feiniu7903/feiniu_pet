package com.lvmama.back.job;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by IntelliJ IDEA.<p/>
 * User: troy-kou<p/>
 * Date: 14-5-26<p/>
 * Time: 下午3:03<p/>
 * Email:kouhongyu@163.com<p/>
 */
public class ComTaskThreadPoolFactory {

    private static ComTaskThreadPoolFactory poolFactory;
    private ExecutorService executorService;
    private Set<Long> taskIdSet = new HashSet<Long>();

    private ComTaskThreadPoolFactory() {
        executorService = Executors.newCachedThreadPool();
    }

    public static ComTaskThreadPoolFactory getInstance() {
        if (poolFactory == null) {
            poolFactory = new ComTaskThreadPoolFactory();
        }
        return poolFactory;
    }

    public void addThread(ComTaskThread thread) {
        executorService.submit(thread);
    }

    public boolean isExist(Long taskId) {
        return taskIdSet.contains(taskId);
    }

    public void addTaskId(Long taskId) {
        taskIdSet.add(taskId);
    }

    public void removeTaskId(Long taskId) {
        taskIdSet.remove(taskId);
    }

    public int size() {
        return taskIdSet.size();
    }
}
