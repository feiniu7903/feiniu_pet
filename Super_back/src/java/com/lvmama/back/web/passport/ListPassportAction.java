package com.lvmama.back.web.passport;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.back.web.BaseAction;
import com.lvmama.comm.bee.po.pass.PassPortCode;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.bee.service.pass.PassCodeService;
import com.lvmama.comm.bee.service.pass.PassPortService;
/**
 * 通关点
 * @author chenlinjun
 *
 */
public class ListPassportAction extends BaseAction{
	private static final Log log = LogFactory.getLog(ListPasscodeAction.class);
	private static final long serialVersionUID = -6624749276774956154L;
	private PassCodeService passCodeService;
	/**
	 * 查询条件
	 */
	private Map<String, Object> queryOption = new HashMap<String, Object>();
	/**
	 * 通关码
	 */
	private List<PassPortCode> passPortList;
	private PassPortCode passPort;
    private Long codeId;
	/**
	 * 查询
	 */
	public void doQuery() {
		this.passPortList = this.passCodeService.searchPassPortByCodeId(codeId);
	}

	public void  doBefore(){
		doQuery();
	}
	public Map<String, Object> getQueryOption() {
		return queryOption;
	}

	public Long getCodeId() {
		return codeId;
	}

	public void setCodeId(Long codeId) {
		this.codeId = codeId;
	}
 
	public List<PassPortCode> getPassPortList() {
		return passPortList;
	}

	public void setPassPortList(List<PassPortCode> passPortList) {
		this.passPortList = passPortList;
	}

	public PassPortCode getPassPort() {
		return passPort;
	}

	public void setPassPort(PassPortCode passPort) {
		this.passPort = passPort;
	}

	public void setQueryOption(Map<String, Object> queryOption) {
		this.queryOption = queryOption;
	}

	public void setPassCodeService(PassCodeService passCodeService) {
		this.passCodeService = passCodeService;
	}
 

}
