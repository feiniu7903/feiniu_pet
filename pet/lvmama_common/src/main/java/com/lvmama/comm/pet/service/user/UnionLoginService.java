package com.lvmama.comm.pet.service.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lvmama.comm.pet.po.user.UserUser;

/**
 * 联合登陆服务接口
 *
 */
public interface UnionLoginService {

	/**
	 * 获取第三方公司的唯一用户标识
	 * @param request request
	 * @param response response
	 * @return 唯一用户标识
	 */
	String getThirdCooperationUUID(final HttpServletRequest request, final HttpServletResponse response);
	/**
	 * 生成临时用户
	 * @param request request
	 * @return 用户对象
	 */
	UserUser generateUsers(HttpServletRequest request);
}
