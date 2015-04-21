package com.lvmama.comm.bee.po.ebooking;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * EBooking公告.
 *
 */
public class EbkAnnouncement implements Serializable {
	
	private static final long serialVersionUID = 3834981928886791384L;
	//标识主键.
	private Long announcementId;
    //标题.
	private String title;
    //内容.
    private String content;
    //附件.保存FSClient返回的文件ID.
    private Long attachment;
    //通知时间.对于Ebooking前台公告的显示只有当前时间大于等于beginDate时才显示此公告信息.
    private Date beginDate;
    //公告发布的目的地.引用com.lvmama.comm.vo.Constant.EBookingBizType中的枚举值.
    private String bizType;
    //操作人.
    private String operator;
    //创建时间.
    private Date createTime;

    //用于页面显示(文件名+文件大小(单位KB)).
    private String attachmentName;
    
    public String getAttachmentName() {
		return attachmentName;
	}

	public void setAttachmentName(String attachmentName) {
		this.attachmentName = attachmentName;
	}

	public Long getAnnouncementId() {
        return announcementId;
    }

    public void setAnnouncementId(Long announcementId) {
        this.announcementId = announcementId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getAttachment() {
        return attachment;
    }

    public void setAttachment(Long attachment) {
        this.attachment = attachment;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public String getBizType() {
        return bizType;
    }

    public void setBizType(String bizType) {
        this.bizType = bizType;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    
    public String getReleaseStatus() {
    	if (this.beginDate == null) {
    		return "已发布";
    	}
    	if (beginDate.before(new Date())) {
    		return "已发布";
    	} else {
    		return "未发布";
    	}
    }
    
    public boolean isHaveAttachment() {
    	return this.attachment != null;
    }
    
    
    
}