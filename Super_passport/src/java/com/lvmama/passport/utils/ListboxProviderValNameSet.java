package com.lvmama.passport.utils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import com.lvmama.comm.spring.SpringBeanProxy;
import com.lvmama.comm.bee.po.pass.PassProvider;
import com.lvmama.comm.bee.service.pass.PassCodeService;
import com.lvmama.comm.bee.service.pass.PassProviderService;

/**
 * 通关点管理服务商列表
 * 
 * @author ShiHui
 */
public class ListboxProviderValNameSet extends Listbox {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1199984288898117015L;
	/**
	 * 服务商
	 */
	private PassCodeService passCodeService = (PassCodeService) SpringBeanProxy.getBean("passCodeService");
	public ListboxProviderValNameSet() {
		super();
		this.setProviderDatas();
	}

	/**
	 * 设置服务商列表值
	 */
	public void setProviderDatas() {
		Listitem listItem = new Listitem();
		listItem.setLabel("-- 请选择 --");
		listItem.setValue(null);
		this.appendChild(listItem);
		List<PassProvider> providers = passCodeService.queryPassProviders(new HashMap<String, Object>());
		for (Iterator<PassProvider> iterator = providers.iterator(); iterator
				.hasNext();) {
			PassProvider provider = (PassProvider) iterator.next();
			Listitem item = new Listitem();
			item.setLabel(provider.getName());
			item.setValue(provider.getName());
			this.appendChild(item);
		}
	}
}
