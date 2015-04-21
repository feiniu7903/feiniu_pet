package com.lvmama.businessreply.utils;

import java.util.Map;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.SuspendNotAllowedException;
import org.zkoss.zul.Window;
import com.lvmama.businessreply.vo.BusinessConstant;

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
	public final static void showWindow(Component parent, String url, Map params) throws SuspendNotAllowedException, InterruptedException {
		Window win = (Window) Executions.createComponents(url, parent, params);
		win.setWidth(BusinessConstant.defaultWidth);
		win.setMaximizable(true);
		win.setClosable(true);
		win.doModal();
	}

}
