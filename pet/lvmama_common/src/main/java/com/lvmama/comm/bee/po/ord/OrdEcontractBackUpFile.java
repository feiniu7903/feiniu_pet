package com.lvmama.comm.bee.po.ord;
/**
 * @author shangzhengyuan
 * @description 电子合同扫描文件
 * @version 在线预售权
 * @time 20120712
 */
import java.io.Serializable;
import java.util.Date;

public class OrdEcontractBackUpFile implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6391942446171976984L;
	/**
	 * 文件编码
	 */
	private Long fileId;
	/**
	 * 电子合同订单编码
	 */
	private Long orderId;
	/**
	 * 文件名称
	 */
	private String fileName;
	/**
	 * 文件描述
	 */
	private String fileDescription;
	/**
	 * 文件内容
	 */
	private byte[] fileContent;
	/**
	 * 是否有效 记录删除操作
	 */
	private String isValid;
	/**
	 * 上传人
	 */
	private String createUser;
	/**
	 * 上传时间
	 */
	private Date createDate;
	/**
	 * 更新人
	 */
	private String updateUser;
	/**
	 * 更新时间
	 */
	private Date updateDate;
	public Long getFileId() {
		return fileId;
	}
	public void setFileId(Long fileId) {
		this.fileId = fileId;
	}
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFileDescription() {
		return fileDescription;
	}
	public void setFileDescription(String fileDescription) {
		this.fileDescription = fileDescription;
	}
	public byte[] getFileContent() {
		return fileContent;
	}
	public void setFileContent(byte[] fileContent) {
		this.fileContent = fileContent;
	}
	public String getIsValid() {
		return isValid;
	}
	public void setIsValid(String isValid) {
		this.isValid = isValid;
	}
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getUpdateUser() {
		return updateUser;
	}
	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
}
