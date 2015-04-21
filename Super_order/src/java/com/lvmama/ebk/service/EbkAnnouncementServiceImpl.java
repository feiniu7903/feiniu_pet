package com.lvmama.ebk.service;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.bee.po.ebooking.EbkAnnouncement;
import com.lvmama.comm.bee.service.ebooking.EbkAnnouncementService;
import com.lvmama.ebk.dao.EbkAnnouncementDAO;

public class EbkAnnouncementServiceImpl implements EbkAnnouncementService{
	
	private EbkAnnouncementDAO ebkAnnouncementDAO;
	
	@Override
	public void insert(EbkAnnouncement ebkAnnouncement) {
		this.ebkAnnouncementDAO.insert(ebkAnnouncement);
	}

	@Override
	public void update(EbkAnnouncement ebkAnnouncement) {
		 this.ebkAnnouncementDAO.updateByPrimaryKey(ebkAnnouncement);
		
	}

	@Override
	public List<EbkAnnouncement> findEbkAnnouncementListByMap(
			Map<String, Object> params) {
		
		return this.ebkAnnouncementDAO.selectByExample(params);
	}
	
	@Override
	public long countEbkAnnouncementListByMap(Map<String, Object> params) {
		
		return this.ebkAnnouncementDAO.countByExample(params);
	}
	
	@Override
	public EbkAnnouncement selectByPrimaryKey(Long announcementId) {
		
		return this.ebkAnnouncementDAO.selectByPrimaryKey(announcementId);
	}

	public void setEbkAnnouncementDAO(EbkAnnouncementDAO ebkAnnouncementDAO) {
		this.ebkAnnouncementDAO = ebkAnnouncementDAO;
	}

	
	
}
