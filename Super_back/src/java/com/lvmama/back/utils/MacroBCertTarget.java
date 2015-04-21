package com.lvmama.back.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.zk.ui.event.InputEvent;

import com.lvmama.back.web.BaseAction;
import com.lvmama.comm.pet.po.sup.SupBCertificateTarget;
import com.lvmama.comm.pet.service.sup.BCertificateTargetService;

public class MacroBCertTarget extends BaseAction {

	List<SupBCertificateTarget> targetList;
	private BCertificateTargetService bCertificateTargetService;

	public void changeInput(InputEvent event) {
		String name = event.getValue();
		Map<String, String> param = new HashMap<String, String>();
		if (name != null && !"".equals(name)) {
			param.put("name", name);
		}
		param.put("maxResults", "10");
		targetList = this.bCertificateTargetService.findBCertificateTarget(param);
	}

	public List<SupBCertificateTarget> getTargetList() {
		return targetList;
	}

	public void setbCertificateTargetService(
			BCertificateTargetService bCertificateTargetService) {
		this.bCertificateTargetService = bCertificateTargetService;
	}

}
