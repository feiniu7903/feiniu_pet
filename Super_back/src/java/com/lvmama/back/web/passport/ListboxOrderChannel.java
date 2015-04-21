package com.lvmama.back.web.passport;

import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;

import com.lvmama.comm.vo.Constant;

public class ListboxOrderChannel extends Listbox {
	private static final long serialVersionUID = -1199984288898117015L;
	public ListboxOrderChannel() {
		super();
		this.setOrderChannelDatas();
	}

	/**
	 * 设置订单渠道的值
	 */
	public void setOrderChannelDatas() {
		Listitem listItem = new Listitem();
		listItem.setLabel("-- 请选择 --");
		listItem.setValue(null);
		this.appendChild(listItem);
		for(Constant.CHANNEL channel : Constant.CHANNEL.values()){
			Listitem item = new Listitem();
			item.setLabel(channel.getCnName());
			item.setValue(channel.getCode());
			this.appendChild(item);
		}
	}
}
