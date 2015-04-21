package com.lvmama.back.job;

import com.lvmama.comm.TaskServiceInterface;
import com.lvmama.comm.bee.service.com.ComTaskService;
import com.lvmama.comm.pet.po.pub.ComTask;
import com.lvmama.comm.pet.po.pub.ComTaskLog;
import com.lvmama.comm.pet.po.pub.TaskResult;
import com.lvmama.comm.vo.Constant;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.xfire.client.XFireProxyFactory;
import org.codehaus.xfire.service.Service;
import org.codehaus.xfire.service.binding.ObjectServiceFactory;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.<p/>
 * User: troy-kou<p/>
 * Date: 14-1-7<p/>
 * Time: 上午10:12<p/>
 * Email:kouhongyu@163.com<p/>
 */
public class ComTaskThread implements Runnable {

    Log log = LogFactory.getLog(this.getClass());
    private ComTask comTask;
    private ComTaskService comTaskService;

    public void run() {

        ComTaskThreadPoolFactory.getInstance().addTaskId(comTask.getTaskId());

        //任务日志
        ComTaskLog comTaskLog = new ComTaskLog();
        comTaskLog.setTaskId(comTask.getTaskId());
        comTaskLog.setTaskName(comTask.getTaskName());
        comTaskLog.setResultStatus(3);//未完成

        //任务开始时间
        comTaskLog.setStartTime(new Date(System.currentTimeMillis()));

        comTaskLog = comTaskService.addComTaskLog(comTaskLog);

        //任务状态修改为开始运行
        comTask.setStatus(Constant.COM_TASK_STATUS.RUN.getCode());
        comTaskService.saveComTask(comTask);

        try {
            //################## 运行任务 ################################
            log.info("#### Run task : " + comTask.getTaskName());

//                Thread.sleep(60 * 1000);//模拟运行60秒

            XFireProxyFactory serviceFactory = new XFireProxyFactory();
            Service serviceModel = new ObjectServiceFactory().create(TaskServiceInterface.class);
            TaskServiceInterface taskService = (TaskServiceInterface) serviceFactory.create(serviceModel, comTask.getWebServiceUrl());

            //运行
            TaskResult taskResult = taskService.execute(comTaskLog.getLogId(), comTask.getParameter());
            //任务返回信息
            comTaskLog.setResultStatus(taskResult.getStatus());
            comTaskLog.setResultInfo(taskResult.getResult());

            //################## 运行结束 ################################
        } catch (Exception e) {
            log.error(e);
            //任务异常信息
            comTaskLog.setResultStatus(3);//未完成
            comTaskLog.setExceptionInfo(getException(e, 2000));
        }

        //重新计算下一次的运行时间
        Date nextRunTime = comTaskService.calculateNextRunTime(comTask);
        comTask.setNextRunTime(nextRunTime);
        comTask.setStatus(Constant.COM_TASK_STATUS.WAIT.getCode());

        //保存任务
        comTaskService.saveComTask(comTask);

        //任务结束时间
        comTaskLog.setEndTime(new Date(System.currentTimeMillis()));
        comTaskService.updateComTaskLog(comTaskLog);

        ComTaskThreadPoolFactory.getInstance().removeTaskId(comTask.getTaskId());
    }

    private String getException(Exception e, int len) {
        StringBuilder messsage = new StringBuilder();
        if (e != null) {
            messsage.append(e.getClass()).append(": ").append(e.getMessage()).append("\n");
            StackTraceElement[] elements = e.getStackTrace();
            for (StackTraceElement stackTraceElement : elements) {
                messsage.append("\t").append(stackTraceElement.toString()).append("\n");
            }
        }
        return messsage.length() > len ? messsage.delete(len, messsage.length()).toString() : messsage.toString();
    }

    public ComTaskService getComTaskService() {
        return comTaskService;
    }

    public void setComTaskService(ComTaskService comTaskService) {
        this.comTaskService = comTaskService;
    }

    public ComTask getComTask() {
        return comTask;
    }

    public void setComTask(ComTask comTask) {
        this.comTask = comTask;
    }

}
