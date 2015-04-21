package com.lvmama.eplace.web.lvmama;

import java.util.ArrayList;
import java.util.List;

import com.lvmama.ZkBaseAction;
import com.lvmama.comm.bee.po.pass.PassPortLog;
import com.lvmama.comm.bee.service.eplace.EPlaceService;

public class ListShowPassPortLogAction extends ZkBaseAction {

	private Long ordItemMetaId;
	private EPlaceService eplaceService;
	List<PassPortLog> passPortLogList;
	
	public void doBefore() throws Exception {
		if(ordItemMetaId>0){
			passPortLogList = new ArrayList<PassPortLog>();
			passPortLogList = eplaceService.queryPassPortLogByOrderItemMetaId(ordItemMetaId);
		}
	}

	public Long getOrdItemMetaId() {
		return ordItemMetaId;
	}

	public void setOrdItemMetaId(Long ordItemMetaId) {
		this.ordItemMetaId = ordItemMetaId;
	}

	public List<PassPortLog> getPassPortLogList() {
		return passPortLogList;
	}

	public void setPassPortLogList(List<PassPortLog> passPortLogList) {
		this.passPortLogList = passPortLogList;
	}

	public void setEplaceService(EPlaceService eplaceService) {
		this.eplaceService = eplaceService;
	}

}
