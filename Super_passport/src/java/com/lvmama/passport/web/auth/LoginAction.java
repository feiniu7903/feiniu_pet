package com.lvmama.passport.web.auth;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.api.Label;

import com.lvmama.ZkBaseAction;
import com.lvmama.comm.bee.po.pass.EplaceSupplier;
import com.lvmama.comm.bee.po.pass.PassPortUser;
import com.lvmama.comm.bee.service.eplace.EPlaceService;
import com.lvmama.comm.pet.po.sup.SupSupplier;
import com.lvmama.comm.pet.service.sup.SupplierService;
import com.lvmama.comm.vo.PassportConstant;

@SuppressWarnings("unchecked")
public class LoginAction extends ZkBaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7177850457955614877L;
	private EPlaceService eplaceService;
	private SupplierService supplierService;
	private PassPortUser user = new PassPortUser();
	private Label message;
	EplaceSupplier eplaceSupplier;
	public final void doBeforeComposeChildren(Component comp) throws Exception {
		super.doBeforeComposeChildren(comp);
		Components.wireVariables(comp, this);
		comp.setAttribute("loginAction", this, false);
	}

	public void login() {
		param.put("password", user.getPassword());
		param.put("userId", user.getName());
		param.put("_startRow", 0);
		param.put("_endRow", 1);
		PassPortUser passUser = eplaceService.getPassPortUserByUserName(param);
		if (passUser != null ) {
			super.session.setAttribute(PassportConstant.SESSION_USER,passUser);
			if(passUser.getEplaceSupplierId()!=null){
				eplaceSupplier=this.eplaceService.getEplaceSupplierByPk(passUser.getEplaceSupplierId());
				SupSupplier supplier = this.supplierService.getSupplier(eplaceSupplier.getSupplierId());
				if(supplier != null && supplier.isValid()){
					super.session.setAttribute(PassportConstant.SESSION_EPLACE_SUPPLIER,eplaceSupplier);
				}
			}
			Executions.sendRedirect("/index.zul");
		} else {
			message.setValue("用户不存在，或密码错误！");
		}
	}

	public void setEplaceService(EPlaceService eplaceService) {
		this.eplaceService = eplaceService;
	}

	public void setSupplierService(SupplierService supplierService) {
		this.supplierService = supplierService;
	}

	public PassPortUser getUser() {
		return user;
	}

	public void setUser(PassPortUser user) {
		this.user = user;
	}

	public Label getMessage() {
		return message;
	}

	public void setMessage(Label message) {
		this.message = message;
	}

	public EplaceSupplier getEplaceSupplier() {
		return eplaceSupplier;
	}

	public void setEplaceSupplier(EplaceSupplier eplaceSupplier) {
		this.eplaceSupplier = eplaceSupplier;
	}
}
