package com.lvmama.comm.bee.service;

import java.util.Date;
import java.util.List;

import com.lvmama.comm.bee.po.prod.LimitSaleTime;

public interface LimitSaleTimeService {
	
	void saveLimitSaleTime(LimitSaleTime limitSaleTime);
	
	List<LimitSaleTime> queryLimitSaleTimeByproductId(Long productId);
	
	List<LimitSaleTime> queryByProductIdAndLimitTime(LimitSaleTime limitSaleTime);
	
	void deleteByLimitSaleTimeId(Long limitSaleTimeId);
	
	LimitSaleTime getLimitSaleTime(Long id, Date choseDate);
}
