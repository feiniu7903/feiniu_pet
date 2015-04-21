package com.lvmama.tnt.helpcenter.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lvmama.tnt.comm.vo.Page;
import com.lvmama.tnt.helpcenter.mapper.TntAnnouncementMapper;
import com.lvmama.tnt.user.po.TntAnnouncement;
import com.lvmama.tnt.user.po.TntCompanyType;
import com.lvmama.tnt.user.po.TntUserDetail;
import com.lvmama.tnt.user.service.TntAnnouncementService;

@Repository("tntAnnouncementService")
public class TntAnnouncementServiceImpl implements TntAnnouncementService{
	
	@Autowired
	private TntAnnouncementMapper tntAnnouncementMapper;
	
	@Override
	public boolean insertTntAnnouncement(TntAnnouncement tntAnnouncement) {
		return tntAnnouncementMapper.insertTntAnnouncement(tntAnnouncement)>0;
	}

	@Override
	public List<TntAnnouncement> queryTntAnnouncementList(Page<TntAnnouncement> page) {
		return tntAnnouncementMapper.queryTntAnnouncementList(page);
	}

	@Override
	public int queryCountAnnouncement() {
		return tntAnnouncementMapper.queryCountAnnouncement();
	}

	@Override
	public TntAnnouncement selectByPrimaryKey(Long announcementId) {
		return tntAnnouncementMapper.selectByPrimaryKey(announcementId);
	}

	@Override
	public boolean delete(Long announcementId) {
		if (announcementId != null)
			return tntAnnouncementMapper.delete(announcementId) > 0;
		return false;
	}

	@Override
	public boolean update(TntAnnouncement tntAnnouncement) {
		return tntAnnouncementMapper.update(tntAnnouncement) > 0;
	}

	@Override
	public int count(TntAnnouncement tntAnnouncement) {
		return tntAnnouncementMapper.findCount(tntAnnouncement);
	}

	@Override
	public List<TntAnnouncement> fetchPage(Page<TntAnnouncement> page) {
		return tntAnnouncementMapper.fetchPage(page);
	}

}
