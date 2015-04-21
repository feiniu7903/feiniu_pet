package com.lvmama.back.web.utils;

import java.util.ArrayList;
import java.util.List;

import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Hbox;

import com.lvmama.comm.pet.po.pub.CodeItem;

public class CheckboxList extends Hbox{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<CodeItem> model;
	
	/**
	 * 
	 * @param split 返回结果的分隔符
	 * @return 返回格式：a,b,c，没有选择项时，返回null
	 */
	public String getSelesAsString(String split){
		String ret=null;
		List list=this.getChildren();
		Object item=null;
		for(int i=0;i<list.size();i++){
			item = list.get(i);
			if (item instanceof Checkbox){
				if (((Checkbox)item).isChecked()){
					if (ret==null) ret=(String)((Checkbox)item).getAttribute("code");
					else ret+=split+((Checkbox)item).getAttribute("code");
				}
			}
		}
		return ret;
	}
	
	/**
	 * 返回 List<String>
	 * @return
	 */
	public List getSelesAsList(){
		List ret= new ArrayList();
		List list=this.getChildren();
		Object item=null;
		for(int i=0;i<list.size();i++){
			item = list.get(i);
			if (item instanceof Checkbox){
				if (((Checkbox)item).isChecked()){
					ret.add(((Checkbox)item).getAttribute("code"));
				}
			}
		}
		return ret;
	}
	
	/**
	 * 设置选择状态
	 * @param sels 使用分隔符隔开的字符串,如:a,b,c
	 * @param split 分隔符，如:,
	 */
	public void setSelesAsString(String sels,String split){
		List list=this.getChildren();
		for(int i=0;i<list.size();i++){
			((Checkbox)list.get(i)).setChecked(false);
		}
		//
		if (sels==null||sels.equals("")) return;
		String[] sel = sels.split(split);
		for(int i=0;i<sel.length;i++){
			for(int j=0;j<list.size();j++){
				if (list.get(j) instanceof Checkbox && ((Checkbox)list.get(j)).getAttribute("code").equals(sel[i])){
					((Checkbox)list.get(j)).setChecked(true);
					break;
				}
			}
			
		}
	}
	
	/**
	 * 设置选择状态
	 * @param sels List(String) 为code
	 */
	public void setSelesAsList(List sels){
		List list=this.getChildren();
		for(int i=0;i<list.size();i++){
			((Checkbox)list.get(i)).setChecked(false);
		}
		//
		for(int i=0;i<sels.size();i++){
			for(int j=0;j<list.size();j++){
				if (list.get(j) instanceof Checkbox && ((Checkbox)list.get(j)).getAttribute("code").equals(sels.get(i))){
					((Checkbox)list.get(j)).setChecked(true);
					break;
				}
			}
		}
	}
	
	/**
	 * 设置全部的选择状态
	 * @param status true or false
	 */
	public void setAllCheckedStatus(boolean status){
		List list=this.getChildren();
		for(int i=0;i<list.size();i++){
			((Checkbox)list.get(i)).setChecked(status);
		}
	}

	public void setModel(List<CodeItem> model) {
		this.model = model;
	}
}
