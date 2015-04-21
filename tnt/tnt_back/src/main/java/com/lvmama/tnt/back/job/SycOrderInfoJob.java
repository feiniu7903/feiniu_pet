package com.lvmama.tnt.back.job;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.lvmama.comm.vo.Constant;
import com.lvmama.tnt.comm.vo.Page;
import com.lvmama.tnt.order.po.TntOrder;
import com.lvmama.tnt.order.service.TntOrderService;

public class SycOrderInfoJob {
	//private final Log log=LogFactory.getLog(SycOrderInfoJob.class);
	@Autowired
	public TntOrderService tntOrderService;
	
	
	public void run(){
		if (Constant.getInstance().isJobRunnable()){
			TntOrder t = new TntOrder();
			Page<TntOrder> p = Page.page(1, t);
			t.setOrderStatus(Constant.ORDER_STATUS.NORMAL.getCode());
			t.setApproveStatus(Constant.ORDER_APPROVE_STATUS.UNVERIFIED.getCode());
			p.setPageSize(999L);
			p = p.desc("TNT_ORDER_ID");
			List<TntOrder> list = tntOrderService.findPage(p);
			for(TntOrder order :list){
				tntOrderService.synOrder(order.getOrderId());
			}
		}
	}
}
