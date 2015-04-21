package com.lvmama.comm.bee.po.ord;

import com.lvmama.comm.bee.po.prod.ProdProduct;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.vo.Constant;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by IntelliJ IDEA.<p/>
 * User: troy-kou<p/>
 * Date: 13-10-28<p/>
 * Time: 下午5:47<p/>
 * Email:kouhongyu@163.com<p/>
 */
public class NcComplaint implements Serializable {


    private static final long serialVersionUID = 7922175272046311612L;

    /**
     * 主键
     */
    private Long complaintId;
    /**
     * 投诉会员名
     */
    private String userName;
    /**
     * 性别
     */
    private String gender;
    /**
     * 投诉日期
     */
    private Date complaintDate;
    /**
     * 开始处理时间
     */
    private Date startProcessTime;
    /**
     * 结案时间
     */
    private Date closeTime;
    /**
     * 完成时间
     */
    private Date completionTime;
    /**
     * 关联投诉
     */
    private String relatedComplaint;
    /**
     * 关联订单
     */
    private String relatedOrder;
    /**
     * 多次投诉
     */
    private String repeatedComplaint;
    /**
     * 投诉联系人
     */
    private String contact;
    /**
     * 联系电话
     */
    private String contactMobile;
    /**
     * 投诉人身份
     */
    private String identity;
    /**
     * 回复时效
     */
    private String replyAging;
    /**
     * 是否紧急
     */
    private String urgent;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 录入人
     */
    private String entryPeople;
    /**
     * 是否升级
     */
    private String upgrade;
    /**
     * 投诉来源
     */
    private String source;
    /**
     * 当前处理人
     */
    private String currentProcessPeople;
    /**
     * 订单号
     */
    private Long orderId;
    /**
     * 产品ID
     */
    private Long productId;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 游玩人数
     */
    private Integer numberPeople;
    /**
     * 所属中心
     */
    private String belongsCenter;
    /**
     * 投诉类型
     */
    private Long complaintType;
    /**
     * 处理状态
     */
    private String processStatus;
    /**
     * 投诉详情
     */
    private String detailsComplaint;
    /**
     * 备注或意见(完成)
     */
    private String remark;
    
    /**
     * 业务系统标示
     * @see com.lvmama.comm.vo.Constant.COMPLAINT_SYS_CODE
     */
    private String sysCode;
    
    private ProdProduct product;
    
    private NcComplaintResult complaintResult;
    
    private NcComplaintRemind complaintRemind;

    public Long getComplaintId() {
        return complaintId;
    }

