package com.lvmama.pet.sweb.place;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.pet.service.pub.ComSearchInfoUpdateService;

/**
 * 目的地管理
 * @author duanshuailiang
 *
 */
@Results( {
	@Result(name = "placeList", location = "/WEB-INF/pages/back/place/dest/place_list.jsp"),
	@Result(name = "placeEdit", location = "/WEB-INF/pages/back/place/dest/place_edit.jsp"),
	@Result(name = "placeAdd", location = "/WEB-INF/pages/back/place/dest/place_add.jsp")
})
public class PlaceAction  extends AbstractPlaceAction {
	private static final long serialVersionUID = 1L;
	
	@Action("/place/placeList")
	public String execute() throws Exception {
		list();
		return "placeList";
	}
	@Action("/place/placeAdd")
	public String placeadd() throws Exception {
		return "placeAdd";
	}		
//	@Action("/place/placeAdd")
//	public String placeadd() throws Exception {
//		add();
//		return "placeAdd";
//	}	
	@Action("/place/placeEdit")
	public String placeEdit() throws Exception {
		edit();
		return "placeEdit";
	}

	@Action("/place/doPlaceSave")
	public String doSave() throws Exception {
		try{
			save();
			this.setMsg("添加成功!");
		}catch(Exception ex){
			ex.printStackTrace();
			this.setMsg("添加失败!");
		}
		return "placeAdd";
	}
	@Action("/place/doPlaceUpdate")
	public String doUpdate() throws Exception {
		try{
			update();
			this.outputToClient("success");
		}catch(Exception ex){
			ex.printStackTrace();
			this.outputToClient("error");
		}
		return null;
	}
	@Override
	public void setCurrentStage() {
		this.setStage("1");
	}
	public void setComSearchInfoUpdateService(ComSearchInfoUpdateService comSearchInfoUpdateService) {
		this.comSearchInfoUpdateService = comSearchInfoUpdateService;
	}
}

