package com.lvmama.eplace.web.lvmama.supplyUser;

import com.lvmama.ZkBaseAction;
import com.lvmama.comm.bee.po.pass.PassPortUser;
import com.lvmama.comm.bee.service.eplace.EPlaceService;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.passport.utils.ZkMessage;

/**
 * 通关平台用户增加.
 * 
 * @author huangli
 * 
 */
@SuppressWarnings("unchecked")
public class PortUserPasswordEditAction extends ZkBaseAction {
	/**
	 * 通关平台用户操作服务.
	 */
	private EPlaceService eplaceService;
	/**
	 * 供应商查询.
	 */
	private PassPortUser passPortUser=new PassPortUser();
	/**
	 * E景通供应商编号.
	 */
	private String passPortUserId;

	public void doBefore() {
		passPortUserId=this.getSessionUser().getPassPortUserId().toString();
	}

	public void updatePassPortUser(String password) {
		if (!StringUtil.isEmptyString(passPortUserId)) {
			PassPortUser user=this.eplaceService.getPassPortUserByPk(passPortUserId);
			if(!user.getPassword().equals(password)){
				ZkMessage.showInfo("请输入正确的原始密码!");
				return ;
			}
			user.setPassword(passPortUser.getPassword());
			eplaceService.updatePassPortUserResources(user, null, null);
			super.closeWindow();
		}
	}

	public void setEplaceService(EPlaceService eplaceService) {
		this.eplaceService = eplaceService;
	}

	public PassPortUser getPassPortUser() {
		return passPortUser;
	}

	public void setPassPortUser(PassPortUser passPortUser) {
		this.passPortUser = passPortUser;
	}

	public String getPassPortUserId() {
		return passPortUserId;
	}

	public void setPassPortUserId(String passPortUserId) {
		this.passPortUserId = passPortUserId;
	}

}
