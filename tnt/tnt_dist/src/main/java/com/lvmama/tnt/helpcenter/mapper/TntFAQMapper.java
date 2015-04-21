package com.lvmama.tnt.helpcenter.mapper;

import java.util.List;

import com.lvmama.tnt.comm.vo.Page;
import com.lvmama.tnt.user.po.TntAnnouncement;
import com.lvmama.tnt.user.po.TntFAQ;

public interface TntFAQMapper {
	
	/**
	 * 保存常见问题数据
	 * @param tntFAQ
	 * @return
	 */
    public int insertTntFAQ(TntFAQ tntFAQ);
    
	/**
	 * 获取常见问题数据
	 * @return
	 */
    public List<TntFAQ> queryTntFAQList(Page<TntFAQ> page);
    
    
	/**
	 * 获取常见问题数据条目数
	 * @return
	 */
    public int queryCountTntFAQ();
    
    /**
     * 根据主键获取TntFAQ对象
     */
    public TntFAQ selectByPrimaryKey(Long tntFAQId);
    
	/**
	 * 更新平台公告信息
	 * @param TntAnnouncement
	 * @return
	 */
	public int update(TntFAQ tntFAQ);

	/**
	 * 根据tntFAQId删除常见问题
	 * 
	 * @param tntFAQId
	 * @return
	 */
	public int delete(Long tntFAQId);
	
	public List<TntFAQ> fetchPage(Page<TntFAQ> page);
	
	public int findCount(TntFAQ entity);
}
