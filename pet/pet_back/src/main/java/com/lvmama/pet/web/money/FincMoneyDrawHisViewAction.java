package com.lvmama.pet.web.money;

import java.util.List;

import com.lvmama.comm.pet.po.money.CashAccount;
import com.lvmama.comm.pet.po.money.CashDraw;
import com.lvmama.comm.pet.po.money.CashMoneyDraw;
import com.lvmama.comm.pet.po.pub.ComLog;
import com.lvmama.comm.pet.po.user.UserUser;
import com.lvmama.comm.pet.service.money.CashAccountService;
import com.lvmama.comm.pet.service.pub.ComLogService;
import com.lvmama.comm.pet.service.user.UserUserProxy;
import com.lvmama.comm.spring.SpringBeanProxy;
import com.lvmama.pet.web.BaseAction;
/**
 * 查看打款历史详细信息Action
 * @author songlianjun
 *
 */
public class FincMoneyDrawHisViewAction extends BaseAction{

	private static final long serialVersionUID = 1L;
	
	private ComLogService petComLogService=(ComLogService) SpringBeanProxy.getBean("petComLogService");
	
	private Long moneyDrawId;
	private CashAccountService cashAccountService;
	private UserUserProxy userUserProxy;
	private CashMoneyDraw cashMoneyDraw;
	private CashDraw cashDraw;
	private UserUser user;
	private List<ComLog> logs;
	@SuppressWarnings("unchecked")
	@Override
	protected void doBefore() throws Exception {
		cashMoneyDraw = cashAccountService.getFincMoneyDrawByPK(moneyDrawId);
		if(cashMoneyDraw!=null){
			CashAccount cashAccount = cashAccountService.queryCashAccountByPk(cashMoneyDraw.getCashAccountId());
			user = userUserProxy.getUserUserByPk(cashAccount.getUserId());
		}
		cashDraw = cashAccountService.findCashDrawByMoneyDrawId(moneyDrawId);
		if(cashDraw==null){
			cashDraw = new CashDraw();//保证页面上不会出现空指针
		}
		logs = petComLogService.queryByObjectId("CASH_MONEY_DRAW", moneyDrawId);
	}
		

	public void setMoneyDrawId(Long moneyDrawId) {
		this.moneyDrawId = moneyDrawId;
	}


	public CashMoneyDraw getCashMoneyDraw() {
		return cashMoneyDraw;
	}


	public UserUser getUser() {
		return user;
	}


	public List<ComLog> getLogs() {
		return logs;
	}


	public CashDraw getCashDraw() {
		return cashDraw;
	}

}
