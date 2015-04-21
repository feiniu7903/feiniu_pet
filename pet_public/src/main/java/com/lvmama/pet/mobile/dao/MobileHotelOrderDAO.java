package com.lvmama.pet.mobile.dao;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.mobile.MobileHotelOrder;
import com.lvmama.comm.pet.po.mobile.MobileHotelOrder;

public class MobileHotelOrderDAO extends BaseIbatisDAO {

	public MobileHotelOrderDAO() {
		super();
	}

	public int deleteByPrimaryKey(Long lvHotelOrderId) {
		MobileHotelOrder key = new MobileHotelOrder();
		key.setLvHotelOrderId(lvHotelOrderId);
		int rows = super.delete(
				"MOBILE_HOTEL_ORDER.deleteByPrimaryKey", key);
		return rows;
	}

	public void insert(MobileHotelOrder record) {
		super.insert("MOBILE_HOTEL_ORDER.insert", record);
	}
	
	public long insertMobileHotelOrder(MobileHotelOrder record) {
		super.insert("MOBILE_HOTEL_ORDER.insert", record);
		return record.getLvHotelOrderId();
	}

	public void insertSelective(MobileHotelOrder record) {
		super.insert("MOBILE_HOTEL_ORDER.insertSelective",
				record);
	}

	public MobileHotelOrder selectByPrimaryKey(Long lvHotelOrderId) {
		MobileHotelOrder key = new MobileHotelOrder();
		key.setLvHotelOrderId(lvHotelOrderId);
		MobileHotelOrder record = (MobileHotelOrder) super
				.queryForObject("MOBILE_HOTEL_ORDER.selectByPrimaryKey", key);
		return record;
	}
	
	public Long selectByUserId(String userId) {
		MobileHotelOrder key = new MobileHotelOrder();
		key.setUserId(userId);
		Long lvHotelOrderId = (Long) super
				.queryForObject("MOBILE_HOTEL_ORDER.selectByUserId", key);
		return lvHotelOrderId;
	}

	public int updateByPrimaryKeySelective(MobileHotelOrder record) {
		int rows = super.update(
				"MOBILE_HOTEL_ORDER.updateByPrimaryKeySelective", record);
		return rows;
	}

	public int updateByPrimaryKey(MobileHotelOrder record) {
		int rows = super.update(
				"MOBILE_HOTEL_ORDER.updateByPrimaryKey", record);
		return rows;
	}
	
	public int updateByPrimaryKey4Job(MobileHotelOrder record) {
		int rows = super.update(
				"MOBILE_HOTEL_ORDER.updateByPrimaryKey4Job", record);
		return rows;
	}
	
	/**
     * 查询列表 
     * @param params
     * @return
     */
    @SuppressWarnings("unchecked")
	public List<MobileHotelOrder> getMobileHotelOrderListByPrarms(Map<String,Object> params){
    	return (List<MobileHotelOrder>)super.queryForList("MOBILE_HOTEL_ORDER.queryMobileHotelOrderList", params);
    	
    }
    
    /**
     * 查询总记录数. 
     * @param param
     * @return
     */
    public Long countMobileHotelOrder(Map<String,Object> param){
    	return (Long) super.queryForObject("MOBILE_HOTEL_ORDER.countMobileHotelOrderList", param);
    }
	
}