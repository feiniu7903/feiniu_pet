package com.lvmama.pet.web.shop.shopOrder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.zul.Label;
import org.zkoss.zul.Paging;

import com.lvmama.comm.pet.po.user.UserUser;
import com.lvmama.comm.pet.service.user.UserUserProxy;
import com.lvmama.comm.pet.vo.UserPointLogWithDescription;
import com.lvmama.comm.spring.SpringBeanProxy;

public class ListUserPointAction extends com.lvmama.pet.web.BaseAction {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 156447564L;
	/**
	 * SSO用户信息远程服务
	 */
	private UserUserProxy userUserProxy = (UserUserProxy) SpringBeanProxy.getBean("userUserProxy");
	/**
	 * 用户信息
	 */
	private UserUser users;
	/**
	 * 当前积分
	 */
	private Long currentPoint;
	/**
	 * 年末过期的积分
	 */
	private Long aboutToExpiredPoint;
	/**
	 * 用户编号
	 */
	private Long uuId;
	/**
	 * 查询参数
	 */
	private Map<String, Object> parameters = new HashMap<String, Object>();
	/**
	 * 用户积分记录
	 */
	private List<UserPointLogWithDescription> userPointLogWithDescriptionList = new ArrayList<UserPointLogWithDescription>();
	
	@Override
	public void doAfter() throws Exception {
		//用户积分
		users = userUserProxy.getUserUserByPk(uuId);
		currentPoint = users.getPoint();
		currentPoint = currentPoint == null ? 0L : currentPoint;
		parameters.put("userId", uuId);
		
		Long usedPoint = userUserProxy.getUsedUsersPoint(users.getId());
		usedPoint = null == usedPoint ? 0L : usedPoint;
		
		aboutToExpiredPoint = userUserProxy.getAboutToExpiredUsersPoint(users.getId());
		aboutToExpiredPoint = null == aboutToExpiredPoint ? 0L : aboutToExpiredPoint;
		
		search();
	}

	/**
	 * 查询
	 */
	public void search() {
		userPointLogWithDescriptionList.clear();
		Long totalRowCount = userUserProxy.getCountUserPointLog(parameters);
		this.initialPageInfoByMap(totalRowCount, parameters);
		userPointLogWithDescriptionList = userUserProxy.getPointLog(parameters);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected Map initialPageInfoByMap(Long totalRowCount,Map map){
		((Label)this.getComponent().getFellow("totalRowCountLabel")).setValue(totalRowCount.toString());
		Paging paging=(Paging)this.getComponent().getFellow("paging");
		paging.setTotalSize(totalRowCount.intValue());
		map.put("_startRow", paging.getActivePage()*paging.getPageSize()+1);
		map.put("_endRow", paging.getActivePage()*paging.getPageSize()+paging.getPageSize());
		return map;
	}

	public Map<String, Object> getParameters() {
		return parameters;
	}

	public void setParameters(final Map<String, Object> parameters) {
		this.parameters = parameters;
	}

	public Long getCurrentPoint() {
		return currentPoint;
	}

	public Long getAboutToExpiredPoint() {
		return aboutToExpiredPoint;
	}

	public List<UserPointLogWithDescription> getUserPointLogWithDescriptionList() {
		return userPointLogWithDescriptionList;
	}


	public void setUuId(final Long uuId) {
		this.uuId = uuId;
	}

	public UserUser getUsers() {
		return users;
	}
	
}
