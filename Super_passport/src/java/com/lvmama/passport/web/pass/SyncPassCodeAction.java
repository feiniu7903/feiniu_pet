package com.lvmama.passport.web.pass;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.zul.Label;

import com.lvmama.ZkBaseAction;
import com.lvmama.comm.bee.po.pass.PassCode;
import com.lvmama.comm.bee.service.pass.PassCodeService;

public class SyncPassCodeAction extends ZkBaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8033341535916095589L;
	/**
	 * 查询条件
	 */
	private Map<String, Object> queryOption = new HashMap<String, Object>();
	/**
	 * 通关码列表
	 */
	private List<PassCode> codeList;
	private PassCodeService passCodeService;
	protected Label updateByCodeIdLabel;
	protected Label updateBySerLabel;
	
	/**
	 * 同步二维码数据
	 */
	public void doSync(){
		if(queryOption.get("termContent")==null || "".equals(queryOption.get("termContent"))){
			return;
		}
		if(queryOption.get("validTime")==null || "".equals(queryOption.get("validTime"))){
			return;
		}
		Integer count = passCodeService.getSyncUpdatePassCodeCount(queryOption);
		_totalRowCountLabel.setValue(count.toString());
	}
	
	/**
	 * 根据codeId更新二维码
	 */
	public void updateByCodeId(){
		if(queryOption.get("codeIds")==null || "".equals(queryOption.get("codeIds"))){
			return;
		}
		String codeStr = ((String) queryOption.get("codeIds")).trim();
		List<String> codelist = Arrays.asList(codeStr.split(","));
		queryOption.put("codelist", codelist);
		Integer count = passCodeService.getUpdatePassCodeByCodeId(queryOption);
		updateByCodeIdLabel.setValue(count.toString());
		
	}
	/**
	 * 根据二维码序列号更新二维码
	 */
	public void updateBySer(){
		if(queryOption.get("serialNo")==null || "".equals(queryOption.get("serialNo"))){
			return;
		}
		String serStr = ((String) queryOption.get("serialNo")).trim();
		List<String> serList = Arrays.asList(serStr.split(","));
		queryOption.put("serList", serList);
		Integer count = passCodeService.getUpdatePassCodeBySerId(queryOption);
		updateBySerLabel.setValue(count.toString());
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
