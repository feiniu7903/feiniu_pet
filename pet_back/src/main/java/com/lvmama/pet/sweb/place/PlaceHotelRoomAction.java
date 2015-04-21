package com.lvmama.pet.sweb.place;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.BackBaseAction;
import com.lvmama.comm.pet.po.place.PlaceHotelRoom;
import com.lvmama.comm.pet.service.place.PlaceHotelRoomService;
import com.lvmama.comm.pet.service.place.PlaceService;
import com.lvmama.comm.pet.service.pub.ComLogService;
import com.lvmama.comm.vo.Constant;

@Results({
    @Result(name = "initPlaceHotelRoom", location = "/WEB-INF/pages/back/place/recommend/placeHotelRoom_add.jsp", type=  "dispatcher")
})
public class PlaceHotelRoomAction extends BackBaseAction {
	
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private PlaceHotelRoomService placeHotelRoomService;
    private ComLogService comLogService;
	
	private PlaceHotelRoom placeHotelRoom;
    private List<PlaceHotelRoom> placeHotelRoomList = new ArrayList<PlaceHotelRoom>();
    
    private String hasSensitiveWord;
    private PlaceService placeService;

    @Action("/place/placeHotelRoom")
	public String queryAllPlaceHotelRoom(){
        placeHotelRoomList = placeHotelRoomService.queryAllPlaceHotelRoom(placeHotelRoom);
		return "placeHotelNoticeList";
	}
	
	@Action("/place/initPlaceHotelRooms")
	public String initPlaceHotelRoom(){
	    if(placeHotelRoom.getRoomId() != null){
	        this.placeHotelRoom = placeHotelRoomService.queryPlaceHotelRoomByRoomId(placeHotelRoom);
	    }
	    return "initPlaceHotelRoom";
	}
	
	@Action("/place/addOrUpdatePlaceHotelRoom")
	public String addOrUpdatePlaceHotelRoom(){
	      this.validatePlaceHotelRoom();
	      //通过参数operateType 
	      try {
	             placeHotelRoomService.addOrUpdatePlaceHotelRoom(placeHotelRoom);
	             comLogService.insert("SCENIC_LOG_PLACE",null,
	                      placeHotelRoom.getPlaceId(),super.getSessionUserName(),Constant.PLACE_HOTLE_LOG.createOrUpdatePlaceHotelRoom.name(),
	                      "创建或或着修改房型信息","创建或或着修改房型信息","");

	             placeService.markPlaceSensitive(placeHotelRoom.getPlaceId(), hasSensitiveWord);
	              ajaxResult(true,"操作成功");
	      } catch (Exception ex) {
	              ajaxResult(false,"操作失败");
	              ex.printStackTrace();
	      } 
	      return null;
	}
	@Action("/place/deletePlaceHotelRoom")
	public String deletePlaceHotelNotice(){
	    try {
            placeHotelRoomService.deletePlaceHotelRoom(placeHotelRoom);
            
            comLogService.insert("SCENIC_LOG_PLACE",null,
                    placeHotelRoom.getPlaceId(),super.getSessionUserName(),Constant.PLACE_HOTLE_LOG.deletePlaceHotelRoom.name(),
                    "删除房型信息","删除房型信息","");
            
            placeService.markPlaceSensitive(placeHotelRoom.getPlaceId(), null);
        } catch (Exception e) {
            e.printStackTrace();
        }
	    return null;
	}
	//前台须验证的值、为NULL则初始化
	public void validatePlaceHotelRoom(){
	    if(this.placeHotelRoom.getBigBedWide() == null){
	        this.placeHotelRoom.setBigBedWide("0");
	    }
	    if(this.placeHotelRoom.getDoubleBedWide() == null){
	        this.placeHotelRoom.setDoubleBedWide("0");
	    }
	}
	public void ajaxResult(boolean success,String message){
	    java.util.Map<String, Object> map = new HashMap<String, Object>();
	    map.put("success", success);
        map.put("message", message);
        this.sendAjaxResultByJson(JSONObject.fromObject(map).toString());
	}
	
	public String operateType; //
	
	public String getOperateType() {
        return operateType;
    }
    public void setOperateType(String operateType) {
        this.operateType = operateType;
    }
    public void setPlaceHotelRoomService(PlaceHotelRoomService placeHotelRoomService) {
        this.placeHotelRoomService = placeHotelRoomService;
    }
    public PlaceHotelRoom getPlaceHotelRoom() {
        return placeHotelRoom;
    }

    public void setPlaceHotelRoom(PlaceHotelRoom placeHotelRoom) {
        this.placeHotelRoom = placeHotelRoom;
    }

    public List<PlaceHotelRoom> getPlaceHotelRoomList() {
        return placeHotelRoomList;
    }

    public void setPlaceHotelRoomList(List<PlaceHotelRoom> placeHotelRoomList) {
        this.placeHotelRoomList = placeHotelRoomList;
    }

    public void setComLogService(ComLogService comLogService) {
        this.comLogService = comLogService;
    }

	public String getHasSensitiveWord() {
		return hasSensitiveWord;
	}

	public void setHasSensitiveWord(String hasSensitiveWord) {
		this.hasSensitiveWord = hasSensitiveWord;
	}

	public void setPlaceService(PlaceService placeService) {
		this.placeService = placeService;
	}
}
