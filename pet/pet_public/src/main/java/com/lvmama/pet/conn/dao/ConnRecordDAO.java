package com.lvmama.pet.conn.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.conn.ConnRecord;
import com.lvmama.comm.pet.vo.Page;

public class ConnRecordDAO extends BaseIbatisDAO {

	public ConnRecordDAO() {
		super();
	}

	public Page<ConnRecord> queryConnRecordPage(String mobile,Page page) {
		Map<String, Object> para = new HashMap<String, Object>();
		para.put("mobile", mobile);
		//统计总数
		Long totalResultSize = (Long) super.queryForObject("CONN_RECORD.queryConnRecordPageCount",para);
		//分页查询
		page.setTotalResultSize(totalResultSize);
		para.put("startRows", page.getStartRows());
		para.put("endRows", page.getEndRows());
		List items = super.queryForList("CONN_RECORD.queryConnRecordPageConfig",para);
		page.setItems(items);
		return page;
	}
	
	public Page<ConnRecord> queryConnRecordPage(Long userId,Page page) {
		Map<String, Object> para = new HashMap<String, Object>();
		para.put("userId", userId);
		//统计总数
		Long totalResultSize = (Long) super.queryForObject("CONN_RECORD.queryConnRecordPageCount",para);
		//分页查询
		page.setTotalResultSize(totalResultSize);
		para.put("startRows", page.getStartRows());
		para.put("endRows", page.getEndRows());
		List items = super.queryForList("CONN_RECORD.queryConnRecordPageConfig",para);
		page.setItems(items);
		return page;
	}

	public Long saveConnRecord(ConnRecord connRecord) {
		return (Long) super.insert("CONN_RECORD.insertSelective",connRecord);
	}

	public Page<ConnRecord> queryConnRecordPage(ConnRecord connRecord, Page page) {
		Map<String, Object> para = new HashMap<String, Object>();
		para.put("mobile", connRecord.getMobile());
		para.put("callBack", connRecord.getCallBack());
		para.put("feedbackTime", connRecord.getFeedbackTime());
		para.put("feedbackTimeEnd", connRecord.getFeedbackTimeEnd());
		//统计总数
		Long totalResultSize = (Long) super.queryForObject("CONN_RECORD.queryConnRecordPageCount",para);
		//分页查询
		page.setTotalResultSize(totalResultSize);
		para.put("startRows", page.getStartRows());
		para.put("endRows", page.getEndRows());
		List items = super.queryForList("CONN_RECORD.queryConnRecordPageConfig",para);
		page.setItems(items);
		return page;
	}
	 
}