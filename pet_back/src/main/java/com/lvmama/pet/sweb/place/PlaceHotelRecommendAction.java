package com.lvmama.pet.sweb.place;

import java.util.HashMap;
import java.util.List;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.BackBaseAction;
import com.lvmama.comm.pet.po.place.PlaceHotelOtherRecommend;
import com.lvmama.comm.pet.service.place.PlaceHotelRecommendService;
import com.lvmama.comm.pet.service.place.PlaceService;
import com.lvmama.comm.pet.service.pub.ComLogService;
import com.lvmama.comm.vo.Constant;

@Results({                                                                                       
    @Result(name = "initPlaceHotelRecommend", location = "/WEB-INF/pages/back/place/recommend/placeHotelRecommend_add.jsp", type=  "dispatcher")
})

public class PlaceHotelRecommendAction extends BackBaseAction{
    private static final long serialVersionUID = 1L;

    public List<PlaceHotelOtherRecommend> placeHotelRecommendList;

    public PlaceHotelOtherRecommend placeHotelRecommend;
                                      
    public PlaceHotelRecommendService placeHotelRecommendService;
    
    public ComLogService comLogService;
    
    private String hasSensitiveWord;
    
    private PlaceService placeService;
    
    @Action("/place/initPlaceHotelRecommend")
    public String initPlaceHotelRecommend(){
        if(placeHotelRecommend.getRecommendId() != null){
            this.placeHotelRecommend = placeHotelRecommendService.queryPlaceHotelRecommendByRecommendId(placeHotelRecommend);
        }
        return "initPlaceHotelRecommend";
    }
    
    @Action("/place/addOrUpdatePlaceHotelRecommend")
    public String addOrUpdatePlaceHotelRecommend(){
        //通过参数operateType 
        try {
                placeHotelRecommendService.addOrUpdatePlaceHotelRecommend(placeHotelRecommend);
                comLogService.insert("SCENIC_LOG_PLACE",null,
                        placeHotelRecommend.getPlaceId(),super.getSessionUserName(),Constant.PLACE_HOTLE_LOG.createOrUpdatePlaceHotelRecommend.name(),
                        placeHotelRecommend.getRecommentType().equals("special")?"新增或着修改特色服务信息":"新增或着修改玩法信息",
                        placeHotelRecommend.getRecommentType().equals("special")?"新增或着修改酒店的特色服务":"新增或着修改酒店的玩法","");
                
                placeService.markPlaceSensitive(placeHotelRecommend.getPlaceId(), hasSensitiveWord);
                ajaxResult(true,"操作成功");
            } catch (Exception ex) {
                ajaxResult(false,"操作失败");
                ex.printStackTrace();
            } 
        return null;
    }
    
    @Action("/place/deletePlaceHotelRecommend")
    public String deletePlaceHotelRecommend(){
        try {
            placeHotelRecommendService.deletePlaceHotelRecommend(placeHotelRecommend);
            comLogService.insert("SCENIC_LOG_PLACE",null,
                    placeHotelRecommend.getPlaceId(),super.getSessionUserName(),Constant.PLACE_HOTLE_LOG.deletePlaceHotelRecommend.name(),
                    placeHotelRecommend.getRecommentType().equals("special")?"删除特色服务信息":"删除玩法信息",
                    placeHotelRecommend.getRecommentType().equals("special")?"删除酒店的特色服务":"删除酒店的玩法","");
            
            placeService.markPlaceSensitive(placeHotelRecommend.getPlaceId(), null);
            ajaxResult(true,"操作成功");
        } catch (Exception e) {
            ajaxResult(false,"操作失败");
            e.printStackTrace();
        }
        return null;
    }
    
    public void ajaxResult(boolean success,String message){
        java.util.Map<String, Object> map = new HashMap<String, Object>();
        map.put("success", success);
        map.put("message", message);
        this.sendAjaxResultByJson(JSONObject.fromObject(map).toString());
    }
    
    public String operateType ;
    
    public String getOperateType() {
        return operateType;
    }
    public void setOperateType(String operateType) {
        this.operateType = operateType;
    }

    public PlaceHotelOtherRecommend getPlaceHotelRecommend() {
        return placeHotelRecommend;
    }
    public void setPlaceHotelRecommend(PlaceHotelOtherRecommend placeHotelRecommend) {
        this.placeHotelRecommend = placeHotelRecommend;
    }
    public List<PlaceHotelOtherRecommend> getPlaceHotelRecommendList() {
        return placeHotelRecommendList;
    }
    public void setPlaceHotelRecommendList(
            List<PlaceHotelOtherRecommend> placeHotelRecommendList) {
        this.placeHotelRecommendList = placeHotelRecommendList;
    }
    public void setPlaceHotelRecommendService(
            PlaceHotelRecommendService placeHotelRecommendService) {
        this.placeHotelRecommendService = placeHotelRecommendService;
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
