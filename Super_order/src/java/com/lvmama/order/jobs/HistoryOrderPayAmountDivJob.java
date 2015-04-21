package com.lvmama.order.jobs;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.order.service.OrderItemMetaSaleAmountServie;

/**
 * 历史订单拆分收入
 * @author zhaojindong
 *
 */
public class HistoryOrderPayAmountDivJob implements Runnable{
	private static final Log log = LogFactory.getLog(HistoryOrderPayAmountDivJob.class);
	
	private OrderItemMetaSaleAmountServie orderItemMetaSaleAmountServie;
	private String startDate;
	private String endDate;
	
	@SuppressWarnings("unchecked")
	public void run() {
		if (Constant.getInstance().isJobRunnable()) {
			log.info("将历史订单拆分收入,支付时间：" + startDate + "~" + endDate);
			Date start = StringUtil.isEmptyString(startDate)?null:DateUtil.toDate(startDate, "yyyy-MM-dd");
			Date end = StringUtil.isEmptyString(endDate)?null:DateUtil.toDate(endDate, "yyyy-MM-dd");
			List<Long> ids = orderItemMetaSaleAmountServie.getHistoryOrderId(start, end);
			if(ids != null && ids.size() > 0){
				for(Long id : ids){
					try{
						orderItemMetaSaleAmountServie.updateOrderItemMetaSaleAmount(id);
					}catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			log.info("历史订单拆分收入完成,支付时间：" + startDate + "~" + endDate);
		}
	}

	public OrderItemMetaSaleAmountServie getOrderItemMetaSaleAmountServie() {
		return orderItemMetaSaleAmountServie;
	}

	public void setOrderItemMetaSaleAmountServie(OrderItemMetaSaleAmountServie orderItemMetaSaleAmountServie) {
		this.orderItemMetaSaleAmountServie = orderItemMetaSaleAmountServie;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	
}
