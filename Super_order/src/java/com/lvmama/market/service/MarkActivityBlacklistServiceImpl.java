package com.lvmama.market.service;

import com.lvmama.comm.bee.service.market.MarkActivityBlacklistService;
import com.lvmama.comm.pet.po.mark.MarkActivityBlacklist;
import com.lvmama.market.dao.MarkActivityBlacklistDAO;

import java.util.*;

/**
 * @author shihui
 */
public class MarkActivityBlacklistServiceImpl implements MarkActivityBlacklistService {
	
	private MarkActivityBlacklistDAO markActivityBlacklistDAO;

	@Override
	public List<MarkActivityBlacklist> getMarkActivityBlacklist(
			Map<String, Object> paramMap) {
		return markActivityBlacklistDAO.getMarkActivityBlacklist(paramMap);
	}

	public void setMarkActivityBlacklistDAO(
			MarkActivityBlacklistDAO markActivityBlacklistDAO) {
		this.markActivityBlacklistDAO = markActivityBlacklistDAO;
	}

	@Override
	public Long getMarkActivityBlacklistCount(Map<String, Object> paramMap) {
		return markActivityBlacklistDAO.getMarkActivityBlacklistCount(paramMap);
	}

	@Override
	public Long saveMarkActivityBlacklist(MarkActivityBlacklist record) {
		return markActivityBlacklistDAO.insert(record);
	}

	@Override
	public void updateMarkActivityBlacklist(MarkActivityBlacklist record) {
		markActivityBlacklistDAO.update(record);
	}

	@Override
	public void deleteMarkActivityBlacklist(Long blackId) {
		markActivityBlacklistDAO.delete(blackId);
	}

	@Override
	public MarkActivityBlacklist selectByPrimaryKey(Long actId) {
		return markActivityBlacklistDAO.selectByPrimaryKey(actId);
	}

	@Override
	public Long checkIsExisted(Map<String, Object> paramMap) {
		return markActivityBlacklistDAO.checkIsExisted(paramMap);
	}
}
