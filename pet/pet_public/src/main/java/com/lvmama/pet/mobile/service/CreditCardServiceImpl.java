package com.lvmama.pet.mobile.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.lvmama.comm.pet.po.mobile.CreditCard;
import com.lvmama.comm.pet.service.mobile.CreditCardService;
import com.lvmama.comm.utils.CreditCardUtil;
import com.lvmama.pet.mobile.dao.CreditCardDAO;

public class CreditCardServiceImpl implements CreditCardService {

	@Autowired
	private CreditCardDAO creditCardDAO;

	@Override
	public CreditCard insert(CreditCard model) throws Exception {
		if (model != null) {
			model = CreditCardUtil.encrypt(model);
			boolean cardIsExists = this.isExsits(model.getUserNo(),
					model.getCardNo());
			if (!cardIsExists) {
				model.setCreditCardId(this.creditCardDAO.getCreditCardId());
				this.creditCardDAO.insert(model);
				return model;
			}
		}
		return null;
	}

	public boolean isExsits(String userNo, String cardNo) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("userNo", userNo);
		paramMap.put("cardNo", cardNo);
		return this.creditCardDAO.selectUserCreditCardByMap(paramMap).size() >= 1 ? true
				: false;
	}

	@Override
	public List<CreditCard> selectCreditCardByUserNo(String userNo)
			throws Exception {
		List<CreditCard> list = this.creditCardDAO.selectCreditCardByUserNo(userNo);
		for (CreditCard creditCard : list) {
			CreditCardUtil.decrypt(creditCard);
		}
		return list;
	}
	
}
