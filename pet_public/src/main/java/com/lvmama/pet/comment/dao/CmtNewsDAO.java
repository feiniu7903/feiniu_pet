package com.lvmama.pet.comment.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.vo.comment.CmtNewsVO;

public class CmtNewsDAO  extends BaseIbatisDAO{
	/**
	 * 日志输出器
	 */
	private static final Log LOG = LogFactory.getLog(CmtNewsDAO.class);

	@SuppressWarnings("unchecked")
	public List<CmtNewsVO> query(final Map<String, Object> params) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("查询小驴说事列表条件:" + params);
		}
		return super.queryForList("CMT_NEWS.query", params);
	}
	
	@SuppressWarnings("unchecked")
	public List<CmtNewsVO> queryReviewBack(Map<String, Object> params)
	{
		if (LOG.isDebugEnabled()) {
			LOG.debug("查询小驴说事往期列表条件:" + params);
		}
		return super.queryForList("CMT_NEWS.queryReview", params);
	}

	public Long count(final Map<String, Object> params) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("计数小驴说事条件:" + params);
		}
		return (Long) super.queryForObject("CMT_NEWS.count", params);
	}

	public CmtNewsVO queryByPk(final Serializable id) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("查询小驴说事,标识:" + id);
		}
		if (null == id) {
			return null;
		}
		return (CmtNewsVO) super.queryForObject("CMT_NEWS.queryByPk", id);
	}

	public Long insert(final CmtNewsVO news) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("新增小驴说事:" + news);
		}
		super.insert("CMT_NEWS.insert", news);
		return news.getId();
    }

	public Long update(final CmtNewsVO news) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("更新小驴说事:" + news);
		}
		super.update("CMT_NEWS.update", news);
		return news.getId();
	}

}