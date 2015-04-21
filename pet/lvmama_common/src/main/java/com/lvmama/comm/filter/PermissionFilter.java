package com.lvmama.comm.filter;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lvmama.comm.pet.po.perm.PermPermission;
import com.lvmama.comm.pet.po.perm.PermUser;
import com.lvmama.comm.utils.ServletUtil;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.vo.Constant;

public class PermissionFilter implements Filter{
	private String[] arr;

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		
	}

	/** 
	 * 
	*/ 
	public void doFilter(ServletRequest request, ServletResponse response,FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		String uri = req.getRequestURI().toLowerCase();
		boolean isNeedCheck = true;
		for (String str : arr) {
			if (uri.toLowerCase().matches(str.toLowerCase().trim())) {
				isNeedCheck = false;
				break;
			}
		}
		if (isNeedCheck) {
			//验证是否登录
			PermUser user = (PermUser) ServletUtil.getSession(req, res, Constant.SESSION_BACK_USER);
			if (user == null 
					|| !user.getPermUserPass() 
					|| uri.equalsIgnoreCase(req.getContextPath()) 
					|| uri.equalsIgnoreCase(req.getContextPath()+"/")) {
				res.sendRedirect("/pet_back/login.do");
				return;
			}
			//验证权限
			if(isPermRefused(req,user)){
				res.sendRedirect("/pet_back/permError.jsp?from=" 
						+ URLEncoder.encode(req.getRequestURI()) 
						+ "&userId=" + URLEncoder.encode(String.valueOf(user.getUserId())));
				return;
			}
		}
		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		String urls = fConfig.getInitParameter("excludeUrl");
		arr = urls.split(",");
	}

	//验证权限
	private boolean isPermRefused(HttpServletRequest request, PermUser user){
		if(user.isAdministrator()){
			return false;
		}
		List<PermPermission> permList = user.getPermissionList();
		if(permList == null || permList.size() == 0){
			return true;
		}
		for(PermPermission perm : permList){
			if(StringUtil.isEmptyString(perm.getUrlPattern())){
				continue;
			}
			String[] arr2 = perm.getUrlPattern().split(";");
			if(arr2 == null || arr2.length == 0){
				continue;
			}
			for(String pattern : arr2){
				try{
					if(perm.getUrlPattern() != null && request.getRequestURI().toLowerCase().matches(pattern.toLowerCase().trim())){
						return false;
					}
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}
		return true;
	}
}
