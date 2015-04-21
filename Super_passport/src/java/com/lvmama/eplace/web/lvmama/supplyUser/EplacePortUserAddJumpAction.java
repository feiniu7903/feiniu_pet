package com.lvmama.eplace.web.lvmama.supplyUser;

import java.util.List;

import com.lvmama.ZkBaseAction;
import com.lvmama.comm.bee.po.pass.PassPortUser;
import com.lvmama.comm.bee.service.eplace.EPlaceService;
import com.lvmama.comm.utils.StringUtil;

/**
 * 通关平台用户增加.
 * 
 * @author huangli
 * 
 */
@SuppressWarnings("unchecked")
public class EplacePortUserAddJumpAction extends ZkBaseAction {
	/**
	 * 通关平台用户操作服务.
	 */
	private EPlaceService eplaceService;
	/**
	 * 供应商查询.
	 */
	private PassPortUser passPortUser=new PassPortUser();
	/**
	 * 用户类型.
	 */
	private String userType = "";
	/**
	 * 供应商编号.
	 */
	private String supplierId;
	/**
	 * 供应商编号.
	 */
	private String eplaceSupplierId;
	/**
	 * 供应商查询.
	 */
	private List<PassPortUser> passPortUserList;

	public void doBefore() {
		if (!StringUtil.isEmptyString(eplaceSupplierId)) {
			param.put("_startRow",0);
			param.put("_endRow",50);
			param.put("eplaceSupplierId",eplaceSupplierId);
			passPortUserList = this.eplaceService.findPassPortUserByMap(param);
		}
	}

	public void setEplaceService(EPlaceService eplaceService) {
		this.eplaceService = eplaceService;
	}

	public PassPortUser getPassPortUser() {
		return passPortUser;
	}

	public void setPassPortUser(PassPortUser passPortUser) {
		this.passPortUser = passPortUser;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}

	public List<PassPortUser> getPassPortUserList() {
		return passPortUserList;
	}

	public void setPassPortUserList(List<PassPortUser> passPortUserList) {
		this.passPortUserList = passPortUserList;
	}
	public String getEplaceSupplierId() {
		return eplaceSupplierId;
	}
	public void setEplaceSupplierId(String eplaceSupplierId) {
		this.eplaceSupplierId = eplaceSupplierId;
	}


}
