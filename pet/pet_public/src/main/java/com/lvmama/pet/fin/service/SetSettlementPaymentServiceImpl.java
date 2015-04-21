package com.lvmama.pet.fin.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lvmama.comm.hessian.HessianService;
import com.lvmama.comm.pet.po.fin.SetSettlementPayment;
import com.lvmama.comm.pet.service.fin.SetSettlementPaymentService;
import com.lvmama.comm.pet.vo.Page;
import com.lvmama.pet.BaseService;
import com.lvmama.pet.fin.dao.SetSettlementPaymentDAO;

/**
 * 
 * @author zhangwenjun
 *
 */
@HessianService("setSettlementPaymentService")
@Service("setSettlementPaymentService")
public class SetSettlementPaymentServiceImpl extends BaseService implements SetSettlementPaymentService{

	@Autowired
	private SetSettlementPaymentDAO setSettlementPaymentDAO;

	@Override
	public Page<SetSettlementPayment> search(Map<String,Object> map){
		return setSettlementPaymentDAO.search(map);
	}
	@Override
	public Page<SetSettlementPayment> getOrdSettlementPayments(
			Map<String, Object> map) {
		return setSettlementPaymentDAO.getOrdSettlementPayments(map);
	}

	@Override
	public void insertPayment(SetSettlementPayment payment){
		setSettlementPaymentDAO.insertPayment(payment);
	}
	
}
