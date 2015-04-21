/**
 * 
 */
package com.lvmama.order.dao;

import java.util.List;

import org.springframework.util.Assert;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.bee.po.ord.OrdOrderForPaymentSms;

/**
 * @author yangbin
 *
 */
public class OrderForPaymentSmsDAO extends BaseIbatisDAO{

	public OrdOrderForPaymentSms selectByMobileAndCode(final String mobile,final String code){
		Assert.hasText(mobile);
		Assert.hasText(code);
		
		OrdOrderForPaymentSms record = new OrdOrderForPaymentSms();
		record.setMobile(mobile);
		record.setSmsCode(code);
		List<OrdOrderForPaymentSms> list = super.queryForList("ORDER_FOR_PAYMENT_SMS.selectByMobileAndCode",record);
		if(list.isEmpty()){
			return null;
		}else{
			return list.get(0);
		}
	}
	
	/**
	 * 暂时只针对状态的变更，不做其他属性修改
	 * @param record
	 */
	public void updateByPrimaryKey(OrdOrderForPaymentSms record){
		super.update("ORDER_FOR_PAYMENT_SMS.updateByPrimaryKey",record);
	}
	
	public void insert(OrdOrderForPaymentSms record){
		super.insert("ORDER_FOR_PAYMENT_SMS.insert",record);
	}
}
