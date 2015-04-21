/**
 * 
 */
package com.lvmama.passport.service;

import java.util.List;

import com.lvmama.comm.bee.po.pass.PassportMessage;
import com.lvmama.comm.bee.service.pass.PassportMessageService;
import com.lvmama.passport.dao.PassportMessageDAO;

/**
 * @author yangbin
 *
 */
public class PassportMessageServiceImpl implements PassportMessageService{

	private PassportMessageDAO passportMessageDAO;
	
	@Override
	public List<PassportMessage> selectList(String hostname,List<String> processor, int size) {
		return passportMessageDAO.selectList(hostname, processor, size);
	}

	@Override
	public void deleteByPK(Long PK) {
		passportMessageDAO.delteByPK(PK);
	}

	@Override
	public void add(PassportMessage message) {
		passportMessageDAO.insert(message);
	}

	public PassportMessageDAO getPassportMessageDAO() {
		return passportMessageDAO;
	}

	public void setPassportMessageDAO(PassportMessageDAO passportMessageDAO) {
		this.passportMessageDAO = passportMessageDAO;
	}

}
