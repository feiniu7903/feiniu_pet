package com.lvmama.pet.comment.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.pet.service.comment.CmtNewsService;
import com.lvmama.comm.vo.comment.CmtNewsVO;
import com.lvmama.pet.comment.dao.CmtNewsDAO;

/**
 * 小驴说事的实现类
 * @author yuzhizeng
 *
 */
public class CmtNewsServiceImpl implements CmtNewsService {
	
	/**
	 * 小驴说事数据库接口
	 */
	private CmtNewsDAO cmtNewsDAO;

	@Override
	public List<CmtNewsVO> query(final Map<String, Object> params) {
		if (null == params) {
			return null;
		}
		return cmtNewsDAO.query(params);
	}

	@Override
	public Long count(final Map<String, Object> params) {
		if (null == params) {
			return null;
		}
		return cmtNewsDAO.count(params);
	}
	
	@Override
	public CmtNewsVO queryByPk(final Serializable id) {
		if (null == id) {
			return null;
		}
		return cmtNewsDAO.queryByPk(id);
	}

	@Override
	public Long save(final CmtNewsVO news) {
		if (null == news) {
			return null;
		}
		if (null == news.getId()) {
			cmtNewsDAO.insert(news);
		} else {
			cmtNewsDAO.update(news);
		}
		return news.getId();
	}

	/**
	 * 查询小驴说事往期
	 */
	public List<CmtNewsVO> queryReview(Map<String, Object> params) {
		if (null == params) {
			return null;
		}
		return cmtNewsDAO.queryReviewBack(params);
	}
	
	
	public void setCmtNewsDAO(final CmtNewsDAO cmtNewsDAO) {
		this.cmtNewsDAO = cmtNewsDAO;
	}
	
}
