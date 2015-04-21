package com.lvmama.comm.pet.po.pub;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.<p/>
 * User: troy-kou<p/>
 * Date: 14-1-3<p/>
 * Time: 上午10:25<p/>
 * Email:kouhongyu@163.com<p/>
 */
public class ComTask implements Serializable {

    private static final long serialVersionUID = -4317240365577039061L;

    /**
     * 主键
     */
    private Long taskId;
    /**
     * 任务名称
     */
    private String taskName;
    /**
     * webService地址
     */
    private String webServiceUrl;
    /**
     * 计划时间
     */
    private Date planTime;
    /**
     * 执行周期
     */
    private String cycle;
    /**
     * 周期尺度
     */
    private Long cycleDimension;
    /**
     * 每周几执行
     */
    private String week;
    /**
     * 任务状态
     */
    private String status;
    /**
     * 任务描述
     */
    private String description;
    /**
     * 是否启用
     */
    private String available;
    /**
     * 参数
     */
    private String parameter;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 下次运行时间
     */
    private Date nextRunTime;

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

    public String getWebServiceUrl() {
        return webServiceUrl;
    }

    public void setWebServiceUrl(String webServiceUrl) {
        this.webServiceUrl = webServiceUrl;
    }

    public Date getPlanTime() {
        return planTime;
    }

    public void setPlanTime(Date planTime) {
        this.planTime = planTime;
    }

    public Date getNextRunTime() {
        return nextRunTime;
    }

    public void setNextRunTime(Date nextRunTime) {
        this.nextRunTime = nextRunTime;
    }

    public String getCycle() {
        return cycle;
    }

    public void setCycle(String cycle) {
        this.cycle = cycle;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAvailable() {
        return available;
    }

    public void setAvailable(String available) {
        this.available = available;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    public Long getCycleDimension() {
        return cycleDimension;
    }

    public void setCycleDimension(Long cycleDimension) {
        this.cycleDimension = cycleDimension;
    }
}
