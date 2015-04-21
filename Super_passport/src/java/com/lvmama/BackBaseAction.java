package com.lvmama;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;

import org.apache.commons.lang3.math.NumberUtils;
import org.apache.log4j.Logger;

import com.lvmama.comm.BaseAction;
import com.lvmama.comm.bee.po.pass.PassPortUser;
import com.lvmama.comm.pet.vo.Page;
import com.lvmama.comm.vo.PassportConstant;

public abstract class BackBaseAction extends BaseAction {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 9197369313409325055L;
	protected Logger log = Logger.getLogger(this.getClass());

	protected <T> Page<?> initPage(){
		long pageSize=NumberUtils.toLong(getRequest().getParameter("perPageRecord"));
		long pageNo=NumberUtils.toLong(getRequest().getParameter("page"));
		pagination=new Page<T>(pageSize<1?10:pageSize, pageNo<1?1:pageNo);
		return pagination;
	}
	/**
	 * 发送xml请求结果
	 * 
	 * @throws ServletException
	 * @throws IOException
	 */
	public void sendXmlResult(String result) {

		this.getResponse().setContentType("text/xml;charset=GBK");
		this.getResponse().setCharacterEncoding("GBK");
		try {
			PrintWriter out = this.getResponse().getWriter();
			out.write(result);
			out.flush();
			out.close();
		} catch (Exception e) {
			log.error("返回xml结果异常", e);
		}
	}
	
	public Page<?> getPagination() {
		return pagination;
	}

	protected Page<?> pagination;
	
	public Object getSession(String str) {
		return getRequest().getSession().getAttribute(str);
	}
	
	private PassPortUser getSessionUser(){
		Object u = this.getSession(PassportConstant.SESSION_USER);
		if(u == null) {
			return null;
		}
		return (PassPortUser)u;
	}
	
	public String getOperatorName() {
		PassPortUser u = this.getSessionUser();
		if(u == null) {
			return "";
		}
		return u.getName();
	}
	
	public Long getOperatorLongId() {
		PassPortUser u = this.getSessionUser();
		if(u == null) {
			return null;
		}
		return u.getPassPortUserId();
	}

}
