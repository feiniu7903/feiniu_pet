package com.lvmama.comm.pet.po.pub;

import java.io.Serializable;
import java.util.Date;

/**
 * 文件系统文件PO.
 * @author sunruyi
 */
public class ComFSFile implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6286452820258702506L;
	/**
	 * 主键ID.
	 */
	private Long fsFileId;
	/**
	 * 文件系统ID.
	 */
	private Long fsId;
	/**
	 * 文件名.
	 */
	private String fileName;
	
	/**
	 * 文件服务器上的文件名.
	 */
	private String serverFileName;
	/**
	 * 文件路径.
	 */
	private String filePath;
	/**
	 * 插入时间.
	 */
	private Date insertedTime;
	public Long getId() {
		return fsFileId;
	}
	public void setId(Long id) {
		this.fsFileId = id;
	}
	public Long getFsId() {
		return fsId;
	}
	public void setFsId(Long fsId) {
		this.fsId = fsId;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public Date getInsertedTime() {
		return insertedTime;
	}
	public void setInsertedTime(Date insertedTime) {
		this.insertedTime = insertedTime;
	}
	public String getServerFileName() {
		return serverFileName;
	}
	public void setServerFileName(String serverFileName) {
		this.serverFileName = serverFileName;
	}
	
}
