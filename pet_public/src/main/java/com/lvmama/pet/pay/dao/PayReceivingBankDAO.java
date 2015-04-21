package com.lvmama.pet.pay.dao;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.pay.PayReceivingBank;


public class PayReceivingBankDAO extends BaseIbatisDAO {
	
	private static String CONFIG_NAME="PAY_RECEIVING_BANK";

	
	public PayReceivingBank selectReceivingBankByPK(Long receivingBankId) {
		return (PayReceivingBank)super.queryForObject(CONFIG_NAME+".selectReceivingBankByPK", receivingBankId);
	}
	public PayReceivingBank selectReceivingBankByBankCardNo(String bankCardNo) {
		return (PayReceivingBank)super.queryForObject(CONFIG_NAME+".selectReceivingBankByBankCardNo", bankCardNo);
	}
	
	@SuppressWarnings("unchecked")
	public List<PayReceivingBank> selectPayReceivingBankByParamMap(Map<String, String> paramMap){
		return super.queryForList(CONFIG_NAME+".selectPayReceivingBankByParamMap", paramMap);
	}	
	public Long selectPayReceivingBankByParamMapCount(Map<String, String> paramMap){
		return (Long) super.queryForObject(CONFIG_NAME+".selectPayReceivingBankByParamMapCount", paramMap);
	}
	@SuppressWarnings("unchecked")
	public List<String> selectEnableReceivingAccount(){
		return (List<String>) super.queryForList(CONFIG_NAME+".selectEnableReceivingAccount");
	}
	@SuppressWarnings("unchecked")
	public List<String> selectBankName(String receivingAccount){
		return (List<String>) super.queryForList(CONFIG_NAME+".selectBankName",receivingAccount);
	}
	
}
