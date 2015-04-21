package com.lvmama.back.web.ord.refundMent;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Textbox;

import com.lvmama.back.web.BaseAction;
import com.lvmama.comm.pet.service.sale.OrderRefundService;
/**
 * 退款单拒绝Action
 * @author songlianjun
 *
 */
public class RefundmentTaskRejectAction extends BaseAction{




	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	Textbox reasonText;
	Long refundmentId;
	private OrderRefundService orderRefundService;
	
	@Override
	protected void doBefore() throws Exception {
		refundmentId = (Long)Executions.getCurrent().getArg().get("refundmentId");
	}
	
	
	//确认
	public void doReject(){
		String memo=reasonText.getValue();
		if (memo==null||memo.equals("")){
			alert("拒绝原因不能为空");
			return;
		}
		boolean succFlag = orderRefundService.rejectCashRefundment(refundmentId, memo, this.getSessionUserName());
		if (succFlag){
			alert("拒绝成功");
			this.refreshParent("search");
			this.closeWindow();
		}else{
			alert("拒绝失败");
		}
		
	}
}
