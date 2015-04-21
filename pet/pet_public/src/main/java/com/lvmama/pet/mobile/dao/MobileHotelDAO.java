package com.lvmama.pet.mobile.dao;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.mobile.MobileHotel;

public class MobileHotelDAO extends BaseIbatisDAO {

    public MobileHotelDAO() {
    	 super();
    }

    public int deleteByPrimaryKey(String hotelId) {
        MobileHotel key = new MobileHotel();
        key.setHotelId(hotelId);
        int rows = super.delete("MOBILE_HOTEL.deleteByPrimaryKey", key);
        return rows;
    }

    public MobileHotel insert(MobileHotel record) {
        super.insert("MOBILE_HOTEL.insert", record);
        return record;
    }

    public void insertSelective(MobileHotel record) {
        super.insert("MOBILE_HOTEL.insertSelective", record);
    }

    public MobileHotel selectByPrimaryKey(String hotelId) {
        MobileHotel key = new MobileHotel();
        key.setHotelId(hotelId);
        MobileHotel record = (MobileHotel) super.queryForObject("MOBILE_HOTEL.selectByPrimaryKey", key);
        return record;
    }

    public int updateByPrimaryKeySelective(MobileHotel record) {
        int rows = super.update("MOBILE_HOTEL.updateByPrimaryKeySelective", record);
        return rows;
    }

    public int updateByPrimaryKey(MobileHotel record) {
        int rows = super.update("MOBILE_HOTEL.updateByPrimaryKey", record);
        return rows;
    }
    
    /**
     * 查询列表 
     * @param params
     * @return
     */
    public List<MobileHotel> getMobileHotelListByPrarms(Map<String,Object> params){
    	return (List<MobileHotel>)super.queryForList("MOBILE_HOTEL.queryMobileHotelList", params);
    	
    }
    
    public List<MobileHotel> queryMobileHotelListByOrderId(
			Map<String, Object> params) {
		// TODO Auto-generated method stub
    	return (List<MobileHotel>)super.queryForList("MOBILE_HOTEL.queryMobileHotelListByOrderId", params);
	}
    
    /**
     * 查询总记录数. 
     * @param param
     * @return
     */
    public Long countMobileHotel(Map<String,Object> param){
    	return (Long) super.queryForObject("MOBILE_HOTEL.countMobileHotelList", param);
    }

	public int deleteMobileHotelVerionNotInHotelMobile() {
		MobileHotel key = new MobileHotel();
		return super.delete("MOBILE_HOTEL.deleteMobileHotelVerionNotInHotelMobile",key);
	}

	
    
}