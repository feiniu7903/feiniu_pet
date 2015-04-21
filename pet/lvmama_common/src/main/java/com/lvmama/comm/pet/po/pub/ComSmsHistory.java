package com.lvmama.comm.pet.po.pub;

import java.io.Serializable;
import java.util.Date;

public class ComSmsHistory implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 7586933460181549211L;

	private Long smsId;

    private String templateId;

    private String status;

    private Date sendTime;

    private Long objectId;

    private String objectType;

    private String content;

    private Date createTime;
    
    private String mobile;
    
    private String description;
    
    private String mms;
    
    private String reapply;
    
    private String codeImageUrl;
    
    private byte[] codeImage;
    
    /**
     * 把密码替换为******
     * @return
     */
    public String getContentRlppa(){
    	return null == content? null : content.replaceAll("密码.*? ", "密码***** ").replaceAll("密码.*?。", "密码*****。")
    		.replaceAll("密码.*，", "密码******，");
    }      

    public Long getSmsId() {
        return smsId;
    }

    public void setSmsId(Long smsId) {
        this.smsId = smsId;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
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
    
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getMms() {
		return mms;
	}

	public void setMms(String mms) {
		this.mms = mms;
	}

	public boolean isMmsMode() {
		return "true".equalsIgnoreCase(mms);
	}

	public String getReapply() {
		return reapply;
	}

	public void setReapply(String reapply) {
		this.reapply = reapply;
	}

	public String getCodeImageUrl() {
		return codeImageUrl;
	}

	public void setCodeImageUrl(String codeImageUrl) {
		this.codeImageUrl = codeImageUrl;
	}

	public byte[] getCodeImage() {
		return codeImage;
	}

	public void setCodeImage(byte[] codeImage) {
		this.codeImage = codeImage;
	}

}