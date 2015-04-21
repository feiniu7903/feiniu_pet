package com.lvmama.back.web;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

import com.lvmama.back.utils.ZkMessage;
import com.lvmama.back.web.utils.ZKUtils;
import com.lvmama.comm.bee.vo.ord.CompositeQuery;
import com.lvmama.comm.bee.vo.ord.CompositeQuery.PageIndex;
import com.lvmama.comm.pet.po.perm.PermUser;
import com.lvmama.comm.spring.SpringBeanProxy;
import com.lvmama.comm.utils.ServletUtil;
import com.lvmama.comm.vo.Constant;

public class BaseAction extends GenericForwardComposer {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3357432598207266653L;

	private Component component;
	protected PermUser user;
	
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
		HttpServletRequest request = (HttpServletRequest) Executions.getCurrent().getNativeRequest();
		HttpServletResponse response = (HttpServletResponse) Executions.getCurrent().getNativeResponse();
		PermUser permUser = (PermUser)ServletUtil.getSession(request, response, Constant.SESSION_BACK_USER);
		if (permUser==null) {
			Executions.sendRedirect("/pet_back/login.do");
			return;
		}else{
			user = permUser;
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
	
	/**
	 * 弹出一个页面
	 * 宽度为默认值950px
	 * @param uri
	 * @param params
	 * @throws Exception
	 */
	public void showWindow(String uri, Map params) throws Exception {
		ZKUtils.showWindow(component, uri, params);
	}
	
	/**
	 * 弹出一个页面，宽高可选
	 * @param uri
	 * @param params
	 * @param width
	 * @param height
	 * @throws Exception
	 */
	public void showWindow(String uri, Map<String,Object> params,String width, String height) throws Exception {
		ZKUtils.showChangSizeWindow(component, uri, params , width ,height);
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
	
	/**
	 * 刷新列表页面到第一页.
	 * @param btnName button元素的id属性值. &lt;button label="查询" id="search" width="100px" onClick='_paging.activePage=0;saction.searchFaxTask();'/>
	 */
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
	
	/**
	 * 关闭上一级页面并且刷新上上级页面
	 * @param btnName
	 */
	public void refreshAndCloseParent(String btnName){
		try{
			Component c = component.getParent().getParent();
			Button b = (Button) c.getFellow(btnName);
			Events.sendEvent(new Event("onClick",b));
			Component com = component.getParent();
			component.detach();
			com.detach();
		}catch(Exception e) {
			e.printStackTrace();
			ZkMessage.showError("refresh "+btnName+" failed");
		}
	}
	
	public void closeWin(){
		component.detach();
	}
	/**
	 * 刷新当前列表页面.(默认刷新父页面)
	 * @param pageId paging元素的id属性值. &lt;paging  id="_paging" pageSize = "4" onPaging='saction.searchFaxTask();'/>
	 */
	public void refreshCurrentPage(String pageId){
		try{
			Component c = component.getParent();
			Paging b = (Paging) c.getFellow(pageId);
			Events.sendEvent(new Event("onPaging",b));
		}catch(Exception e) {
			e.printStackTrace();
			ZkMessage.showError("refresh "+pageId+" failed");
		}
	}
	/**
	 * 刷新当前列表页面.(刷新component对象页面)
	 * @author ZHANG Nan
	 * @param pageId paging组件ID
	 * @param component 组件对象
	 */
	public void refreshCurrentPage(Component component,String pageId){
		try{
			Paging b = (Paging) component.getFellow(pageId);
			Events.sendEvent(new Event("onPaging",b));
		}catch(Exception e) {
			e.printStackTrace();
			ZkMessage.showError("refresh "+pageId+" failed");
		}
	}
	/**
	 * 关闭当前窗口
	 */
	public void closeWindow() {
		component.detach();
	}
	
	/**
	 * 得到当前组件的父类组件
	 * @return
	 */
	public Component getParent() {
		return component.getParent();
	}
	
	/**
	 * 得到当前组件
	 * @return
	 */
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
					if (name.endsWith("Service") || name.endsWith("Producer") || name.endsWith("Proxy")) {
						Object obj = SpringBeanProxy.getBean(name);
						if(obj != null) {
							AccessibleObject.setAccessible(fields,   true);
							fields[i].set(this, obj);
						}
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

	/**
	 * 设定组件名
	 * @return
	 */
	protected String getComponetName() {
		return "saction";
	}
	/**
	 * 获取当前登录用户
	 * @return
	 */
	public PermUser getSessionUser(){
		HttpServletRequest request = (HttpServletRequest) Executions.getCurrent().getNativeRequest();
		HttpServletResponse response = (HttpServletResponse) Executions.getCurrent().getNativeResponse();
		PermUser user = (PermUser)ServletUtil.getSession(request, response, com.lvmama.comm.vo.Constant.SESSION_BACK_USER);
		if (user!=null) {
			return user;
		}else{
			return new PermUser();
		}
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
	
	/**
	 * 获得session中的UserName
	 * @return
	 */
	public String getOperatorName() {
		return getSessionUser().getUserName();
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
	
	protected Map<String, Object> initialPageInfoByMap(Long totalRowCount,Map<String, Object> map){
		((Label)this.getComponent().getFellow("_totalRowCountLabel")).setValue(totalRowCount.toString());
		Paging paging=(Paging)this.getComponent().getFellow("_paging");
		paging.setTotalSize(totalRowCount.intValue());
		map.put("skipResults", paging.getActivePage() * paging.getPageSize());
		map.put("maxResults", paging.getActivePage() * paging.getPageSize() + paging.getPageSize());
		return map;
	}
	
	protected Map<String, Object> formatMap(Map<String, Object> map) {
		Object idObject = map.get("orderId");
		if(idObject != null) {
			String ordId = idObject.toString().trim();
			if(!"".equals(ordId)) {
				Long orderId = Long.valueOf(ordId);
				map.put("orderId", orderId);
			}
		}
		return map;
	}
}
