package com.lvmama.search.util;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.StrutsStatics;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class EncodingInterceptor extends AbstractInterceptor {
	private static final long serialVersionUID = 1L;
	
	//目标编码
	private String encoding = "utf-8";

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		HttpServletResponse response = (HttpServletResponse) invocation
				.getInvocationContext().get(StrutsStatics.HTTP_RESPONSE);
		response.setCharacterEncoding(encoding);
		response.getWriter().write("");
		return invocation.invoke();
	}

	/**
	 * setter and getter methods as follow:
	 */
	
	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}	
}
