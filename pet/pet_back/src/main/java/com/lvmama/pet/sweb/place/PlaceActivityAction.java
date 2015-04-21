package com.lvmama.pet.sweb.place;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.BackBaseAction;
import com.lvmama.comm.pet.po.place.Place;
import com.lvmama.comm.pet.po.place.PlaceActivity;
import com.lvmama.comm.pet.service.place.PlaceActivityService;
import com.lvmama.comm.pet.service.place.PlaceService;
import com.lvmama.comm.pet.service.pub.ComSearchInfoUpdateService;
@Results( {
	@Result(name = "success", location = "/WEB-INF/pages/back/place/activity/place_activity.jsp"),
	@Result(name = "placeActivityAdd", location = "/WEB-INF/pages/back/place/activity/place_activity_add.jsp")
})
public class PlaceActivityAction extends BackBaseAction{
	private static final long serialVersionUID = -1933262201783089923L;
	/**
	 * 每次修改place信息之后需要向COM_SEARCH_INFO_UPDATE表中增加一条记录，用于同步PLACE表与PLACE_SEARCH_INFO的信息
	 * add by yanggan 
	 */
	private ComSearchInfoUpdateService comSearchInfoUpdateService;
	
	private PlaceActivityService placeActivityService;
	private PlaceService placeService;
	private List<PlaceActivity> placeActivityList;
	private PlaceActivity placeActivity;
	private String placeActivityId;
	private String placeId;
	private String stage;
	private String msg;
	private String flag="Y";//标示可以继续添加活动
	private String hasSensitiveWord;
	
	@Action("/place/placeActivity")
	public String execute() throws Exception {
		if(StringUtils.isNotBlank(placeId)){
			placeActivityList=placeActivityService.queryPlaceActivityListByPlaceId(new Long(placeId));
			if(placeActivityList!=null&&placeActivityList.size()>2)
				flag="N";
		}
		return SUCCESS;
	}
	@Action("/place/placeActivityAdd")
	public String placeActivityAdd()throws Exception{
		return "placeActivityAdd";
	}
	@Action("/place/placeActivityEdit")
	public String placeActivityEdit()throws Exception{
		placeActivity=placeActivityService.queryPlaceActivityByPlaceActivityId(new Long(placeActivityId));
		return "placeActivityAdd";
	}
	@Action("/place/placeActivitySave")
	public String placeActivitySave() throws Exception{
		try{
			if(null==placeActivity.getPlaceActivityId()||"".equals(placeActivity.getPlaceActivityId())){//新增
				//校验活动数量
				List<PlaceActivity> list=placeActivityService.queryPlaceActivityListByPlaceId(new Long(placeId));
				if(list!=null&&list.size()>2){
					msg="最多允许添加3个活动";
				}else{
					placeActivityService.savePlaceActivity(placeActivity);
					msg="添加成功！";
					Place place = placeService.queryPlaceByPlaceId(placeActivity.getPlaceId());
					comSearchInfoUpdateService.placeUpdated(place.getPlaceId(),place.getStage());
				}
			}else{//修改
				placeActivityService.savePlaceActivity(placeActivity);
				msg="修改成功！";
				Place place = placeService.queryPlaceByPlaceId(placeActivity.getPlaceId());
				comSearchInfoUpdateService.placeUpdated(place.getPlaceId(),place.getStage());
			}
		}catch(Exception ex){
			ex.printStackTrace();
			msg="操作失败！";
		}
		
		placeService.markPlaceSensitive(new Long(placeId), hasSensitiveWord);
		this.outputToClient(msg);
		return null;
	}
	@Action("/place/placeActivityDel")
	public String placeActivityDel() throws Exception{
		try{
			placeActivity = placeActivityService.queryPlaceActivityByPlaceActivityId(new Long(placeActivityId));
			placeActivityService.deletePlaceActivityByPlaceActivityId(new Long(placeActivityId));
			Place place = placeService.queryPlaceByPlaceId(placeActivity.getPlaceId());
			comSearchInfoUpdateService.placeUpdated(place.getPlaceId(),place.getStage());
			
			placeService.markPlaceSensitive(place.getPlaceId(), null);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
		return execute();
	}
	public String getPlaceId() {
		return placeId;
	}
	public void setPlaceId(String placeId) {
		this.placeId = placeId;
	}
	public String getStage() {
		return stage;
	}
	public void setStage(String stage) {
		this.stage = stage;
	}
	public List<PlaceActivity> getPlaceActivityList() {
		return placeActivityList;
	}
	public void setPlaceActivityList(List<PlaceActivity> placeActivityList) {
		this.placeActivityList = placeActivityList;
	}
	public PlaceActivityService getPlaceActivityService() {
		return placeActivityService;
	}
	public void setPlaceActivityService(PlaceActivityService placeActivityService) {
		this.placeActivityService = placeActivityService;
	}
	public PlaceActivity getPlaceActivity() {
		return placeActivity;
	}
	public void setPlaceActivity(PlaceActivity placeActivity) {
		this.placeActivity = placeActivity;
	}
	public String getPlaceActivityId() {
		return placeActivityId;
	}
	public void setPlaceActivityId(String placeActivityId) {
		this.placeActivityId = placeActivityId;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public void setComSearchInfoUpdateService(ComSearchInfoUpdateService comSearchInfoUpdateService) {
		this.comSearchInfoUpdateService = comSearchInfoUpdateService;
	}
	public void setPlaceService(PlaceService placeService) {
		this.placeService = placeService;
	}
	public String getHasSensitiveWord() {
		return hasSensitiveWord;
	}
	public void setHasSensitiveWord(String hasSensitiveWord) {
		this.hasSensitiveWord = hasSensitiveWord;
	} 
	
	
}
