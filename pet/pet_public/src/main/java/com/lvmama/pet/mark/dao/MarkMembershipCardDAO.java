package com.lvmama.pet.mark.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.mark.MarkMembershipCard;
import com.lvmama.comm.pet.vo.mark.MarkMembershipCardDetails;

public class MarkMembershipCardDAO extends BaseIbatisDAO {
	/**
	 * 插入
	 * @param card
	 */
	public void insert(final MarkMembershipCard card) {
		super.insert("MARK_MEMBERSHIP_CARD.insert", card);
	}
	
	/**
	 * 计数
	 * @param parameters
	 * @return
	 */
	public Long count(final Map<String,Object> parameters) {
		return (Long) super.queryForObject("MARK_MEMBERSHIP_CARD.count", parameters);
	}
	
	/**
	 * 查询
	 * @param parameters
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<MarkMembershipCardDetails> query(final Map<String,Object> parameters) {
		return super.queryForList("MARK_MEMBERSHIP_CARD.query", parameters);
	}
	
	/**
	 * 根据主键查询
	 * @param id
	 * @return
	 */
	public MarkMembershipCardDetails queryByPK(final Serializable id) {
		return (MarkMembershipCardDetails) super.queryForObject("MARK_MEMBERSHIP_CARD.queryByPK", id);
	}
	
	/**
	 * 删除
	 * @param id
	 */
	public void delete(final Serializable id) {
		super.delete("MARK_MEMBERSHIP_CARD.delete", id);
	}
	
	/**
	 * 绑定优惠券
	 * @param id
	 */
	public void updateBindingDiscount(final Serializable id) {
		super.update("MARK_MEMBERSHIP_CARD.updateBindingDiscount",id);
	}
	
	/**
	 * 解绑优惠券
	 * @param id
	 */
	public void updateUnBindingDiscount(final Serializable id) {
		super.update("MARK_MEMBERSHIP_CARD.updateUnBindingDiscount",id);
	}
	/**
	 * 绑定渠道
	 * @param card
	 * @return
	 * @author shangzhengyuan
	 * @create 2011-04-25
	 */
	public int updateBindChannel(final MarkMembershipCard card){
		return super.update("MARK_MEMBERSHIP_CARD.updateBindChannel",card);
	}
	
	/**
	 * 根据已使用的会员卡号激活此次会员卡批次
	 * @param code 会员卡号
	 */
	public void active(final String code) {
		if (null == code) {
			return;
		}
		super.update("MARK_MEMBERSHIP_CARD.active",code);
	}
	
	/**
	 * 返回使用会员卡的用户标识
	 * @param id
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<String> queryUserIdByCardId(final Serializable id) {
		return super.queryForList("MARK_MEMBERSHIP_CARD.queryUserIdByCardId",id);
	}
}
