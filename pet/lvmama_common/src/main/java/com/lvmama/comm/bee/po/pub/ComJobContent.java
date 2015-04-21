package com.lvmama.comm.bee.po.pub;

import java.io.Serializable;
import java.util.Date;

public class ComJobContent implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = -6386272110792990116L;

	private Long comJobContentId;

    private Long objectId;

    private String objectType;

    private String jobType;

    private Date planTime;

    private Date createTime=new Date();
    
    public static enum JOB_TYPE{
    	TRAIN_FAIL_CANCEL_ORDER,//火车票出票失败废单
    	TRAIN_CREATE_ORDER;//火车票订单创建消息推送
    }

    public Long getComJobContentId() {
        return comJobContentId;
    }

    public void setComJobContentId(Long comJobContentId) {
        this.comJobContentId = comJobContentId;
    }

    public Long getObjectId() {
        return objectId;
    }

    public void setObjectId(Long objectId) {
        this.objectId = objectId;
    }

    public String getObjectType() {
        return objectType;
    }

    public void setObjectType(String objectType) {
        this.objectType = objectType == null ? null : objectType.trim();
    }

    public String getJobType() {
        return jobType;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType == null ? null : jobType.trim();
    }

    public Date getPlanTime() {
        return planTime;
    }

    public void setPlanTime(Date planTime) {
        this.planTime = planTime;
    }
    

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}