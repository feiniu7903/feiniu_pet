package com.lvmama.pet.pub.dao;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.pub.ComLogContent;

public class ComLogContentDAO extends BaseIbatisDAO {

	public Long insert(ComLogContent record) {
		return (Long) super.insert("COM_LOG_CONTENT.insert", record);
	}

	public ComLogContent selectComLogById(Long id) {
		return (ComLogContent) super.queryForObject("COM_LOG_CONTENT.selectComLogById", id);
	}
}