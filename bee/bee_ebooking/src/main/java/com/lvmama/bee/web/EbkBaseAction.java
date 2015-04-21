package com.lvmama.bee.web;

import org.apache.commons.lang3.math.NumberUtils;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.BaseAction;
import com.lvmama.comm.bee.po.eplace.EbkUser;
import com.lvmama.comm.pet.vo.Page;
import com.lvmama.comm.utils.ServletUtil;
import com.lvmama.comm.vo.Constant;

@Results(value={
		@Result(name="error_404",location="/error/error_404.jsp")
})
public class EbkBaseAction extends BaseAction {
	private static final long serialVersionUID = 1L;
	protected Long page = 1L;
	protected Page pagination;
	/**
	 * 获取当前登录用户
	 * @return
	 */
	public boolean isLogined() {
		EbkUser user = getSessionUser();
		return user == null ? false:true;
	}

	/**
	 * 获取当前登录用户
	 * @return
	 */
	public EbkUser getSessionUser(){
		return (EbkUser)ServletUtil.getSession(getRequest(), getResponse(), Constant.SESSION_EBOOKING_USER);
	}
	
	/**
	 * 获取当前登录的LVMAMA超级管理员用户
	 * @return
	 */
	public EbkUser getLvmamaSessionUser(){
		return (EbkUser)ServletUtil.getSession(getRequest(), getResponse(), Constant.SESSION_EBOOKING_USER_LVMAMA);
	}
	
	public String getBasePath() {
		String path = this.getRequest().getContextPath();
		String basePath = getRequest().getScheme() + "://"
				+ getRequest().getServerName() + ":" + getRequest().getServerPort()
				+ path + "/";
		return basePath;
	}

	/**
	 * 获得session中的UserName
	 * 
	 * @return
	 */
	public String getSessionUserName() {
		EbkUser EbkUser=getSessionUser();
		if(EbkUser!=null){
			return EbkUser.getUserName();
		}
		return "";
	}
	/**
	 * 获得当前的供应商ID
	 * 
	 * @author: ranlongfei 2012-12-6 下午6:43:15
	 * @return
	 */
	public Long getCurrentSupplierId() {
		EbkUser u=getSessionUser();
		if(u!=null){
			return u.getSupplierId();
		}
		return null;
	}
	/**
	 * 项目部署根路径
	 * @return
	 */
	public String getContextPath(){
		return getRequest().getContextPath();
	}
	protected <T> Page<?> initPage(){
		long pageSize=NumberUtils.toLong(getRequest().getParameter("perPageRecord"));
		long pageNo=NumberUtils.toLong(getRequest().getParameter("page"));
		pagination=new Page<T>(pageSize<1?10:pageSize, pageNo<1?1:pageNo);
		return pagination;
	}
	
	public Long getPage() {
		return page;
	}
	public void setPage(Long page) {
		this.page = page;
	}

	public Page<?> getPagination() {
		return pagination;
	}
}
