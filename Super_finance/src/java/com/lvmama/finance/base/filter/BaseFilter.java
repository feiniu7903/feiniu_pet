package com.lvmama.finance.base.filter;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lvmama.comm.pet.po.perm.PermUser;
import com.lvmama.comm.utils.ServletUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.finance.base.FinanceContext;

/**
 * 基础Filter 生成Finance上下文
 * 
 * @author yanggan
 * 
 */
public class BaseFilter implements Filter {

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
		FinanceContext.setFinanceStack(new HashMap<String, Object>());
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		if(null == request.getSession().getAttribute(Constant.SESSION_BACK_USER)){
			PermUser user = (PermUser)ServletUtil.getSession((HttpServletRequest)servletRequest, (HttpServletResponse)servletResponse, com.lvmama.comm.vo.Constant.SESSION_BACK_USER);
			request.getSession().setAttribute(Constant.SESSION_BACK_USER, user);
		}
		FinanceContext.putValue("request", request);
		FinanceContext.putValue("response", response);
		chain.doFilter(servletRequest, servletResponse);
		FinanceContext.remove();

	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {

	}

	@Override
	public void destroy() {

	}
}
