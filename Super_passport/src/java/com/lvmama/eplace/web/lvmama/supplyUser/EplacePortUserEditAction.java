package com.lvmama.eplace.web.lvmama.supplyUser;

import java.util.ArrayList;
import java.util.List;

import com.lvmama.ZkBaseAction;
import com.lvmama.comm.bee.po.pass.PassPortAuthResources;
import com.lvmama.comm.bee.po.pass.PassPortUser;
import com.lvmama.comm.bee.po.pass.SupplierRelateProduct;
import com.lvmama.comm.bee.po.pass.UserRelateMenu;
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
public class EplacePortUserEditAction extends ZkBaseAction {
	/**
	 * 通关平台用户操作服务.
	 */
	private EPlaceService eplaceService;
	/**
	 * 供应商查询.
	 */
	private PassPortUser passPortUser;
	/**
	 * 供应商编号.
	 */
	private String supplierId;
	/**
	 * 供应商查询.
	 */
	private List<PassPortUser> passPortUserList;
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
	private String passPortUserId;

	public void doBefore() {
		if (!StringUtil.isEmptyString(passPortUserId)) {
			param.put("_startRow",0);
			param.put("_endRow",50);
			param.put("passPortUserId",passPortUserId);
			List<String> strList=new ArrayList<String>();
			strList.add(PassportConstant.MEMU_EPLACE);
			param.put("category",strList);
			passPortUser=this.eplaceService.getPassPortUserByPk(passPortUserId);
			passPortAuthResourcesList=this.eplaceService.findPassPortAuthResourcesByMapIsChecked(param);
			param.put("eplaceSupplierId",supplierId);
			supplierRelateProductList=this.eplaceService.findSupplierRelateProductByMapIsChecked(param);
		}
	}

	public void updatePassPortUser() {
		if (!StringUtil.isEmptyString(supplierId)) {
			PassPortUser user=this.eplaceService.getPassPortUserByPk(passPortUserId);
			user.setName(passPortUser.getName());
			user.setPassword(passPortUser.getPassword());
			user.setUserId(passPortUser.getUserId());
			eplaceService.updatePassPortUserResources(user, passPortAuthResourcesList, supplierRelateProductList);
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

	public List<PassPortAuthResources> getPassPortAuthResourcesList() {
		return passPortAuthResourcesList;
	}

	public String getPassPortUserId() {
		return passPortUserId;
	}

	public void setPassPortUserId(String passPortUserId) {
		this.passPortUserId = passPortUserId;
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
}
