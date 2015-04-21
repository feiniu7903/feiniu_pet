package com.lvmama.pet.mobile.dao;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.mobile.MobileHotelLandmark;

public class MobileHotelLandmarkDAO extends BaseIbatisDAO {

    public MobileHotelLandmarkDAO() {
        super();
    }

    public int deleteByPrimaryKey(Long mobileHotelLandmarkId) {
        MobileHotelLandmark key = new MobileHotelLandmark();
        key.setMobileHotelLandmarkId(mobileHotelLandmarkId);
        int rows = super.delete("MOBILE_HOTEL_LANDMARK.deleteByPrimaryKey", key);
        return rows;
    }

    public MobileHotelLandmark insert(MobileHotelLandmark record) {
        super.insert("MOBILE_HOTEL_LANDMARK.insert", record);
        return record;
    }

    public void insertSelective(MobileHotelLandmark record) {
        super.insert("MOBILE_HOTEL_LANDMARK.insertSelective", record);
    }

    public MobileHotelLandmark selectByPrimaryKey(Long mobileHotelLandmarkId) {
        MobileHotelLandmark key = new MobileHotelLandmark();
        key.setMobileHotelLandmarkId(mobileHotelLandmarkId);
        MobileHotelLandmark record = (MobileHotelLandmark) super.queryForObject("MOBILE_HOTEL_LANDMARK.selectByPrimaryKey", key);
        return record;
    }

    public int updateByPrimaryKeySelective(MobileHotelLandmark record) {
        int rows = super.update("MOBILE_HOTEL_LANDMARK.updateByPrimaryKeySelective", record);
        return rows;
    }

    public int updateByPrimaryKey(MobileHotelLandmark record) {
        int rows = super.update("MOBILE_HOTEL_LANDMARK.updateByPrimaryKey", record);
        return rows;
    }
    
    /**
     * 查询列表 
     * @param params
     * @return
     */
    public List<MobileHotelLandmark> getMobileHotelLandmarkListByPrarms(Map<String,Object> params){
    	return (List<MobileHotelLandmark>)super.queryForList("MOBILE_HOTEL_LANDMARK.queryMobileHotelLandmarkList", params);
    	
    }
    
    /**
     * 查询总记录数. 
     * @param param
     * @return
     */
    public Long countMobileHotelLandmark(Map<String,Object> param){
    	return (Long) super.queryForObject("MOBILE_HOTEL_LANDMARK.countMobileHotelLandmarkList", param);
    }

	public int deleteByPrimaryCityCode(String cityCode) {
		// TODO Auto-generated method stub
		MobileHotelLandmark key = new MobileHotelLandmark();
        key.setCityCode(cityCode);
        int rows = super.delete("MOBILE_HOTEL_LANDMARK.deleteByCityCode", key);
        return rows;
	}
}