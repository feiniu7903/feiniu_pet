package com.lvmama.bee.web.security;

import java.io.IOException;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 权限Filter
 * @author zhaojindong
 *
 */
public class SecurityFilter implements Filter{
	private List<String> excludeUsers;
	private List<String> excludeUrls;
	private List<SecurityCheck> securityCheckList;
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		
	}
	
	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
			FilterChain filterChain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest)servletRequest;
		HttpServletResponse response = (HttpServletResponse)servletResponse;

		String uri = request.getRequestURI().toLowerCase();
		if(excludeUrls != null && excludeUrls.size() > 0){
			for(String urlPattern : excludeUrls){
				if(uri.matches(urlPattern.toLowerCase())){
					filterChain.doFilter(servletRequest, servletResponse);
					return;
				}
			}
		}
		
		for(SecurityCheck securityCheck : securityCheckList){
			CheckResult result = securityCheck.check(request, response);
			if(!CheckResult.SUCCESS.equals(result)){
				response.sendRedirect(result.getRedirectUrl());
				return;
			}
		}
		if (isIndexUri(request)) {
			response.sendRedirect("index.do");
			return;
		}
		filterChain.doFilter(servletRequest, servletResponse);
	}
	private boolean isIndexUri(HttpServletRequest request) {
		String uri = request.getRequestURI().toLowerCase();
		String contextPath = request.getContextPath();
		if (uri.equalsIgnoreCase(contextPath)) {
			return true;
		}
		if (uri.equalsIgnoreCase(contextPath+"/")) {
			return true;
		}
		if (uri.equalsIgnoreCase(contextPath+"/ebooking/")) {
			return true;
		}
		if (uri.equalsIgnoreCase(contextPath+"/ebooking")) {
			return true;
		}
		if (uri.equalsIgnoreCase(contextPath+"/eplace/")) {
			return true;
		}
		if (uri.equalsIgnoreCase(contextPath+"/eplace")) {
			return true;
		}
		return false;
	}
	@Override
	public void destroy() {
		
	}
	
	public List<String> getExcludeUrls() {
		return excludeUrls;
	}
	public void setExcludeUrls(List<String> excludeUrls) {
		this.excludeUrls = excludeUrls;
	}
	public List<SecurityCheck> getSecurityCheckList() {
		return securityCheckList;
	}
	public void setSecurityCheckList(List<SecurityCheck> securityCheckList) {
		this.securityCheckList = securityCheckList;
	}

	public List<String> getExcludeUsers() {
		return excludeUsers;
	}

	public void setExcludeUsers(List<String> excludeUsers) {
		this.excludeUsers = excludeUsers;
	}
}
