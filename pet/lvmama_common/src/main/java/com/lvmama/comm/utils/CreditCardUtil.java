package com.lvmama.comm.utils;

import org.apache.commons.beanutils.BeanUtils;

import com.lvmama.comm.pet.po.mobile.CreditCard;

/**
 * 信用卡工具类
 * 
 * @author likun
 * @date 2014/3/26
 */
public class CreditCardUtil {

	/**
	 * 秘钥
	 */
	private static final String KEY = "12345678";

	/**
	 * 行用卡信息加密
	 * 
	 * @param creditCard
	 * @return
	 * @throws Exception
	 */
	public static CreditCard encrypt(CreditCard creditCard) throws Exception {
		if (creditCard != null
				&& StringUtil.isNotEmptyString(creditCard.getCardNo())
				&& creditCard.getCardNo().length() > 4) {
			CreditCard model = new CreditCard();
			BeanUtils.copyProperties(model, creditCard);
			model.setCardNo(DES.encrypt(
					model.getCardNo().substring(0,
							model.getCardNo().length() - 4), KEY));
			return model;
		}
		return creditCard;
	}

	/**
	 * 信用卡信息解密
	 * 
	 * @param message
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static void decrypt(CreditCard creditCard) throws Exception {
		if (creditCard != null
				&& StringUtil.isNotEmptyString(creditCard.getCardNo())
				&& creditCard.getCardNo().length() > 4) {
			creditCard.setCardNo(DES.decrypt(creditCard.getCardNo(), KEY));
		}
	}
}
