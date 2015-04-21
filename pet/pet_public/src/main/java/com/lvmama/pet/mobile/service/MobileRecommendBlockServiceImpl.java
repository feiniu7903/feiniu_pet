package com.lvmama.pet.mobile.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.lvmama.comm.pet.po.mobile.MobileRecommendBlock;
import com.lvmama.comm.pet.service.mobile.MobileRecommendBlockService;
import com.lvmama.pet.mobile.dao.MobileRecommendBlockDAO;
import com.lvmama.pet.mobile.dao.MobileRecommendInfoDAO;

/**
 * 推举块. 
 * @author qinzubo
 *
 */
public class MobileRecommendBlockServiceImpl implements MobileRecommendBlockService {

	@Autowired
	private MobileRecommendInfoDAO mobileRecommendInfoDAO;
	
	@Autowired
	private MobileRecommendBlockDAO mobileRecommendBlockDAO;
	
	@Override
	public MobileRecommendBlock getMobileRecommendBlockById(Long id) {
		return mobileRecommendBlockDAO.getMobileRecommendBlockById(id);
	}

	@Override
	public List<MobileRecommendBlock> queryMobileRecommendBlockByParam( Map<String, Object> param) {
		return mobileRecommendBlockDAO.queryMobileRecommendBlockByParam(param);
	}

	/**
	 * 删除 . 
	 * @param mobileRecommendBlock
	 * @param type节点类型 0：根节点，1：子节点 （  0:从根节点开始删除 ，1：从子节点开始时）
	 */
	@Override
	public void deleteMobileRecommendBlockAndInfo( MobileRecommendBlock mobileRecommendBlock,String type) {
		if (mobileRecommendBlock != null) {
			if ("1".equals(type)) { // 从子节点开始删 
				//删除相应推荐信息
				Map<String,Object> params = new HashMap<String,Object>();
				params.put("recommendBlockId", mobileRecommendBlock.getId());
				mobileRecommendInfoDAO.deleteByParam(params);
			} else if ("0".equals(type)) {  // 从根节点开始删  
				//最顶级删除推荐信息-删除下级推荐模块
				mobileRecommendInfoDAO.deleteMobileRecommendInfoByBlockIdAndSon(mobileRecommendBlock.getId());
				Map<String,Object> m = new HashMap<String,Object>();
				m.put("parentId", mobileRecommendBlock.getId());
				mobileRecommendBlockDAO.deleteByParams(m);
			}
			//删除本模块
			mobileRecommendBlockDAO.deleteByPrimaryKey(mobileRecommendBlock.getId());
		}
		
	}

	@Override
	public MobileRecommendBlock insertMobileRecommendBlock( MobileRecommendBlock mobileRecommendBlock) {
		Long l = mobileRecommendBlockDAO.insert(mobileRecommendBlock);
		return getMobileRecommendBlockById(l);
	}

	@Override
	public void updateMobileRecommendBlock( MobileRecommendBlock mobileRecommendBlock) {
		mobileRecommendBlockDAO.updateByPrimaryKey(mobileRecommendBlock);
	}


	public void setMobileRecommendInfoDAO(
			MobileRecommendInfoDAO mobileRecommendInfoDAO) {
		this.mobileRecommendInfoDAO = mobileRecommendInfoDAO;
	}

	public void setMobileRecommendBlockDAO(
			MobileRecommendBlockDAO mobileRecommendBlockDAO) {
		this.mobileRecommendBlockDAO = mobileRecommendBlockDAO;
	}


}
