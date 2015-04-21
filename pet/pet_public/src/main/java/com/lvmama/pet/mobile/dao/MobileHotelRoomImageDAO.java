package com.lvmama.pet.mobile.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.mobile.MobileHotelRoomImage;

public class MobileHotelRoomImageDAO extends BaseIbatisDAO {

    public MobileHotelRoomImageDAO() {
        super();
    }

    public int deleteByPrimaryKey(Long mobileHotelRoomImageId) {
        MobileHotelRoomImage key = new MobileHotelRoomImage();
        key.setMobileHotelRoomImageId(mobileHotelRoomImageId);
        int rows = super.delete("MOBILE_HOTEL_ROOM_IMAGE.deleteByPrimaryKey", key);
        return rows;
    }

    public int deleteMobileHotelRoomImageByHotelIdAndRoomId(String roomId,String hotelId) {
        MobileHotelRoomImage key = new MobileHotelRoomImage();
        key.setRoomId(roomId);
        key.setHotelId(hotelId);
        int rows = super.delete("MOBILE_HOTEL_ROOM_IMAGE.deleteMobileHotelRoomImageByHotelIdAndRoomId", key);
        return rows;
    }
    public MobileHotelRoomImage insert(MobileHotelRoomImage record) {
        super.insert("MOBILE_HOTEL_ROOM_IMAGE.insert", record);
        return record;
    }

    public void insertSelective(MobileHotelRoomImage record) {
        super.insert("MOBILE_HOTEL_ROOM_IMAGE.insertSelective", record);
    }

    public MobileHotelRoomImage selectByPrimaryKey(Long mobileHotelRoomImageId) {
        MobileHotelRoomImage key = new MobileHotelRoomImage();
        key.setMobileHotelRoomImageId(mobileHotelRoomImageId);
        MobileHotelRoomImage record = (MobileHotelRoomImage) super.queryForObject("MOBILE_HOTEL_ROOM_IMAGE.selectByPrimaryKey", key);
        return record;
    }

    public int updateByPrimaryKeySelective(MobileHotelRoomImage record) {
        int rows = super.update("MOBILE_HOTEL_ROOM_IMAGE.updateByPrimaryKeySelective", record);
        return rows;
    }

    public int updateByPrimaryKey(MobileHotelRoomImage record) {
        int rows = super.update("MOBILE_HOTEL_ROOM_IMAGE.updateByPrimaryKey", record);
        return rows;
    }
    
    /**
     * 查询列表 
     * @param params
     * @return
     */
    public List<MobileHotelRoomImage> getMobileHotelRoomImageListByPrarms(Map<String,Object> params){
    	return (List<MobileHotelRoomImage>)super.queryForList("MOBILE_HOTEL_ROOM_IMAGE.queryMobileHotelRoomImageList", params);
    	
    }
    
    /**
     * 查询总记录数. 
     * @param param
     * @return
     */
    public Long countMobileHotelRoomImage(Map<String,Object> param){
    	return (Long) super.queryForObject("MOBILE_HOTEL_ROOM_IMAGE.countMobileHotelRoomImageList", param);
    }


	public List<MobileHotelRoomImage> queryMobileHotelRoomImageListByHotelIdAndRoomId(
			String roomId, String hotelId) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("hotelId", hotelId);
		params.put("roomId", roomId);
		return (List<MobileHotelRoomImage>)super.queryForList("MOBILE_HOTEL_ROOM_IMAGE.queryMobileHotelRoomImageListByHotelIdAndRoomId", params);
	}
}