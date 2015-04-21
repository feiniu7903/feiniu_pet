package com.lvmama.pet.mobile.dao;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.mobile.MobileHotelRoom;

public class MobileHotelRoomDAO extends BaseIbatisDAO {

    public MobileHotelRoomDAO() {
        super();
    }

    public int deleteByPrimaryKey(Long roomId) {
        MobileHotelRoom key = new MobileHotelRoom();
        key.setMobileRoomId(roomId);
        int rows = super.delete("MOBILE_HOTEL_ROOM.deleteByPrimaryKey", key);
        return rows;
    }

    public MobileHotelRoom insert(MobileHotelRoom record) {
        super.insert("MOBILE_HOTEL_ROOM.insert", record);
        return record;
    }

    public void insertSelective(MobileHotelRoom record) {
        super.insert("MOBILE_HOTEL_ROOM.insertSelective", record);
    }

    public MobileHotelRoom selectByPrimaryKey(Long roomId) {
        MobileHotelRoom key = new MobileHotelRoom();
        key.setMobileRoomId(roomId);
        MobileHotelRoom record = (MobileHotelRoom) super.queryForObject("MOBILE_HOTEL_ROOM.selectByPrimaryKey", key);
        return record;
    }

    public int updateByPrimaryKeySelective(MobileHotelRoom record) {
        int rows = super.update("MOBILE_HOTEL_ROOM.updateByPrimaryKeySelective", record);
        return rows;
    }

    public int updateByPrimaryKey(MobileHotelRoom record) {
        int rows = super.update("MOBILE_HOTEL_ROOM.updateByPrimaryKey", record);
        return rows;
    }
    
    /**
     * 查询列表 
     * @param params
     * @return
     */
    public List<MobileHotelRoom> getMobileHotelRoomListByPrarms(Map<String,Object> params){
    	return (List<MobileHotelRoom>)super.queryForList("MOBILE_HOTEL_ROOM.queryMobileHotelRoomList", params);
    	
    }
    
    /**
     * 查询总记录数. 
     * @param param
     * @return
     */
    public Long countMobileHotelRoom(Map<String,Object> param){
    	return (Long) super.queryForObject("MOBILE_HOTEL_ROOM.countMobileHotelRoomList", param);
    }
}