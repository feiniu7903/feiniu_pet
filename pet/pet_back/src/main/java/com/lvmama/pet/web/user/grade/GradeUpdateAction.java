package com.lvmama.pet.web.user.grade;

import com.lvmama.comm.bee.po.ord.OrdOrderSum;
import com.lvmama.comm.pet.po.user.UserUser;
import com.lvmama.comm.pet.service.user.UserUserProxy;
import com.lvmama.comm.spring.SpringBeanProxy;
import com.lvmama.comm.utils.MemberGradeUtil;
import com.lvmama.comm.vo.Constant.USER_MEMBER_GRADE;
import com.lvmama.pet.utils.ZkMessage;
import com.lvmama.pet.web.BaseAction;

/**
 * 用户等级Action
 * @author yangchen
 */
public class GradeUpdateAction extends BaseAction {
	/**
	 * 序列值
	 */
	private static final long serialVersionUID = -5590942859878909139L;
	/** 用户信息 **/
	private UserUser users;
	/** 备注 **/
	private String memo;
	/** 等级 **/
	private USER_MEMBER_GRADE grade = null;
	/** 用户ID **/
	private Long uuId;
	/** 订单金额的统计对象 **/
	private OrdOrderSum orderSum = null;
	/** 用户的一年的积累金额 **/
	private float amount;
	/** SSO用户信息远程服务 */
	private UserUserProxy userUserProxy = (UserUserProxy) SpringBeanProxy
			.getBean("userUserProxy");

	@Override
	public void doBefore() throws Exception {
		// 获取用户的对象
		users = userUserProxy.getUserUserByPk(uuId);
		users.setGrade(MemberGradeUtil.getUserMemberGrade(users.getGrade())
				.getChGrade().toString());
		// 获取近一年的用户的消费总额
		//orderSum = getLastestUserCost(uuId, -12);
		orderSum = null;
		amount = null == orderSum ? 0F : orderSum.getActualPayYan();
	}

	/**
	 * 修改方法
	 */
	public void update() {
		if (grade == null) {
			alert("请选择级别");
			return;
		} else {
			userUserProxy.updateMemberGradeByManual(users, grade, memo, getSessionUserName());
			ZkMessage.showInfo("保存成功!");
			getComponent().detach();
		}
	}

	/**
	 * 获取用户最近几个月的消费额
	 * @param userId
	 *            用户标识
	 * @param beforeMonth
	 *            最近几个月
	 * @return 消费额
	 */
//	private OrdOrderSum getLastestUserCost(final String userId,
//			final int beforeMonth) {
//		CompositeQuery compositeQuery = new CompositeQuery();
//		OrderContent content = new OrderContent();
//		content.setUserId(userId);
//		compositeQuery.setContent(content);
//		OrderStatus orderStatusForQuery = new OrderStatus();
//		orderStatusForQuery.setOrderStatus(Constant.ORDER_STATUS.NORMAL.name());
//		orderStatusForQuery.setPaymentStatus(Constant.PAYMENT_STATUS.PAYED
//				.name());
//		compositeQuery.setStatus(orderStatusForQuery);
//		OrderTimeRange orderTimeRange = new OrderTimeRange();
//		Calendar cal = Calendar.getInstance();
//		cal.set(Calendar.HOUR_OF_DAY, 0);
//		cal.set(Calendar.MINUTE, 0);
//		cal.set(Calendar.SECOND, 0);
//		orderTimeRange.setOrdOrderVisitTimeEnd(cal.getTime());
//		cal.add(Calendar.MONTH, beforeMonth);
//		orderTimeRange.setOrdOrderVisitTimeStart(cal.getTime());
//		compositeQuery.setOrderTimeRange(orderTimeRange);
//		return orderServiceProxy.compositeQueryOrdOrderSum(compositeQuery);
//	}

	/**
	 * 获取等级
	 * @param level 等级
	 */
	public void changGrade(final String level) {
		grade = MemberGradeUtil.getUserMemberGrade(level);
	}

	public void setUsers(final UserUser users) {
		this.users = users;
	}

	public void setUuId(final Long uuId) {
		this.uuId = uuId;
	}

	public void setMemo(final String memo) {
		this.memo = memo;
	}

	public UserUser getUsers() {
		return users;
	}

	public String getMemo() {
		return memo;
	}

	public float getAmount() {
		return amount;
	}

	public void setAmount(final float amount) {
		this.amount = amount;
	}

	public USER_MEMBER_GRADE getGrade() {
		return grade;
	}

	public void setGrade(final USER_MEMBER_GRADE grade) {
		this.grade = grade;
	}

}
