package com.lvmama.tnt.helpcenter.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lvmama.tnt.comm.vo.Page;
import com.lvmama.tnt.helpcenter.mapper.TntFAQMapper;
import com.lvmama.tnt.user.po.TntFAQ;
import com.lvmama.tnt.user.service.TntFAQService;

@Repository("tntFAQService")
public class TntFAQServiceImpl implements TntFAQService{
	
	@Autowired
	private TntFAQMapper tntFAQMapper;

	@Override
	public boolean insertTntFAQ(TntFAQ tntFAQ) {
		return tntFAQMapper.insertTntFAQ(tntFAQ)>0;
	}

	@Override
	public List<TntFAQ> queryTntFAQList(Page<TntFAQ> page) {
		return tntFAQMapper.queryTntFAQList(page);
	}

	@Override
	public int queryCountTntFAQ() {
		return tntFAQMapper.queryCountTntFAQ();
	}

	@Override
	public boolean delete(Long tntFAQId) {
		if (tntFAQId != null)
			return tntFAQMapper.delete(tntFAQId) > 0;
		return false;
	}

	@Override
	public boolean update(TntFAQ tntFAQ) {
		return tntFAQMapper.update(tntFAQ) > 0;
	}

	@Override
	public int count(TntFAQ tntFAQ) {
		return tntFAQMapper.findCount(tntFAQ);
	}

	@Override
	public List<TntFAQ> fetchPage(Page<TntFAQ> page) {
		return tntFAQMapper.fetchPage(page);
	}

	@Override
	public TntFAQ selectByPrimaryKey(Long tntFAQId) {
		return tntFAQMapper.selectByPrimaryKey(tntFAQId);
	}

	
}
