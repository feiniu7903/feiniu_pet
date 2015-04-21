package com.lvmama.comm.bee.vo.ord;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.bee.vo.ord.CompositeQuery.OrderContent;
import com.lvmama.comm.bee.vo.ord.CompositeQuery.SaleServiceRelate;
import com.lvmama.comm.pet.po.user.UserUser;

public class UserQuery implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -245948560076729000L;
	/**
	 * 用户ID列表
	 */
	private List<String> userIdList;
	/**
	 * 用户ID列表
	 * @return
	 */
	public List<String> getUserIdList() {
		return userIdList;
	}
	/**
	 * 用户ID列表
	 */
	public void setUserIdList(List<String> userIdList) {
		this.userIdList = userIdList;
	}
	/**
	 * 组装用户ID的IN语句的SQL条件
	 * 
	 * @author: ranlongfei 2012-8-28 下午9:31:51
	 * @return
	 */
	public String getUserIdListWithSqlFormat() {
		if(this.userIdList != null && this.userIdList.size() > 0) {
			StringBuilder sql = new StringBuilder();
			for(int i = 0; i < this.userIdList.size(); i++) {
				sql.append("'").append(this.userIdList.get(i)).append("'");
				if(i < this.userIdList.size() -1) {
					sql.append(",");
				}
			}
			return sql.toString();
		}
		return "''";
	}
	/**
	 * 组装用户相关的参数Map
	 * 
	 * @author: ranlongfei 2012-8-29 下午6:31:33
	 * @return
	 */
	public Map<String, Object> getUserParam(OrderContent oc) {
		Map<String, Object> params = new HashMap<String, Object>();
		int i = 0;
		if(oc.getUserName() != null && !"".equals(oc.getUserName())) {
			i++;
			params.put("userName", oc.getUserName());
		} 
		if(oc.getEmail() != null && !"".equals(oc.getEmail())) {
			i++;
			params.put("email", oc.getEmail());
		} 
		if(oc.getMembershipCard() != null && !"".equals(oc.getMembershipCard())) {
			i++;
			params.put("memberShipCard", oc.getMembershipCard());
		} 
		if(oc.getMobile() != null && !"".equals(oc.getMobile())) {
			i++;
			params.put("mobileNumber", oc.getMobile());
		} 
		if(i > 0) {
			return params;
		}
		return null;
	}
	/**
	 * 组装用户相关的参数Map
	 * 
	 * @author: ranlongfei 2012-8-29 下午6:31:33
	 * @return
	 */
	public Map<String, Object> getUserParam(SaleServiceRelate ssr) {
		Map<String, Object> params = new HashMap<String, Object>();
		int i = 0;
		if(ssr.getSaleServiceUserName() != null && !"".equals(ssr.getSaleServiceUserName())) {
			i++;
			params.put("userName", ssr.getSaleServiceUserName());
		} 
		if(ssr.getSaleServiceMobile() != null && !"".equals(ssr.getSaleServiceMobile())) {
			i++;
			params.put("mobileNumber", ssr.getSaleServiceMobile());
		} 
		if(i > 0) {
			return params;
		}
		return null;
	}
	public List<String> getUserList(List<UserUser> uList) {
		List<String> idList = new ArrayList<String>();
		idList.add("-1");
		if(uList != null) {
			for(UserUser u : uList) {
				idList.add(u.getUserId());
			}
		}
		return idList;
	}
}
