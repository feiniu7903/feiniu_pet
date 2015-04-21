package com.lvmama.pet.pay.service;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.pet.po.pay.PayReceivingBank;
import com.lvmama.comm.pet.service.pay.PayReceivingBankService;
import com.lvmama.pet.pay.dao.PayReceivingBankDAO;

public class PayReceivingBankServiceImpl implements PayReceivingBankService {
	
	private PayReceivingBankDAO payReceivingBankDAO;

	@Override
	public PayReceivingBank selectReceivingBankByPK(Long receivingBankId) {
		return payReceivingBankDAO.selectReceivingBankByPK(receivingBankId);
	}
	public PayReceivingBank selectReceivingBankByBankCardNo(String bankCardNo) {
		return payReceivingBankDAO.selectReceivingBankByBankCardNo(bankCardNo);
	}

	@Override
	public List<PayReceivingBank> selectPayReceivingBankByParamMap(Map<String, String> paramMap) {
		return payReceivingBankDAO.selectPayReceivingBankByParamMap(paramMap);
	}

	@Override
	public Long selectPayReceivingBankByParamMapCount(Map<String, String> paramMap) {
		return payReceivingBankDAO.selectPayReceivingBankByParamMapCount(paramMap);
	}
	public List<String> selectBankName(String receivingAccount){
		return payReceivingBankDAO.selectBankName(receivingAccount);
	}
	public List<String> selectEnableReceivingAccount(){
		return payReceivingBankDAO.selectEnableReceivingAccount();
	}

	public PayReceivingBankDAO getPayReceivingBankDAO() {
		return payReceivingBankDAO;
	}

	public void setPayReceivingBankDAO(PayReceivingBankDAO payReceivingBankDAO) {
		this.payReceivingBankDAO = payReceivingBankDAO;
	}	
}
