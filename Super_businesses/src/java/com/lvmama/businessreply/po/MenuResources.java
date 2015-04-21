/**
 * 
 */
package com.lvmama.businessreply.po;

/**
 * @author liuyi
 *
 */
public class MenuResources {
	
	/**
	 * 编号.
	 */
	private Long resourceId;
	
	
	/**
	 * 资源名.
	 */
	private String resourceName;
	
	
	/**
	 * 文件名.
	 */
	private String fileName;


	public void setResourceId(Long resourceId) {
		this.resourceId = resourceId;
	}


	public Long getResourceId() {
		return resourceId;
	}


	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}


	public String getResourceName() {
		return resourceName;
	}


	public void setFileName(String fileName) {
		this.fileName = fileName;
	}


	public String getFileName() {
		return fileName;
	}

}
