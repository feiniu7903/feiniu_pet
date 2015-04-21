package com.lvmama.back.utils;

import java.util.Iterator;
import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Textbox;

import com.lvmama.comm.pet.po.pub.CodeItem;
import com.lvmama.comm.pet.po.pub.CodeSet;

/**
 * 
 * @author MrZhu
 *
 */
public class CommonSelectAction extends GenericForwardComposer{
	private Textbox refTextboxId;
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		
		/*代码集表*/
		String codeset = (String)comp.getAttribute("codeset");
		
		/*当前显示项的值*/
		String value=(String)comp.getAttribute("value");
		
		refTextboxId = (Textbox)comp.getParent().getParent().getFellow((String)comp.getAttribute("refTextboxId"));
		
		//
		List<CodeItem> list=CodeSet.getInstance().getCodeList(codeset);
		comp.getChildren().clear();
		Listitem listitem = new Listitem();
		listitem.setLabel("-- 请选择  --");
		listitem.setValue("");
		comp.appendChild(listitem);
		for (Iterator<CodeItem> iter = list.iterator(); iter.hasNext();) {
			CodeItem item =  iter.next();
			listitem = new Listitem();
			listitem.setLabel(item.getName());
			listitem.setValue(item.getCode());
			if (item.getCode().equals(value)) {
				listitem.setSelected(true);
			} else {
				listitem.setSelected(false);
			}
			comp.appendChild(listitem);
		}
	}
	
	public void doselect(String value){
		refTextboxId.setValue(value);
		Events.sendEvent(new Event("onChange", refTextboxId));
	}
	
	


}
