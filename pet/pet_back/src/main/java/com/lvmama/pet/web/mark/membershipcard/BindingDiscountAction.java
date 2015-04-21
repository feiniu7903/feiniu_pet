package com.lvmama.pet.web.mark.membershipcard;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.zkoss.zul.Longbox;
import com.lvmama.comm.pet.po.mark.MarkCoupon;
import com.lvmama.comm.pet.po.user.UserUser;
import com.lvmama.comm.pet.service.mark.MarkCouponService;
import com.lvmama.comm.pet.service.mark.MarkMembershipCardDiscountService;
import com.lvmama.comm.pet.service.mark.MarkMembershipCardService;
import com.lvmama.comm.pet.service.user.UserUserProxy;
import com.lvmama.comm.pet.vo.mark.MarkMembershipCardDetails;
import com.lvmama.comm.pet.vo.mark.MarkMembershipCardDiscountDetails;
import com.lvmama.pet.utils.ZkMessage;
import com.lvmama.pet.utils.ZkMsgCallBack;

public class BindingDiscountAction extends com.lvmama.pet.web.BaseAction {
	private static final long serialVersionUID = -5311089371947182835L;

	/**
	 * 日志输出器
	 */
	private static final Log LOG = LogFactory.getLog(BindingDiscountAction.class);
	private MarkMembershipCardService markMembershipCardService;
	private MarkMembershipCardDiscountService markMembershipCardDiscountService;
	private MarkMembershipCardDetails markMembershipCardDetails;
	private List<MarkMembershipCardDiscountDetails> markMembershipCardDiscountDetailsList;
	private Long cardId;
	private Long couponId;
	private UserUserProxy userUserProxy;
	private MarkCouponService markCouponService;
	
	@Override
	public void doBefore() {
		markMembershipCardDetails = markMembershipCardService.queryByPK(cardId);
		loadMarkMembershipCardDiscountDetailsList();
	}
	
	/**
	 * 绑定优惠券
	 */
	public void binding() {
		Longbox tempCouponId = (Longbox) getComponent().getFellow("tempCouponId");
		if (null != tempCouponId.getValue() 
				&& 0 != tempCouponId.getValue()
				) {
			couponId = tempCouponId.getValue();
		}
		
		if (null == couponId) {
			alert("请先选择优惠券批次");
			return;
		}
		
		markMembershipCardService.bindingDiscount(markMembershipCardDetails.getCardId(),couponId,getSessionUserName());
		
		//给已激活的会员卡发送优惠券
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("cardId", markMembershipCardDetails.getCardId());
		List<UserUser> users = userUserProxy.getUsers(parameters);
		markCouponService.generateAndBindCouponCodeForUserList(users, couponId);
		
		refreshComponent("btnRefresh");
		alert("新增优惠成功!");
		super.refreshParent("refresh");
	}
	
	
	public void remove(Map<String,Object> param) {
		final Long cardDiscountId = (Long) param.get("cardDiscountId");
		ZkMessage.showQuestion("您确信需要删除此优惠吗?", new ZkMsgCallBack() {
			public void execute() {
				Map<String,Object> parameters = new HashMap<String,Object>();
				parameters.put("cardDiscountId", cardDiscountId);
				markMembershipCardDiscountService.delete(parameters,getSessionUserName());
				refreshComponent("btnRefresh");
				refreshParent("refresh");
			}
		}, new ZkMsgCallBack() {
			public void execute() {
			}
		});
	}
	
	/**
	 * 设置优惠标识
	 * @param couponId
	 */
	public void setCouponId(Long couponId) {
		this.couponId = couponId;
	}
	
	/**
	 * 调用会员卡所绑定的所有优惠措施
	 */
	public void loadMarkMembershipCardDiscountDetailsList() {
		Map<String,Object> parameters = new HashMap<String,Object>();
		parameters.put("cardId", this.cardId);
		parameters.put("order", "discount.CREATE_TIME desc");
		markMembershipCardDiscountDetailsList = markMembershipCardDiscountService.query(parameters);
		/**
		 * @deprecated 此处代码为临时解决方案，等待coupon迁移后删除
		 */
		for (MarkMembershipCardDiscountDetails details : markMembershipCardDiscountDetailsList) {
			if (null != details.getCouponId()) {
				MarkCoupon markCoupon = markCouponService.selectMarkCouponByPk(details.getCouponId());
				if (null != markCoupon) {
					details.setCouponName(markCoupon.getCouponName());
					details.setAmountDescription(markCoupon.getFavorTypeDescription());
					//TODO 优惠  这里需要确认怎么设置
					//details.setAmount(markCoupon.get);
				}
			}
		}
		
	}
	
	

	//setter and getter
	public Long getCardId() {
		return cardId;
	}

	public void setCardId(Long cardId) {
		this.cardId = cardId;
	}

	public MarkMembershipCardDetails getMarkMembershipCardDetails() {
		return markMembershipCardDetails;
	}

	public void setMarkMembershipCardService(
			MarkMembershipCardService markMembershipCardService) {
		this.markMembershipCardService = markMembershipCardService;
	}

	public void setMarkMembershipCardDiscountService(
			MarkMembershipCardDiscountService markMembershipCardDiscountService) {
		this.markMembershipCardDiscountService = markMembershipCardDiscountService;
	}

	public List<MarkMembershipCardDiscountDetails> getMarkMembershipCardDiscountDetailsList() {
		return markMembershipCardDiscountDetailsList;
	}

	public void setUserUserProxy(UserUserProxy userUserProxy) {
		this.userUserProxy = userUserProxy;
	}

	public void setMarkCouponService(MarkCouponService markCouponService) {
		this.markCouponService = markCouponService;
	}



}
