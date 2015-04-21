package com.lvmama.finance.common.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lvmama.finance.common.ibatis.dao.ComLogDAO;
import com.lvmama.finance.common.ibatis.po.ComLog;

@Service
public class ComLogServiceImpl implements ComLogService {

	@Autowired
	private ComLogDAO comLogDAO;
	
	@Override
	public List<ComLog> searchLog(Long objectId, String objectType) {
		return comLogDAO.searchLog(objectId, objectType);
	}

}
