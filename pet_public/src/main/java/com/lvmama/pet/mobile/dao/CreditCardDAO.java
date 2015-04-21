package com.lvmama.pet.mobile.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.mobile.CreditCard;

/**
 * 用户信用卡
 * 
 * @author likun
 * @date 2014/3/26
 */
public class CreditCardDAO extends BaseIbatisDAO {

	public static final String SQLMAPNAMESPACE = "CREDIT_CARD.";

	public CreditCardDAO() {
		super();
	}

	public void insert(CreditCard model) {
		super.insert(SQLMAPNAMESPACE + "insert", model);
	}

	public Long getCreditCardId() {
		return (Long) this.queryForObject(SQLMAPNAMESPACE + "getCreditCardId");
	}

	public List<CreditCard> selectCreditCardByUserNo(String userNo) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("userNo", userNo);
		return this.selectUserCreditCardByMap(paramMap);
	}

	@SuppressWarnings("unchecked")
	public List<CreditCard> selectUserCreditCardByMap(
			Map<String, Object> paramMap) {
		return super.queryForList(SQLMAPNAMESPACE + "selectCreditCardByMap",
				paramMap);
	}

}