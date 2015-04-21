package com.lvmama.eplace.web.lvmama.supplyUser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.ZkBaseAction;
import com.lvmama.comm.bee.po.pass.PassPortAuthResources;
import com.lvmama.comm.bee.po.pass.PassPortUser;
import com.lvmama.comm.bee.service.eplace.EPlaceService;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.vo.PassportConstant;

/**
 * 通关平台用户增加.
 * 
 * @author huangli
 * 
 */
@SuppressWarnings("unchecked")
public class PassPortUserEditAction extends ZkBaseAction {
	/**
	 * 通关平台用户操作服务.
	 */
	private EPlaceService eplaceService;
	/**
	 * 供应商查询.
	 */
	private PassPortUser passPortUser;
	/**
	 * 用户类型.
	 */
	private String userType = "";
	/**
	 * 用户编号.
	 */
	private String passPortUserId;
	/**
	 * 当前所有的菜单集合.
	 */
	private  List<PassPortAuthResources>  passPortAuthResourcesList;

	public void doBefore() {
		if (!StringUtil.isEmptyString(passPortUserId)) {
			passPortUser = this.eplaceService
					.getPassPortUserByPk(passPortUserId);
			List<String> strList=new ArrayList<String>();
			Map<String,Object> param=new HashMap<String,Object>();
			if("PASSPORT_USER".equalsIgnoreCase(userType)){
				strList.add(PassportConstant.MEMU_PASSPORT);
				strList.add(PassportConstant.MEMU_SYSTEM);
				param.put("category",strList);
			}else{
				strList.add(PassportConstant.MEMU_EPLACE);
				param.put("category",strList);
			}
			param.put("passPortUserId",passPortUserId);
			passPortAuthResourcesList=this.eplaceService.findPassPortAuthResourcesByMapIsChecked(param);
		}

	}

	public void editPassPortUser() {
		if (!StringUtil.isEmptyString(passPortUserId)) {
			PassPortUser user = this.eplaceService
					.getPassPortUserByPk(passPortUserId);
			user.setName(passPortUser.getName());
			user.setPassword(passPortUser.getPassword());
			user.setUserId(passPortUser.getUserId());
			eplaceService.updatePassPortUser(user, passPortAuthResourcesList);
			super.refreshParent("search");
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

	public String getPassPortUserId() {
		return passPortUserId;
	}

	public void setPassPortUserId(String passPortUserId) {
		this.passPortUserId = passPortUserId;
	}

	public List<PassPortAuthResources> getPassPortAuthResourcesList() {
		return passPortAuthResourcesList;
	}

	public void setPassPortAuthResourcesList(
			List<PassPortAuthResources> passPortAuthResourcesList) {
		this.passPortAuthResourcesList = passPortAuthResourcesList;
	}

}
