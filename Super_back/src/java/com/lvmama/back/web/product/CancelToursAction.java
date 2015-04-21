package com.lvmama.back.web.product;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.back.utils.ZkMessage;
import com.lvmama.back.utils.ZkMsgCallBack;
import com.lvmama.back.web.BaseAction;
import com.lvmama.comm.bee.po.prod.ProceedTours;
import com.lvmama.comm.bee.service.ProceedToursService;
import com.lvmama.comm.vo.Constant;

public class CancelToursAction extends BaseAction {
	private static final long serialVersionUID = 3171959283443845667L;
	
	private Long productId;
	private Date visitDate;
	
	private ProceedTours proceedTours;
	private ProceedToursService proceedToursService;
	
	@Override
	public void doBefore() {
		Map<String,Object> parameters = new HashMap<String,Object>();
		parameters.put("productId", productId);
		parameters.put("visitDate", visitDate);
		List<ProceedTours> tours = proceedToursService.query(parameters);
		if (!tours.isEmpty()) {
			proceedTours = tours.get(0);
		}
	}
	
	public void cancel() {
		if (null != proceedTours) {
			ZkMessage.showQuestion("您确定需要取消此班次吗?", new ZkMsgCallBack() {
				public void execute() {
					proceedTours.setStatus(Constant.PROCEED_TOURS_STATUS.CANCEL.name());
					proceedTours.setOperatorName(getOperatorName());
					proceedToursService.updateStatus(proceedTours);
					refreshParent("refresh");
					closeWindow();
				}
			}, new ZkMsgCallBack() {
				public void execute() {
				}
			});
		} else {
			ZkMessage.showError("无法找到需要取消的班次!");
		}
	}
	

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public void setVisitDate(Date visitDate) {
		this.visitDate = visitDate;
	}

	public ProceedTours getProceedTours() {
		return proceedTours;
	}

	public void setProceedToursService(ProceedToursService proceedToursService) {
		this.proceedToursService = proceedToursService;
	}
	
	
}
