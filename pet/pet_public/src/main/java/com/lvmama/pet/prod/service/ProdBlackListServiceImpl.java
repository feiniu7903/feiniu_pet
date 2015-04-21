package com.lvmama.pet.prod.service;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.bee.po.prod.ProdBlackList;
import com.lvmama.comm.pet.service.prod.ProdBlackListService;
import com.lvmama.pet.prod.dao.ProdBlackListDao;

public class ProdBlackListServiceImpl implements ProdBlackListService{
	
	private ProdBlackListDao prodBlackListDao;
	
	@Override
	public void insertBlackList(List<ProdBlackList> prodBlackLists) {
		prodBlackListDao.insertBlackList(prodBlackLists);
	}

	@Override
	public void updateBlackList(ProdBlackList prodBlackList) {
		prodBlackListDao.updateBlackList(prodBlackList);
	}

	@Override
	public void deleteBlackList(ProdBlackList prodBlackList) {
		prodBlackListDao.deleteBlackList(prodBlackList);
	}

	@Override
	public List<ProdBlackList> queryBlackListByParam(Map<String, Object> param) {
		return prodBlackListDao.queryBlackListByParam(param);
	}

	public void setProdBlackListDao(ProdBlackListDao prodBlackListDao) {
		this.prodBlackListDao = prodBlackListDao;
	}

	@Override
	public Integer selectRowCount(Map<String, Object> param) {
		return prodBlackListDao.selectRowCount(param);
	}
	
	@Override
	public List<ProdBlackList> queryBlackListByBlacks(ProdBlackList prodBlackList) {
		return prodBlackListDao.queryBlackListByBlack(prodBlackList);	
	}
}
