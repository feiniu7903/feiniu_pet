package com.lvmama.comm.pet.po.pub;

import java.io.Serializable;

/**
 * 文件系统服务配置PO.
 * @author sunruyi
 */
public class ComFSServiceConfig implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 359378401321375457L;
	/**
	 * 主键ID.
	 */
	private Long fsServiceConfigId;
	/**
	 * 服务类型.
	 */
	private String serverType;
	/**
	 * 文件系统ID.
	 */
	private Long fsId;
	/**
	 * 存放文件根目录.
	 */
	private String rootDir;
	public Long getId() {
		return fsServiceConfigId;
	}
	public void setId(Long id) {
		this.fsServiceConfigId = id;
	}
	public String getServerType() {
		return serverType;
	}
	public void setServerType(String serverType) {
		this.serverType = serverType;
	}
	public Long getFsId() {
		return fsId;
	}
	public void setFsId(Long fsId) {
		this.fsId = fsId;
	}
	public String getRootDir() {
		return rootDir;
	}
	public void setRootDir(String rootDir) {
		this.rootDir = rootDir;
	}
}
