package com.lvmama.train.web;

import org.apache.struts2.convention.annotation.Action;

import com.lvmama.comm.vo.train.Rsp;
import com.lvmama.comm.vo.train.notify.ProductUpdateNotifyVo;
import com.lvmama.train.service.NotifyMessageService;

public class ProductUpdateAction extends TrainNotifyAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 123491234123L;
	private NotifyMessageService notifyMessageService;
	private static final int CODE = 934;

	@Action(value="/v1/notice/interface_update_notify")
	public String issue(){
		Rsp rsp = checkSign();
		if(rsp!=null){
			return output(rsp);
		}
		ProductUpdateNotifyVo vo = new ProductUpdateNotifyVo();
		Object merchantId = this.getRequest().getParameter("merchant_id");
		Object sign = this.getRequest().getParameter("sign");
		Object interfaceId = this.getRequest().getParameter("interface_id");
		Object requestDate = this.getRequest().getParameter("request_date");
		Object requestType = this.getRequest().getParameter("request_type");
		Object operInfo = this.getRequest().getParameter("oper_info");
		if(merchantId != null) vo.setMerchantId((String)merchantId);
		if(sign != null) vo.setSign((String)sign);
		if(interfaceId != null) vo.setInterfaceId((String)interfaceId);
		if(requestDate != null) vo.setRequestDate((String)requestDate);
		if(requestType != null) vo.setRequestType((String)requestType);
		if(operInfo != null) vo.setOperInfo((String)operInfo);
		log.info(vo);
		rsp = notifyMessageService.request(vo, CODE);
		return output(rsp);
//		try {
//			if(rsp.getStatus().getRet_code() == Constant.HTTP_SUCCESS)
//				this.getResponse().setStatus(rsp.getStatus().getRet_code());
//			else
//				this.getResponse().sendError(rsp.getStatus().getRet_code(), rsp.getStatus().getRet_msg());
//			this.getResponse().getWriter().write(rsp.getRspJson());
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
	
	public NotifyMessageService getNotifyMessageService() {
		return notifyMessageService;
	}
	public void setNotifyMessageService(NotifyMessageService notifyMessageService) {
		this.notifyMessageService = notifyMessageService;
	}
}
