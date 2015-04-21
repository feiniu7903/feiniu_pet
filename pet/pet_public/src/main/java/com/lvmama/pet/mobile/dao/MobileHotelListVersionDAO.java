package com.lvmama.pet.mobile.dao;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.mobile.MobileHotelListVersion;

public class MobileHotelListVersionDAO extends BaseIbatisDAO {

    public MobileHotelListVersionDAO() {
        super();
    }

    public int deleteByPrimaryKey(String hotelId) {
        MobileHotelListVersion key = new MobileHotelListVersion();
        key.setHotelId(hotelId);
        int rows = super.delete("MOBILE_HOTEL_LIST_VERSION.deleteByPrimaryKey", key);
        return rows;
    }

    public MobileHotelListVersion insert(MobileHotelListVersion record) {
        super.insert("MOBILE_HOTEL_LIST_VERSION.insert", record);
        return record;
    }

    public void insertSelective(MobileHotelListVersion record) {
        super.insert("MOBILE_HOTEL_LIST_VERSION.insertSelective", record);
    }

    public MobileHotelListVersion selectByPrimaryKey(String hotelId) {
        MobileHotelListVersion key = new MobileHotelListVersion();
        key.setHotelId(hotelId);
        MobileHotelListVersion record = (MobileHotelListVersion) super.queryForObject("MOBILE_HOTEL_LIST_VERSION.selectByPrimaryKey", key);
        return record;
    }

    public int updateByPrimaryKeySelective(MobileHotelListVersion record) {
        int rows = super.update("MOBILE_HOTEL_LIST_VERSION.updateByPrimaryKeySelective", record);
        return rows;
    }

    public int updateByPrimaryKey(MobileHotelListVersion record) {
        int rows = super.update("MOBILE_HOTEL_LIST_VERSION.updateByPrimaryKey", record);
        return rows;
    }
    
    /**
     * 查询列表 
     * @param params
     * @return
     */
    public List<MobileHotelListVersion> getMobileHotelListVersionListByPrarms(Map<String,Object> params){
    	return (List<MobileHotelListVersion>)super.queryForList("MOBILE_HOTEL_LIST_VERSION.queryMobileHotelListVersionList", params);
    	
    }
    
    /**
     * 查询总记录数. 
     * @param param
     * @return
     */
    public Long countMobileHotelListVersion(Map<String,Object> param){
    	return (Long) super.queryForObject("MOBILE_HOTEL_LIST_VERSION.countMobileHotelListVersionList", param);
    }
}