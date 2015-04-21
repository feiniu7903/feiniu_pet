package com.lvmama;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.util.List;
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

import com.lvmama.comm.bee.po.pass.PassPortUser;
import com.lvmama.comm.bee.vo.ord.CompositeQuery;
import com.lvmama.comm.bee.vo.ord.CompositeQuery.PageIndex;
import com.lvmama.comm.pet.po.perm.PermPermission;
import com.lvmama.comm.pet.po.perm.PermUser;
import com.lvmama.comm.spring.SpringBeanProxy;
import com.lvmama.comm.utils.ServletUtil;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.PassportConstant;
import com.lvmama.passport.utils.ZKUtils;
import com.lvmama.passport.utils.ZkMessage;
import com.lvmama.passport.web.auth.LoginAction;

public class ZkBaseAction extends GenericForwardComposer {
	
	private Component component;
	protected PassPortUser user;
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
	
	public void doBeforeComposeChildren(Component comp) throws Exception {
		this.component = comp;
		super.doBeforeComposeChildren(comp);
		Components.wireVariables(comp, this);
		fillBean(this.getClass());
		this.session = Executions.getCurrent().getSession();
		Object obj = this.session.getAttribute(PassportConstant.SESSION_USER);//passport当前登录用户
		if(null!=obj){//passport中已登录用户
			user = (PassPortUser)obj;
		}else{//passport中未登录用户，则可能自pet_back用户
			HttpServletRequest request = (HttpServletRequest) Executions.getCurrent().getNativeRequest();
			HttpServletResponse response = (HttpServletResponse) Executions.getCurrent().getNativeResponse();
			//获取当前pet_back用户
			PermUser permUser = (PermUser)ServletUtil.getSession(request, response, Constant.SESSION_BACK_USER);
			if(null==permUser){//pet_back中未登录
				if (!this.getClass().equals(LoginAction.class)) {
					comp.setAttribute("doAfter", "false");//设置不执行后处理方法
					Executions.sendRedirect("/login.zul");//去passport登录
					return;
				}
			}else{//pet_back已登录,校验权限
				user=new PassPortUser();
				user.setUserId(String.valueOf(permUser.getUserId()));
				user.setName(permUser.getUserName());
			}
		}
		BeanUtils.copyProperties(this, Executions.getCurrent().getParameterMap());
		BeanUtils.copyProperties(this, Executions.getCurrent().getArg());
		if (getComponetName()!=null) {
			comp.setAttribute(getComponetName(), this, false);
		}
		doBefore();
	}
	
	//验证权限,验证逻辑同PermissionFilter
	private boolean isPermRefused(HttpServletRequest request, PermUser user){
		if(user.isAdministrator()){
			return false;
		}
		List<PermPermission> permList = user.getPermissionList();
		if(permList == null || permList.size() == 0){
			return true;
		}
		for(PermPermission perm : permList){
			if(StringUtil.isEmptyString(perm.getUrlPattern())){
				continue;
			}
			String[] arr2 = perm.getUrlPattern().split(";");
			if(arr2 == null || arr2.length == 0){
				continue;
			}
			for(String pattern : arr2){
				try{
					if(perm.getUrlPattern() != null && request.getRequestURI().toLowerCase().matches(pattern.toLowerCase().trim())){
						return false;
					}
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}
		return true;
	}
	
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		String doAfter=(String) comp.getAttribute("doAfter");
		if(doAfter==null){//判断是否要执行后处理方法
			doAfter();
		}
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
		if (myClass.getName().endsWith(".ZkBaseAction")) {
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
	/**
	 * 获取当前登录用户
	 * @return
	 */
	public PassPortUser getSessionUser(){
		return (PassPortUser)super.session.getAttribute(PassportConstant.SESSION_USER);
	}
	
	public String getOperatorName() {
		return getSessionUser().getName();
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

	public PassPortUser getUser() {
		return user;
	}

	public void setUser(PassPortUser user) {
		this.user = user;
	}
}
