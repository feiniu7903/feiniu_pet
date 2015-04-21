package com.lvmama.pet.sweb.place;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.pet.po.place.Place;
import com.lvmama.comm.pet.po.place.PlaceLandMark;
import com.lvmama.comm.pet.po.place.PlaceSearchPinyin;
import com.lvmama.comm.pet.service.place.PlaceLandMarkService;
import com.lvmama.comm.utils.json.JSONResult;
import com.lvmama.comm.vo.Constant;
/**
 * 地标管理
 * @author yuzhizeng
 *
 */
@Results( {
	@Result(name = "placeLandMarkList", location = "/WEB-INF/pages/back/place/landMark/land_mark_list.jsp"),
	@Result(name = "placeLandMarkEdit", location = "/WEB-INF/pages/back/place/landMark/land_mark_edit.jsp"),
	@Result(name = "addPlaceLandMarkPinyin", location = "/WEB-INF/pages/back/place/landMark/land_mark_pinyin_add.jsp")
})
public class PlaceLandMarkAction  extends AbstractPlaceAction {
	
	/**
	 * 序列
	 */
	private static final long serialVersionUID = -6835813002160734986L;

	private PlaceLandMarkService placeLandMarkService;
	private PlaceLandMark placeLandMark = new PlaceLandMark();
	private List<PlaceLandMark> placeLandMarkList;
	private long placeLandMarkId;
	private String valid;//设置是否有效
	private String parentPlaceId;
	private String parentPlaceName;
	private PlaceSearchPinyin placeSearchPinyin;
	
	@Action("/place/placeLandMarkList")
	public String placeLandMarkList() throws Exception {
		
		pagination = initPage();
		pagination.setPageSize(15);
		
		Map<String, Object> param = new HashMap<String, Object>();
		if(parentPlaceId != null&& !"".equals(parentPlaceId)){
			param.put("parentPlaceId", Long.parseLong(parentPlaceId));
		}
		if(placeLandMark.getLandMarkName()!=null&&!"".equals(placeLandMark.getLandMarkName())){
			param.put("landMarkName", placeLandMark.getLandMarkName());
		}
		
		pagination.setTotalResultSize(placeLandMarkService.selectByParamsCount(param));
		if(pagination.getTotalResultSize() > 0){
			param.put("_startRow", pagination.getStartRows());
			param.put("_endRow", pagination.getEndRows());
			placeLandMarkList = placeLandMarkService.selectByParams(param);
		}
		pagination.setItems(placeLandMarkList);
		pagination.buildUrl(getRequest());
		
		return "placeLandMarkList";
	}
 
	@Action("/place/placeLandMarkEdit")
	public String placeLandMarkEdit() throws Exception {
		placeLandMark = placeLandMarkService.selectByPrimaryKey(placeLandMarkId);
		return "placeLandMarkEdit";
	}

	@Action("/place/doPlaceLandMarkSetValid")
	public String doPlaceLandMarkSetValid() throws Exception {
		try{
			placeLandMark = placeLandMarkService.selectByPrimaryKey(placeLandMarkId);
			placeLandMark.setIsValid(valid);
			placeLandMarkService.saveOrUpdatePlaceLandMark(placeLandMark);
			this.outputToClient("success");
			this.setMsg("删除成功!");
		}catch(Exception ex){
			ex.printStackTrace();
			this.setMsg("删除失败!");
		}
		return null;
	}
	
	@Action("/place/doPlaceLandMarkSave")
	public String doPlaceLandMarkSave() throws Exception {
		try{
			placeLandMarkService.saveOrUpdatePlaceLandMark(placeLandMark);
			this.outputToClient("success");
			this.setMsg("添加成功!");
		}catch(Exception ex){
			ex.printStackTrace();
			this.setMsg("添加失败!");
		}
		return null;
	}
	
	/** 
	 *   *   *   *   placeLandMark对象设置拼音和高频字    *   *   *   *   *  
	 */
	@Action("/place/toAddLandMarkPinyinOrHFKW")
	public String toAddLandMarkPinyinOrHFKW() {
		placeLandMark = placeLandMarkService.selectByPrimaryKey(placeSearchPinyin.getObjectId());
		return "addPlaceLandMarkPinyin";
	} 
	
	@Action("/place/saveOrUpdatePinYin")
	public void saveOrUpdatePinYin() throws IOException {
		JSONObject json = new JSONObject();
		json.put("success", false);
		json.put("message", "");
		
		placeLandMark = placeLandMarkService.selectByPrimaryKey(placeSearchPinyin.getObjectId());
		if(Constant.SEARCH_PINYIN_OBJECT_TYPE_PLACE_LAND_MARK_NAME.equalsIgnoreCase(placeSearchPinyin.getObjectType())){
			placeLandMark.setPinYin(placeSearchPinyin.getPinYin());//保存拼音
			
		}else if (Constant.SEARCH_PINYIN_OBJECT_TYPE_PLACE_LAND_MARK_HFKW.equalsIgnoreCase(placeSearchPinyin.getObjectType())){
			placeLandMark.setHfkw(placeSearchPinyin.getPinYin());//保存高频字
		}
		placeLandMarkService.saveOrUpdatePlaceLandMark(placeLandMark);
		json.put("success", true);
		json.put("name", placeSearchPinyin.getObjectName());
		json.put("pinYin", placeSearchPinyin.getPinYin());
		getResponse().getWriter().print(json.toString());
	}
 
	public PlaceLandMark getPlaceLandMark() {
		return placeLandMark;
	}

	public void setPlaceLandMark(PlaceLandMark placeLandMark) {
		this.placeLandMark = placeLandMark;
	}

	public void setPlaceLandMarkService(PlaceLandMarkService placeLandMarkService) {
		this.placeLandMarkService = placeLandMarkService;
	}

	public List<PlaceLandMark> getPlaceLandMarkList() {
		return placeLandMarkList;
	}

	public void setPlaceLandMarkList(List<PlaceLandMark> placeLandMarkList) {
		this.placeLandMarkList = placeLandMarkList;
	}

	@Override
	public void setCurrentStage() {
	}

	public long getPlaceLandMarkId() {
		return placeLandMarkId;
	}

	public void setPlaceLandMarkId(long placeLandMarkId) {
		this.placeLandMarkId = placeLandMarkId;
	}

	public String getValid() {
		return valid;
	}

	public void setValid(String valid) {
		this.valid = valid;
	}

	public String getParentPlaceId() {
		return parentPlaceId;
	}

	public void setParentPlaceId(String parentPlaceId) {
		this.parentPlaceId = parentPlaceId;
	}

	public String getParentPlaceName() {
		return parentPlaceName;
	}

	public void setParentPlaceName(String parentPlaceName) {
		this.parentPlaceName = parentPlaceName;
	}

	public PlaceSearchPinyin getPlaceSearchPinyin() {
		return placeSearchPinyin;
	}

	public void setPlaceSearchPinyin(PlaceSearchPinyin placeSearchPinyin) {
		this.placeSearchPinyin = placeSearchPinyin;
	}
	
}