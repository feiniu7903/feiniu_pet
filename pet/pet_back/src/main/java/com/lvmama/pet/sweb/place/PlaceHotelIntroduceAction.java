package com.lvmama.pet.sweb.place;

import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.json.JSONObject;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.BackBaseAction;
import com.lvmama.comm.pet.po.place.PlaceHotelNotice;
import com.lvmama.comm.pet.po.place.PlaceHotelOtherRecommend;
import com.lvmama.comm.pet.po.place.PlaceHotelRoom;
import com.lvmama.comm.pet.service.place.PlaceHotelNoticeService;
import com.lvmama.comm.pet.service.place.PlaceHotelRecommendService;
import com.lvmama.comm.pet.service.place.PlaceHotelRoomService;
import com.lvmama.comm.utils.homePage.PlaceUtils;

@Results({                                                                                       
    @Result(name = "queryAllIntroduce",location = "/WEB-INF/pages/back/place/recommend/placeHotelIntroduceList.jsp", type= "dispatcher")
})
public class PlaceHotelIntroduceAction extends BackBaseAction{
    private static final long serialVersionUID = 1L;

    public List<PlaceHotelRoom> placeHotelRoomList;
    public List<PlaceHotelOtherRecommend> placeHotelRecommendList;
    public List<PlaceHotelNotice> placeHotelNoticeList;

    public PlaceHotelNotice placeHotelNotice = new PlaceHotelNotice();
    public PlaceHotelRoom placeHotelRoom = new PlaceHotelRoom();
    public PlaceHotelOtherRecommend placeHotelRecommend = new PlaceHotelOtherRecommend();
                                      
    public PlaceHotelRecommendService placeHotelRecommendService;
    public PlaceHotelNoticeService placeHotelNoticeService;
    public PlaceHotelRoomService placeHotelRoomService;
    
    public Long placeId;
    public Long getPlaceId() {
        return placeId;
    }
    public void setPlaceId(Long placeId) {
        this.placeId = placeId;
    }
    @Action("/place/queryAllIntroduce")
    public String queryAllIntroduce(){
        this.placeHotelNotice.setNoticeType(PlaceUtils.RECOMMEND);
        
        this.placeHotelNotice.setPlaceId(placeId);
        this.placeHotelRoom.setPlaceId(placeId);
        this.placeHotelRecommend.setPlaceId(placeId);
        //一句话推荐
        placeHotelNoticeList = placeHotelNoticeService.queryByHotelNotice(placeHotelNotice);
        placeHotelRoomList = placeHotelRoomService.queryAllPlaceHotelRoom(placeHotelRoom);
        placeHotelRecommendList = placeHotelRecommendService.queryAllPlaceHotelRecommend(placeHotelRecommend);
        for (int i = 0; i < placeHotelRoomList.size(); i++) {
            if(placeHotelRoomList.get(i).getRoomRecommend() != null){
                placeHotelRoomList.get(i).setRoomRecommendNoHtml(this.noHtml(placeHotelRoomList.get(i).getRoomRecommend()));
            }
        }
        for (int i = 0; i < placeHotelRecommendList.size(); i++) {
            if(placeHotelRecommendList.get(i).getRecommentContent() != null){
                placeHotelRecommendList.get(i).setRecommentContentNoHtml(this.noHtml(placeHotelRecommendList.get(i).getRecommentContent()));
            }
        }
        return "queryAllIntroduce";
    }
    @Action("/place/dataValidate")
    public String dataValidate(){
        this.placeHotelNotice.setNoticeType(PlaceUtils.RECOMMEND);
        this.placeHotelNotice.setPlaceId(placeId);
        placeHotelNoticeList = placeHotelNoticeService.queryByHotelNotice(placeHotelNotice);
        if(placeHotelNoticeList.size() != 0){
            this.sendAjaxMsg("success");
        }else{
            this.sendAjaxMsg("fail");
        }
        return null;
    }
    public void ajaxResult(boolean success,String message){
        java.util.Map<String, Object> map = new HashMap<String, Object>();
        map.put("success", success);
        map.put("message", message);
        map.put("noticeType",this.placeHotelNotice.getNoticeType());
        this.sendAjaxResultByJson(JSONObject.fromObject(map).toString());
    }
    public String noHtml(String html){
        Pattern p_html = Pattern.compile("<[^>]+>",Pattern.CASE_INSENSITIVE);
        Matcher m_html = p_html.matcher(html);
        return m_html.replaceAll("");
    }
    public String operateType ;
    
    public String getOperateType() {
        return operateType;
    }
    public void setOperateType(String operateType) {
        this.operateType = operateType;
    }

    public PlaceHotelNotice getPlaceHotelNotice() {
        return placeHotelNotice;
    }
    public void setPlaceHotelNotice(PlaceHotelNotice placeHotelNotice) {
        this.placeHotelNotice = placeHotelNotice;
    }
    public PlaceHotelRoom getPlaceHotelRoom() {
        return placeHotelRoom;
    }
    public void setPlaceHotelRoom(PlaceHotelRoom placeHotelRoom) {
        this.placeHotelRoom = placeHotelRoom;
    }
    public PlaceHotelOtherRecommend getPlaceHotelRecommend() {
        return placeHotelRecommend;
    }
    public void setPlaceHotelRecommend(PlaceHotelOtherRecommend placeHotelRecommend) {
        this.placeHotelRecommend = placeHotelRecommend;
    }
    public List<PlaceHotelRoom> getPlaceHotelRoomList() {
        return placeHotelRoomList;
    }
    public void setPlaceHotelRoomList(List<PlaceHotelRoom> placeHotelRoomList) {
        this.placeHotelRoomList = placeHotelRoomList;
    }
    public List<PlaceHotelOtherRecommend> getPlaceHotelRecommendList() {
        return placeHotelRecommendList;
    }
    public void setPlaceHotelRecommendList(
            List<PlaceHotelOtherRecommend> placeHotelRecommendList) {
        this.placeHotelRecommendList = placeHotelRecommendList;
    }
    public List<PlaceHotelNotice> getPlaceHotelNoticeList() {
        return placeHotelNoticeList;
    }
    public void setPlaceHotelNoticeList(List<PlaceHotelNotice> placeHotelNoticeList) {
        this.placeHotelNoticeList = placeHotelNoticeList;
    }

    public void setPlaceHotelRecommendService(
            PlaceHotelRecommendService placeHotelRecommendService) {
        this.placeHotelRecommendService = placeHotelRecommendService;
    }

    public void setPlaceHotelNoticeService(
            PlaceHotelNoticeService placeHotelNoticeService) {
        this.placeHotelNoticeService = placeHotelNoticeService;
    }

    public void setPlaceHotelRoomService(PlaceHotelRoomService placeHotelRoomService) {
        this.placeHotelRoomService = placeHotelRoomService;
    }
}

