package com.lvmama.pet.pub.service;

import java.util.List;

import com.lvmama.comm.pet.po.pub.ComPinyin;
import com.lvmama.comm.pet.service.pub.ComPinyinService;
import com.lvmama.pet.pub.dao.ComPinyinDAO;

class ComPinyinServiceImpl implements ComPinyinService {
	private ComPinyinDAO comPinyinDAO;

	@Override
	public List<ComPinyin> findComPinyin(ComPinyin comPinyin) {
		return  comPinyinDAO.findComPinyin(comPinyin);
	}

	public ComPinyinDAO getComPinyinDAO() {
		return comPinyinDAO;
	}

	public void setComPinyinDAO(ComPinyinDAO comPinyinDAO) {
		this.comPinyinDAO = comPinyinDAO;
	}

}
