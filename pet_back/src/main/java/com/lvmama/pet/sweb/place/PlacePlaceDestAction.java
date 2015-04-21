package com.lvmama.pet.sweb.place;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.BackBaseAction;
import com.lvmama.comm.jms.MessageFactory;
import com.lvmama.comm.jms.TopicMessageProducer;
import com.lvmama.comm.pet.po.place.Place;
import com.lvmama.comm.pet.po.place.PlacePlaceDest;
import com.lvmama.comm.pet.po.prod.ProductProductPlace;
import com.lvmama.comm.pet.service.place.PlacePlaceDestService;
import com.lvmama.comm.pet.service.place.PlaceService;
import com.lvmama.comm.pet.service.prod.ProductProductPlaceService;
import com.lvmama.comm.utils.json.JSONResult;
import com.lvmama.comm.vo.Constant;

/**
 * 目的地图片管理
 *
 */
@Results( {
	@Result(name = "placeRelationship", location = "/WEB-INF/pages/back/place/place_relationship.jsp", type = "dispatcher")
	})
public class PlacePlaceDestAction extends BackBaseAction {
	private static final long serialVersionUID = -4480450711433806301L;
	private PlacePlaceDest placePlaceDest = new PlacePlaceDest();
	private List<PlacePlaceDest> parentPlaceList = new ArrayList<PlacePlaceDest>();
	private PlacePlaceDestService placePlaceDestService;
	private PlaceService placeService;
	private String message;
	private String placeName;
	private TopicMessageProducer productMessageProducer;
	private ProductProductPlaceService productProductPlaceService;
	private String stage;

	public TopicMessageProducer getProductMessageProducer() {
		return productMessageProducer;
	}

	public void setProductMessageProducer(
			TopicMessageProducer productMessageProducer) {
		this.productMessageProducer = productMessageProducer;
	}

	@Action("/place/parentPlaceList")
	public String queryParentPlaceList(){
		parentPlaceList = placePlaceDestService.queryParentPlaceList(placePlaceDest.getPlaceId());
		return "placeRelationship";
	}

	@Action("/place/savePlaceRelationShip")
	public void savePlaceRelationship() throws IOException {
		message = "ok";
		Long pid=placePlaceDest.getPlaceId();
		//System.out.println(placePlaceDest.getPlaceId());
		parentPlaceList = placePlaceDestService.queryParentPlaceList(placePlaceDest.getPlaceId());
		if (parentPlaceHasExist(parentPlaceList)) {
			message = "destExist";
		} else if (isExistInLevelTree(placePlaceDest.getPlaceId(), placePlaceDest.getParentPlaceId())) {
			message = "destExistInLevelTree";
		} else if ((placePlaceDest.getPlaceId()).equals(placePlaceDest.getParentPlaceId())) {
			message = "addSelfIsNotAllowed";
		} else {
			placePlaceDestService.saveOrUpdate(placePlaceDest);
		}
		sendMsg(pid);
		
		
		loadParentPlaceJson(message);
	}

	
	
