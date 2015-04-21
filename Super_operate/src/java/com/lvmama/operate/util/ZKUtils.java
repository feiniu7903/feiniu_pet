package com.lvmama.operate.util;

import java.util.Map;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.SuspendNotAllowedException;
import org.zkoss.zul.Window;

import com.lvmama.comm.utils.StringUtil;


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
          win.setWidth(com.lvmama.operate.util.Constant.DEFAULT_WIDTH);
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
               win.setWidth(com.lvmama.operate.util.Constant.DEFAULT_WIDTH);
          }else{
               win.setWidth(width);
          }
          if(StringUtil.isEmptyString(height)){
               win.setWidth(height);
          }
          win.setHeight(height);
          win.setMaximizable(true);
          win.setClosable(true);
          win.doModal();
     }

}
