package com.lvmama.passport.web.pass;

import java.util.Map;

import org.zkoss.zk.ui.Executions;

import com.lvmama.ZkBaseAction;
import com.lvmama.comm.bee.po.pass.PassPort;
import com.lvmama.comm.bee.service.pass.PassCodeService;

/**
 * @author ShiHui
 */
public class PassPortEditAction extends ZkBaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2651356448131564536L;
	/**
	 * 记录是否是新记录
	 */
	boolean newRecord;
	private PassCodeService passCodeService;
	private PassPort passPort;

	/**
	 * 判断是在新增还是修改
	 */
	protected void doBefore() throws Exception {
		Map arg = Executions.getCurrent().getArg();
		if (arg.get("passPort") == null) {
			passPort = new PassPort();
			newRecord = true;
		} else {
			passPort = (PassPort) arg.get("passPort");
			newRecord = false;
		}
	}

	/**
	 * 新增或修改
	 */
	public void doSave() {
		if (newRecord) {
			passCodeService.addPassPort(passPort);
		} else {
			passCodeService.updatePassPort(passPort);
		}
		this.refreshParent("search");
		this.closeWindow();
	}

	public void setPassPort(PassPort passPort) {
		this.passPort = passPort;
	}

	public PassPort getPassPort() {
		return passPort;
	}
}
