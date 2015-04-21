package com.lvmama.pet.web.money;

import java.util.HashMap;

import com.lvmama.comm.pet.po.money.CashAccount;
import com.lvmama.comm.pet.po.money.CashMoneyDraw;
import com.lvmama.comm.pet.po.user.UserUser;
import com.lvmama.comm.pet.service.money.CashAccountService;
import com.lvmama.comm.pet.service.user.UserUserProxy;
import com.lvmama.pet.web.BaseAction;
/**
 *  打款任务查看 Action
 * @author songlianjun
 *
 */
public class FincMoneyDrawTaskViewAction extends BaseAction{

	private static final long serialVersionUID = 1L;
	
	private Long moneyDrawId;
	private HashMap<String,Object> fincMoneyDraw;
	private CashAccountService cashAccountService;
	private UserUserProxy userUserProxy;
	private CashMoneyDraw cashMoneyDraw;
	private UserUser user;
	@SuppressWarnings("unchecked")
	@Override
	protected void doBefore() throws Exception {
		cashMoneyDraw = cashAccountService.getFincMoneyDrawByPK(moneyDrawId);
	    if(cashMoneyDraw != null){
		   CashAccount cashAccount = cashAccountService.queryCashAccountByPk(cashMoneyDraw.getCashAccountId());
		   user = userUserProxy.getUserUserByPk(cashAccount.getUserId());
		}
	}
	
	public HashMap<String,Object> getFincMoneyDraw() {
		return fincMoneyDraw;
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

}
