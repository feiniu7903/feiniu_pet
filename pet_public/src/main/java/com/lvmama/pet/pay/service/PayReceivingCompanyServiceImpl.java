package com.lvmama.pet.pay.service;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.pet.po.pay.PayReceivingCompany;
import com.lvmama.comm.pet.service.pay.PayReceivingCompanyService;
import com.lvmama.pet.pay.dao.PayReceivingCompanyDAO;

public class PayReceivingCompanyServiceImpl implements PayReceivingCompanyService {
	
	private PayReceivingCompanyDAO payReceivingCompanyDAO;

	@Override
	public Long savePayReceivingCompany(PayReceivingCompany payReceivingCompany) {
		return payReceivingCompanyDAO.savePayReceivingCompany(payReceivingCompany);
	}

	@Override
	public PayReceivingCompany selectReceivingCompanyByPK(Long receivingCompanyId) {
		return payReceivingCompanyDAO.selectReceivingCompanyByPK(receivingCompanyId);
	}

	@Override
	public List<PayReceivingCompany> selectPayReceivingCompanyByParamMap(Map<String, String> paramMap) {
		return payReceivingCompanyDAO.selectPayReceivingCompanyByParamMap(paramMap);
	}

	@Override
	public Long selectPayReceivingCompanyByParamMapCount(Map<String, String> paramMap) {
		return payReceivingCompanyDAO.selectPayReceivingCompanyByParamMapCount(paramMap);
	}

	
	
	public PayReceivingCompanyDAO getPayReceivingCompanyDAO() {
		return payReceivingCompanyDAO;
	}

	public void setPayReceivingCompanyDAO(PayReceivingCompanyDAO payReceivingCompanyDAO) {
		this.payReceivingCompanyDAO = payReceivingCompanyDAO;
	}
}
