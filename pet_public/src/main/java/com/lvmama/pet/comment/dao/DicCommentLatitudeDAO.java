/**
 * 
 */
package com.lvmama.pet.comment.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.comment.DicCommentLatitude;

/**
 * @author liuyi
 * 点评维度字典DAO
 */
public class DicCommentLatitudeDAO extends BaseIbatisDAO {
	
	
	/**
	 * 增加字典记录
	 * @param dic 字典对象
	 * @return 插入的对象标识
	 */
	public Long insert(final DicCommentLatitude dic) {
		return (Long) super.insert("DIC_COMMENT_LATITUDE.insert", dic);
	}

	/**
	 * 查询纬度字典列表
	 * @param parames 查询参数
	 * @return 字典表列表
	 */
	@SuppressWarnings("unchecked")
	public List<DicCommentLatitude> getDicCommentLatitudeList(final Map<String, Object> parames) {
		if (parames.size() == 0) {
			return new ArrayList<DicCommentLatitude>();
		} else {
			return (List<DicCommentLatitude>) super.queryForList("DIC_COMMENT_LATITUDE.query", parames);
		}
	}

	/**
	 * 修改字典记录
	 * @param dic 字典对象
	 * @return 修改个数
	 */
	public int update(final DicCommentLatitude dic) {
		return super.update("DIC_COMMENT_LATITUDE.update", dic);
	}

	/**
	 * 根据主键查询
	 * @param id 主键
	 * @return 字典对象
	 */
	public DicCommentLatitude queryByKey(final String id) {
		return (DicCommentLatitude) super.queryForObject("DIC_COMMENT_LATITUDE.queryByKey", id);
	}
	
	/**
	 * 更新点评主题时的点评维度对应表
	 * @param param
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<DicCommentLatitude> queryUpdateLatitudeMapping(final Map<String, Object> param) {
		return (List<DicCommentLatitude>) super.queryForList("DIC_COMMENT_LATITUDE.queryUpdateLatitudeMapping", param);
	}

}
