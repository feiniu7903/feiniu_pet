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
 * Time: 下午3:59<p/>
 * Email:kouhongyu@163.com<p/>
 */
public class ComTaskGuardJob implements Runnable {

    Log log = LogFactory.getLog(this.getClass());
    private ComTaskService comTaskService;

    public void run() {
        if (Constant.getInstance().isJobRunnable()) {
            //计算下次执行时间
            resetNextRunTime();
            //清理死线程
            cleanDieThread();
        }
    }

    private void cleanDieThread() {
        Map<String, Object> paramMap = new HashMap<String, Object>();

        paramMap.put("available", Constant.COM_TASK_AVAILABLE.ENABLE.getCode());//任务为“启用”
        paramMap.put("status", Constant.COM_TASK_STATUS.RUN.getCode());//运行状态为“运行”

        paramMap.put("_startRow", 1);
        paramMap.put("_endRow", 1000);

        List<ComTask> comTaskList = comTaskService.getComTaskList(paramMap);

        for (ComTask comTask : comTaskList) {
            if (!ComTaskThreadPoolFactory.getInstance().isExist(comTask.getTaskId())) {
                log.info("Clean die thread , taskId : " + comTask.getTaskId()+" ; taskName : "+comTask.getTaskName());
                comTask.setStatus(Constant.COM_TASK_STATUS.END.getCode());
                comTaskService.saveComTask(comTask);
            }
        }
    }

    private void resetNextRunTime() {
        Map<String, Object> paramMap = new HashMap<String, Object>();

        paramMap.put("available", Constant.COM_TASK_AVAILABLE.ENABLE.getCode());//任务为“启用”
        paramMap.put("status", Constant.COM_TASK_STATUS.END.getCode());//运行状态为“结束”

        paramMap.put("_startRow", 1);
        paramMap.put("_endRow", 1000);
        List<ComTask> comTaskList = comTaskService.getComTaskList(paramMap);

        for (ComTask comTask : comTaskList) {
            try {
                //重新计算下一次的运行时间
                Date nextRunTime = comTaskService.calculateNextRunTime(comTask);

                comTask.setNextRunTime(nextRunTime);
                comTask.setStatus(Constant.COM_TASK_STATUS.WAIT.getCode());

                comTaskService.saveComTask(comTask);

            } catch (Exception e) {
                log.error(e);
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
