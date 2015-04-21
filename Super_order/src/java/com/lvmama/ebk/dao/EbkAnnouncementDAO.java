package com.lvmama.ebk.dao;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.bee.po.ebooking.EbkAnnouncement;

public class EbkAnnouncementDAO extends BaseIbatisDAO {

    public int countByExample(Map<String, Object> example) {
        Integer count = (Integer)  super.queryForObject("EBK_ANNOUNCEMENT.countByExample", example);
        return count.intValue();
    }

    public int deleteByPrimaryKey(Long announcementId) {
        int rows = super.delete("EBK_ANNOUNCEMENT.deleteByPrimaryKey", announcementId);
        return rows;
    }

    public void insert(EbkAnnouncement record) {
    	super.insert("EBK_ANNOUNCEMENT.insert", record);
    }

    @SuppressWarnings("unchecked")
	public List<EbkAnnouncement> selectByExample(Map<String, Object> example) {
        List<EbkAnnouncement> list = super.queryForList("EBK_ANNOUNCEMENT.selectByExample", example);
        return list;
    }

    public EbkAnnouncement selectByPrimaryKey(Long announcementId) {
        EbkAnnouncement record = (EbkAnnouncement) super.queryForObject("EBK_ANNOUNCEMENT.selectByPrimaryKey", announcementId);
        return record;
    }

    public int updateByPrimaryKey(EbkAnnouncement record) {
        int rows = super.update("EBK_ANNOUNCEMENT.updateByPrimaryKey", record);
        return rows;
    }

}