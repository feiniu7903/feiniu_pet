package com.lvmama.prd.service;

import java.util.Date;
import java.util.List;

import org.springframework.util.Assert;

import com.lvmama.comm.bee.po.prod.LimitSaleTime;
import com.lvmama.comm.bee.service.LimitSaleTimeService;
import com.lvmama.prd.dao.LimitSaleTimeDAO;
import com.lvmama.prd.logic.ProductTimePriceLogic;

public class LimitSaleTimeServiceImpl implements LimitSaleTimeService{
	private LimitSaleTimeDAO limitSaleTimeDAO;
	private ProductTimePriceLogic productTimePriceLogic;
	public void setProductTimePriceLogic(ProductTimePriceLogic productTimePriceLogic) {
		this.productTimePriceLogic = productTimePriceLogic;
	}

	public void saveLimitSaleTime(LimitSaleTime limitSaleTime) {
		limitSaleTimeDAO.insert(limitSaleTime);
	}
	
	public List<LimitSaleTime> queryLimitSaleTimeByproductId(Long productId) {
		
		return limitSaleTimeDAO.queryLimitSaleTimeByproductId(productId);
	}
	
	public void deleteByLimitSaleTimeId(Long limitSaleTimeId) {
		Assert.notNull(limitSaleTimeId,"对象不存在");
		Assert.notNull(limitSaleTimeDAO.selectByPrimaryKey(limitSaleTimeId),"对象不存在");
		limitSaleTimeDAO.deleteByPrimaryKey(limitSaleTimeId);
		
	}

	public LimitSaleTimeDAO getLimitSaleTimeDAO() {
		return limitSaleTimeDAO;
	}
	public void setLimitSaleTimeDAO(LimitSaleTimeDAO limitSaleTimeDAO) {
		this.limitSaleTimeDAO = limitSaleTimeDAO;
	}

	public LimitSaleTime getLimitSaleTime(Long id, Date choseDate) {
		return productTimePriceLogic.getLimitSaleTime(id, choseDate);
	}

	@Override
	public List<LimitSaleTime> queryByProductIdAndLimitTime(
			LimitSaleTime limitSaleTime) {
		return limitSaleTimeDAO.queryByProductIdAndLimitTime(limitSaleTime);
	}


}
