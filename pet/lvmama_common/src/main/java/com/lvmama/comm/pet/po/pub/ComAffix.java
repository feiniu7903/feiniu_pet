/**
 * 
 */
package com.lvmama.comm.pet.po.pub;

import java.io.Serializable;
import java.util.Date;

import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.vo.Constant;

/**
 * 
 * 附件类，上传的文件共用同一个位置保存
 * 
 * @author yangbin
 *
 */
public class ComAffix implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2402132929044085659L;

	/**
	 * 文件类型：出团通知书
	 */
	public static final String GROUP_ADVICE_NOTE_FILE_TYPE = "GROUP_ADVICE_NOTE";

	private Long affixId;
	private String userId;
	private Long objectId;
	private String objectType;
	private String fileType;
	private String name;
	private String memo;
	private String path;
	private Date createTime;
	private Long fileId;
	public Long getAffixId() {
		return affixId;
	}
	public void setAffixId(Long affixId) {
		this.affixId = affixId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
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
		this.objectType = objectType;
	}
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * @return the fileId
	 */
	public Long getFileId() {
		return fileId;
	}
	/**
	 * @param fileId the fileId to set
	 */
	public void setFileId(Long fileId) {
		this.fileId = fileId;
	}
	@Override
	public String toString() {
		return "ComAffix [affixId=" + affixId + ", userId=" + userId
				+ ", objectId=" + objectId + ", objectType=" + objectType
				+ ", fileType=" + fileType + ", name=" + name + ", memo="
				+ memo + ", path=" + path + ", createTime=" + createTime
				+ ", fileId=" + fileId + "]";
	}
	
	public String getZhFileType() {
		if(StringUtil.isEmptyString(fileType)) {
			return "";
		}
		String userFileType = Constant.COM_LOG_USER_MANAGER.getCnName(this.fileType);
		if(StringUtil.isEmptyString(userFileType)) {
			return "";
		}
		return userFileType;
	}
}
