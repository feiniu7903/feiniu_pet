package com.lvmama.pet.web.money;

import java.util.HashMap;

import org.zkoss.zk.ui.Executions;

import com.lvmama.comm.pet.po.money.CashMoneyDraw;
import com.lvmama.comm.pet.service.money.CashAccountService;
import com.lvmama.comm.vo.Constant;
import com.lvmama.pet.web.BaseAction;

public class ApproveDetailAction extends BaseAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private CashAccountService cashAccountService;
	
	private HashMap<String,Object> drawInfo;
	
	@SuppressWarnings("unchecked")
	@Override
	protected void doBefore() throws Exception {
		drawInfo = (HashMap<String,Object>)Executions.getCurrent().getArg().get("item");
		CashMoneyDraw cashMoneyDraw=(CashMoneyDraw) drawInfo.get("fincMoneyDraw");
		//提现银行卡账户名申请提现次数
		Long moneyDrowCounts=cashAccountService.queryMoneyDrowCountByBankAccount(cashMoneyDraw.getBankAccountName());
		drawInfo.put("moneyDrowCounts", moneyDrowCounts);
	}
	public HashMap<String, Object> getDrawInfo() {
		return drawInfo;
	}
	
	//确认
	public void doConfirm(){
		if (cashAccountService.updateCashMoneyDrawAuditStatus(((CashMoneyDraw)drawInfo.get("fincMoneyDraw")).getMoneyDrawId(), Constant.FINC_CASH_STATUS.VERIFIED.name(),"", this.getSessionUserName())){
			alert("确认成功");
			this.refreshParent("search");
			this.closeWindow();
		}else{
			alert("确认失败");
		}
		
	}
	/**
	 * 待用户确认
	 * @author ZHANG Nan
	 */
	public void waitUserConfirm(){
		if (cashAccountService.updateCashMoneyDrawAuditStatus(((CashMoneyDraw)drawInfo.get("fincMoneyDraw")).getMoneyDrawId(), Constant.FINC_CASH_STATUS.WaitUserConfirm.name(),"", this.getSessionUserName())){
			alert("待用户确认成功");
			this.refreshParent("search");
			this.closeWindow();
		}else{
			alert("待用户确认失败");
		}
		
	}
}
