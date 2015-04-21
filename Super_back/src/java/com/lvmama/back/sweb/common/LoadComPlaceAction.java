package com.lvmama.back.sweb.common;

import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

import com.lvmama.back.sweb.BaseAction;
import com.lvmama.comm.pet.po.place.Place;
import com.lvmama.comm.pet.service.place.PlaceService;
@ParentPackage("json-default")
public class LoadComPlaceAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2942389419493785455L;
	private List<Place> comPlaceList;
	private PlaceService placeService;
	private Long id;
	private Place comPlace;
	@Action(value="/common/loadRootDest",results=@Result(type="json",name="root",params={"includeProperties","comPlaceList\\[\\d+\\]\\.name,comPlaceList\\[\\d+\\]\\.placeId"}))	
	public String loadRootDest(){
		this.comPlaceList = placeService.getRootDest();
		return "root";
	}
	
	@Action(value="/common/loadDestByPlaceId",results=@Result(type="json",name="dest",params={"includeProperties","comPlaceList\\[\\d+\\]\\.name,comPlaceList\\[\\d+\\]\\.placeId,comPlaceList\\[\\d+\\]\\.destId"}))
	public String loadDestByPlaceId(){
		if (id!=null &&!"".equals(id)) {
		this.comPlaceList = placeService.selectDestByRootId(id);
		}
		return "dest";
	}
	@Action(value="/common/loadComPlace",results=@Result(type="json",name="complace",params={"includeProperties","comPlace.*"}))
	public String loadComPlace(){
		if (id!=null &&!"".equals(id)) {
		this.comPlace = placeService.queryPlaceByPlaceId(id);
		}
		return "complace";
	}
	
	public List<Place> getComPlaceList() {
		return comPlaceList;
	}
	public void setComPlaceList(List<Place> comPlaceList) {
		this.comPlaceList = comPlaceList;
	}
 
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Place getComPlace() {
		return comPlace;
	}

	public void setComPlace(Place comPlace) {
		this.comPlace = comPlace;
	}
}
