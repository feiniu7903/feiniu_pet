package com.lvmama.back.web.supplier;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.InputEvent;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.api.Bandbox;
import org.zkoss.zul.api.Textbox;

import com.lvmama.comm.pet.po.sup.SupSupplier;
import com.lvmama.comm.pet.service.sup.SupplierService;
import com.lvmama.comm.spring.SpringBeanProxy;
import com.lvmama.comm.utils.StringUtil;

public class MacroChooseSupplier extends GenericForwardComposer {

	Bandbox bandsupplier;
	List<SupSupplier> supplierList;
	
	Textbox supId;
	
	private SupplierService supplierService = (SupplierService)SpringBeanProxy.getBean("supplierService");

	public void changeSupplier(InputEvent event) {
		String name = event.getValue();
		Map<String,Object> param = new HashMap<String,Object>();
		if (name!=null && !"".equals(name)) {
			param.put("supplierName", StringUtil.escapeSQLChar(name));
		}
		System.out.println("SupplierName:" + param.get("supplierName"));
		param.put("_endRow", 10);
		supplierList = supplierService.getSupSuppliers(param);
	}
	
	public void doAfterCompose(Component win) throws Exception {
		// super.doAfterCompose(win);
		// win.setVariable(win.getId(), this, true);
		Components.wireVariables(win, this);
		// register onXxx event listeners
		Events.addEventListeners(win, this);
		// auto forward
		Components.addForwards(win, this);
		
		if (supId!=null && supId.getValue()!=null && !supId.getValue().equals("")) {
			Long supIdL=Long.parseLong(supId.getValue());
			System.out.println("in macro : supplierid="+supIdL);
			SupSupplier supplier = supplierService.getSupplier(supIdL);
			bandsupplier.setValue(supplier.getSupplierName());
		}
	}
	
	
	public List<SupSupplier> getSupplierList() {
		return supplierList;
	}
}
