package com.lvmama.bee.web.security;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lvmama.comm.bee.po.eplace.EbkPermission;
import com.lvmama.comm.bee.po.eplace.EbkUser;
import com.lvmama.comm.utils.ServletUtil;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.vo.Constant;

/**
 * URL权限校验
 * @author zhaojindong
 *
 */
public class UrlSecurityCheck implements SecurityCheck{
	public CheckResult check(HttpServletRequest request, HttpServletResponse response){
		//admin账号不需校验
		EbkUser loginUser = (EbkUser)ServletUtil.getSession(request, response, Constant.SESSION_EBOOKING_USER);
		if("true".equals(loginUser.getIsAdmin())){
			return CheckResult.SUCCESS;
		}
		if(loginUser.getUserName().equals(Constant.getInstance().getProperty("ebooking.lvmama.admin.username"))){
			return CheckResult.SUCCESS;
		}
		
		List<EbkPermission> permList = (List<EbkPermission>)ServletUtil.getSession(request, response, Constant.Session_EBOOKING_USER_PERMISSION_LIST);
		String uri = request.getRequestURI();
		if(permList == null || permList.size() == 0){
			return CheckResult.FAIL_URL;
		}
		for(EbkPermission permission : permList){
			if(!StringUtil.isEmptyString(permission.getUrlPattern())){
				String[] patterns = permission.getUrlPattern().split(";");
				if(patterns != null && patterns.length > 0){
					for(String pattern : patterns){
						if(uri.matches(pattern)){
							return CheckResult.SUCCESS;
						}
					}
				}
			}
		}
		return CheckResult.FAIL_URL;
	}
}