    public void setComplaintId(Long complaintId) {
        this.complaintId = complaintId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getComplaintDate() {
        return complaintDate;
    }

    public void setComplaintDate(Date complaintDate) {
        this.complaintDate = complaintDate;
    }
    public String getZhComplaintDate() {
		return DateUtil.getFormatDate(complaintDate, "yyyy-MM-dd HH:mm");
	}

    public Date getStartProcessTime() {
        return startProcessTime;
    }

    public void setStartProcessTime(Date startProcessTime) {
        this.startProcessTime = startProcessTime;
    }

    public Date getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(Date closeTime) {
        this.closeTime = closeTime;
    }

    public Date getCompletionTime() {
        return completionTime;
    }

    public void setCompletionTime(Date completionTime) {
        this.completionTime = completionTime;
    }

    public String getRelatedComplaint() {
        return relatedComplaint;
    }

    public void setRelatedComplaint(String relatedComplaint) {
        this.relatedComplaint = relatedComplaint;
    }

    public String getRepeatedComplaint() {
        return repeatedComplaint;
    }

    public void setRepeatedComplaint(String repeatedComplaint) {
        this.repeatedComplaint = repeatedComplaint;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getContactMobile() {
        return contactMobile;
    }

    public void setContactMobile(String contactMobile) {
        this.contactMobile = contactMobile;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public String getReplyAging() {
        return replyAging;
    }

    public void setReplyAging(String replyAging) {
        this.replyAging = replyAging;
    }
    public String getStrReplyAging() {
		if (null != replyAging) {
			if (Constant.NC_COMPLAINT_REPLY_AGING.IMMEDIATE_RESPONSE.name().equals(replyAging)) {
				return "立即回复";
			}else if(Constant.NC_COMPLAINT_REPLY_AGING.WAITING_REPLY.name().equals(replyAging)){
				return "等待回复";
			} else if(Constant.NC_COMPLAINT_REPLY_AGING.SPECIFIC_TIME.name().equals(replyAging)){
				return "特定时间";
			} else {
				return "说说而已";
			}
		}
		return "";
	}

    public String getUrgent() {
        return urgent;
    }

    public void setUrgent(String urgent) {
        this.urgent = urgent;
    }
    public String getStrUrgent() {
		if (null != urgent && "YES".equals(urgent)) {
			return "是";
		}
		return "否";
	}

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getEntryPeople() {
        return entryPeople;
    }

    public void setEntryPeople(String entryPeople) {
        this.entryPeople = entryPeople;
    }

    public String getUpgrade() {
        return upgrade;
    }

    public void setUpgrade(String upgrade) {
        this.upgrade = upgrade;
    }
    public String getStrUpgrade() {
		if (null != upgrade && "YES".equals(upgrade)) {
			return "是";
		}else if(null != upgrade && "NO".equals(upgrade)){
			return "否";
		}
		return "疑似升级";
	}

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getCurrentProcessPeople() {
        return currentProcessPeople;
    }

    public void setCurrentProcessPeople(String currentProcessPeople) {
        this.currentProcessPeople = currentProcessPeople;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
    public Integer getNumberPeople() {
        return numberPeople;
    }

    public void setNumberPeople(Integer numberPeople) {
        this.numberPeople = numberPeople;
    }

    public String getBelongsCenter() {
        return belongsCenter;
    }

    public void setBelongsCenter(String belongsCenter) {
        this.belongsCenter = belongsCenter;
    }

    public String getProcessStatus() {
        return processStatus;
    }

    public void setProcessStatus(String processStatus) {
        this.processStatus = processStatus;
    }
    public String getStrProcessStatus() {
		if (null != processStatus) {
			if (Constant.NC_COMPLAINT_PROCESSING_STATUS.UNTREATED.name().equals(processStatus)) {
				return "未处理";
			}else if(Constant.NC_COMPLAINT_PROCESSING_STATUS.PROCESSING.name().equals(processStatus)){
				return "处理中";
			} else if(Constant.NC_COMPLAINT_PROCESSING_STATUS.SUSPENDED.name().equals(processStatus)){
				return "暂缓";
			} else if(Constant.NC_COMPLAINT_PROCESSING_STATUS.CLOSED.name().equals(processStatus)){
				return "结案";
			} else if(Constant.NC_COMPLAINT_PROCESSING_STATUS.COMPLETE.name().equals(processStatus)){
				return "完成";
			} else if(Constant.NC_COMPLAINT_PROCESSING_STATUS.STOP.name().equals(processStatus)){
				return "中止";
			} else {
				return "关闭";
			}
		}
		return "";
	}

    public String getDetailsComplaint() {
        return detailsComplaint;
    }

    public void setDetailsComplaint(String detailsComplaint) {
        this.detailsComplaint = detailsComplaint;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Long getComplaintType() {
        return complaintType;
    }

    public void setComplaintType(Long complaintType) {
        this.complaintType = complaintType;
    }

    public String getRelatedOrder() {
        return relatedOrder;
    }

    public void setRelatedOrder(String relatedOrder) {
        this.relatedOrder = relatedOrder;
    }

    public Long getProductNumber() {
        return productId;
    }

    public void setProductNumber(Long productId) {
        this.productId = productId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

	public ProdProduct getProduct() {
		return product;
	}

	public void setProduct(ProdProduct product) {
		this.product = product;
	}

	public NcComplaintResult getComplaintResult() {
		return complaintResult;
	}

	public void setComplaintResult(NcComplaintResult complaintResult) {
		this.complaintResult = complaintResult;
	}

	public NcComplaintRemind getComplaintRemind() {
		return complaintRemind;
	}

	public void setComplaintRemind(NcComplaintRemind complaintRemind) {
		this.complaintRemind = complaintRemind;
	}

	public String getSysCode() {
		return sysCode;
	}

	public void setSysCode(String sysCode) {
		this.sysCode = sysCode;
	}

	public String getSysCodeCnName() {
		if (StringUtils.isEmpty(this.sysCode)) {
			return "";
		}
		return Constant.COMPLAINT_SYS_CODE.getCnName(this.sysCode);
	}
	
	public String toString() {
        return "NcComplaint{" +
                "complaintId=" + complaintId +
                ", userName='" + userName + '\'' +
                ", gender='" + gender + '\'' +
                ", complaintDate=" + complaintDate +
                ", startProcessTime=" + startProcessTime +
                ", closeTime=" + closeTime +
                ", completionTime=" + completionTime +
                ", relatedComplaint=" + relatedComplaint +
                ", relatedOrder='" + relatedOrder + '\'' +
                ", repeatedComplaint='" + repeatedComplaint + '\'' +
                ", contact='" + contact + '\'' +
                ", contactMobile='" + contactMobile + '\'' +
                ", identity='" + identity + '\'' +
                ", replyAging='" + replyAging + '\'' +
                ", urgent='" + urgent + '\'' +
                ", createTime=" + createTime +
                ", entryPeople='" + entryPeople + '\'' +
                ", upgrade='" + upgrade + '\'' +
                ", source='" + source + '\'' +
                ", currentProcessPeople='" + currentProcessPeople + '\'' +
                ", orderId=" + orderId +
                ", productId=" + productId +
                ", email='" + email + '\'' +
                ", numberPeople=" + numberPeople +
                ", belongsCenter=" + belongsCenter +
                ", complaintType=" + complaintType +
                ", processStatus='" + processStatus + '\'' +
                ", detailsComplaint='" + detailsComplaint + '\'' +
                ", remark='" + remark + '\'' +
                ", sysCode='" + sysCode + '\'' +
                '}';
    }
}
