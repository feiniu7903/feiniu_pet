package com.lvmama.comm.pet.po.pub;

import java.io.Serializable;


@SuppressWarnings("serial")
public class CodeItem implements Serializable{
	/**
	 * 代码
	 */
	private String code;
	/**
	 * 名称
	 */
	private String name;
	
	private String checked;
	
	/**
	 * 扩展属性1
	 * @return
	 */
	private String attr01;
	private String attr02;
	private String attr03;
	private String attr04;
	private String attr05;
	
	public CodeItem() {
	}
	
	public CodeItem(String code, String name) {
		this.code = code;
		this.name = name;
	}
	public CodeItem(String code, String name,String checked) {
		this.code = code;
		this.name = name;
	}
	/**
	 * 
	 * @return
	 */
	public String getAttr01() {
		return attr01;
	}
	public void setAttr01(String attr01) {
		this.attr01 = attr01;
	}
	public String getAttr02() {
		return attr02;
	}
	public void setAttr02(String attr02) {
		this.attr02 = attr02;
	}
	public String getAttr03() {
		return attr03;
	}
	public void setAttr03(String attr03) {
		this.attr03 = attr03;
	}
	public String getAttr04() {
		return attr04;
	}
	public void setAttr04(String attr04) {
		this.attr04 = attr04;
	}
	public String getAttr05() {
		return attr05;
	}
	public void setAttr05(String attr05) {
		this.attr05 = attr05;
	}
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getChecked() {
		return checked;
	}
	public void setChecked(String checked) {
		this.checked = checked;
	}
	public boolean isChecked() {
		return "true".equalsIgnoreCase(checked);
	}
	public boolean equals(Object obj) {
		if (obj instanceof CodeItem) {
			CodeItem cc = (CodeItem)obj;
			if (code==null) {
				return cc.getCode()==null;
			} else {
				return code == cc.getCode() || code.equals(cc.getCode());
			}
		}else{
			return false;
		}
	}

	@Override
	public int hashCode() {
		if (code!=null)
			return code.hashCode();
		else
			return 0;
	}

	@Override
	public String toString() {
		if (code!=null)
			return "CodeItem_"+code;
		else
			return "CodeItem_null";
	}
}
