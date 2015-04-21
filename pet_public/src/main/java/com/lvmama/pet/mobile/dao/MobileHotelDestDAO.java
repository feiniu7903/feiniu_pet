package com.lvmama.pet.mobile.dao;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.mobile.MobileHotelDest;
public class MobileHotelDestDAO extends BaseIbatisDAO {

    public MobileHotelDestDAO() {
        super();
    }

    public int deleteByPrimaryKey(String cityCode) {
        MobileHotelDest key = new MobileHotelDest();
        key.setCityCode(cityCode);
        int rows = super.delete("MOBILE_HOTEL_DEST.deleteByPrimaryKey", key);
        return rows;
    }

    public MobileHotelDest insert(MobileHotelDest record) {
        super.insert("MOBILE_HOTEL_DEST.insert", record);
        return record;
    }

    public void insertSelective(MobileHotelDest record) {
        super.insert("MOBILE_HOTEL_DEST.insertSelective", record);
    }

    public MobileHotelDest selectByPrimaryKey(String cityCode) {
        MobileHotelDest key = new MobileHotelDest();
        key.setCityCode(cityCode);
        MobileHotelDest record = (MobileHotelDest) super.queryForObject("MOBILE_HOTEL_DEST.selectByPrimaryKey", key);
        return record;
    }

    public int updateByPrimaryKeySelective(MobileHotelDest record) {
        int rows = super.update("MOBILE_HOTEL_DEST.updateByPrimaryKeySelective", record);
        return rows;
    }

    public int updateByPrimaryKey(MobileHotelDest record) {
        int rows = super.update("MOBILE_HOTEL_DEST.updateByPrimaryKey", record);
        return rows;
    }
    
    /**
     * 查询列表 
     * @param params
     * @return
     */
    public List<MobileHotelDest> getMobileHotelDestListByPrarms(Map<String,Object> params){
    	return (List<MobileHotelDest>)super.queryForListForReport("MOBILE_HOTEL_DEST.queryMobileHotelDestList", params);
    	
    }
    
    /**
     * 查询总记录数. 
     * @param param
     * @return
     */
    public Long countMobileHotelDest(Map<String,Object> param){
    	return (Long) super.queryForObject("MOBILE_HOTEL_DEST.countMobileHotelDestList", param);
    }
}