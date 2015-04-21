package com.lvmama.comm.bee.service.view;

import java.util.List;

import com.lvmama.comm.bee.po.prod.ViewPageTag;
import com.lvmama.comm.bee.po.prod.ViewTag;

/**
 * 页面标签关联
 * 
 * @author MrZhu
 *
 */
public interface ViewPageTagService {
	/**
	 * 根据pageId查询所有的tag
	 * @param pageId
	 * @return
	 */
	public List<ViewPageTag> selectByPageId(Long pageId);
	
	/**
	 * 新增一个标签
	 * @param viewPageTag
	 */
	public void insertTag(ViewPageTag viewPageTag);
	
	/**
	 * 删除一个标签
	 * @param pageTagId
	 */
	public void deletePageTag(Long pageTagId);
	
	/**
	 * 通过名称查找所有的tag
	 * @param name
	 * @return
	 */
	public List<ViewTag> searchViewTags(String name) ;
}
