package com.lvmama.comm.pet.service.fin;

import java.util.Map;

import com.lvmama.comm.hessian.RemoteService;
import com.lvmama.comm.pet.po.fin.SetSettlementPayment;
import com.lvmama.comm.pet.vo.Page;

@RemoteService("setSettlementPaymentService")
public interface SetSettlementPaymentService {
	
	Page<SetSettlementPayment> search(Map<String,Object> map);
	
	Page<SetSettlementPayment> getOrdSettlementPayments(Map<String, Object> map);
	
	void insertPayment(SetSettlementPayment payment);
}
