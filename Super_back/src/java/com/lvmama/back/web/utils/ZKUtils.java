package com.lvmama.back.web.utils;

import java.util.Map;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.SuspendNotAllowedException;
import org.zkoss.zul.Window;

import com.lvmama.back.utils.StringUtil;
import com.lvmama.comm.vo.Constant;

public class ZKUtils {

	/**
	 * 打开窗口操作
	 * 
	 * @param parent
	 * @param url
	 * @param params
	 * @throws SuspendNotAllowedException
	 * @throws InterruptedException
	 */
	public final static void showWindow(Component parent, String url, Map<String,Object> params) throws SuspendNotAllowedException, InterruptedException {
		Window win = (Window) Executions.createComponents(url, parent, params);
		win.setWidth(Constant.defaultWidth);
		win.setMaximizable(true);
		win.setClosable(true);
		win.doModal();
	}
	
	/**
	 * 可手动调节弹出窗口的大小
	 * @param parent
	 * @param url
	 * @param params
	 * @throws SuspendNotAllowedException
	 * @throws InterruptedException
	 */
	public final static void showChangSizeWindow(Component parent, String url, Map<String ,Object> params,String width,String height) throws SuspendNotAllowedException, InterruptedException {
		Window win = (Window) Executions.createComponents(url, parent, params);
		if(StringUtil.isEmptyString(width)){
			win.setWidth(Constant.defaultWidth);
		}else{
			win.setWidth(width);
		}
		if(!StringUtil.isEmptyString(height)){
			win.setHeight(height);
		}
		win.setMaximizable(true);
		win.setClosable(true);
		win.doModal();
	}

}
