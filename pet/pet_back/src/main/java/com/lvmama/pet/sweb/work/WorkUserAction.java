package com.lvmama.pet.sweb.work;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;

import com.lvmama.comm.BackBaseAction;
import com.lvmama.comm.pet.service.perm.PermUserService;
import com.lvmama.comm.utils.StringUtil;

@Namespace("/work/user")
public class WorkUserAction extends BackBaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private PermUserService permUserService;

	@Action("change")
	public String changeUser() {
		// 用户名
		String userName = getRequestParameter("userName");
		// 更改状态
		String paramStatus = getRequestParameter("status");

		if (!StringUtil.isEmptyString(userName)
				&& !StringUtil.isEmptyString(paramStatus)) {
			String status = "";
			if ("0".equals(paramStatus)) {
				status = "REST";
			} else {
				status = "ONLINE";
			}
			permUserService.updateWorkStatus(userName, status);
		}

		return null;
	}

	public PermUserService getPermUserService() {
		return permUserService;
	}

	public void setPermUserService(PermUserService permUserService) {
		this.permUserService = permUserService;
	}

}
