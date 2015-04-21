package com.lvmama.passport.web.pass;

import java.util.Map;

import org.zkoss.zk.ui.Executions;

import com.lvmama.ZkBaseAction;
import com.lvmama.comm.bee.po.pass.PassProduct;
import com.lvmama.comm.bee.service.pass.PassCodeService;
import com.lvmama.comm.bee.service.pass.PassProductService;

public class EditPassProductAction extends ZkBaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private PassCodeService passCodeService;
	private PassProduct passProduct;
	private boolean newRecord;


	@SuppressWarnings("unchecked")
	protected void doBefore() throws Exception {
		Map arg = Executions.getCurrent().getArg();
		passProduct = (PassProduct) arg.get("passProduct");
		newRecord = false;

	}

	public void doSave() {
		if (newRecord) {
			passCodeService.insertPassProduct(passProduct);
		} else {
			passCodeService.updatePassProduct(passProduct);
		}
		this.refreshParent("search");
		this.closeWindow();
	}
 
	public PassProduct getPassProduct() {
		return passProduct;
	}

	public void setPassProduct(PassProduct passProduct) {
		this.passProduct = passProduct;
	}

	public void setPassCodeService(PassCodeService passCodeService) {
		this.passCodeService = passCodeService;
	}
	
}
