package com.lvmama.comm.bee.po.ord;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.<p/>
 * User: troy-kou<p/>
 * Date: 13-11-6<p/>
 * Time: 上午10:47<p/>
 * Email:kouhongyu@163.com<p/>
 */
public class NcComplaintDuty implements Serializable {
    private static final long serialVersionUID = -3749001980755509612L;
    /**
     * 主键
     */
    private Long dutyId;
    /**
     * 主表ID
     */
    private Long complaintId;
    /**
     * 缺陷类别
     */
    private String defectCategory;
    /**
     * 赔付总金额
     */
    private Float totalAmount;
    /**
     * 备注
     */
    private String remark;
    /**
     * 类型：责任认定,赔偿认定
     */
    private String type;
    
    /**
     * 问题简述
     */
    private String desc;

    /**
     * 认定结果
     */
    private String result;
    
    /**
     * 改进建议
     */
    private String advice;
    
    public Long getDutyId() {
        return dutyId;
    }

    public void setDutyId(Long dutyId) {
        this.dutyId = dutyId;
    }

    public Long getComplaintId() {
        return complaintId;
    }

    public void setComplaintId(Long complaintId) {
        this.complaintId = complaintId;
    }

    public String getDefectCategory() {
        return defectCategory;
    }

    public void setDefectCategory(String defectCategory) {
        this.defectCategory = defectCategory;
    }

    public Float getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Float totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getAdvice() {
		return advice;
	}

	public void setAdvice(String advice) {
		this.advice = advice;
	}

	public String toString() {
        return "NcComplaintDuty{" +
                "dutyId=" + dutyId +
                ", complaintId=" + complaintId +
                ", defectCategory='" + defectCategory + '\'' +
                ", totalAmount=" + totalAmount +
                ", remark='" + remark + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
