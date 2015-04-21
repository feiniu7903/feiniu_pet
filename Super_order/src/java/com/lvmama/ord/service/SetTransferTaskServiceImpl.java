package com.lvmama.ord.service;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.pet.service.fin.SetTransferTaskService;
import com.lvmama.ord.dao.SetTransferTaskDAO;

public class SetTransferTaskServiceImpl implements SetTransferTaskService {
	
	private SetTransferTaskDAO setTransferTaskDAO;
	@Override
	public Object insert(Long orderId) {
		return setTransferTaskDAO.insert(orderId);
	}

	@Override
	public List<Map<String,Object>> select() {
		return setTransferTaskDAO.select();
	}

	@Override
	public int delete(Long orderId) {
		return setTransferTaskDAO.delete(orderId);
	}

	public void setSetTransferTaskDAO(SetTransferTaskDAO setTransferTaskDAO) {
		this.setTransferTaskDAO=setTransferTaskDAO;
	}

}
