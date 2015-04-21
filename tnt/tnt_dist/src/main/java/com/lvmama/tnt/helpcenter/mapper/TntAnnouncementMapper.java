package com.lvmama.tnt.helpcenter.mapper;

import java.util.List;

import com.lvmama.tnt.comm.vo.Page;
import com.lvmama.tnt.user.po.TntAnnouncement;
import com.lvmama.tnt.user.po.TntCompanyType;
import com.lvmama.tnt.user.po.TntUser;

public interface TntAnnouncementMapper {
	
	/**
	 * 保存平台公告中心数据
	 * @param tntAnnouncement
	 * @return
	 */
    public int insertTntAnnouncement(TntAnnouncement tntAnnouncement);
    
    /**
     * 获取平台公告中心数据
     * @return
     */
    public List<TntAnnouncement> queryTntAnnouncementList(Page<TntAnnouncement> page);
    
    /**
     * 查询平台公告中心数据条数
     * @return
     */
    public int queryCountAnnouncement();
    
    /**
     * 根据主键获取TntAnnouncement对象
     */
    public TntAnnouncement selectByPrimaryKey(Long announcementId);
    
	/**
	 * 更新平台公告信息
	 * @param TntAnnouncement
	 * @return
	 */
	public int update(TntAnnouncement tntAnnouncement);

	/**
	 * 根据announcementId删除平台公告
	 * 
	 * @param announcementId
	 * @return
	 */
	public int delete(Long announcementId);
	
	public List<TntAnnouncement> fetchPage(Page<TntAnnouncement> page);
	
	public int findCount(TntAnnouncement entity);
}
