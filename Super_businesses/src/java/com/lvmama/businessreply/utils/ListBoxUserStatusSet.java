/**
 * 
 */
package com.lvmama.businessreply.utils;

import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;

import com.lvmama.businessreply.vo.BusinessConstant;

/**
 * @author liuyi
 *
 */
public class ListBoxUserStatusSet extends Listbox {
	
	
	public ListBoxUserStatusSet()
	{
		super();
		setUserStatusDatas();
	}
	
	
	/**
	 * 设置用户账号状态列表
	 */
	public void setUserStatusDatas() {
		Listitem listItem1 = new Listitem();
		listItem1.setLabel("全部");
		listItem1.setValue(BusinessConstant.USER_STATUS_ALL);
		this.appendChild(listItem1);
		
		Listitem listItem2 = new Listitem();
		listItem2.setLabel("启用");
		listItem2.setValue(BusinessConstant.USER_STATUS_VALID);
		this.appendChild(listItem2);
		
		Listitem listItem3 = new Listitem();
		listItem3.setLabel("停用");
		listItem3.setValue(BusinessConstant.USER_STATUS_INVALID);
		this.appendChild(listItem3);
		
		this.selectItem(listItem1);
	}

}
