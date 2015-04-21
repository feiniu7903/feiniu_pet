package com.lvmama.pet.mark.service;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.lvmama.comm.pet.po.mark.MarkMembershipCard;
import com.lvmama.comm.pet.po.mark.MarkMembershipCardDiscount;
import com.lvmama.comm.pet.service.mark.MarkMembershipCardService;
import com.lvmama.comm.pet.service.pub.ComLogService;
import com.lvmama.comm.pet.vo.mark.MarkMembershipCardDetails;
import com.lvmama.comm.utils.LogViewUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.pet.mark.dao.MarkMembershipCardCodeDAO;
import com.lvmama.pet.mark.dao.MarkMembershipCardDAO;
import com.lvmama.pet.mark.dao.MarkMembershipCardDiscountDAO;

public class MarkMembershipCardServiceImpl implements MarkMembershipCardService {
	@Autowired
	private MarkMembershipCardDAO markMembershipCardDAO;
	@Autowired
	private MarkMembershipCardCodeDAO markMembershipCardCodeDAO;
	@Autowired
	private MarkMembershipCardDiscountDAO markMembershipCardDiscountDAO;
	@Autowired
	private ComLogService comLogService;
	

	@Override
	public void insert(MarkMembershipCard markMemebershipCard,String operatorName) {
		markMembershipCardDAO.insert(markMemebershipCard);
		comLogService.insert("MARK_MEMBERSHIP_CARD", null, markMemebershipCard.getCardId(), operatorName,
				Constant.MEMBERCARDSHIP.CREATE_MEMBERCARD.name(), "创建会员卡", LogViewUtil.logNewStr(operatorName), null);
	}

	@Override
	public List<MarkMembershipCardDetails> query(Map<String, Object> parameters) {
		return markMembershipCardDAO.query(parameters);
	}
	
	@Override
	public MarkMembershipCardDetails queryByPK(final Serializable id) {
		return markMembershipCardDAO.queryByPK(id);
	}

	@Override
	public Long count(Map<String, Object> parameters) {
		return markMembershipCardDAO.count(parameters);
	}
	
	@Override
	public void delete(final Long cardId,String operatorName) {
		Map<String,Object> parameters = new HashMap<String,Object>();
		parameters.put("cardId", cardId);
		
		//删除会员卡号与优惠券的关联
		markMembershipCardDiscountDAO.delete(parameters);
		comLogService.insert("MARK_MEMBERSHIP_CARD", null, cardId, operatorName,
			Constant.MEMBERCARDSHIP.DELETE_MC_AND_DC.name(), "删除会员卡与优惠券关联", LogViewUtil.logDeleteStr(operatorName), null);
		
		//删除会员卡号
		markMembershipCardCodeDAO.delete(parameters);
		comLogService.insert("MARK_MEMBERSHIP_CARD", null, cardId, operatorName,
			Constant.MEMBERCARDSHIP.BINDING_DISCOUNT.name(), "删除会员卡", LogViewUtil.logDeleteStr(operatorName), null);
		
		//删除会员卡批次
		markMembershipCardDAO.delete(cardId);
		comLogService.insert("MARK_MEMBERSHIP_CARD", null, cardId, operatorName,
			Constant.MEMBERCARDSHIP.DELETE_MEMBERCARD_BATCH.name(), "删除会员卡批次", LogViewUtil.logDeleteStr(operatorName), null);
	}
	
	@Override
	public void bindingDiscount(Long cardId, Long couponId, String operatorName) {
		MarkMembershipCardDiscount mmcd = new MarkMembershipCardDiscount();
		mmcd.setCouponId(couponId);
		mmcd.setCardId(cardId);
		mmcd.setOperatorName(operatorName);
		markMembershipCardDiscountDAO.insert(mmcd);
		markMembershipCardDAO.updateBindingDiscount(cardId);
		
		//给已激活的会员卡发送优惠券
		/**
		 * @ TODO 此处代码需要移到Action层操作
		 */
//		Map<String, Object> parameters = new HashMap<String, Object>();
//		parameters.put("cardId", cardId);
//		List<UserUser> users = userUserProxy.getUsers(parameters);
//		for (UserUser user: users) {
//			String couponCode = couponLogic.generateNewMarkcouponCode(couponId);
//			List<MarkCouponCode> list = markCouponCodeDAO.selectByCouponCode(couponCode);
//			if (list.size()>0) {
//				MarkCouponCode code = list.get(0);
//				MarkCouponRelateUser bindUser = new MarkCouponRelateUser();
//				bindUser.setUserId(user.getUserId());
//				bindUser.setCouponCodeId(code.getCouponCodeId());
//				markCouponRelateUserDAO.insert(bindUser);
//			}
//		}
		comLogService.insert("MARK_COUPON", cardId, couponId, operatorName,
				Constant.MEMBERCARDSHIP.BINDING_DISCOUNT.name(), "绑定优惠券", LogViewUtil.logNewStr(operatorName), "MARK_MEMBERSHIP_CARD");
	}
	/**
	 * 绑定渠道
	 * @param card
	 * @return
	 * @author shangzhengyuan
	 * @create 2011-04-25
	 */
	public int updateBindChannel(final MarkMembershipCard card,String operatorName){
		int r = markMembershipCardDAO.updateBindChannel(card);
		comLogService.insert("MARK_MEMBERSHIP_CARD", null, card.getCardId(), operatorName,
				Constant.MEMBERCARDSHIP.BING_CHANNEL.name(), "绑定渠道", LogViewUtil.logNewStr(operatorName), null);
		return  r;
	}

}
