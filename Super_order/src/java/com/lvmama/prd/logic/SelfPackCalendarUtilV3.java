package com.lvmama.prd.logic;

import java.util.Date;
import java.util.List;

import com.lvmama.comm.bee.po.prod.TimePrice;

public class SelfPackCalendarUtilV3 extends CalendarUtilV2{

	
	private Date startDate;
	private Date endDate;
	
	public SelfPackCalendarUtilV3(Date startDate,Date endDate){
		super();
		this.startDate = startDate;
		this.endDate = endDate;
		MAX_DAYS=43;
	}
	
	@Override
	protected List<TimePrice> getTimePriceList(Long productId, Long prodBranchId) {
		return productTimePriceLogic.getTimePriceListByTime(productId, prodBranchId,
				MAX_DAYS, startDate,endDate);
	}
}
