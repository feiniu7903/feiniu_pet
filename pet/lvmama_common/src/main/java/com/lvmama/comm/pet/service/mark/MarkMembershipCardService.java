package com.lvmama.comm.pet.service.mark;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.pet.po.mark.MarkMembershipCard;
import com.lvmama.comm.pet.vo.mark.MarkMembershipCardDetails;

public interface MarkMembershipCardService {
	/**
	 * 插入
	 */
	void insert(final MarkMembershipCard markMemebershipCard,final String operatorName);
	
	/**
	 * 查询
	 * @param parameters
	 * @return
	 */
	List<MarkMembershipCardDetails> query(final Map<String,Object> parameters);
	
	/**
	 * 根据主键查询
	 * @param id
	 */
	MarkMembershipCardDetails queryByPK(final Serializable id);
	
	/**
	 * 计数
	 * @param parameters
	 * @return
	 */
	Long count(final Map<String,Object> parameters);
	
	/**
	 * 物理删除会员卡及批次，并释放关联的优惠券
	 * @param cardId
	 */
	void delete(final Long cardId,final String operatorName);
	
	/**
	 * 绑定优惠券
	 * @param cardId 会员卡批次
	 * @param couponId 优惠券批次
	 * @param operatorName 操作人
	 */
	void bindingDiscount(final Long cardId, final Long couponId, final String operatorName);
	/**
	 * 绑定渠道
	 * @param card
	 * @return
	 * @author shangzhengyuan
	 * @create 2011-04-25
	 */
	public int updateBindChannel(final MarkMembershipCard card,final String operatorName);
}
