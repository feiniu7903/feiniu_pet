package com.lvmama.passport.web.passportauth;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.ZkBaseAction;
import com.lvmama.comm.bee.po.pass.PassPortAuthResources;
import com.lvmama.comm.bee.service.eplace.EPlaceService;

/**
 * 通关资源列表
 * 
 * @author chenlinjun
 * 
 */
public class ListResourceAction extends ZkBaseAction {

	private static final long serialVersionUID = 1574051072634300259L;

	private EPlaceService eplaceService;

	/**
	 * 查询条件
	 */
	private Map<String, Object> queryOption = new HashMap<String, Object>();
	/**
	 * 资源列表
	 */
	private List<PassPortAuthResources> resourceList;

	/**
	 * 查询
	 */
	public void doQuery() {
		List<String> list=new ArrayList<String>();
		String category=(String)queryOption.get("category");
		if(category!=null&&!"".equalsIgnoreCase(category)){
			list.add(category);
			queryOption.put("category", list);
		}
		resourceList = eplaceService.selectByParms(queryOption);
	}

	public void doDelete(Long resourceId) {
		eplaceService.deleteResource(resourceId);
		super.refreshComponent("search");
	}

	public List<PassPortAuthResources> getResourceList() {
		return resourceList;
	}

	public void setResourceList(List<PassPortAuthResources> resourceList) {
		this.resourceList = resourceList;
	}

	public Map<String, Object> getQueryOption() {
		return queryOption;
	}

	public void setEplaceService(EPlaceService eplaceService) {
		this.eplaceService = eplaceService;
	}
}
