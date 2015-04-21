package com.lvmama.pet.pay.dao;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.pay.PayReceivingCompany;


public class PayReceivingCompanyDAO extends BaseIbatisDAO {
	
	private static String CONFIG_NAME="PAY_RECEIVING_COMPANY";

	public Long savePayReceivingCompany(PayReceivingCompany payReceivingCompany){
		return (Long)super.insert(CONFIG_NAME+".insert", payReceivingCompany);
	}
	public PayReceivingCompany selectReceivingCompanyByPK(Long receivingCompanyId) {
		return (PayReceivingCompany)super.queryForObject(CONFIG_NAME+".selectReceivingCompanyByPK", receivingCompanyId);
	}
	@SuppressWarnings("unchecked")
	public List<PayReceivingCompany> selectPayReceivingCompanyByParamMap(Map<String, String> paramMap){
		return super.queryForList(CONFIG_NAME+".selectPayReceivingCompanyByParamMap", paramMap);
	}	
	public Long selectPayReceivingCompanyByParamMapCount(Map<String, String> paramMap){
		return (Long) super.queryForObject(CONFIG_NAME+".selectPayReceivingCompanyByParamMapCount", paramMap);
	}
}
