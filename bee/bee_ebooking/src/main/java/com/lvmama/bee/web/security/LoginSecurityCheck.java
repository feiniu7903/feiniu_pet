package com.lvmama.bee.web.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lvmama.comm.bee.po.eplace.EbkUser;
import com.lvmama.comm.utils.ServletUtil;
import com.lvmama.comm.vo.Constant;

/**
 * 登录校验
 * @author zhaojindong
 *
 */
public class LoginSecurityCheck implements SecurityCheck{
	public CheckResult check(HttpServletRequest request,HttpServletResponse response){
		EbkUser loginUser = (EbkUser)ServletUtil.getSession(request, response, Constant.SESSION_EBOOKING_USER);
		if(loginUser == null){
			return CheckResult.FAIL_LOGIN;
		}
		return CheckResult.SUCCESS;
	}
}
