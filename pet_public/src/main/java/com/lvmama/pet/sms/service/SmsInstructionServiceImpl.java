package com.lvmama.pet.sms.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.lvmama.comm.pet.po.sms.SmsInstruction;
import com.lvmama.comm.pet.service.sms.SmsInstructionService;
import com.lvmama.pet.sms.dao.SmsInstructionDAO;

class SmsInstructionServiceImpl implements SmsInstructionService {
	@Autowired
	private SmsInstructionDAO smsInstructionDAO;
	
	@Override
	public SmsInstruction save(final SmsInstruction instructions) {
		smsInstructionDAO.save(instructions);
		return instructions;
	}

	@Override
	public void deleteByPrimaryKey(final String instructionCode) {
		smsInstructionDAO.deleteByPrimaryKey(instructionCode);
	}

	@Override
	public void update(final SmsInstruction instructions) {
		smsInstructionDAO.update(instructions);
	}

	@Override
	public SmsInstruction queryByPrimaryKey(final String instructionCode) {
		return smsInstructionDAO.queryByPrimaryKey(instructionCode);
	}

	@Override
	public List<SmsInstruction> query(final Map<String, Object> param) {
		return smsInstructionDAO.query(param);
	}

	@Override
	public List<SmsInstruction> queryAll() {
		return smsInstructionDAO.query(new HashMap<String, Object>(0));
	}

	@Override
	public Long count(Map<String, Object> param) {
		return smsInstructionDAO.count(param);
	}

}
