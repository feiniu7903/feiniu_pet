package com.lvmama.back.utils;

import java.util.HashMap;
import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.InputEvent;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.api.Bandbox;
import org.zkoss.zul.api.Textbox;

import com.lvmama.comm.pet.po.user.UserUser;
import com.lvmama.comm.pet.service.user.UserUserProxy;
import com.lvmama.comm.spring.SpringBeanProxy;

public class MacroUserSelect extends GenericForwardComposer {
	UserUserProxy userUserProxy = (UserUserProxy)SpringBeanProxy.getBean("userUserProxy");
	private List<UserUser> userList;
	Textbox userId;
	Bandbox mc_banduser;
	public void changeUser(InputEvent event) {
			String name = event.getValue();
			HashMap<String, Object> param = new HashMap<String, Object>();
			if (name != null) {
				param.put("search", name);
			}else{
				param.put("search", "");
			}
			param.put("maxRows", "" + 10);
			userList = userUserProxy.getUsers(param);
		
	}
	
	public void doAfterCompose(Component win) throws Exception {
		Components.wireVariables(win, this);
		Events.addEventListeners(win, this);
		Components.addForwards(win, this);

		if (userId != null && userId.getValue() != null && !userId.getValue().equals("")) {
			UserUser user = userUserProxy.getUserUserByUserNo (userId.getValue());
			mc_banduser.setValue(user.getUserName());
		}
	}
	public List<UserUser> getUserList() {
		return userList;
	}

	public void setUserList(List<UserUser> userList) {
		this.userList = userList;
	}
}
