package com.lvmama.pet.web.money;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Textbox;

import com.lvmama.comm.pet.service.money.CashAccountService;
import com.lvmama.pet.web.BaseAction;

public class PayFailTaskAction extends BaseAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	Textbox memo;
	Long moneyDrawId;
	private CashAccountService cashAccountService;
	
	@Override
	protected void doBefore() throws Exception {
		moneyDrawId = (Long)Executions.getCurrent().getArg().get("moneyDrawId");
	}
	
	
	//确认
	public void doSave(){
		if (memo.getValue()==null||memo.getValue().equals("")){
			alert("备注不能为空");
			return;
		}
		if (cashAccountService.setDoneToFincMoneyDrawPayStatus(moneyDrawId ,memo.getValue(), this.getSessionUserName())){
			this.refreshParent("search");
			alert("处理成功");
			this.closeWindow();
		}else{
			alert("处理失败");
		}
		
		
	}


	public void setMoneyDrawId(Long moneyDrawId) {
		this.moneyDrawId = moneyDrawId;
	}

}
