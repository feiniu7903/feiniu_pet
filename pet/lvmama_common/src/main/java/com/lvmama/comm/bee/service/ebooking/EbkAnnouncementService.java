package com.lvmama.comm.bee.service.ebooking;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.bee.po.ebooking.EbkAnnouncement;

/**
 * 
 * EBooking公告.
 *
 */
public interface EbkAnnouncementService {
	/**
	 * 添加公告.
	 * @param ebkAnnouncement
	 */
	public void insert(EbkAnnouncement ebkAnnouncement);
	
	/**
	 * 更新公告.
	 * @param ebkAnnouncement
	 */
	public void update(EbkAnnouncement ebkAnnouncement);
	
	/**
	 * 分页查询公告列表.
	 * @param params
	 * @return
	 */
	public List<EbkAnnouncement> findEbkAnnouncementListByMap(Map<String,Object> params);
	/**
	 * 统计公告列表数量.
	 * @param params
	 * @return
	 */
	public long countEbkAnnouncementListByMap(Map<String,Object> params);
	/**
	 * 根据ID查询公告信息.
	 * @param ebkAnnouncement
	 * @return
	 */
	public EbkAnnouncement selectByPrimaryKey(Long ebkAnnouncement);
	
}
