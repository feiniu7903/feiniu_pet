package com.lvmama.back.web.permission;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.zkoss.zk.ui.event.InputEvent;
import org.zkoss.zul.api.Bandbox;

import com.lvmama.back.web.BaseAction;
import com.lvmama.comm.pet.po.perm.PermUser;
import com.lvmama.comm.pet.service.perm.PermUserService;

public class PermUserMacroChooseAction extends BaseAction {
	private static final long serialVersionUID = 5338048300583397867L;

	private static final Log loger = LogFactory.getLog(PermUserMacroChooseAction.class);

	private PermUserService permUserService;

	private Bandbox bandPermUser;

	private List<PermUser> permUserList;

	private String managerName;

	/**
	 * 查找产品经理
	 * 
	 * @param event
	 */
	public void changeMangerUser(InputEvent event) {
		String realName = event.getValue();
		loger.debug("into changeMangerUser realName is :" + realName);
		permUserList = permUserService.findPermUser(realName);
	}

	public void doAfter() throws Exception {
		if (StringUtils.isNotEmpty(this.getManagerName())) {
			bandPermUser.setValue(this.getManagerName());
		}
	}

	public List<PermUser> getPermUserList() {
		return permUserList;
	}

	public String getManagerName() {
		return managerName;
	}

	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}

	public void setPermUserService(PermUserService permUserService) {
		this.permUserService = permUserService;
	}

}
