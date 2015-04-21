package com.lvmama.comm.pet.po.mark;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.<p/>
 * User: troy-kou<p/>
 * Date: 13-12-11<p/>
 * Time: 下午6:20<p/>
 * Email:kouhongyu@163.com<p/>
 */
public class MarkActivityBlacklist implements Serializable{
    private static final long serialVersionUID = -7958207768302174901L;
    /**
     * 主键
     */
    private Long blackId;
    /**
     * 邮件地址
     */
    private String email;
    /**
     * 手机号
     */
    private String mobileNumber;
    
    private Date createTime;

    public Long getBlackId() {
        return blackId;
    }

    public void setBlackId(Long blackId) {
        this.blackId = blackId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
