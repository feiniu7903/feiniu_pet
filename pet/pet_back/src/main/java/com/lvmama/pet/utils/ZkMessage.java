package com.lvmama.pet.utils;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Messagebox;

public class ZkMessage {
	 /**
	  * 显示提示信息
	  * 
	  * @param value
	  */
	 public static void showInfo(String value) {
	   Messagebox.show(value, "提示", Messagebox.OK, Messagebox.INFORMATION);
	 }

	 /**
	  * 显示询问信息
	  * 
	  * @param value
	  * @return
	  */
	 public static int showQuestion(String value) {
	 
	   return Messagebox.show(value, "询问", Messagebox.YES | Messagebox.NO,
	     Messagebox.QUESTION);
	
	 }

	 /**
	  * 显示询问信息
	  * 
	  * @param value
	  * @return
	  */
	 public static void showQuestion(String value,final ZkMsgCallBack yes,final ZkMsgCallBack no) {

		  Messagebox.show(value, "询问信息", Messagebox.YES | Messagebox.NO, Messagebox.ERROR, new EventListener() {
				public void onEvent(Event evt) {
					switch (((Integer) evt.getData()).intValue()) {
					case Messagebox.YES:
						yes.execute();
						break; // the Yes button is pressed
					case Messagebox.NO:
						no.execute();
						break; // the No button is pressed
					}
				}
			});
	   
	 
	 }
	 /**
	  * 显示警告
	  * 
	  * @param value
	  */
	 public static void showWarning(String value) {
	
	   Messagebox.show(value, "警告", Messagebox.OK, Messagebox.EXCLAMATION);
	  
	 }

	 /**
	  * 显示错误
	  * 
	  * @param value
	  */
	 public static void showError(String value) {
	  
	   Messagebox.show(value, "错误", Messagebox.OK, Messagebox.ERROR);
	 
	 }
}
