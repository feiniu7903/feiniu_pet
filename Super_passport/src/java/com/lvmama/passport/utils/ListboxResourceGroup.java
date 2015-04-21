package com.lvmama.passport.utils;

import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;

public class ListboxResourceGroup extends Listbox {

	private static final long serialVersionUID = 1L;
	private String codeset;

	public void setCodeset(String codeset) {
		this.codeset = codeset;
		Listitem listitem = new Listitem();
		listitem.setLabel("-- 请选择  --");
		listitem.setValue("");
		this.appendChild(listitem);
		for (PASSPORT_RESOURCE_GROUP item:PASSPORT_RESOURCE_GROUP.values()) {
			listitem = new Listitem();
			listitem.setLabel(item.getCnName());
			listitem.setValue(item.getCode());
			this.appendChild(listitem);
		}
	}

	public String getCodeset() {
		return codeset;
	}

	public enum PASSPORT_RESOURCE_GROUP {
		/** 电子通关 */
		PASSPORT("电子通关"),
		/** 供应商信息互动平台 */
		EPLACE("通关"),
		/** 系统管理 */
		SYSTEM("系统管理");
		
		private String cnName;
		PASSPORT_RESOURCE_GROUP(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(PASSPORT_RESOURCE_GROUP item:PASSPORT_RESOURCE_GROUP.values()){
				if(item.getCode().equals(code))
				{
					return item.getCnName();
				}
			}
			return code;
		}
		public String toString(){
			return this.name();
		}
	}
}
