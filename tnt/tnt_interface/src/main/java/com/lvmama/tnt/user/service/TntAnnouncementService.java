package com.lvmama.tnt.user.service;

import java.util.List;

import com.lvmama.tnt.comm.vo.Page;
import com.lvmama.tnt.user.po.TntAnnouncement;

public interface TntAnnouncementService {

	/**
	 * 保存平台公告中心数据
	 * 
	 * @param tntAnnouncement
	 * @return
	 */
	public boolean insertTntAnnouncement(TntAnnouncement tntAnnouncement);

	/**
	 * 获取平台公告中心数据
	 * 
	 * @return
	 */
	public List<TntAnnouncement> queryTntAnnouncementList(
			Page<TntAnnouncement> page);

	/**
	 * 查询平台公告中心数据条数
	 * 
	 * @return
	 */
	public int queryCountAnnouncement();

	/**
	 * 根据主键获取TAnnouncement对象
	 */
	public TntAnnouncement selectByPrimaryKey(Long announcementId);

	/**
	 * 根据announcementId删除平台公告
	 * 
	 * @param announcementId
	 * @return
	 */
	public boolean delete(Long announcementId);

	/**
	 * 更新平台公告信息
	 * 
	 * @param TntAnnouncement
	 * @return
	 */
	public boolean update(TntAnnouncement tntAnnouncement);

	/**
	 * 根据条件查询条目数
	 * 
	 * @param tntAnnouncement
	 * @return
	 */
	public int count(TntAnnouncement tntAnnouncement);

	/**
	 * 分页获取TntAnnouncement数据集
	 * 
	 * @param Page
	 *            <TntAnnouncement>
	 * @return
	 */
	public List<TntAnnouncement> fetchPage(Page<TntAnnouncement> page);

}
