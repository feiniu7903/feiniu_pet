package com.lvmama.passport.utils;

import java.util.List;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import com.lvmama.comm.spring.SpringBeanProxy;
import com.lvmama.comm.bee.po.pass.PassPortUser;
import com.lvmama.comm.bee.po.pass.UserRelateSupplierProduct;
import com.lvmama.comm.bee.service.eplace.EPlaceService;
import com.lvmama.comm.spring.SpringBeanProxy;
import com.lvmama.comm.vo.PassportConstant;

/**
 * 供应商产品列表
 * 
 * @author chenlinjun
 * 
 */
public class ListboxSupplierProductSet extends Listbox {

	private static final long serialVersionUID = 6868071040305909226L;
	private EPlaceService eplaceService = (EPlaceService) SpringBeanProxy.getBean("eplaceService");
	public ListboxSupplierProductSet() {
		super();
		this.setData();
	}

	public void setData() {
		Listitem listItem = new Listitem();
		listItem.setLabel("-- 请选择 --");
		listItem.setValue(new Long(0));
		listItem.setSelected(true);
		this.appendChild(listItem);
		Session session = Executions.getCurrent().getSession();
		PassPortUser user = (PassPortUser) session.getAttribute(PassportConstant.SESSION_USER);
		List<UserRelateSupplierProduct> list = eplaceService.getSupplierUserProductByUserId(user
				.getPassPortUserId());
		for (UserRelateSupplierProduct supplierRelateProduct : list) {
			Listitem item = new Listitem();
			item.setLabel(supplierRelateProduct.getMetaProductBranch().getBranchName());
			item.setValue(supplierRelateProduct.getMetaProductBranchId());
			this.appendChild(item);
		}
		Listitem item = new Listitem();
		item.setLabel("全部");
		item.setValue(new Long(-1));
		this.appendChild(item);
	}
}
