package com.lvmama.pet.web.user.manager;

import org.apache.commons.lang3.StringUtils;
import com.lvmama.comm.pet.po.pub.ComLog;
import com.lvmama.comm.pet.po.user.UserUser;
import com.lvmama.comm.pet.service.money.CashAccountService;
import com.lvmama.comm.pet.service.pub.ComLogService;
import com.lvmama.comm.pet.service.user.UserUserProxy;
import com.lvmama.comm.vo.Constant;
import com.lvmama.pet.web.BaseAction;

/**
 * 后台冻结用户ACTION
 * 
 * @author shihui
 * 
 */
public class FreezeUserAction extends BaseAction {

	private static final long serialVersionUID = -4200450210963644639L;
	private UserUserProxy userUserProxy;
	private Long uuId;
	private String userName;
	private ComLogService comLogService;
	private String freezeReason;
	private CashAccountService cashAccountService;

	@Override
	public void doBefore() {
		UserUser user = userUserProxy.getUserUserByPk(uuId);
		if (user != null) {
			userName = user.getUserName();
		}
	}

	public void doSubmit() {
		if (StringUtils.isEmpty(freezeReason) || StringUtils.isEmpty(freezeReason.trim())) {
			alert("冻结理由不能为空！");
			return;
		}
		freezeReason = freezeReason.trim();
		UserUser user = userUserProxy.getUserUserByPk(uuId);
		if (null == user) {
			alert("用户不存在！");
			return;
		}
		
		//将现金账户改为不可用
		cashAccountService.changeCashAccountValidByParams(uuId, "N", freezeReason, super.getSessionUserName());
		
		final ComLog log = new ComLog();
		log.setObjectType("USER_USER");
		log.setObjectId(uuId);
		log.setOperatorName(super.getSessionUserName());
		log.setLogType(Constant.COM_LOG_USER_MANAGER.FREEZE_USER.name());
		log.setLogName("冻结账户");
		log.setContent(freezeReason);
		comLogService.addComLog(log);
		alert("冻结用户成功");
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

	public String getFreezeReason() {
		return freezeReason;
	}

	public void setFreezeReason(String freezeReason) {
		this.freezeReason = freezeReason;
	}

	public void setCashAccountService(CashAccountService cashAccountService) {
		this.cashAccountService = cashAccountService;
	}
}
