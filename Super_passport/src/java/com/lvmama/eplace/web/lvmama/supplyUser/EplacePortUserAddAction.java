package com.lvmama.eplace.web.lvmama.supplyUser;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.ZkBaseAction;
import com.lvmama.comm.bee.po.pass.PassPortAuthResources;
import com.lvmama.comm.bee.po.pass.PassPortUser;
import com.lvmama.comm.bee.po.pass.SupplierRelateProduct;
import com.lvmama.comm.bee.po.pass.UserRelateMenu;
import com.lvmama.comm.bee.service.eplace.EPlaceService;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.vo.PassportConstant;
import com.lvmama.passport.utils.ZkMessage;

/**
 * 通关平台用户增加.
 * 
 * @author huangli
 * 
 */
@SuppressWarnings("unchecked")
public class EplacePortUserAddAction extends ZkBaseAction {
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
	private String userType = "EPLACE_USER";
	/**
	 * 供应商编号.
	 */
	private String supplierId;
	/**
	 * 当前所有的菜单集合.
	 */
	private  List<PassPortAuthResources>  passPortAuthResourcesList;
	/**
	 * 用户下的菜单权限.
	 */
	private List<UserRelateMenu> userRelateMenuList;
	/**
	 * 当前供应商下选择的采购产品.
	 */
	private List<SupplierRelateProduct> supplierRelateProductList;
	/**
	 * E景通供应商编号.
	 */
	private String eplaceSupplierId;

	public void doBefore() {
		if (!StringUtil.isEmptyString(supplierId)) {
			param.put("_startRow",0);
			param.put("_endRow",50);
			param.put("supplierId",supplierId);
			param.put("eplaceSupplierId",eplaceSupplierId);
			List<String> strList=new ArrayList<String>();
			strList.add(PassportConstant.MEMU_EPLACE);
			param.put("category",strList);
			passPortAuthResourcesList=this.eplaceService.findPassPortAuthResourcesByMap(param);
			supplierRelateProductList=this.eplaceService.findSupplierRelateProductByMap(param);
			userRelateMenuList=this.eplaceService.findUserRelateMenuByMap(param);
		}
	}

	public void addPassPortUser(String supplierId) {
		if (!StringUtil.isEmptyString(supplierId)) {
			Map<String,Object> data=new HashMap<String,Object>();
			data.put("userId", passPortUser.getUserId().trim());
			List list=this.eplaceService.findPassPortUserByMap(data);
			if(list.size()>0){
				ZkMessage.showInfo("该用户名已经被注册使用过，请重新更换用户名!");
				return ;
			}
			PassPortUser user = new PassPortUser();
			user.setCreateDate(new Date());
			user.setEplaceSupplierId(Long.valueOf(eplaceSupplierId));
			user.setName(passPortUser.getName());
			user.setPassword(passPortUser.getPassword());
			user.setStatus("Y");
			user.setUserId(passPortUser.getUserId());
			user.setUserType("EPLACE_USER");
			eplaceService.addPassPortUserResources(user, passPortAuthResourcesList, supplierRelateProductList);
			super.refreshParent("btnRefresh");
			super.closeWindow();
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

	public List<PassPortAuthResources> getPassPortAuthResourcesList() {
		return passPortAuthResourcesList;
	}

	public void setPassPortAuthResourcesList(
			List<PassPortAuthResources> passPortAuthResourcesList) {
		this.passPortAuthResourcesList = passPortAuthResourcesList;
	}

	public List<UserRelateMenu> getUserRelateMenuList() {
		return userRelateMenuList;
	}

	public void setUserRelateMenuList(List<UserRelateMenu> userRelateMenuList) {
		this.userRelateMenuList = userRelateMenuList;
	}

	public List<SupplierRelateProduct> getSupplierRelateProductList() {
		return supplierRelateProductList;
	}

	public void setSupplierRelateProductList(
			List<SupplierRelateProduct> supplierRelateProductList) {
		this.supplierRelateProductList = supplierRelateProductList;
	}

	public String getEplaceSupplierId() {
		return eplaceSupplierId;
	}

	public void setEplaceSupplierId(String eplaceSupplierId) {
		this.eplaceSupplierId = eplaceSupplierId;
	}

}
