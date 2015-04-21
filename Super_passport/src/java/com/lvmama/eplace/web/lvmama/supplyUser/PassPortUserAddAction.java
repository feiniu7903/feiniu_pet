package com.lvmama.eplace.web.lvmama.supplyUser;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.ZkBaseAction;
import com.lvmama.comm.bee.po.pass.PassPortAuthResources;
import com.lvmama.comm.bee.po.pass.PassPortUser;
import com.lvmama.comm.bee.service.eplace.EPlaceService;
import com.lvmama.comm.vo.PassportConstant;
import com.lvmama.passport.utils.ZkMessage;

/**
 * 通关平台用户增加.
 * 
 * @author huangli
 * 
 */
@SuppressWarnings("unchecked")
public class PassPortUserAddAction extends ZkBaseAction {
	/**
	 * 通关平台用户操作服务.
	 */
	private EPlaceService eplaceService;
	/**
	 * 供应商查询.
	 */
	private PassPortUser passPortUser = new PassPortUser();
	/**
	 * 用户类型.
	 */
	private String userType = "";
	/**
	 * 当前所有的菜单集合.
	 */
	private List<PassPortAuthResources> passPortAuthResourcesList;

	public void doBefore() {
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
		passPortAuthResourcesList = this.eplaceService
				.findPassPortAuthResourcesByMap(param);
	}

	public void addPassPortUser() {
		param.put("userId", passPortUser.getUserId());
//		param.put("userType", userType);
		List list=this.eplaceService.findPassPortUserByMap(param);
		if(list.size()>0){
			ZkMessage.showInfo("该用户名已经被注册使用过，请重新更换用户名!");
			return ;
		}
		PassPortUser user = new PassPortUser();
		user.setCreateDate(new Date());
		user.setEplaceSupplierId(null);
		user.setName(passPortUser.getName());
		user.setPassword(passPortUser.getPassword());
		user.setStatus("Y");
		user.setUserId(passPortUser.getUserId());
		user.setUserType(userType);
		eplaceService.addPassPortUser(user,passPortAuthResourcesList);
		super.refreshParent("search");
		this.closeWindow();
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

	public List<PassPortAuthResources> getPassPortAuthResourcesList() {
		return passPortAuthResourcesList;
	}

	public void setPassPortAuthResourcesList(
			List<PassPortAuthResources> passPortAuthResourcesList) {
		this.passPortAuthResourcesList = passPortAuthResourcesList;
	}

}
