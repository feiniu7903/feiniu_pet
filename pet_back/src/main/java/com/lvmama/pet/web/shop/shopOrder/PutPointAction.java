package com.lvmama.pet.web.shop.shopOrder;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.pet.po.user.UserUser;
import com.lvmama.comm.pet.service.shop.ShopLogService;
import com.lvmama.comm.pet.service.shop.ShopUserService;
import com.lvmama.comm.pet.service.user.UserUserProxy;
import com.lvmama.comm.spring.SpringBeanProxy;
import com.lvmama.pet.utils.ZkMessage;
import com.lvmama.pet.utils.ZkMsgCallBack;


/***
 * 客服方法积分
 * @author yangchen
 */
public class PutPointAction extends com.lvmama.pet.web.BaseAction {
	/**
	 * 序列值
	 */
	private static final long serialVersionUID = -8852005824658686747L;
	/** 发放事宜的列表 **/
	private List<String> things = new ArrayList<String>();
	/** 积分列表 **/
	private List<String> points = new ArrayList<String>();
	/** SSO用户信息远程服务 */
	private UserUserProxy userUserProxy = (UserUserProxy) SpringBeanProxy
			.getBean("userUserProxy");
	/** 用户信息 **/
	private UserUser users;
	/** 用户ID **/
	private Long uuId;
	/** 客服发放积分的对象的属性 **/
	private Map<String, Object> putPoint = new HashMap<String, Object>();
	/** 日志逻辑 **/
	private ShopLogService shopLogService;
	/**
	 * 用户逻辑接口
	 */
	private ShopUserService shopUserService;

	@Override
	public void doBefore() {
		refreshThings();
	}

	@Override
	public void doAfter() throws Exception {
		users = userUserProxy.getUserUserByPk(uuId);
		putPoint.put("userName", users.getUserName());
		putPoint.put("userId", users.getId());
		//获取登录用户名
		putPoint.put("csName",getSessionUserName());
	}

	/**
	 * 保存客服发放积分的数据
	 */
	public void save() {
		ZkMessage.showQuestion("确定发放积分?本次发放行为会被完整记录。", new ZkMsgCallBack() {
			public void execute() {
				if (getPoint()) {
					shopLogService.savePutPoint(putPoint);
					addPoint(putPoint);
					getComponent().detach();
				}
			}
		}, new ZkMsgCallBack() {
			public void execute() {
			}
		});
	}

	/**
	 * 客服发放积分

	 * @param parames 参数列表
	 */
	public void addPoint(final Map<String, Object> parames) {
			shopUserService.reducePoint((Long) parames.get("userId") , "POINT_FOR_CUSTOMIZED_CS",
					Long.parseLong((String) parames.get("point")), (String) parames.get("memo"));
			alert("增加成功");
	}

	/**
	 * 判断是否选中发送事宜和积分
	 * @return bool
	 */
	public boolean getPoint() {
		if (putPoint.get("putThings") == null || putPoint.get("point") == null) {
			ZkMessage.showError("请先选中发送事宜和发送积分,再次点击发送");
			return false;
		}
		return true;
	}

	/**
	 * 发放事宜改变
	 * @param putTings
	 *            发放事宜的内容
	 */
	public void changeThings(final String putTings) {
		putPoint.put("putThings", putTings);
		refreshPoint(putTings);
	}

	/**
	 * 设置发放事宜的列表集合
	 */
	private void refreshThings() {
		things.clear();
		things.add("体验不佳");
		things.add("上门投诉");
		things.add("删除本网负面点评");
		things.add("删除外网负面点评");
		things.add("提供建议");
	}

	/**
	 * 获取选中的积分
	 * @param point
	 *            积分
	 */
	public void changPoint(final String point) {
		putPoint.put("point", point);
	}

	/**
	 * 根据不同的发放事宜,获取不同的积分
	 * @param putTings
	 *            发放事宜
	 */
	private void refreshPoint(final String putTings) {
		points.clear();
		Integer i = new Integer(500);
		if (putTings.equals("体验不佳")) {
			for (; i <= 10000; i = i + 500) {
				points.add(i.toString());
			}
		} else if (putTings.equals("删除本网负面点评")) {
			points.add("500");
		} else if (putTings.equals("上门投诉")) {
			for (; i <= 3000; i = i + 500) {
				points.add(i.toString());
			}
		} else if (putTings.equals("删除外网负面点评")) {
			points.add("500");
			points.add("1000");
		} else if (putTings.equals("提供建议")) {
			points.add("500");
		}
	}

	public List<String> getThings() {
		return things;
	}

	public void setThings(final List<String> things) {
		this.things = things;
	}

	public List<String> getPoints() {
		return points;
	}

	public void setPoints(final List<String> points) {
		this.points = points;
	}

	public UserUser getUsers() {
		return users;
	}

	public Long getUuId() {
		return uuId;
	}

	public void setUuId(final Long uuId) {
		this.uuId = uuId;
	}

	public void setPutPoint(final Map<String, Object> putPoint) {
		this.putPoint = putPoint;
	}

	public ShopLogService getShopLogService() {
		return shopLogService;
	}

	public void setShopLogService(ShopLogService shopLogService) {
		this.shopLogService = shopLogService;
	}

	public Map<String, Object> getPutPoint() {
		return putPoint;
	}

	public void setShopUserService(final ShopUserService shopUserService) {
		this.shopUserService = shopUserService;
	}

	public void setUserUserProxy(UserUserProxy userUserProxy) {
		this.userUserProxy = userUserProxy;
	}
}
