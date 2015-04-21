/**
 * 
 */
package com.lvmama.comm.utils;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.lang.ArrayUtils;

import com.lvmama.comm.pet.po.pub.CodeItem;

/**
 * 
 * @author yangbin
 *
 */
public class CodeItemSelectedAdapter {

	private List<CodeItem> itemList;
	
	public CodeItemSelectedAdapter(List<CodeItem> itemList) {
		super();
		this.itemList = itemList;
	}
	
	public List<CodeItem> handle(String...selected){
		if(!ArrayUtils.isEmpty(selected)){
			for(final String v:selected){
				CodeItem ci=(CodeItem)CollectionUtils.find(itemList, new Predicate() {
					
					@Override
					public boolean evaluate(Object arg0) {
						return ((CodeItem)arg0).getCode().equals(v);
					}
				});
				
				if(ci!=null){
					ci.setChecked("true");
				}
			}
		}
		
		return itemList;
	}
	
}
