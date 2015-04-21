package com.lvmama.comm.pet.service.mobile;

import java.util.List;

import com.lvmama.comm.pet.po.mobile.CreditCard;

/**
 * 用户信用卡服务接口
 * 
 * @author likun
 * @date 2014/3/26
 */
public interface CreditCardService {
	/**
	 * 新增用户信用卡id，当信用卡已经存在的时候返回null，如果库中不存在（根据userNo和cardNo判断），则返回新增以后的信用卡信息
	 */
	public CreditCard insert(CreditCard model) throws Exception;

	/**
	 * 根据用户编号获取用户所有信用卡信息，如果传入的userNo为空则返回null
	 * 
	 * @param userNo
	 * @return
	 * @throws Exception
	 */
	public List<CreditCard> selectCreditCardByUserNo(String userNo)
			throws Exception;
}
