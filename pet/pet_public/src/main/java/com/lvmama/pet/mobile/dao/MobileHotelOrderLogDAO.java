package com.lvmama.pet.mobile.dao;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.mobile.MobileHotelOrder;
import com.lvmama.comm.pet.po.mobile.MobileHotelOrderLog;

public class MobileHotelOrderLogDAO extends BaseIbatisDAO {
	
    public MobileHotelOrderLogDAO() {
        super();
    }

    public int deleteByPrimaryKey(Long logId) {
        MobileHotelOrderLog key = new MobileHotelOrderLog();
        key.setLogId(logId);
        int rows = super.delete("MOBILE_HOTEL_ORDER_LOG.deleteByPrimaryKey", key);
        return rows;
    }

    public MobileHotelOrderLog insert(MobileHotelOrderLog record) {
        super.insert("MOBILE_HOTEL_ORDER_LOG.insert", record);
        return record;
    }

    public void insertSelective(MobileHotelOrderLog record) {
        super.insert("MOBILE_HOTEL_ORDER_LOG.insertSelective", record);
    }

    public MobileHotelOrderLog selectByPrimaryKey(Long logId) {
        MobileHotelOrderLog key = new MobileHotelOrderLog();
        key.setLogId(logId);
        MobileHotelOrderLog record = (MobileHotelOrderLog) super.queryForObject("MOBILE_HOTEL_ORDER_LOG.selectByPrimaryKey", key);
        return record;
    }

    public int updateByPrimaryKeySelective(MobileHotelOrderLog record) {
        int rows = super.update("MOBILE_HOTEL_ORDER_LOG.updateByPrimaryKeySelective", record);
        return rows;
    }

    public int updateByPrimaryKey(MobileHotelOrderLog record) {
        int rows = super.update("MOBILE_HOTEL_ORDER_LOG.updateByPrimaryKey", record);
        return rows;
    }
    
    
    /**
     * 查询列表 
     * @param params
     * @return
     */
    public List<MobileHotelOrderLog> getMobileHotelOrderLogListByPrarms(Map<String,Object> params){
    	return (List<MobileHotelOrderLog>)super.queryForList("MOBILE_HOTEL_ORDER_LOG.queryMobileHotelOrderLogList", params);
    	
    }
    
    /**
     * 查询总记录数. 
     * @param param
     * @return
     */
    public Long countMobileHotelOrderLog(Map<String,Object> param){
    	return (Long) super.queryForObject("MOBILE_HOTEL_ORDER_LOG.countMobileHotelOrderLogList", param);
    }
}