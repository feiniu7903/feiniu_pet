package com.lvmama.pet.sweb.place;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import com.lvmama.comm.pet.po.place.HotelTrafficInfo;
import com.lvmama.comm.pet.po.place.Place;
import com.lvmama.comm.pet.po.place.PlaceCoordinateGoogle;
import com.lvmama.comm.pet.service.place.HotelTrafficInfoService;
import com.lvmama.comm.pet.service.place.PlaceCoordinateGoogleService;

/**
 * 酒店管理
 * @author 
 *
 */
@Results( {
	@Result(name = "hotelList", location = "/WEB-INF/pages/back/place/hotel/hotel_list.jsp"),
	@Result(name = "hotelAdd", location = "/WEB-INF/pages/back/place/hotel/hotel_add.jsp"),
	@Result(name = "hotelSaleInfoAdd", location = "/WEB-INF/pages/back/place/hotel/hotelSaleInfoAdd.jsp"),
	@Result(name = "hotelDescAdd", location = "/WEB-INF/pages/back/place/hotel/hotelDescAdd.jsp"),
	@Result(name = "viewHotelTrafficInfo", location="/WEB-INF/pages/back/place/hotel/viewHotelTrafficInfo.jsp")
})
public class HotelPlaceAction  extends AbstractPlaceAction {
	private static final long serialVersionUID = 1L;
	private PlaceCoordinateGoogleService placeCoordinateGoogleService;
	private HotelTrafficInfoService hotelTrafficInfoService;
	List<PlaceCoordinateGoogle> coordinateByPlaceList;
	private Map<String,String> breakfastPriceMap = null; 
	private Map<String,String> facilitiesRemarkMap = null;
	
	List<HotelTrafficInfo> trafficInfoList;
	HotelTrafficInfo hotelTrafficInfo;
	
	@Action("/place/hotelList")
	public String execute() throws Exception {
		list();
		initSubjectList();
		return "hotelList";
	}
	@Action("/place/hotelEdit")
	public String hotelEdit() throws Exception {
		edit();
		if(StringUtils.isNotBlank(this.getPlaceId())){
			coordinateByPlaceList = placeCoordinateGoogleService.getCoordinateByPlace(Long.valueOf(this.getPlaceId()), "2");
		}
		Place place = getPlace();
		if(place!=null && place.getPlaceHotel() != null ){
			if(StringUtils.isNotEmpty(place.getPlaceHotel().getBreakfastType())){//处理早餐的金额显示
				String[] types = place.getPlaceHotel().getBreakfastType().split(",");
				String[] prices = new String[5];
				if(place.getPlaceHotel().getBreakfastPrice() != null){
				    prices = place.getPlaceHotel().getBreakfastPrice().split(",");
				}
				breakfastPriceMap = new HashMap<String,String>();
				for(int i=0;i<types.length;i++){
					breakfastPriceMap.put(types[i],prices[i]==null?"0":prices[i]);
				}
			}
			initFacilitiesRemarkMap(place.getPlaceHotel().getIntegratedFacilities());
			initFacilitiesRemarkMap(place.getPlaceHotel().getRoomFacilities());
			initFacilitiesRemarkMap(place.getPlaceHotel().getDiningFacilities());
			initFacilitiesRemarkMap(place.getPlaceHotel().getRecreationalFacilities());
		}
		return "hotelAdd";
	}
	
	private void initFacilitiesRemarkMap(String facilities){
		if( facilitiesRemarkMap == null ){
			facilitiesRemarkMap = new HashMap<String, String>();
		}
		if(StringUtils.isNotEmpty(facilities)){
			String[] fs = facilities.split(",");
			for(String s : fs){
				int inx_0 = s.indexOf("(");
				int inx_1 = s.indexOf(")");
				if(inx_0 != -1 && inx_1!=-1){
					facilitiesRemarkMap.put(s.substring(0,inx_0),s.substring(inx_0+1,inx_1));
				}
			}
		}
	}
	@Action("/place/hotelAdd")
	public String hotelAdd() throws Exception {
		add();
		return "hotelAdd";
	}
	
