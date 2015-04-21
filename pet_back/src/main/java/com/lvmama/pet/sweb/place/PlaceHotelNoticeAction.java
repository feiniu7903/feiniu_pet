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
import com.lvmama.comm.pet.po.place.PlaceHotelNotice;
import com.lvmama.comm.pet.service.place.PlaceHotelNoticeService;
import com.lvmama.comm.pet.service.place.PlaceService;
import com.lvmama.comm.pet.service.pub.ComLogService;
import com.lvmama.comm.vo.Constant;

@Results({
    @Result(name = "placeHotelNoticeList", location = "/WEB-INF/pages/back/place/notice/placeHotelNoticeList.jsp", type = "dispatcher"),
    @Result(name = "initPlaceHotelNotice", location = "/WEB-INF/pages/back/place/notice/placeHotelNotice_add.jsp", type = "dispatcher")
})
public class PlaceHotelNoticeAction extends BackBaseAction {
	/**
     * 
     */
    private static final long serialVersionUID = 1L;

    private PlaceHotelNoticeService placeHotelNoticeService;
	
	private PlaceHotelNotice placeHotelNotice = new PlaceHotelNotice();
	private List<PlaceHotelNotice> placeHotelNoticeList = new ArrayList<PlaceHotelNotice>();
	private ComLogService comLogService;
	private Long placeId;
	private Long noticeId;
	private String noticeType;
	private String hasSensitiveWord;
	private PlaceService placeService;
	//查询公告用
	/**
	 * 公告查询
	 * @return
	 */
    @Action("/place/placeHotelNotice")
	public String productSearchInfo(){
        this.placeHotelNotice.setPlaceId(placeId);
        this.placeHotelNotice.setNoticeType(noticeType);
	    placeHotelNoticeList = placeHotelNoticeService.queryByHotelNotice(placeHotelNotice);
		return "placeHotelNoticeList";
	}
	
    /**
     * 公告添加、修改初始化 (公用方法)
     * 添加(需传的值)
     * 1.需给placeHotelNotice.noticeType赋值，(对内:internal  全部:all  一句话推荐:recommend  公告:product))
     * 2.successUrl为添加、修改操作成功后的跳转路径 *(选传)
     * 3.placeHotelNotice.placeId  关联ID
     * 
     * 修改
     * 1.placeHotelNotice.noticeId 公告ID
     * 2.successUrl为添加、修改操作成功后的跳转路径(选传)
     * @return
     */
	@Action("/place/initPlaceHotelNotice")
	public String initPlaceHotelNotice(){
	    this.placeHotelNotice.setNoticeType(noticeType);
	    if(placeHotelNotice.getNoticeId() != null){
	        this.placeHotelNotice = placeHotelNoticeService.queryPlacehotelNoticeByNoticeId(placeHotelNotice);
	    }
	    return "initPlaceHotelNotice";
	}
	/**
	 * 公告添加、修改
	 * @return
	 */
	@Action("/place/addOrUpdatePlaceHotelNotice")
	public String addOrUpdatePlaceHotelNotice(){
	    //通过参数operateType 
            try {
                placeHotelNoticeService.addOrUpdatePlaceHotelNotice(placeHotelNotice);
               comLogService.insert("SCENIC_LOG_PLACE",null,
                        placeHotelNotice.getPlaceId(),super.getSessionUserName(),Constant.PLACE_HOTLE_LOG.createOrUpdatePlaceHotelNotice.name(),
                        placeHotelNotice.getNoticeType().equals("RECOMMEND")?"新增或着修改一句话推荐":"新建或着修改公告信息",
                        placeHotelNotice.getNoticeType().equals("RECOMMEND")?"新增或着修改一句话推荐":"新建或着修改公告信息","");
               
               placeService.markPlaceSensitive(placeHotelNotice.getPlaceId(), hasSensitiveWord);
                ajaxResult(true,"操作成功");
            } catch (Exception ex) {
                ajaxResult(false,"操作失败");
                ex.printStackTrace();
            } 
	    return null;
	}
	/**
	 * 公告删除
	 * @return
	 */
	@Action("/place/deletePlaceHotelNotice")
	public String deletePlaceHotelNotice(){
	    try {
	        this.placeHotelNotice.setNoticeType(noticeType);
	        this.placeHotelNotice.setPlaceId(placeId);
            placeHotelNoticeService.deletePlaceHotelNotice(placeHotelNotice);
            System.out.println("================"+placeHotelNotice.getPlaceId()+"=========="+placeHotelNotice.getNoticeType());
            
            comLogService.insert("SCENIC_LOG_PLACE",null,
                    placeHotelNotice.getPlaceId(),super.getSessionUserName(),Constant.PLACE_HOTLE_LOG.deletePlaceHotelNotice.name(),
                    placeHotelNotice.getNoticeType().equals("RECOMMEND")?"删除一句话推荐":"删除公告",
                    placeHotelNotice.getNoticeType().equals("RECOMMEND")?"删除一句话推荐":"删除公告","");
            
            placeService.markPlaceSensitive(placeId, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
	    return null;
	}
	
	public void ajaxResult(boolean success,String message){
	    java.util.Map<String, Object> map = new HashMap<String, Object>();
	    map.put("success", success);
        map.put("message", message);
        map.put("noticeType",this.placeHotelNotice.getNoticeType());
        map.put("successUrl",successUrl);
        this.sendAjaxResultByJson(JSONObject.fromObject(map).toString());
	}
	
	public String successUrl = "/place/placeHotelNotice.do";
	
    public String getSuccessUrl() {
        return successUrl;
    }
    public void setSuccessUrl(String successUrl) {
        this.successUrl = successUrl;
    }

    public void setPlaceHotelNoticeService(
			PlaceHotelNoticeService placeHotelNoticeService) {
		this.placeHotelNoticeService = placeHotelNoticeService;
	}
    public PlaceHotelNotice getPlaceHotelNotice() {
        return placeHotelNotice;
    }
    public void setPlaceHotelNotice(PlaceHotelNotice placeHotelNotice) {
        this.placeHotelNotice = placeHotelNotice;
    }

    public List<PlaceHotelNotice> getPlaceHotelNoticeList() {
        return placeHotelNoticeList;
    }

    public void setPlaceHotelNoticeList(List<PlaceHotelNotice> placeHotelNoticeList) {
        this.placeHotelNoticeList = placeHotelNoticeList;
    }
    public Long getPlaceId() {
        return placeId;
    }
    public void setPlaceId(Long placeId) {
        this.placeId = placeId;
    }
    public Long getNoticeId() {
        return noticeId;
    }
    public void setNoticeId(Long noticeId) {
        this.noticeId = noticeId;
    }

    public String getNoticeType() {
        return noticeType;
    }

    public void setNoticeType(String noticeType) {
        this.noticeType = noticeType;
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
