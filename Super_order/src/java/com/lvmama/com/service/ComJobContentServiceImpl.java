/**
 * 
 */
package com.lvmama.com.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.com.dao.ComJobContentDAO;
import com.lvmama.comm.bee.po.pub.ComJobContent;
import com.lvmama.comm.bee.po.pub.ComJobContent.JOB_TYPE;
import com.lvmama.comm.bee.service.com.ComJobContentService;

/**
 * @author lancey
 *
 */
public class ComJobContentServiceImpl implements ComJobContentService {

	private ComJobContentDAO comJobContentDAO;
	
	/* (non-Javadoc)
	 * @see com.lvmama.comm.bee.service.com.ComJobContentService#select(com.lvmama.comm.bee.po.pub.ComJobContent.JOB_TYPE, java.util.Date)
	 */
	@Override
	public List<ComJobContent> select(JOB_TYPE type, Date date) {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("jobType", type.name());
		map.put("planTime", date);
		return comJobContentDAO.selectList(map);
	}

	/* (non-Javadoc)
	 * @see com.lvmama.comm.bee.service.com.ComJobContentService#delete(java.lang.Long)
	 */
	@Override
	public void delete(Long pk) {
		comJobContentDAO.deleteByPrimaryKey(pk);
	}

	public void setComJobContentDAO(ComJobContentDAO comJobContentDAO) {
		this.comJobContentDAO = comJobContentDAO;
	}

	@Override
	public void add(ComJobContent record) {
		comJobContentDAO.insert(record);
	}

	@Override
	public List<ComJobContent> selectByParams(JOB_TYPE type, Date beginDate,
			Date endDate) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("jobType", type.name());
		params.put("beginDate", beginDate);
		params.put("endDate", endDate);
		return comJobContentDAO.selectByParams(params);
	}

}
