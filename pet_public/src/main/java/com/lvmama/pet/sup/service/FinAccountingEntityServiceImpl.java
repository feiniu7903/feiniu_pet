/**
 * 
 */
package com.lvmama.pet.sup.service;

import java.util.List;

import com.lvmama.comm.pet.po.sup.FinAccountingEntity;
import com.lvmama.comm.pet.service.sup.FinAccountingEntityService;
import com.lvmama.pet.sup.dao.FinAccountingEntityDAO;

/**
 * @author shihui
 *
 */
public class FinAccountingEntityServiceImpl implements FinAccountingEntityService{
	
	private FinAccountingEntityDAO finAccountingEntityDAO;

	@Override
	public List<FinAccountingEntity> selectEntityList() {
		return finAccountingEntityDAO.selectEntityList();
	}

	public void setFinAccountingEntityDAO(
			FinAccountingEntityDAO finAccountingEntityDAO) {
		this.finAccountingEntityDAO = finAccountingEntityDAO;
	}

}
