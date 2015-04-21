package com.lvmama.businessreply.web;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.util.Map;
import org.apache.commons.beanutils.BeanUtils;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Label;
import org.zkoss.zul.Paging;
import org.zkoss.zul.api.Button;
import com.lvmama.businessreply.utils.ZKUtils;
import com.lvmama.businessreply.utils.ZkMessage;
import com.lvmama.businessreply.vo.BusinessConstant;
import com.lvmama.comm.bee.vo.ord.CompositeQuery;
import com.lvmama.comm.bee.vo.ord.CompositeQuery.PageIndex;
import com.lvmama.comm.pet.po.comment.CmtBusinessUser;
import com.lvmama.comm.spring.SpringBeanProxy;


public class BaseAction extends GenericForwardComposer {
	
	private Component component;
	protected CmtBusinessUser user;
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
		Object obj = this.session.getAttribute(BusinessConstant.SESSION_USER);
		if (obj==null) {
//			Executions.sendRedirect("/login.zul");
//			return;
		}else{
			user = (CmtBusinessUser)obj;
		}
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
	
	public void showWindow(String uri, Map params) throws Exception {
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
	private void fillBean(Class myClass){
		if (myClass.getName().endsWith(".BaseAction")) {
			return;
		}else {
			try {
				Field[] fields = myClass.getDeclaredFields();
				for (int i = 0; i < fields.length; i++) {
					String name = fields[i].getName();
					if (name.endsWith("Service") || name.endsWith("Producer") || name.endsWith("XmlJob")) {
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
	/**
	 * 获取当前登录用户
	 * @return
	 */
	public CmtBusinessUser getSessionUser(){
		return (CmtBusinessUser)super.session.getAttribute(BusinessConstant.SESSION_USER);
	}
	
	public String getOperatorName() {
		return getSessionUser().getUserID();
	}
	
	protected void initialPageInfo(Long totalRowCount,CompositeQuery compositeQuery){
		((Label)this.getComponent().getFellow("_totalRowCountLabel")).setValue(totalRowCount.toString());
		Paging paging=(Paging)this.getComponent().getFellow("_paging");
		paging.setTotalSize(totalRowCount.intValue());
		PageIndex pageIndex =new PageIndex();
		pageIndex.setBeginIndex(paging.getActivePage()*paging.getPageSize()+1);
		pageIndex.setEndIndex(paging.getActivePage()*paging.getPageSize()+paging.getPageSize());
		compositeQuery.setPageIndex(pageIndex);
	}
	
	protected Map initialPageInfoByMap(Long totalRowCount,Map map){
		_totalRowCountLabel.setValue(totalRowCount.toString());
		_paging.setTotalSize(totalRowCount.intValue());
		map.put("_startRow", _paging.getActivePage()*_paging.getPageSize());
		map.put("_endRow", _paging.getActivePage()*_paging.getPageSize()+_paging.getPageSize());
		return map;
	}

	public CmtBusinessUser getUser() {
		return user;
	}

	public void setUser(CmtBusinessUser user) {
		this.user = user;
	}
}
