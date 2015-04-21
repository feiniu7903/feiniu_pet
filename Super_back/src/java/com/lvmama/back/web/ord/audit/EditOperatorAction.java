package com.lvmama.back.web.ord.audit;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.axis.utils.StringUtils;

import com.lvmama.back.web.BaseAction;
import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.pet.po.perm.PermUser;
import com.lvmama.comm.pet.service.perm.PermRoleService;
import com.lvmama.comm.pet.service.perm.PermUserService;
import com.lvmama.comm.spring.SpringBeanProxy;
import com.lvmama.comm.vo.Constant;

/**
 * @author luo
 * 
 */
public class EditOperatorAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	private OrderService orderServiceProxy = (OrderService) SpringBeanProxy
			.getBean("orderServiceProxy");
	private PermUserService permUserService;
	private PermRoleService permRoleService;
	private String operator = "";
	private List<OrdOrder> orderList;
	

	public void doBefore() throws Exception {
	}


	public void doSubmit() throws Exception {
		if(StringUtils.isEmpty(this.operator)){
			alert("请输入处理人");
			return;
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userName", operator);
		PermUser user = permUserService.getPermUserByParams(params);
		
		if(user == null){
			alert("输入的用户不存在或者不可用");
			return;
		}
		
		if(!permRoleService.checkUserRole(user.getUserId(), Constant.PERM_ROLE_ORDER)){
			alert("输入的用户无订单管理权限");
			return;
		}
		String sessionUserName = this.getSessionUserName();
		if(StringUtils.isEmpty(sessionUserName)){
			alert("分单失败，没有获取到登陆用户，请重新登陆后在试。");
			return;
		}
		int res = 0;
		for(int i = 0;i<orderList.size();i++){
			if(orderServiceProxy.makeOrdOrderAuditByOrderId(sessionUserName, orderList.get(i).getOrderId(),user.getUserName())){
				res++;
			}
		}
		if(res>0){
			alert("选择了"+orderList.size()+"条，成功分配"+res+"条");
		}else{
			alert("因所选订单均已被领单所以无法分配");
		}
		this.refreshParent("search");
		this.closeWindow();
	}

	public void doSubmitConfim() throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userName", operator);
		PermUser user = permUserService.getPermUserByParams(params);
		
		if(user == null){
			alert("输入的用户不存在或者不可用");
			return;
		}
		
		if(!permRoleService.checkUserRole(user.getUserId(), Constant.PERM_ROLE_ORDER)){
			alert("输入的用户无订单管理权限");
			return;
		}
		int res = 0;
		String sessionUserName = this.getSessionUserName();
		if(StringUtils.isEmpty(sessionUserName)){
			alert("分单失败，没有获取到登陆用户，请重新登陆后在试。");
			return;
		}
		for(int i = 0;i<orderList.size();i++){
			if(orderServiceProxy.makeOrdOrderConfirmAuditByOrderId(sessionUserName, orderList.get(i).getOrderId(),user.getUserName())){
				res++;
			}
		}
		if(res>0){
			alert("选择了"+orderList.size()+"条，成功分配"+res+"条");
		}else{
			alert("因所选订单均已被领单所以无法分配");
		}
		this.refreshParent("search");
		this.closeWindow();
	}
	
	public OrderService getOrderServiceProxy() {
		return orderServiceProxy;
	}

	public void setOrderServiceProxy(OrderService orderServiceProxy) {
		this.orderServiceProxy = orderServiceProxy;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public List<OrdOrder> getOrderList() {
		return orderList;
	}

	public void setOrderList(List<OrdOrder> orderList) {
		this.orderList = orderList;
	}

	public void setPermRoleService(PermRoleService permRoleService) {
		this.permRoleService = permRoleService;
	}

	public void setPermUserService(PermUserService permUserService) {
		this.permUserService = permUserService;
	}

}
