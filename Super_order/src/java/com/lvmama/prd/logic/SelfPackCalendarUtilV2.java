package com.lvmama.prd.logic;

import java.util.Date;
import java.util.List;

import com.lvmama.comm.bee.po.prod.TimePrice;

public class SelfPackCalendarUtilV2 extends CalendarUtilV2{

	
	private Date startDate;

	public SelfPackCalendarUtilV2(Date startDate) {
		super();
		this.startDate = startDate;
		MAX_DAYS=43;
	}

	@Override
	protected List<TimePrice> getTimePriceList(Long productId, Long prodBranchId) {
		return productTimePriceLogic.getTimePriceList(productId, prodBranchId,
				MAX_DAYS, startDate);
	}

}
