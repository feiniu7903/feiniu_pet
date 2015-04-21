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
public class ListboxUserTypeSet extends Listbox {
	
	public ListboxUserTypeSet()
	{
		super();
		setUserTypeDatas();
	}
	
	
	/**
	 * 设置用户类型列表
	 */
	public void setUserTypeDatas() {
		Listitem listItem1 = new Listitem();
		listItem1.setLabel("驴妈妈");
		listItem1.setValue(BusinessConstant.USER_TYPE_LVMAMA);
		this.appendChild(listItem1);
		
		Listitem listItem2 = new Listitem();
		listItem2.setLabel("普通商家");
		listItem2.setValue(BusinessConstant.USER_TYPE_MERCHANT);
		this.appendChild(listItem2);
		
		this.selectItem(listItem1);
	}

}
