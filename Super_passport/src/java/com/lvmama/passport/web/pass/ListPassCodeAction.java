package com.lvmama.passport.web.pass;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.ZkBaseAction;
import com.lvmama.comm.bee.po.pass.PassCode;
import com.lvmama.comm.bee.service.pass.PassCodeService;

/**
 * @author ShiHui
 */
public class ListPassCodeAction extends ZkBaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1444420451767194528L;
	/**
	 * 查询条件
	 */
	private Map<String, Object> queryOption = new HashMap<String, Object>();
	/**
	 * 通关码列表
	 */
	private List<PassCode> codeList;
	private PassCodeService passCodeService;
	   private Integer totalRowCount;
	/**
	 * 查询
	 */
	public void doQuery() {
		
		if("".equals(queryOption.get("serialNo"))){
			queryOption.remove("serialNo");
		}
		if("".equals(queryOption.get("status"))){
			queryOption.remove("status");
		}
		if("".equals(queryOption.get("addCode"))){
			queryOption.remove("addCode");
		}
		if("".equals(queryOption.get("mobile"))){
			queryOption.remove("mobile");
		}
	 	totalRowCount=passCodeService.selectPassCodeRowCount(queryOption);
		_totalRowCountLabel.setValue(totalRowCount.toString()); 
		_paging.setTotalSize(totalRowCount.intValue());
		
		queryOption.put("_startRow", _paging.getActivePage()*_paging.getPageSize()+1);
		queryOption.put("_endRow", _paging.getActivePage()*_paging.getPageSize()+_paging.getPageSize());
		
		codeList = passCodeService.queryPassCodes(queryOption);
	}

	public List<PassCode> getCodeList() {
		return codeList;
	}

	public void setPassCodeService(PassCodeService passCodeService) {
		this.passCodeService = passCodeService;
	}

	public Map<String, Object> getQueryOption() {
		return queryOption;
	}

}
