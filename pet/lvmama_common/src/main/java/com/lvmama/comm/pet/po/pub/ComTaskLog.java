package com.lvmama.comm.pet.po.pub;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.<p/>
 * User: troy-kou<p/>
 * Date: 14-1-7<p/>
 * Time: 下午4:26<p/>
 * Email:kouhongyu@163.com<p/>
 */
public class ComTaskLog implements Serializable{

    private static final long serialVersionUID = 4988952573415571254L;
    /**
     * 日志ID
     */
    private Long logId;
    /**
     * 任务ID
     */
    private Long taskId;
    /**
     * 任务名称
     */
    private String taskName;
    /**
     * 远程任务返回状态 1:完成;2:部分完成;3:未完成;
     */
    private Integer resultStatus;
    /**
     * 远程任务返回结果
     */
    private String resultInfo;
    /**
     * 异常信息
     */
    private String exceptionInfo;
    /**
     * 任务开始时间
     */
    private Date startTime;
    /**
     * 任务结束时间
     */
    private Date endTime;

    public Long getLogId() {
        return logId;
    }

    public void setLogId(Long logId) {
        this.logId = logId;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public Integer getResultStatus() {
        return resultStatus;
    }

    public void setResultStatus(Integer resultStatus) {
        this.resultStatus = resultStatus;
    }

    public String getResultInfo() {
        return resultInfo;
    }

    public void setResultInfo(String resultInfo) {
        this.resultInfo = resultInfo;
    }

    public String getExceptionInfo() {
        return exceptionInfo;
    }

    public void setExceptionInfo(String exceptionInfo) {
        this.exceptionInfo = exceptionInfo;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String toString() {
        return "ComTaskLog{" +
                "logId=" + logId +
                ", taskId=" + taskId +
                ", taskName='" + taskName + '\'' +
                ", resultStatus=" + resultStatus +
                ", resultInfo='" + resultInfo + '\'' +
                ", exceptionInfo='" + exceptionInfo + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }
}
