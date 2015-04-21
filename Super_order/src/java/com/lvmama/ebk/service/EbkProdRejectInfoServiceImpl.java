package com.lvmama.ebk.service;

import java.util.ArrayList;
import java.util.List;

import com.lvmama.comm.bee.po.ebooking.EbkProdRejectInfo;
import com.lvmama.comm.bee.service.ebooking.EbkProdRejectInfoService;
import com.lvmama.ebk.dao.EbkProdRejectInfoDAO;

public class EbkProdRejectInfoServiceImpl implements EbkProdRejectInfoService {
	
	
	private EbkProdRejectInfoDAO ebkProdRejectInfoDAO;
	@Override
	public Long insert(EbkProdRejectInfo ebkProdRejectInfoDO) {
		return ebkProdRejectInfoDAO.insertEbkProdRejectInfoDO(ebkProdRejectInfoDO);
	}

	public List<Long> insertList(Long ebkProdProductId,List<EbkProdRejectInfo> ebkProdRejectInfoDOList) {
		EbkProdRejectInfo ebkProdRejectInfoDO=new EbkProdRejectInfo();
		ebkProdRejectInfoDO.setProductId(ebkProdProductId);
		delete(ebkProdRejectInfoDO);
		
		List<Long> rejectInfoIdList=new ArrayList<Long>();
		for (EbkProdRejectInfo ebkProdRejectInfo : ebkProdRejectInfoDOList) {
			Long rejectInfoId=insert(ebkProdRejectInfo);
			rejectInfoIdList.add(rejectInfoId);
		}
		return rejectInfoIdList;
	}
	
	@Override
	public Integer update(EbkProdRejectInfo ebkProdRejectInfoDO) {
		return ebkProdRejectInfoDAO.updateEbkProdRejectInfoDO(ebkProdRejectInfoDO);
	}

	@Override
	public Integer delete(EbkProdRejectInfo ebkProdRejectInfoDO) {
		return ebkProdRejectInfoDAO.delete(ebkProdRejectInfoDO);
	}

	@Override
	public List<EbkProdRejectInfo> query(EbkProdRejectInfo ebkProdRejectInfoDO) {
		return ebkProdRejectInfoDAO.findListByExample(ebkProdRejectInfoDO);
	}
	
	
	
	
	
	

	public void setEbkProdRejectInfoDAO(EbkProdRejectInfoDAO ebkProdRejectInfoDAO) {
		this.ebkProdRejectInfoDAO = ebkProdRejectInfoDAO;
	}

}
