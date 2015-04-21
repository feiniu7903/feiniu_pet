package com.lvmama.passport.web.pass;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.ZkBaseAction;
import com.lvmama.comm.bee.po.pass.PassDevice;
import com.lvmama.comm.bee.po.pass.PassProvider;
import com.lvmama.comm.bee.service.pass.PassCodeService;
import com.lvmama.comm.bee.service.pass.PassDeviceService;
import com.lvmama.passport.utils.ZkMessage;
import com.lvmama.passport.utils.ZkMsgCallBack;

/**
 * 设备编号管理列表
 * 
 * @author chenlinjun
 * 
 */
@SuppressWarnings("unused")
public class ListPassDeviceAction extends ZkBaseAction {
	private static final long serialVersionUID = -1942339139305597649L;
	/**
	 * 查询条件
	 */
	private Map<String, Object> queryOption = new HashMap<String, Object>();
	/**
	 * 服务商列表
	 */
	private List<PassProvider> providerList;
	private PassDevice passDevice;
	private PassCodeService passCodeService;
	private List<PassDevice> passDeviceList;
	/**
	 * 服务商ID
	 */
	private Long providerId;
	/**
	 * 查询
	 */
	public void doQuery() {
		_paging.setTotalSize(passCodeService.searchPassDeviceCount(queryOption));
		_totalRowCountLabel.setValue(String.valueOf(_paging.getTotalSize()));
		if(_paging.getTotalSize()>0){
			queryOption.put("_startRow", (_paging.getActivePage())*_paging.getPageSize());
			queryOption.put("_endRow",(_paging.getActivePage()+1)*_paging.getPageSize());			
			passDeviceList = passCodeService.searchPassDevice(queryOption);
		}
	}
	
	/**
	 * 从服务商管理链接,查询相关通关点信息
	 * 
	 * @param 服务商ID
	 */
	public void setProviderId(Long providerId) {
		queryOption.put("providerId", providerId);
		doQuery();
	}
	
	public void delDevice(final Long deviceId){
		ZkMessage.showQuestion("您确认要删除此设备吗", new ZkMsgCallBack() {
			public void execute() {
				passCodeService.delDeviceByDeviceId(deviceId);
				refreshComponent("search");
			}
		}, new ZkMsgCallBack() {
			public void execute() {

			}
		});
	}
	public Map<String, Object> getQueryOption() {
		return queryOption;
	}

	public void setQueryOption(Map<String, Object> queryOption) {
		this.queryOption = queryOption;
	}

	public PassDevice getPassDevice() {
		return passDevice;
	}

	public void setPassDevice(PassDevice passDevice) {
		this.passDevice = passDevice;
	}

	public List<PassDevice> getPassDeviceList() {
		return passDeviceList;
	}

	public void setPassDeviceList(List<PassDevice> passDeviceList) {
		this.passDeviceList = passDeviceList;
	}


	public List<PassProvider> getProviderList() {
		return providerList;
	}


	public void setProviderList(List<PassProvider> providerList) {
		this.providerList = providerList;
	}


	public Long getProviderId() {
		return providerId;
	}

	public void setPassCodeService(PassCodeService passCodeService) {
		this.passCodeService = passCodeService;
	}

}
