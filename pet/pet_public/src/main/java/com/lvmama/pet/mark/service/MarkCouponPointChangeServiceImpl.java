/**
 * 
 */
package com.lvmama.pet.mark.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.bee.po.prod.ProdProduct;
import com.lvmama.comm.pet.po.mark.MarkCouponPointChange;
import com.lvmama.comm.pet.service.mark.MarkCouponPointChangeService;
import com.lvmama.pet.mark.dao.MarkCouponPointChangeDAO;

/**
 * @author liuyi
 *
 */
public class MarkCouponPointChangeServiceImpl implements
		MarkCouponPointChangeService {

	private MarkCouponPointChangeDAO markCouponPointChangeDAO;
	
	public MarkCouponPointChange selectBySubProductType(String subProductType){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("subProductType", subProductType);
		List<MarkCouponPointChange> markCouponPointChangeList =  markCouponPointChangeDAO.selectByParam(param);
		if(markCouponPointChangeList != null && markCouponPointChangeList.size() > 0){
			return markCouponPointChangeList.get(0);
		}else{
			return null;
		}
	}
	
	/* (non-Javadoc)
	 * @see com.lvmama.comm.pet.service.mark.MarkCouponPointChangeService#selectByParam(java.util.Map)
	 */
	@Override
	public List<MarkCouponPointChange> selectByParam(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return markCouponPointChangeDAO.selectByParam(param);
	}

	/* (non-Javadoc)
	 * @see com.lvmama.comm.pet.service.mark.MarkCouponPointChangeService#countByParam(java.util.Map)
	 */
	@Override
	public Long countByParam(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return markCouponPointChangeDAO.countByParam(param);
	}

	/* (non-Javadoc)
	 * @see com.lvmama.comm.pet.service.mark.MarkCouponPointChangeService#insert(com.lvmama.comm.pet.po.mark.MarkCouponPointChange)
	 */
	@Override
	public MarkCouponPointChange insert(
			MarkCouponPointChange markCouponPointChange) {
		// TODO Auto-generated method stub
		return markCouponPointChangeDAO.insert(markCouponPointChange);
	}

	/* (non-Javadoc)
	 * @see com.lvmama.comm.pet.service.mark.MarkCouponPointChangeService#updateByPrimaryKey(com.lvmama.comm.pet.po.mark.MarkCouponPointChange)
	 */
	@Override
	public int updateByPrimaryKey(MarkCouponPointChange markCouponPointChange) {
		// TODO Auto-generated method stub
		return markCouponPointChangeDAO.updateByPrimaryKey(markCouponPointChange);
	}

	/* (non-Javadoc)
	 * @see com.lvmama.comm.pet.service.mark.MarkCouponPointChangeService#deleteByPrimaryKey(java.lang.Long)
	 */
	@Override
	public int deleteByPrimaryKey(Long markCouponPointChangeId) {
		// TODO Auto-generated method stub
		return markCouponPointChangeDAO.deleteByPrimaryKey(markCouponPointChangeId);
	}

	public void setMarkCouponPointChangeDAO(
			MarkCouponPointChangeDAO markCouponPointChangeDAO) {
		this.markCouponPointChangeDAO = markCouponPointChangeDAO;
	}

}
