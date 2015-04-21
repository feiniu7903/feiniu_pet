package com.lvmama.ebk.service;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.bee.po.ebooking.EbkHousePrice;
import com.lvmama.comm.bee.service.ebooking.EbkHousePriceService;
import com.lvmama.ebk.dao.EbkHousePriceDAO;

public class EbkHousePriceServiceImpl implements EbkHousePriceService {

	private EbkHousePriceDAO ebkHousePriceDAO;
	
	@Override
	public Long insert(EbkHousePrice ebkHousePrice) {
		return this.ebkHousePriceDAO.insert(ebkHousePrice);
	}
	
	@Override
	 public int deleteEbkHousePriceByPrimaryKey(Long housePriceId) {
		 return this.ebkHousePriceDAO.deleteByPrimaryKey(housePriceId);
	 }
	 
	 @Override
	 public List<EbkHousePrice> findEbkHousePriceListByExample(Map<String, Object> example) {
		 return this.ebkHousePriceDAO.selectByExample(example);
	 }
	
	@Override
	public int countEbkHousePriceListByExample(
			Map<String, Object> example) {
		return this.ebkHousePriceDAO.countByExample(example);
	}

	public void setEbkHousePriceDAO(EbkHousePriceDAO ebkHousePriceDAO) {
		this.ebkHousePriceDAO = ebkHousePriceDAO;
	}
	
	public int updateByPrimaryKey(EbkHousePrice ehp) {
		return this.ebkHousePriceDAO.updateByPrimaryKey(ehp);
	}
	
	public EbkHousePrice selectByPrimaryKey(Long housePriceId) {
		return this.ebkHousePriceDAO.selectByPrimaryKey(housePriceId);
	}	

}
