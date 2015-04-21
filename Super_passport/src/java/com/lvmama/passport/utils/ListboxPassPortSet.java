package com.lvmama.passport.utils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;

import com.lvmama.comm.bee.po.pass.PassPort;
import com.lvmama.comm.bee.service.pass.PassCodeService;
import com.lvmama.comm.spring.SpringBeanProxy;
/**
 * 通关码查询通关点列表
 * 
 * @author ShiHui
 */
public class ListboxPassPortSet extends Listbox {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6868071040305909226L;
	/**
	 * 通关点
	 */
	private PassCodeService passCodeService = (PassCodeService) SpringBeanProxy.getBean("passCodeService");;
	public ListboxPassPortSet() {
		super();
		this.setPassPortDatas();
	}

	/**
	 * 设置通关点列表值
	 */
	public void setPassPortDatas() {
		Listitem listItem = new Listitem();
		listItem.setLabel("-- 请选择 --");
		listItem.setValue(new Long(-999999));
		this.appendChild(listItem);
		List<PassPort> ports = passCodeService.queryPassPorts(new HashMap<String, Object>());
		for (Iterator<PassPort> iterator = ports.iterator(); iterator.hasNext();) {
			PassPort port = (PassPort) iterator.next();
			Listitem item = new Listitem();
			item.setLabel(port.getName());
			item.setValue(port.getTargetId());
			this.appendChild(item);
		}
	}
}
