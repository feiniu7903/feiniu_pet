package com.lvmama.pet.web.user.manager;

import org.apache.commons.lang3.StringUtils;

import com.lvmama.comm.pet.po.money.CashAccount;
import com.lvmama.comm.pet.po.pub.ComLog;
import com.lvmama.comm.pet.po.user.UserUser;
import com.lvmama.comm.pet.service.money.CashAccountService;
import com.lvmama.comm.pet.service.pub.ComLogService;
import com.lvmama.comm.pet.service.user.UserUserProxy;
import com.lvmama.comm.vo.Constant;
import com.lvmama.pet.web.BaseAction;

/**
 * 修改现金账户支付手机号ACTION
 * 
 * @author shihui
 * 
 */
public class ModifyCashPayMobileNumberAction extends BaseAction {

	private static final long serialVersionUID = -4200450210963644639L;
	private UserUserProxy userUserProxy;
	private Long uuId;
	private String userName;
	private String oldMobile;
	private String newMobile;
	private ComLogService comLogService;
	private CashAccountService cashAccountService;

	@Override
	public void doBefore() {
		UserUser user = userUserProxy.getUserUserByPk(uuId);
		if (user != null) {
			userName = user.getUserName();
			CashAccount ca = cashAccountService.queryCashAccountByUserId(user.getId());
			if(ca != null) {
				oldMobile = ca.getMobileNumber();
			}
		}
	}

	public void doSubmit() {
		if (StringUtils.isEmpty(newMobile)) {
			alert("新支付手机号不能为空！");
			return;
		}
		newMobile = newMobile.trim();
		UserUser user = userUserProxy.getUserUserByPk(uuId);
		if (null == user) {
			alert("用户不存在！");
			return;
		}
		if(!cashAccountService.isMobileRegistrable(newMobile)) {
			alert("手机号输入错误或手机号已存在，不能修改！");
			return;
		}
		
		cashAccountService.bindMobileNumber(user.getId(), newMobile, true);
		
		final ComLog log = new ComLog();
		log.setObjectType("USER_USER");
		log.setObjectId(uuId);
		log.setOperatorName(super.getSessionUserName());
		log.setLogType(Constant.COM_LOG_USER_MANAGER.MODIFY_CASH_PAY_MOBILE_NUMBER.name());
		log.setLogName("修改现金账户支付手机号");
		log.setContent(oldMobile + "-->" + newMobile);
		comLogService.addComLog(log);
		alert("操作成功");
		return;
	}

	public void setUserUserProxy(UserUserProxy userUserProxy) {
		this.userUserProxy = userUserProxy;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setUuId(Long uuId) {
		this.uuId = uuId;
	}

	public void setComLogService(ComLogService comLogService) {
		this.comLogService = comLogService;
	}

	public void setCashAccountService(CashAccountService cashAccountService) {
		this.cashAccountService = cashAccountService;
	}

	public String getOldMobile() {
		return oldMobile;
	}

	public void setOldMobile(String oldMobile) {
		this.oldMobile = oldMobile;
	}

	public String getNewMobile() {
		return newMobile;
	}

	public void setNewMobile(String newMobile) {
		this.newMobile = newMobile;
	}
}
