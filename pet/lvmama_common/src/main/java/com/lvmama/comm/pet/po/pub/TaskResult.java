package com.lvmama.comm.pet.po.pub;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.<p/>
 * User: troy-kou<p/>
 * Date: 14-1-7<p/>
 * Time: 下午4:10<p/>
 * Email:kouhongyu@163.com<p/>
 */
public class TaskResult implements Serializable {
    private static final long serialVersionUID = -9125140179290984246L;
    /**
     * 远程任务返回状态 1:完成;2:部分完成;3:未完成;
     */
    private Integer status;
    /**
     * 远程任务返回结果
     */
    private String result;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String toString() {
        return "TaskResult{" +
                "status=" + status +
                ", result='" + result + '\'' +
                '}';
    }
}
