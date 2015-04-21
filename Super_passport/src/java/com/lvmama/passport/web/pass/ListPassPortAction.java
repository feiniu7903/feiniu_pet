package com.lvmama.passport.web.pass;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.ZkBaseAction;
import com.lvmama.comm.bee.po.pass.PassPort;
import com.lvmama.comm.bee.po.pass.PassProvider;
import com.lvmama.comm.bee.service.pass.PassCodeService;

/**
 * @author ShiHui
 */
public class ListPassPortAction extends ZkBaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8094554348248856527L;
	/**
	 * 查询条件
	 */
	private Map<String, Object> queryOption = new HashMap<String, Object>();
	/**
	 * 服务商列表
	 */
	private List<PassProvider> providerList;
	private PassCodeService passCodeService;

	/**
	 * 通关点列表
	 */
	private List<PassPort> passPortList;
	/**
	 * 服务商ID
	 */
	private Long providerId;

	/**
	 * 查询
	 */
	public void doQuery() {
		_paging.setTotalSize(passCodeService.queryPassPortCount(queryOption));
		_totalRowCountLabel.setValue(String.valueOf(_paging.getTotalSize()));
		if(_paging.getTotalSize()>0){
			queryOption.put("_startRow", (_paging.getActivePage())*_paging.getPageSize());
			queryOption.put("_endRow", (_paging.getActivePage()+1)*_paging.getPageSize());
			passPortList=passCodeService.queryPassPorts(queryOption);
		}
	}

	/**
	 * 从服务商管理链接,查询相关通关点信息
	 * 
	 * @param 服务商ID
	 */
	public void setProviderId(Long providerId) {
		queryOption.put("providerId", providerId);
		passPortList=passCodeService.queryPassPorts(queryOption);
	}

	public List<PassPort> getPassPortList() {
		return passPortList;
	}

	public void setPassPortList(List<PassPort> passPortList) {
		this.passPortList = passPortList;
	}

	public List<PassProvider> getProviderList() {
		return providerList;
	}

	public Map<String, Object> getQueryOption() {
		return queryOption;
	}

	public Long getProviderId() {
		return providerId;
	}

}
