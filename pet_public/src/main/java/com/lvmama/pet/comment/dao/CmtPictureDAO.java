/**
 * 
 */
package com.lvmama.pet.comment.dao;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.vo.comment.CmtPictureVO;

/**
 * @author liuyi
 *
 */
public class CmtPictureDAO  extends BaseIbatisDAO {
	
	/**
	 * 根据条件查询图片
	 * @param parameters 查询条件
	 * @return 列表
	 */
	@SuppressWarnings("unchecked")
	public List<CmtPictureVO> query(final Map<String, Object> parameters) {
		return (List<CmtPictureVO>) super.queryForList("CMT_PICTURE.query", parameters);
	}

	/**
	 * 上传点评图片
	 * @param cmtPicture  点评图片
	 * @return 图片表识
	 */
	public long insert(final CmtPictureVO cmtPicture) {
		super.insert("CMT_PICTURE.insert", cmtPicture);
		return cmtPicture.getPictureId();
	}

	/**
	 * 修改图片审核状态
	 * @param cmtPicture 修改图片
	 * @return 表识
	 */
	public int update(final CmtPictureVO cmtPicture) {
		return super.update("CMT_PICTURE.update", cmtPicture);
	}

	/**
	  * 根据条件查询图片数
	  * @param parameters 查询条件
	  * @return 数量
	 */
	public Long count(final Map<String, Object> parameters) {
		return (Long) super.queryForObject("CMT_PICTURE.count", parameters);
	}

	/**
	 * 根据条件查询图片
	 * @param id 表识
	 * @return 图片
	 */
	public CmtPictureVO queryCmtPictureByKey(final long id) {
		return (CmtPictureVO) super.queryForObject("CMT_PICTURE.queryByKey", id);
	}

}