	@Action("/place/hotelSaleInfoAdd")
	public String hotelSaleInfoAdd() throws Exception {
		initPlaceByPlaceId();
		return "hotelSaleInfoAdd";
	}
	@Action("/place/hotelSaleInfoSave")
	public String hotelSaleInfoSave() throws Exception {
		try{
			save();
			this.setMsg("添加成功!");
		}catch(Exception ex){
			ex.printStackTrace();
			this.setMsg("添加失败!");
		}
	   return hotelSaleInfoAdd();
	//	return "hotelAdd";
	}
	@Action("/place/hotelSaleInfoUpdate")
	public void hotelSaleInfoUpdate() throws Exception {
		java.util.Map<String, Object> map = new HashMap<String, Object>();
		try{
			update();
			if(this.getMsg()==null||"".equals(this.getMsg()))
			map.put("success", true);
			map.put("message", "修改成功!");
		}catch(Exception ex){
			ex.printStackTrace();
			map.put("success", false);
			map.put("message", "修改失败!");
		}finally{
			this.sendAjaxResultByJson(JSONObject.fromObject(map).toString());
		}
	}
	@Action("/place/hotelDescAdd")
	public String hotelDescAdd() throws Exception {
		initPlaceByPlaceId();
		return "hotelDescAdd";
	}
	@Action("/place/saveHotelDescAdd")
	public String saveHotelDescAdd() throws Exception {
		try{
			save();
			this.setMsg("添加成功!");
		}catch(Exception ex){
			ex.printStackTrace();
			this.setMsg("添加失败!");
		}
		initPlaceByPlaceId();
		return "hotelDescAdd";
	}
	@Action("/place/dohotelSave")
	public String doSave() throws Exception {
		try{
			save();
			this.setMsg("添加成功!");
		}catch(Exception ex){
			ex.printStackTrace();
			this.setMsg("添加失败!");
		}
		
		return "hotelAdd";
	}

	@Action("/place/dohotelUpdate")
	public void doUpdate() throws Exception {
		java.util.Map<String, Object> map = new HashMap<String, Object>();
		try {
			update();
			if (this.getMsg() == null || "".equals(this.getMsg())) {
				map.put("success", true);
				map.put("message", "修改成功!");
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			map.put("success", false);
			map.put("message", "修改失败!");
		} finally {
			this.sendAjaxResultByJson(JSONObject.fromObject(map).toString());
		}

	}
	@Override
	public void setCurrentStage() {
		this.setStage("3");
	}
	
	@Action("/hotel/viewHotelTrafficInfo")
	public String viewHotelTrafficInfo() {
		trafficInfoList = hotelTrafficInfoService.queryByPlaceId(Long.parseLong(getPlaceId()));
		return "viewHotelTrafficInfo";
	}
	
	@Action("/hotel/saveHotelTrafficInfo")
	public void saveHotelTrafficInfo() throws Exception {
		JSONObject json = new JSONObject();
		if (null != hotelTrafficInfo) {
			if (null == hotelTrafficInfo.getId()) {
				hotelTrafficInfoService.insert(hotelTrafficInfo, getSessionUserName());
				json.put("success", true);
			} else {
				hotelTrafficInfoService.update(hotelTrafficInfo, getSessionUserName());
				json.put("success", true);
			}
		} else {
			json.put("success", false);
		}
		getResponse().getWriter().print(json.toString());
	}
	
	@Action("/hotel/delHotelTrafficInfo")
	public void delHotelTrafficInfo() throws Exception {
		JSONObject json = new JSONObject();
		if (null != hotelTrafficInfo && null != hotelTrafficInfo.getId()) {
			hotelTrafficInfoService.delete(hotelTrafficInfo.getId(), getSessionUserName());
			json.put("success", true);
		} else {
			json.put("success", false);
		}
		getResponse().getWriter().print(json.toString());
	}	
	
	public List<PlaceCoordinateGoogle> getCoordinateByPlaceList() {
		return coordinateByPlaceList;
	}
	public void setPlaceCoordinateGoogleService(PlaceCoordinateGoogleService placeCoordinateGoogleService) {
		this.placeCoordinateGoogleService = placeCoordinateGoogleService;
	}
	public Map<String, String> getBreakfastPriceMap() {
		return breakfastPriceMap;
	}
	public Map<String, String> getFacilitiesRemarkMap() {
		return facilitiesRemarkMap;
	}
	
	public void setHotelTrafficInfoService(
			HotelTrafficInfoService hotelTrafficInfoService) {
		this.hotelTrafficInfoService = hotelTrafficInfoService;
	}
	public HotelTrafficInfo getHotelTrafficInfo() {
		return hotelTrafficInfo;
	}
	public void setHotelTrafficInfo(HotelTrafficInfo hotelTrafficInfo) {
		this.hotelTrafficInfo = hotelTrafficInfo;
	}
	public List<HotelTrafficInfo> getTrafficInfoList() {
		return trafficInfoList;
	}
}

