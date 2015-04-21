package com.lvmama.pet.mark.dao;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.mark.MarkMembershipCardDiscount;
import com.lvmama.comm.pet.vo.mark.MarkMembershipCardDiscountDetails;

public class MarkMembershipCardDiscountDAO extends BaseIbatisDAO {
	/**
	 * 插入
	 * @param codes
	 */
	public void insert(final MarkMembershipCardDiscount markMembershipCardDiscount) {
		super.insert("MARK_MEMBERSHIP_CARD_DISCOUNT.insert",markMembershipCardDiscount);
	}
	
	/**
	 * 查询
	 * @param parameters
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<MarkMembershipCardDiscountDetails> query(final Map<String,Object> parameters) {
		return super.queryForList("MARK_MEMBERSHIP_CARD_DISCOUNT.query", parameters);
	}
	
	/**
	 * 删除
	 * @param parameters
	 */
	public void delete(final Map<String,Object> parameters) {
		super.delete("MARK_MEMBERSHIP_CARD_DISCOUNT.delete", parameters);
	}
}
