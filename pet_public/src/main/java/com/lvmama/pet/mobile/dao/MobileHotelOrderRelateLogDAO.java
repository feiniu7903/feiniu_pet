package com.lvmama.pet.mobile.dao;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.mobile.MobileHotelOrderRelateLog;

public class MobileHotelOrderRelateLogDAO extends  BaseIbatisDAO {

    public MobileHotelOrderRelateLogDAO() {
        super();
    }

    public int deleteByPrimaryKey(Long mobileHotelOrderRelateLog) {
        MobileHotelOrderRelateLog key = new MobileHotelOrderRelateLog();
        key.setMobileHotelOrderRelateId(mobileHotelOrderRelateLog);
        int rows = super.delete("MOBILE_HOTEL_ORDER_RELATE_LOG.deleteByPrimaryKey", key);
        return rows;
    }

    public MobileHotelOrderRelateLog insert(MobileHotelOrderRelateLog record) {
        super.insert("MOBILE_HOTEL_ORDER_RELATE_LOG.insert", record);
        return record;
    }

    public void insertSelective(MobileHotelOrderRelateLog record) {
        super.insert("MOBILE_HOTEL_ORDER_RELATE_LOG.insertSelective", record);
    }

    public MobileHotelOrderRelateLog selectByPrimaryKey(Long mobileHotelOrderRelateLog) {
        MobileHotelOrderRelateLog key = new MobileHotelOrderRelateLog();
        key.setMobileHotelOrderRelateId(mobileHotelOrderRelateLog);
        MobileHotelOrderRelateLog record = (MobileHotelOrderRelateLog) super.queryForObject("MOBILE_HOTEL_ORDER_RELATE_LOG.selectByPrimaryKey", key);
        return record;
    }

    public int updateByPrimaryKeySelective(MobileHotelOrderRelateLog record) {
        int rows = super.update("MOBILE_HOTEL_ORDER_RELATE_LOG.updateByPrimaryKeySelective", record);
        return rows;
    }

    public int updateByPrimaryKey(MobileHotelOrderRelateLog record) {
        int rows = super.update("MOBILE_HOTEL_ORDER_RELATE_LOG.updateByPrimaryKey", record);
        return rows;
    }
    
    /**
     * 查询列表 
     * @param params
     * @return
     */
    public List<MobileHotelOrderRelateLog> getMobileHotelOrderRelateLogListByPrarms(Map<String,Object> params){
    	return (List<MobileHotelOrderRelateLog>)super.queryForList("MOBILE_HOTEL_ORDER_RELATE_LOG.queryMobileHotelOrderRelateLogList", params);
    	
    }
    
    /**
     * 查询总记录数. 
     * @param param
     * @return
     */
    public Long countMobileHotelOrderRelateLog(Map<String,Object> param){
    	return (Long) super.queryForObject("MOBILE_HOTEL_ORDER_RELATE_LOG.countMobileHotelOrderRelateLogList", param);
    }
}