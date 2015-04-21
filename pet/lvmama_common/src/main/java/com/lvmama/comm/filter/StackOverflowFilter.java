package com.lvmama.comm.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lvmama.comm.utils.StackOverFlowUtil;

public class StackOverflowFilter implements Filter {

	public void doFilter(ServletRequest arg0, ServletResponse arg1,
			FilterChain arg2) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) arg0;
		HttpServletResponse res=(HttpServletResponse) arg1;
		try{
			arg2.doFilter(arg0, arg1);
		}catch (Exception ex) {
			StackOverFlowUtil.printErrorStack(req, res, ex);
		}catch(Throwable e) {
			System.out.println(" captured Throwable error url: " + req.getRequestURL().toString());
			e.printStackTrace();
		}
	}

	public void destroy() {
	}

	public void init(FilterConfig arg0) throws ServletException {
	}

}
