package com.lvmama.pet.web.money;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Textbox;

import com.lvmama.comm.pet.service.money.CashAccountService;
import com.lvmama.comm.vo.Constant;
import com.lvmama.pet.web.BaseAction;

public class ApproveDetailRejectAction extends BaseAction{




	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	Textbox reasonText;
	Long moneyDrawId;
	private CashAccountService cashAccountService;
	
	@Override
	protected void doBefore() throws Exception {
		moneyDrawId = (Long)Executions.getCurrent().getArg().get("moneyDrawId");
	}
	
	
	//确认
	public void doReject(){
		String rejectReason=reasonText.getValue();
		if (rejectReason==null||rejectReason.equals("")){
			alert("拒绝原因不能为空");
			return;
		}
		if (cashAccountService.updateCashMoneyDrawAuditStatus(moneyDrawId, Constant.FINC_CASH_STATUS.REJECTED.name(),rejectReason, this.getSessionUserName())){
			alert("拒绝成功");
			((BaseAction)this.getParent().getAttribute("saction")).refreshParent("search");
			this.getParent().detach();
			this.closeWindow();
		}else{
			alert("拒绝失败");
		}
		
	}
}
