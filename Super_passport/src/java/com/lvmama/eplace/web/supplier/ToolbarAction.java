package com.lvmama.eplace.web.supplier;

import java.util.ArrayList;
import java.util.List;

import com.lvmama.ZkBaseAction;
import com.lvmama.comm.bee.po.pass.UserRelateSupplierProduct;
import com.lvmama.comm.bee.service.eplace.EPlaceService;

public class ToolbarAction extends ZkBaseAction{
	private EPlaceService eplaceService;
	private List<UserRelateSupplierProduct> userRelateSupplierList = new ArrayList();
	private String showSuplierTarget;
	public void doBefore() throws Exception {
		Long userId = this.getSessionUser().getPassPortUserId();
		userRelateSupplierList = eplaceService.getSupplierUserListByTargetId(userId);
		showSuplierTarget=this.showUserRelateSupplierTarget();
	}
	
	private String showUserRelateSupplierTarget(){
		String target="";
		for(int i=0;i<this.getUserRelateSupplierList().size();i++){
			UserRelateSupplierProduct u=this.getUserRelateSupplierList().get(i);
			if(u.getSupPerformTarget()!=null){
				target+=u.getSupPerformTarget().getTargetId()+"、";
			}
		}
		return target;
	}
	public List<UserRelateSupplierProduct> getUserRelateSupplierList() {
		return userRelateSupplierList;
	}

	public void setUserRelateSupplierList(
			List<UserRelateSupplierProduct> userRelateSupplierList) {
		this.userRelateSupplierList = userRelateSupplierList;
	}

	public String getShowSuplierTarget() {
		return showSuplierTarget;
	}

	public void setShowSuplierTarget(String showSuplierTarget) {
		this.showSuplierTarget = showSuplierTarget;
	}

	public void setEplaceService(EPlaceService eplaceService) {
		this.eplaceService = eplaceService;
	}
}
