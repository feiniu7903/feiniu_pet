package com.lvmama.businessreply.web.auth;

import org.apache.commons.jexl2.UnifiedJEXL.Exception;
import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.api.Label;

import com.lvmama.businessreply.vo.BusinessConstant;
import com.lvmama.comm.pet.po.comment.CmtBusinessUser;
import com.lvmama.comm.pet.service.comment.CmtBusinessUserService;
import com.lvmama.comm.spring.SpringBeanProxy;

public class LoginAction extends GenericForwardComposer {

	private static Logger logger = Logger.getLogger(LoginAction.class);
	private CmtBusinessUserService cmtBusinessUserService =  (CmtBusinessUserService) SpringBeanProxy.getBean("cmtBusinessUserService");;
	private CmtBusinessUser user = new CmtBusinessUser();
	private Label message;

	public final void doBeforeComposeChildren(Component comp) throws Exception {
		Components.wireVariables(comp, this);
		comp.setAttribute("loginAction", this, false);
	}

	public void login() {
		param.put("password", user.getPassword());
		param.put("userID", user.getUserID());
		param.put("_startRow", 0);
		param.put("_endRow", 1);
		param.put("isValid", "Y");
		CmtBusinessUser businessUser = cmtBusinessUserService
				.getCmtBusinessUser(param);
		if (businessUser != null) {
			super.session.setAttribute(BusinessConstant.SESSION_USER,
					businessUser);
			Executions.sendRedirect("/index.zul");

		} else {
			getMessage().setValue("用户不存在，或密码错误！");
			logger.info("用户不存在，或密码错误！ " + user.getUserID());
		}
	}

	public void setUser(CmtBusinessUser user) {
		this.user = user;
	}


	public CmtBusinessUser getUser() {
		return user;
	}

	public void setMessage(Label message) {
		this.message = message;
	}


	public Label getMessage() {
		return message;
	}

	public CmtBusinessUserService getCmtBusinessUserService() {
		return cmtBusinessUserService;
	}

	public void setCmtBusinessUserService(
			CmtBusinessUserService cmtBusinessUserService) {
		this.cmtBusinessUserService = cmtBusinessUserService;
	}

}
