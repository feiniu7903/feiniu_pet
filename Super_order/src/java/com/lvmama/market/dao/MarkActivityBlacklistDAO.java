package com.lvmama.market.dao;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.mark.MarkActivityBlacklist;
import java.util.List;
import java.util.Map;

/**
 * @author shihui
 */
public class MarkActivityBlacklistDAO extends BaseIbatisDAO {

	@SuppressWarnings("unchecked")
	public List<MarkActivityBlacklist> getMarkActivityBlacklist(
			Map<String, Object> paramMap) {
		return super.queryForList(
				"MARK_ACTIVITY_BLACKLIST.getMarkActivityBlacklist", paramMap,true);
	}

	public Long insert(MarkActivityBlacklist record) {
		return (Long) super.insert("MARK_ACTIVITY_BLACKLIST.insert", record);
	}

	public int delete(Long id) {
		return super.delete("MARK_ACTIVITY_BLACKLIST.delete", id);
	}

	public Long getMarkActivityBlacklistCount(Map<String, Object> paramMap) {
		return (Long) super.queryForObject(
				"MARK_ACTIVITY_BLACKLIST.getMarkActivityBlacklistCount",
				paramMap);
	}

	public MarkActivityBlacklist selectByPrimaryKey(Long actId) {
		return (MarkActivityBlacklist) super.queryForObject(
				"MARK_ACTIVITY_BLACKLIST.selectByPrimaryKey", actId);
	}

	public void update(MarkActivityBlacklist record) {
		super.update("MARK_ACTIVITY_BLACKLIST.update",
				record);

	}
	
	public Long checkIsExisted(Map<String, Object> paramMap) {
		return (Long) super.queryForObject(
				"MARK_ACTIVITY_BLACKLIST.checkIsExisted",
				paramMap);
	}
}
