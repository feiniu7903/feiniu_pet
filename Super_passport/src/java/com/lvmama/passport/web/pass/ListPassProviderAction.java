package com.lvmama.passport.web.pass;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.ZkBaseAction;
import com.lvmama.comm.bee.po.pass.PassCode;
import com.lvmama.comm.bee.po.pass.PassProvider;
import com.lvmama.comm.bee.service.pass.PassCodeService;
import com.lvmama.comm.bee.service.pass.PassProviderService;

/**
 * @author ShiHui
 */
public class ListPassProviderAction extends ZkBaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4307116454107461254L;
	private PassCodeService passCodeService;
	/**
	 * 查询条件
	 */
	private Map<String, Object> queryOption = new HashMap<String, Object>();
	/**
	 * 通关点列表
	 */
	private List<PassProvider> passProviderList;

	/**
	 * 查询
	 */
	public void doQuery() {
		passProviderList = passCodeService.queryPassProviders(queryOption);
	}

	public Map<String, Object> getQueryOption() {
		return queryOption;
	}

	public List<PassProvider> getPassProviderList() {
		return passProviderList;
	}
 
}
