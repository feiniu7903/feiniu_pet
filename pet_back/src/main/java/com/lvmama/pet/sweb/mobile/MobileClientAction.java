package com.lvmama.pet.sweb.mobile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.BackBaseAction;
import com.lvmama.comm.pet.po.mobile.MobileVersion;
import com.lvmama.comm.pet.service.mobile.MobileClientService;
import com.lvmama.comm.pet.vo.Page;

/**
 * 驴途 v3 公共action.
 * @author qinzubo
 *
 */
@Results( {
	@Result(name = "success", location = "/WEB-INF/pages/back/mobile/mobile_client_index.jsp"),
	@Result(name = "mobileVersionList", location = "/WEB-INF/pages/back/mobile/mobile_client_version_list.jsp")
})
public class MobileClientAction extends BackBaseAction {
	private static final long serialVersionUID = 1L;

	/**
	 * 移动客户端服务. 
	 */
	MobileClientService mobileClientService;
	
	/**
	 * 分页信息 . 
	 */
	protected Page pagination; 
	
	private MobileVersion mobileVersion;
	
	@Override
	@Action("/mobile/mobileClient")
	public String execute() throws Exception {
		return super.execute();
	}
	
	/**
	 * 查询版本信息. 
	 * @return string 
	 */
	public String getMoibleVersionList() {
		try{
			// 分页相关  
			pagination=initPage();
			pagination.setPageSize(15);
			Map<String, Object> param = new HashMap<String,Object>();
			
			// 查询 
			copyMobileVersionParams2Map(mobileVersion,param);
			pagination.setTotalResultSize(mobileClientService.countMobileVersionList(param));
			if(pagination.getTotalResultSize()>0){
				param.put("startRows", pagination.getStartRows());
				param.put("endRows", pagination.getEndRows());
				param.put("isPaging", "true"); // 分页 
				List<MobileVersion> mlist = mobileClientService.queryMobileVersionList(param);
				pagination.setItems(mlist);
			}
			pagination.buildUrl(getRequest());
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return "mobileVersionList";
	}
	
	/**
	 * 保存version. 
	 */
	public void saveMobileVersion() {
		String json = "{\"flag\":\"false\"}";
		String msg = "";
		try {
			if(null == mobileVersion) {
				super.sendAjaxMsg(json);
				return;
			}
			// 新增
			if(null == mobileVersion.getId()) {
				msg = "新增";
				mobileClientService.insertMobileVersion(mobileVersion);
			} else { // 修改.
				msg = "修改";
				mobileClientService.updateMobileVersion(mobileVersion);
			}
			json = "{\"flag\":\"true\",\"msg\":\""+msg+"成功!\"}";
		}catch(Exception e ) {
			e.printStackTrace();
			json = "{\"flag\":\"true\",\"msg\":\""+msg+"失败!\"}";
		}
		super.sendAjaxMsg(json);
	}
	
	/**
	 * 更改审核状态. 
	 */
	public void updateAuditing() {
		String json = "{\"flag\":\"false\"}";
		try{
			if(null == mobileVersion || null == mobileVersion.getId() || StringUtils.isEmpty(mobileVersion.getIsAuditing())) {
				super.sendAjaxMsg(json);
			}
			boolean b = mobileClientService.updateAuditing(mobileVersion.getIsAuditing(), mobileVersion.getId());
			if(b) {
				json = "{\"flag\":\"true\",\"msg\":\"状态修改成功!\"}";
			} else {
				json = "{\"flag\":\"true\",\"msg\":\"状态修改失败!\"}";
			}
			
		}catch(Exception e){
			e.printStackTrace();
			json = "{\"flag\":\"true\",\"msg\":\"状态修改失败!\"}";
		}
		super.sendAjaxMsg(json);
	}
	
	/**
	 * 删除version 
	 */
	public void delMobileVersion() {
		String json = "{\"flag\":\"false\"}";
		try{
			if(null == mobileVersion || null == mobileVersion.getId()) {
				super.sendAjaxMsg(json);
			}
			boolean b = mobileClientService.deleteMobileVersionById(mobileVersion.getId());
			if(b) {
				json = "{\"flag\":\"true\",\"msg\":\"删除成功!\"}";
			} else {
				json = "{\"flag\":\"true\",\"msg\":\"删除失败!\"}";
			}
			
		}catch(Exception e){
			e.printStackTrace();
			json = "{\"flag\":\"true\",\"msg\":\"删除失败!\"}";
		}
		super.sendAjaxMsg(json);
	}
	
	public void copyMobileVersionParams2Map(MobileVersion mv,Map<String,Object> params) {
		if(null == mv) {
			return ;
		}
		
		if(!StringUtils.isEmpty(mv.getTitle())) {
			params.put("title", mv.getTitle());
		}
		if(!StringUtils.isEmpty(mv.getIsAuditing())) {
			params.put("isAuditing", mv.getIsAuditing());
		}
		if(!StringUtils.isEmpty(mv.getPlatform())) {
			params.put("platform", mv.getPlatform());
		}
		if(!StringUtils.isEmpty(mv.getFirstChannel())) {
			params.put("firstChannel", mv.getFirstChannel());
		}
		if(!StringUtils.isEmpty(mv.getSeconedChannel())) {
			params.put("seconedChannel", mv.getSeconedChannel());
		}
	}
	public void setMobileClientService(MobileClientService mobileClientService) {
		this.mobileClientService = mobileClientService;
	}
	
	public Page getPagination() {
		return pagination;
	}

	public void setPagination(Page pagination) {
		this.pagination = pagination;
	}
	
	public MobileVersion getMobileVersion() {
		return mobileVersion;
	}

	public void setMobileVersion(MobileVersion mobileVersion) {
		this.mobileVersion = mobileVersion;
	}

	
}
