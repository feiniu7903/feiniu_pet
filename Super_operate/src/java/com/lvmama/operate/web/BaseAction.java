package com.lvmama.operate.web;

import java.io.File;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jxls.transformer.XLSTransformer;

import org.apache.commons.beanutils.BeanUtils;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Label;
import org.zkoss.zul.Paging;
import org.zkoss.zul.api.Button;
import com.lvmama.comm.pet.po.perm.PermUser;
import com.lvmama.comm.spring.SpringBeanProxy;
import com.lvmama.comm.utils.ResourceUtil;
import com.lvmama.comm.utils.ServletUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.operate.util.ZKUtils;
import com.lvmama.operate.util.ZkMessage;

public class BaseAction extends GenericForwardComposer {
     
     private Component component;
     /**
      * 
      */
     private static final long serialVersionUID = -3357432598207266653L;

     /**
      * 分页Label
      */
     protected Label _totalRowCountLabel;
     /**
      * 分页组件
      */
     protected Paging _paging;
     
     public final void doBeforeComposeChildren(Component comp) throws Exception {
          this.component = comp;
          super.doBeforeComposeChildren(comp);
          Components.wireVariables(comp, this);
          fillBean(this.getClass());
          this.session = Executions.getCurrent().getSession();
          BeanUtils.copyProperties(this, Executions.getCurrent().getParameterMap());
          BeanUtils.copyProperties(this, Executions.getCurrent().getArg());
          if (getComponetName()!=null) {
               comp.setAttribute(getComponetName(), this, false);
          }
          doBefore();
     }
     
     public final void doAfterCompose(Component comp) throws Exception {
          super.doAfterCompose(comp);
          doAfter();
     }
     
     public void showWindow(String uri, Map<String,Object> params) throws Exception {
          ZKUtils.showWindow(component, uri, params);
     }

     public void refreshComponent(String btnName) {
          try{
               Button b = (Button) component.getFellow(btnName);
               Events.sendEvent(new Event("onClick",b));
          }catch(Exception e) {
               e.printStackTrace();
               ZkMessage.showError("refresh "+btnName+" failed");
          }
     }
     
     /**
      * 主要是针对tabpanel里的组件调用
      * @param component
      * @param btnName
      */
     public void refreshParent(Component component,String btnName){
          try{
               Component c = component.getParent();
               Button b = (Button) c.getFellow(btnName);
               Events.sendEvent(new Event("onClick",b));
          }catch(Exception e) {
               e.printStackTrace();
               ZkMessage.showError("refresh "+btnName+" failed");
          }
     }
     
     public void refreshParent(String btnName){
          try{
               Component c = component.getParent();
               Button b = (Button) c.getFellow(btnName);
               Events.sendEvent(new Event("onClick",b));
          }catch(Exception e) {
               e.printStackTrace();
               ZkMessage.showError("refresh "+btnName+" failed");
          }
     }
     
     public void closeWindow() {
          component.detach();
     }
     
     public Component getParent() {
          return component.getParent();
     }
     
     /**
      * 获取当前登录用户
      * @return
      */
     public PermUser getSessionUser(){
    	HttpServletRequest request  = (HttpServletRequest)Executions.getCurrent().getNativeRequest(); 
 		HttpServletResponse response = (HttpServletResponse)Executions.getCurrent().getNativeResponse();
 		return (PermUser)ServletUtil.getSession(request, response, com.lvmama.comm.vo.Constant.SESSION_BACK_USER);
     }
     
     /**
      * 获得session中的UserName
      * 
      * @return
      */
     public String getSessionUserName() {
          return getSessionUser().getUserName();
     }
     
     /**
      * 获得session中的DepartmentId
      * 
      * @return
      */
     public Long getSessionUserDepartmentId() {
          return getSessionUser().getDepartmentId();
     }
     
     /**
      * 获得session中的User RealName
      * 
      * @return
      */
     public String getSessionUserRealName() {
          return getSessionUser().getRealName();
     }
     
     public Component getComponent() {
          return component;
     }
     
     
     
     /**
      *  从界面提取数据，并填充到Bean或Map
      *  
      * @param parComp 界面部件
      * 
      * @param formData 填充的bean或map
      */
     @SuppressWarnings("unchecked")
     private void fillBean(Class myClass){
          if (myClass.getName().endsWith(".BaseAction")) {
               return;
          }else {
               try {
                    Field[] fields = myClass.getDeclaredFields();
                    for (int i = 0; i < fields.length; i++) {
                         String name = fields[i].getName();
                         if (name.endsWith("Service") || name.endsWith("Producer")) {
                              Object obj = SpringBeanProxy.getBean(name);
                              AccessibleObject.setAccessible(fields,   true);
                              fields[i].set(this, obj);
                         }
                    }
               } catch (Exception e) {
                    e.printStackTrace();
               }
               fillBean(myClass.getSuperclass());
          }
     }
     
     protected void doBefore() throws Exception {};
     
     protected void doAfter() throws Exception {};

     protected String getComponetName() {
          return "saction";
     }     
     protected Map<String,Object> initialPageInfoByMap(Long totalRowCount,Map<String,Object> map){
          _totalRowCountLabel.setValue(totalRowCount.toString());
          _paging.setTotalSize(totalRowCount.intValue());
          map.put("_startRow", _paging.getActivePage()*_paging.getPageSize()+1);
          map.put("_endRow", _paging.getActivePage()*_paging.getPageSize()+_paging.getPageSize());
          return map;
     }
     
     public void doExcel(Map<String,Object> beans,String path) throws Exception {
          try {
               File templateResource = ResourceUtil.getResourceFile(path);
               String templateFileName = templateResource.getAbsolutePath();
               String destFileName = Constant.getTempDir() + "/excel.xls";
               
               XLSTransformer transformer = new XLSTransformer();
               transformer.transformXLS(templateFileName, beans, destFileName);

               File file = new File(destFileName);
               if (file != null && file.exists()) {
                    Filedownload.save(file, "application/vnd.ms-excel");
               } else {
                    alert("下载失败");
                    return;
               }
          } catch (Exception e) {
               e.printStackTrace();
          }

     }
     /**
      * 获得session中的UserName
      * @return
      */
     public String getOperatorName() {
          PermUser user = getSessionUser();
          if(null != user ){
               return user.getUserName();
          }
          return "admin";
     }
}