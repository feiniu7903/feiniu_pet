/**
 * 
 */
package com.lvmama.comm.pet.po.info;

import java.io.Serializable;
import java.util.Date;

/**
 * @author liuyi
 *
 */
public class InfoHelpCenter implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = -7785529979788518300L;

	private Long id;

    private Long typeId;
    
    private String typeName;
    
    private String title;

    private String userName;

    private String content;

    private Date createTime;
    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
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

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}


}
