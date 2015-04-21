package com.lvmama.job;

import com.lvmama.comm.vo.Constant;
import com.lvmama.shanghu.service.ShanghuProductService;
public class ShanghuTimePricesJob {
private ShanghuProductService shanghuProductService;
	public void run() {
		if (Constant.getInstance().isJobRunnable()) {
			shanghuProductService.makeTimePrice();
		}
	}
	public void setShanghuProductService(ShanghuProductService shanghuProductService) {
		this.shanghuProductService = shanghuProductService;
	}
	

}
