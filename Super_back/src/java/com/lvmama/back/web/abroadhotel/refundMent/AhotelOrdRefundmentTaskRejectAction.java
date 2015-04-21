package com.lvmama.back.web.abroadhotel.refundMent;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Textbox;

import com.lvmama.back.web.BaseAction;
import com.lvmama.comm.abroad.service.AbroadhotelOrderService;
/**
 * 退款单拒绝Action
 */
public class AhotelOrdRefundmentTaskRejectAction extends BaseAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7797227317503217753L;
	Textbox reasonText;
	Long refundmentId;
	/**
	 * 海外酒店订单的service.
	 */
	private AbroadhotelOrderService abroadhotelOrderService;
	
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
		boolean succFlag = abroadhotelOrderService.rejectAbroadHotelCashRefundment(refundmentId, memo, this.getSessionUserName());
		if (succFlag){
			alert("拒绝成功");
			this.refreshParent("search");
			this.closeWindow();
		}else{
			alert("拒绝失败");
		}
		
	}
}
