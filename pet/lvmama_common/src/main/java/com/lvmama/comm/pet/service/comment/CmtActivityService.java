/**
 * 
 */
package com.lvmama.comm.pet.service.comment;

import java.util.List;

import com.lvmama.comm.pet.po.comment.CmtActivity;

/**
 * @author liuyi
 * 点评活动
 */
public interface CmtActivityService {

	/**
	 * 查询点评活动的列表
	 * @return 活动集合
	 */
	List<CmtActivity> query();
	/**
	 * 根据ID查询活动点评
	 *  1.广告点评    2.成功页面的广告    3,4 dest页面的点评
	 * @param id 参数
	 * @return 活动点评对象
	 */
	CmtActivity queryById(Long id);

	/**
	 * 修改活动的信息
	 * @param cmtActivity  参数
	 */
	void update(CmtActivity cmtActivity);
	
	/**
	 * 根据ID查询活动点评 
	 * @param activitySubject 活动
	 * @return 活动点评对象
	 */
	 CmtActivity queryByActivitySubject(final String activitySubject);
}
