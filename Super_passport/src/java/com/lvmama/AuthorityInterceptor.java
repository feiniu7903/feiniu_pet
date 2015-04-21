package com.lvmama;

import java.util.Map;

import com.lvmama.comm.vo.PassportConstant;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
/**
 * 系统权限拦截器
 * @author chenlinjun
 *
 */
public class AuthorityInterceptor extends AbstractInterceptor {

	private static final long serialVersionUID = 2884824021127815318L;
	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		Map<String, Object> session = invocation.getInvocationContext().getSession();
		Object obj=session.get(PassportConstant.SESSION_USER);
		if(obj==null){
			return "login";
		}
		return invocation.invoke();
	}
}
