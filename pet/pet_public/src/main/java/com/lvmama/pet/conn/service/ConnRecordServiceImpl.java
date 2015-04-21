package com.lvmama.pet.conn.service;

import java.util.List;

import com.lvmama.comm.pet.po.conn.ConnRecord;
import com.lvmama.comm.pet.po.conn.ConnType;
import com.lvmama.comm.pet.service.conn.ConnRecordService;
import com.lvmama.comm.pet.vo.Page;
import com.lvmama.pet.conn.dao.ConnRecordDAO;
import com.lvmama.pet.conn.dao.ConnTypeDAO;

public class ConnRecordServiceImpl implements ConnRecordService{
	private ConnRecordDAO connRecordDAO;
	private ConnTypeDAO connTypeDAO;
		
	public void setConnTypeDAO(ConnTypeDAO connTypeDAO) {
		this.connTypeDAO = connTypeDAO;
	}

	@Override
	public Long saveConnRecord(ConnRecord connRecord) {
		return connRecordDAO.saveConnRecord(connRecord);
	}

	public void setConnRecordDAO(ConnRecordDAO connRecordDAO) {
		this.connRecordDAO = connRecordDAO;
	}
 
	@Override
	public Page<ConnRecord> queryConnRecordPage(String mobile,
			Long pageSize, Long currentPage) {
		Page pageConfig = Page.page(pageSize, currentPage);
		return connRecordDAO.queryConnRecordPage(mobile,pageConfig);
	}
	
	@Override
	public Page<ConnRecord> queryConnRecordPage(Long userId,
			Long pageSize, Long currentPage) {
		Page pageConfig = Page.page(pageSize, currentPage);
		return connRecordDAO.queryConnRecordPage(userId,pageConfig);
	}

	@Override
	public List<ConnType> queryConnTypeCallBack(String callBack) {
		return this.connTypeDAO.queryConnTypeByCallBack(callBack);
	}

	@Override
	public List<ConnType> quueryConnTypeByConnType(ConnType connType) {
		return this.connTypeDAO.queryConnTypeByConnType(connType);
	}

	@Override
	public Page<ConnRecord> queryConnRecordWithPage(ConnRecord connRecord, long pageSize, int page) {
		Page pageConfig = Page.page(pageSize, page);
		return this.connRecordDAO.queryConnRecordPage(connRecord, pageConfig);
	}
}
