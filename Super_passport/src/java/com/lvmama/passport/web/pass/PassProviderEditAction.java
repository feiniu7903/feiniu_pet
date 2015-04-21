package com.lvmama.passport.web.pass;

import java.util.Map;

import org.zkoss.zk.ui.Executions;

import com.lvmama.ZkBaseAction;
import com.lvmama.comm.bee.po.pass.PassProvider;
import com.lvmama.comm.bee.service.pass.PassCodeService;
import com.lvmama.comm.bee.service.pass.PassProviderService;

/**
 * @author ShiHui
 */
public class PassProviderEditAction extends ZkBaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3332025178549077746L;
	/**
	 * 记录是否是新记录
	 */
	boolean newRecord;
	private PassCodeService passCodeService;
	private PassProvider provider;

	/**
	 * 判断是在新增还是修改
	 */
	protected void doBefore() throws Exception {
		Map arg = Executions.getCurrent().getArg();
		if (arg.get("provider") == null) {
			provider = new PassProvider();
			newRecord = true;
		} else {
			provider = (PassProvider) arg.get("provider");
			newRecord = false;
		}
	}

	/**
	 * 新增或修改
	 */
	public void doSave() {
		if (provider.getName() == null || provider.getName().equals("")) {
			this.alert("请输入服务商名称！");
		} else {
			if (newRecord) {
				passCodeService.addPassProvider(provider);
			} else {
				passCodeService.updatePassProvider(provider);
			}
			this.refreshParent("search");
			this.closeWindow();
		}
	}
 
	public PassProvider getProvider() {
		return provider;
	}

	public void setProvider(PassProvider provider) {
		this.provider = provider;
	}

}