	/**
	 * @deprecated 这段代码真的没有问题吗？
	 * @param placeId
	 */
	private void sendMsg(Long placeId){
		try {
			int count=productProductPlaceService.countProductProductPlaceListByPlaceId(placeId);
			int maxResult=500;
			int startIndex=1;
			if(count>0){
				int totalPage = (count /maxResult);
        		if (count % maxResult >0){
        			totalPage++;
        		}
				for(int i=1;i<=totalPage;i++){
					if(startIndex >= 1){
						startIndex = (i -1)* maxResult +1;
		    		}else{
		    			startIndex=1;
		    		}
					List<ProductProductPlace> productProductPlaceList=productProductPlaceService.getProductProductPlaceListByPlaceId(placeId,startIndex,maxResult);
					if(productProductPlaceList!=null&&productProductPlaceList.size()>0){
						for(ProductProductPlace p:productProductPlaceList){
							productMessageProducer.sendMsg(MessageFactory.newProductPlaceUpdateMessage(p.getProductId()));
						}
					}
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Action("/place/deletePlacePlaceDest")
	public void deletePlacePlaceDest() {
		placePlaceDestService.deletePlacePlaceDest(placePlaceDest);
		sendMsg(placePlaceDest.getPlaceId());
		loadParentPlaceJson("ok");
	}

	@Action("/place/updateIsMaster")
	public void updateIsMaster() {
		try{
	    //去掉原来的master
		PlacePlaceDest masterParam = new PlacePlaceDest();
		masterParam.setIsMaster("N");
		masterParam.setPlaceId(placePlaceDest.getPlaceId());
		placePlaceDestService.updateMaster(masterParam);
        //改为现在master
		PlacePlaceDest record = placePlaceDestService
				.selectByPrimaryKey(placePlaceDest.getPlacePlaceDestId());
		if (record != null) {
			record.setIsMaster("Y");
			placePlaceDestService.updateMaster(record);
		}
		// 更改 目的地 增加父亲id
		if (placeService.queryPlaceByPlaceId(placePlaceDest.getPlaceId()) != null) {
			Place place = new Place();
			place.setPlaceId(placePlaceDest.getPlaceId());
			place.setParentPlaceId(placePlaceDest.getParentPlaceId());
			placeService.savePlace(place);
		}
		}catch(Exception e){
			loadParentPlaceJson("error");
		}
		loadParentPlaceJson("ok");
	}
	
	@Action("/place/ajaxPlaceList")
	public String loadPlaceList() {
		Map<String,Object> param = new HashMap<String,Object> ();
		param.put("name", placeName);
	//	param.put("stage", Constant.STAGE_OF_CITY);
		List<String> stages = new ArrayList<String>();
		stages.add("0");
		stages.add("1");
		param.put("stages", stages);
		param.put("isValid", "Y");
		List<Place> placeList = placeService.queryPlaceListByParam(param);
		
		JSONResult result=new JSONResult();
		try{
			JSONArray array=new JSONArray();
			if(!placeList.isEmpty()){
				for(Place place:placeList){
					JSONObject obj=new JSONObject();
					obj.put("id", place.getPlaceId());
					obj.put("name", place.getName());
					array.add(obj);
				}
			}
			result.put("list", array);
		}catch(Exception ex){
			result.raise(ex);
		}
		result.output(ServletActionContext.getResponse());
		return null;
	}
	
	@Action("/place/loadParentPlaceJson")
	public void loadParentPlaceJson() throws IOException {
		JSONObject json = new JSONObject();
		json.put("success", true);
		json.put("message", "");
		
		parentPlaceList = placePlaceDestService.queryParentPlaceList(placePlaceDest.getPlaceId());
		
		JSONArray array=new JSONArray();
		if(!parentPlaceList.isEmpty()){
			for(PlacePlaceDest place:parentPlaceList){
				JSONObject obj=new JSONObject();
				obj.put("id", place.getPlacePlaceDestId());
				obj.put("name", place.getParentPlaceName());
				obj.put("parentPlaceId", place.getParentPlaceId());
				obj.put("isMaster", place.getIsMaster());
				array.add(obj);
			}
		}
		json.put("placePlaceDests", array);
		
		String text = placePlaceDestService.queryPlaceSuperior(placePlaceDest.getPlaceId(),placeName);
		if (StringUtils.isNotBlank(text)) {
			text = text.replaceAll(",", "<br/>");
		}
		json.put("text", text); 
		json.put("success", true);
		getResponse().getWriter().print(json.toString());
	}
	
	/**
	 * 是否已经是直接父亲了
	 * 
	 * @param placePlaceList
	 * @return
	 */
	private boolean parentPlaceHasExist(List<PlacePlaceDest> placePlaceList) {
		if (placePlaceList != null && !placePlaceList.isEmpty()) {
			for (PlacePlaceDest ppd : placePlaceList) {
				if ((placePlaceDest.getParentPlaceId()).equals(ppd.getParentPlaceId())) {
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * 将所有的直接父亲转化成json对象
	 * 
	 * @param message
	 */
	private void loadParentPlaceJson(String message) {
			parentPlaceList = placePlaceDestService.queryParentPlaceList(placePlaceDest.getPlaceId());
			
			JSONResult result=new JSONResult();
			try{
				JSONArray array=new JSONArray();
				if(!parentPlaceList.isEmpty()){
					for(PlacePlaceDest place:parentPlaceList){
						JSONObject obj=new JSONObject();
						obj.put("id", place.getPlacePlaceDestId());
						obj.put("name", place.getParentPlaceName());
						obj.put("parentPlaceId", place.getParentPlaceId());
						obj.put("isMaster", place.getIsMaster());
						array.add(obj);
					}
				}
				result.put("placePlaceDests", array);
				result.put("message", message);
			}catch(Exception ex){
				result.raise(ex);
			}
			result.outPutNoMessage(ServletActionContext.getResponse());
	}
	
	/**
	 * 是否已经在层级关系中存在
	 * 
	 * @param placeId
	 * @param parentPlaceId
	 * @return
	 */
	private boolean isExistInLevelTree(long placeId, long parentPlaceId) {
		return placePlaceDestService.isExistInParentLevelTree(placeId, parentPlaceId) || placePlaceDestService.isExistInChildrenLevelTree(placeId, parentPlaceId);
	}
	
	public void setPlacePlaceDestService(PlacePlaceDestService placePlaceDestService) {
		this.placePlaceDestService = placePlaceDestService;
	}

	public PlacePlaceDest getPlacePlaceDest() {
		return placePlaceDest;
	}

	public void setPlacePlaceDest(PlacePlaceDest placePlaceDest) {
		this.placePlaceDest = placePlaceDest;
	}

	public List<PlacePlaceDest> getParentPlaceList() {
		return parentPlaceList;
	}

	public String getMessage() {
		return message;
	}

	public String getPlaceName() {
		return placeName;
	}

	public void setPlaceName(String placeName) {
		this.placeName = placeName;
	}

	public void setPlaceService(PlaceService placeService) {
		this.placeService = placeService;
	}

	public ProductProductPlaceService getProductProductPlaceService() {
		return productProductPlaceService;
	}

	public void setProductProductPlaceService(
			ProductProductPlaceService productProductPlaceService) {
		this.productProductPlaceService = productProductPlaceService;
	}
	
	
	
}
