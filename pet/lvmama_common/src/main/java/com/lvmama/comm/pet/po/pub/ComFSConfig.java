package com.lvmama.comm.pet.po.pub;

import java.io.Serializable;

/**
 * 文件系统配置PO.
 * @author sunruyi
 */
public class ComFSConfig implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5780651954146964263L;
	/**
	 * 主键ID.
	 */
	private Long fsId;
	/**
	 * 文件系统类型：目前包括本地、FTP两种.
	 */
	private String fsType;
	/**
	 * FTP服务器IP地址.
	 */
	private String fsIP;
	/**
	 * FTP服务器端口号.
	 */
	private int fsPort;
	/**
	 * FTP登录用户名.
	 */
	private String ftpUsername;
	/**
	 * FTP登录密码.
	 */
	private String ftpPassword;
	/**
	 * FTP服务器操作系统类型.
	 */
	private String ftpOSType;
	/**
	 * FTP服务器默认的编码.
	 */
	private String ftpDefaultControlEncoding;

	public Long getFsId() {
		return fsId;
	}
	public void setFsId(Long fsId) {
		this.fsId = fsId;
	}
	public String getFsType() {
		return fsType;
	}
	public void setFsType(String fsType) {
		this.fsType = fsType;
	}
	public String getFsIP() {
		return fsIP;
	}
	public void setFsIP(String fsIP) {
		this.fsIP = fsIP;
	}
	public int getFsPort() {
		return fsPort;
	}
	public void setFsPort(int fsPort) {
		this.fsPort = fsPort;
	}
	public String getFtpUsername() {
		return ftpUsername;
	}
	public void setFtpUsername(String ftpUsername) {
		this.ftpUsername = ftpUsername;
	}
	public String getFtpPassword() {
		return ftpPassword;
	}
	public void setFtpPassword(String ftpPassword) {
		this.ftpPassword = ftpPassword;
	}
	public String getFtpOSType() {
		return ftpOSType;
	}
	public void setFtpOSType(String ftpOSType) {
		this.ftpOSType = ftpOSType;
	}
	public String getFtpDefaultControlEncoding() {
		return ftpDefaultControlEncoding;
	}
	public void setFtpDefaultControlEncoding(String ftpDefaultControlEncoding) {
		this.ftpDefaultControlEncoding = ftpDefaultControlEncoding;
	}
}
