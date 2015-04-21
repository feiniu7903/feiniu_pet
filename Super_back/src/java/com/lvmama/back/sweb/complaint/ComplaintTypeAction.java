package com.lvmama.back.sweb.complaint;

import java.util.HashMap;
import java.util.Map;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.BackBaseAction;
import com.lvmama.comm.bee.po.ord.NcComplaintType;
import com.lvmama.comm.bee.service.complaint.NcComplaintTypeService;
import com.lvmama.comm.pet.vo.Page;

/**
 * 投诉类型
 * @author zhushuying
 *
 */
@SuppressWarnings("serial")
@Results({
	@Result(name = "typeList", location = "/WEB-INF/pages/back/complaint/type_list.jsp"),
	@Result(name = "update", location = "/WEB-INF/pages/back/complaint/updateType.jsp"),
	@Result(name = "add", location = "/WEB-INF/pages/back/complaint/addType.jsp")})
public class ComplaintTypeAction extends BackBaseAction{
	private NcComplaintTypeService ncComplaintTypeService;
	
	private String typeId;
	private String typeName;
	private String typeDescription;
	private int sort;
	private Page<NcComplaintType> typePage = new Page<NcComplaintType>();
	
	@Action("/complaintType/queryTypeList")
	public String AllRoleList(){
		typePage.setTotalResultSize(ncComplaintTypeService.getTypeCount());
		typePage.buildUrl(getRequest());
		typePage.setCurrentPage(super.page);
		Map<String, Object> params=new HashMap<String, Object>();
		params.put("start", typePage.getStartRows());
		params.put("end", typePage.getEndRows());
		if(typePage.getTotalResultSize()>0){
			typePage.setItems(ncComplaintTypeService.getAllTypeByPage(params));
		}
		return "typeList";
	}
	@Action("/complaintType/searchType")
	public String updateUsers(){
		Map<String, Object> params=new HashMap<String, Object>();
		if(typeId!=null){
			params.put("typeId", typeId);
			NcComplaintType type=ncComplaintTypeService.selectTypeById(params);
			setRequestAttribute("type", type);
			return "update";
		}else{
			return "add";
		}
	}
	@Action("/complaintType/update_type")
	public void update(){
		int res=0;
		Map<String, Object> params=new HashMap<String, Object>();
		params.put("typeId", typeId);
		NcComplaintType type=ncComplaintTypeService.selectTypeById(params);
		if(type!=null){
			NcComplaintType ct=new NcComplaintType();
			ct.setTypeId(Long.valueOf(typeId));
			ct.setTypeName(typeName);
			ct.setTypeDescription(typeDescription);
			ct.setSort(sort);
			res=ncComplaintTypeService.updateComplaintType(ct);
		}
		if(res>0){
			sendAjaxMsg("SUCCESS");
		}else{
			sendAjaxMsg("FAILED");
		}
	}
	
	@Action("/complaintType/addType")
	public void addType(){
		Long res = 0L;
		Map<String, Object> params=new HashMap<String, Object>();
		params.put("typeName", typeName);
		NcComplaintType type=ncComplaintTypeService.selectTypeById(params);
		if(type!=null){
			sendAjaxMsg("ISEXIST");
		}else{
			NcComplaintType ct = new NcComplaintType();
			ct.setTypeName(typeName);
			ct.setTypeDescription(typeDescription);
			ct.setSort(sort);
			res = ncComplaintTypeService.addComplaintType(ct);
			if (res > 0) {
				sendAjaxMsg("SUCCESS");
			} else {
				sendAjaxMsg("FAILED");
			}
		}
	}

	public NcComplaintTypeService getNcComplaintTypeService() {
		return ncComplaintTypeService;
	}
	public void setNcComplaintTypeService(
			NcComplaintTypeService ncComplaintTypeService) {
		this.ncComplaintTypeService = ncComplaintTypeService;
	}
	public String getTypeId() {
		return typeId;
	}
	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public String getTypeDescription() {
		return typeDescription;
	}
	public void setTypeDescription(String typeDescription) {
		this.typeDescription = typeDescription;
	}
	public int getSort() {
		return sort;
	}
	public void setSort(int sort) {
		this.sort = sort;
	}
	public Page<NcComplaintType> getTypePage() {
		return typePage;
	}
	public void setTypePage(Page<NcComplaintType> typePage) {
		this.typePage = typePage;
	}
}
