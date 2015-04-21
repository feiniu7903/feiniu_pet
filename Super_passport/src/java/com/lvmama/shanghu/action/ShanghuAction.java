package com.lvmama.shanghu.action;

import org.apache.struts2.convention.annotation.Action;

import com.lvmama.comm.BaseAction;
import com.lvmama.shanghu.service.ShanghuProductService;

public class ShanghuAction extends BaseAction{
	private static final long serialVersionUID = 1L;
	private ShanghuProductService shanghuProductService;
	private String prodNo;
	@Action("/shanghu/update")
	public void update() throws Exception {
		shanghuProductService.makeTimePrice();
		sendAjaxMsg("同步完成");
	}
	
	@Action("/shanghu/updateOne")
	public void updateOne()throws Exception{
		shanghuProductService.makeTimePriceByProductNoandDate(prodNo);
		sendAjaxMsg("同步完成");
	}
	
	public void setShanghuProductService(ShanghuProductService shanghuProductService) {
		this.shanghuProductService = shanghuProductService;
	}

	public void setProdNo(String prodNo) {
		this.prodNo = prodNo;
	}
}
