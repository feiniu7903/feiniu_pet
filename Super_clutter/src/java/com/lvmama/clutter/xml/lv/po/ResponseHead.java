package com.lvmama.clutter.xml.lv.po;

/**
 * xml相应头信息
 * @author dengcheng
 *
 */
public class ResponseHead {
	/**
	 * 版本
	 */
	private String version;
	/**
	 * 当前页数
	 */
	private String page;
	/**
	 * 是否是最后一页
	 */
	private boolean isLastPage;
	/**
	 * 
	 */
	private String systemError;
	/**
	 * 错误信息提示
	 */
	private String errorMessage="1";
	/**
	 * 图片域名服务器
	 */
	private String picHost="http://pic.lvmama.com";
	/**
	 * 图片尺寸1
	 * http://pic.lvmama.com/200x100
	 */
	private String picSize1="200x100";
	/**
	 * 图片尺寸2
	 * http://pic.lvmama.com/120x60
	 */
	private String picSize2="120x60";
	/**
	 * 图片尺寸3
	 * http://pic.lvmama.com/580x290
	 */
	private String picSize3="580x290";
	/**
	 * 
	 */
	private String pics="pics";
	/**
	 * 请求状态
	 */
	private String responseState="200";
	
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getPage() {
		return page;
	}
	public void setPage(String page) {
		this.page = page;
	}
	public boolean isLastPage() {
		return isLastPage;
	}
	public void setLastPage(boolean isLastPage) {
		this.isLastPage = isLastPage;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public String getPicHost() {
		return picHost;
	}
	public void setPicHost(String picHost) {
		this.picHost = picHost;
	}
	public String getPicSize1() {
		return picSize1;
	}
	public void setPicSize1(String picSize1) {
		this.picSize1 = picSize1;
	}
	public String getPicSize2() {
		return picSize2;
	}
	public void setPicSize2(String picSize2) {
		this.picSize2 = picSize2;
	}
	public String getResponseState() {
		return responseState;
	}
	public void setResponseState(String responseState) {
		this.responseState = responseState;
	}
	public String getSystemError() {
		return systemError;
	}
	public void setSystemError(String systemError) {
		this.systemError = systemError;
	}
	public String getPicSize3() {
		return picSize3;
	}
	public void setPicSize3(String picSize3) {
		this.picSize3 = picSize3;
	}
	public String getPics() {
		return pics;
	}
	public void setPics(String pics) {
		this.pics = pics;
	}

}	
