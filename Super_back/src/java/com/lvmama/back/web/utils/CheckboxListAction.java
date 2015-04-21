package com.lvmama.back.web.utils;

import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Listbox;

import com.lvmama.comm.pet.po.pub.CodeItem;

public class CheckboxListAction extends GenericForwardComposer{

	public void doAfterCompose(Component comp) throws Exception {
		Listbox listbox = new Listbox();
		super.doAfterCompose(comp);
		List<CodeItem> list=(List)comp.getAttribute("codeItemList");
		CodeItem codeItem=null;
		for(int i=0;i<list.size();i++){
			codeItem=(CodeItem)list.get(i);
			Checkbox checkbox=new Checkbox();
			checkbox.setLabel(codeItem.getName());
			checkbox.setAttribute("code", codeItem.getCode());
			comp.appendChild(checkbox);
		}
	}

}
