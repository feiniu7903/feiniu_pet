package com.lvmama.passport.web.passportauth;

import java.util.Map;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Textbox;

import com.lvmama.ZkBaseAction;
import com.lvmama.comm.bee.po.pass.PassPortAuthResources;
import com.lvmama.comm.bee.service.eplace.EPlaceService;

/**
 * 编辑资源地址
 * 
 * @author chenlinjun
 * 
 */
public class ResourceEditAction extends ZkBaseAction {

	private static final long serialVersionUID = 7500219454397446835L;
	private Textbox resourceId;

	private EPlaceService eplaceService;
	private PassPortAuthResources resource;
	private boolean newRecord;


	protected void doBefore() throws Exception {
		Map arg = Executions.getCurrent().getArg();
		if (arg.get("resource") == null) {
			resource = new PassPortAuthResources();
			newRecord = true;
		} else {
			resource = (PassPortAuthResources) arg.get("resource");
			newRecord = false;
		}

	}

	public void doSave() {
		if (newRecord) {
			eplaceService.insertResource(resource);
		} else {
			eplaceService.updateResource(resource);
		}
		this.refreshParent("search");
		this.closeWindow();
	}

	public PassPortAuthResources getResource() {
		return resource;
	}

	public void setResource(PassPortAuthResources resource) {
		this.resource = resource;
	}

	public Textbox getResourceId() {
		return resourceId;
	}

	public void setResourceId(Textbox resourceId) {
		this.resourceId = resourceId;
	}

	public void setEplaceService(EPlaceService eplaceService) {
		this.eplaceService = eplaceService;
	}

}
