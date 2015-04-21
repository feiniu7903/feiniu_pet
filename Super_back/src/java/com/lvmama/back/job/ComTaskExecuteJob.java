package com.lvmama.back.job;

import com.lvmama.comm.bee.service.com.ComTaskService;
import com.lvmama.comm.pet.po.pub.ComTask;
import com.lvmama.comm.vo.Constant;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.<p/>
 * User: troy-kou<p/>
 * Date: 14-1-6<p/>
 * Time: 下午4:00<p/>
 * Email:kouhongyu@163.com<p/>
 */
public class ComTaskExecuteJob implements Runnable {

    Log log = LogFactory.getLog(this.getClass());
    private ComTaskService comTaskService;

    public void run() {
        if (Constant.getInstance().isJobRunnable()) {

            Map<String, Object> paramMap = new HashMap<String, Object>();

            paramMap.put("available", Constant.COM_TASK_AVAILABLE.ENABLE.getCode());//任务为“启用”
            paramMap.put("status", Constant.COM_TASK_STATUS.WAIT.getCode());//运行状态为“等待”
            paramMap.put("currentTime", new Date(System.currentTimeMillis()));

            paramMap.put("_startRow", 1);
            paramMap.put("_endRow", 1000);
            List<ComTask> comTaskList = comTaskService.getComTaskList(paramMap);

            for (ComTask comTask : comTaskList) {
                try {
                    ComTaskThread comTaskThread = new ComTaskThread();
                    comTaskThread.setComTask(comTask);
                    comTaskThread.setComTaskService(comTaskService);

                    ComTaskThreadPoolFactory.getInstance().addThread(comTaskThread);
                } catch (Exception e) {
                    log.error(e);
                }
            }
        }
    }

    public ComTaskService getComTaskService() {
        return comTaskService;
    }

    public void setComTaskService(ComTaskService comTaskService) {
        this.comTaskService = comTaskService;
    }
}
