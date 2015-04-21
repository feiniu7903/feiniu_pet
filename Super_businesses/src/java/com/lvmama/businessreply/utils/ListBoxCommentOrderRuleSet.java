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
public class ListBoxCommentOrderRuleSet extends Listbox {
	
	public ListBoxCommentOrderRuleSet()
	{
		super();
		setCommentOrderRuleDatas();
	}
	
	
	
	/**
	 * 设置评论排序列表
	 */
	public void setCommentOrderRuleDatas() {
		Listitem listItem1 = new Listitem();
		listItem1.setLabel("1 时间新>>旧");
		listItem1.setValue(BusinessConstant.USER_STATUS_ALL);
		this.appendChild(listItem1);
		
		Listitem listItem2 = new Listitem();
		listItem2.setLabel("2 时间旧>>新");
		listItem2.setValue(BusinessConstant.USER_STATUS_VALID);
		this.appendChild(listItem2);
		
		Listitem listItem3 = new Listitem();
		listItem3.setLabel("3 回复多>>少");
		listItem3.setValue(BusinessConstant.USER_STATUS_INVALID);
		this.appendChild(listItem3);
		
		
		Listitem listItem4 = new Listitem();
		listItem4.setLabel("4 回复少>>多");
		listItem4.setValue(BusinessConstant.USER_STATUS_INVALID);
		this.appendChild(listItem4);
		
		this.selectItem(listItem1);
	}

}
