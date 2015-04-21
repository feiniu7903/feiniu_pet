/**
 * 
 */
package com.lvmama.back.sweb.common;

import java.util.HashMap;
import java.util.Map;

import org.apache.struts2.convention.annotation.Action;
import org.springframework.util.Assert;

import com.lvmama.back.sweb.BaseAction;
import com.lvmama.comm.pet.po.perm.PermUser;
import com.lvmama.comm.pet.service.perm.PermUserService;
import com.lvmama.comm.utils.json.JSONResult;
import com.lvmama.comm.utils.json.JSONResultException;

/**
 * 通过http访问perm对象.并且给客户端返回json数据
 * @author yangbin
 */
@SuppressWarnings("serial")
public class AjaxPermAction extends BaseAction {

	/**
	 * 传入的参数，用户登录名
	 */
	private String userName;

	/**
	 * 读取一个用户的信息，根据用户名
	 */
	@Action("/common/permDetail")
	public void getPerm() {
		JSONResult result = new JSONResult();
		try {
			Assert.hasLength(userName, "用户不可以为空");
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("userName", userName);
			PermUser user = permUserService.getPermUserByParams(map);

			Assert.notNull(user, "用户不存在");
			result.put("realName", user.getRealName());
			result.put("userId", user.getUserId());

			// result.put("", o)
		} catch (Exception ex) {
			result.raise(new JSONResultException(ex.getMessage()));
		}
		result.output(getResponse());
	}

	/**
	 * perm service
	 */
	private PermUserService permUserService;

	/**
	 * @param userName
	 *            the userName to set
	 */
	public void setUserName(final String userName) {
		this.userName = userName;
	}

	/**
	 * @param permUserService
	 *            the permUserService to set
	 */
	public void setPermUserService(final PermUserService permUserService) {
		this.permUserService = permUserService;
	}
}
