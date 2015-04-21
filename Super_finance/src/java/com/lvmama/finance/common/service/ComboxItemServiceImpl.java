package com.lvmama.finance.common.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lvmama.finance.common.ibatis.dao.ComboxItemDAO;
import com.lvmama.finance.common.ibatis.po.ComboxItem;

@Service
public class ComboxItemServiceImpl implements ComboxItemService{
	
	@Autowired
	private ComboxItemDAO comboxItemDAO;
	
	public List<ComboxItem> getComboxItems(String sqlId){
		return comboxItemDAO.getComboxItem("COMBOX_DATA_SOURCE." + sqlId);
	}
	
}
