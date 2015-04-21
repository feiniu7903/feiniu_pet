package com.lvmama.tnt.user.service;

import java.util.List;

import com.lvmama.tnt.comm.vo.Page;
import com.lvmama.tnt.user.po.TntFAQ;

public interface TntFAQService {

	/**
	 * 保存常见问题数据
	 * @param tntFAQ
	 * @return
	 */
    public boolean insertTntFAQ(TntFAQ tntFAQ);
    
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
	 * 根据tntFAQId删除常见问题
	 * 
	 * @param tntFAQId
	 * @return
	 */
	public boolean delete(Long tntFAQId);
	
	/**
	 * 更新平台公告信息
	 * @param TntFAQ
	 * @return
	 */
	public boolean update(TntFAQ tntFAQ);
	
	/**
	 * 根据条件查询条目数
	 * 
	 * @param tntFAQ
	 * @return
	 */
	public int count(TntFAQ tntFAQ);
	
	/**
	 * 分页获取TntFAQ数据集
	 * 
	 * @param Page<TntFAQ>
	 * @return
	 */
	public List<TntFAQ> fetchPage(Page<TntFAQ> page);
}
