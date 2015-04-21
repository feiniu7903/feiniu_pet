/**
 * 
 */
package com.lvmama.pet.comment.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.vo.comment.CmtLatitudeVO;

/**
 * 维度管理DAO
 * @author liuyi
 *
 */
public class CmtLatitudeDAO  extends BaseIbatisDAO {
	
	/**
	 * 根据条件查询维度
	 * @param parameters 查询条件
	 * @return 维度列表
	 */
	@SuppressWarnings("unchecked")
	public List<CmtLatitudeVO> query(final Map<String, Object> parameters) {
		if(parameters.size() == 0)
		{
			return new ArrayList<CmtLatitudeVO>();
		}
		else
		{
			return (List<CmtLatitudeVO>) super.queryForList("CMT_COMMENT_LATITUDE.query", parameters);
		}
		
	}

	/**
	 * 插入维度
	 * @param cmtLatitude  维度
	 * @return 维度表识
	 */
	public Long insert(final CmtLatitudeVO cmtLatitude) {
		super.insert("CMT_COMMENT_LATITUDE.insert", cmtLatitude);
		return cmtLatitude.getCmtLatitudeId();
	}

	/**
	 * 根据KEY查对象
	 * @param  id 表识
	 * @return PO
	 */
	public CmtLatitudeVO queryPOByKey(final long id) {
		return (CmtLatitudeVO) super.queryForObject("CMT_COMMENT_LATITUDE.queryByKey", id);
	}
	
	public void updateLatitudeForChangedCmtTitle(final Map<String, Object> param) {
		super.update("CMT_COMMENT_LATITUDE.updateLatitudeForChangedCmtTitle", param);
	}


}
