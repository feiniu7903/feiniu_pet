package com.lvmama.pet.mobile.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.lvmama.comm.pet.po.mobile.MobileRecommendInfo;
import com.lvmama.comm.pet.service.mobile.MobileRecommendInfoService;
import com.lvmama.pet.mobile.dao.MobileRecommendInfoDAO;

/**
 * 推荐信息.
 * @author qinzubo
 *
 */
public class MobileRecommendInfoServiceImpl implements MobileRecommendInfoService {
	/**
	 * 日志输出器
	 */
	private static final Log LOG = LogFactory.getLog(MobileRecommendInfoServiceImpl.class);
	
	@Autowired
	private MobileRecommendInfoDAO mobileRecommendInfoDAO;
	
	/**
	 * 新增
	 */
	@Override
	public MobileRecommendInfo insertMobileRecommendInfo( MobileRecommendInfo rinfo) {
		MobileRecommendInfo mri = mobileRecommendInfoDAO.insert(rinfo);
		return mri;
	}

	/**
	 * 修改
	 */
	@Override
	public MobileRecommendInfo updateMobileRecommendInfo(MobileRecommendInfo rinfo) {
		mobileRecommendInfoDAO.updateByPrimaryKey(rinfo);
		return rinfo;
	}

	/**
	 * 根据主键查找
	 */
	@Override
	public MobileRecommendInfo selectMobileRecommendInfoById(Long id) {
		MobileRecommendInfo mri = mobileRecommendInfoDAO.selectByPrimaryKey(id);
		return mri;
	}

	/**
	 * 查询记录数.
	 */
	@Override
	public List<MobileRecommendInfo> queryMobileRecommendInfoList(Map<String, Object> param) {
		return mobileRecommendInfoDAO.queryRecommendInfoList(param);
	}

	/**
	 * 查询总数. 
	 */
	@Override
	public Long countMobileRecommendInfoList(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return mobileRecommendInfoDAO.countMobileRecommendInfoList(param);
	}
	
	/**
	 * 删除
	 */
	@Override
	public int deleteMobileRecommendInfoById(Long id) {
		return mobileRecommendInfoDAO.deleteByPrimaryKey(id);

	}

	/**
	 * 更新状态. 
	 */
	@Override
	public boolean updateStatus(String isValid, Long id) {
		Map m = new HashMap();
		m.put("id", id);
		m.put("isValid", isValid);
		int i = mobileRecommendInfoDAO.updateIsValidById(m);
		if(i > 0){
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean updateSeq(String seq, Long id) {
		// TODO Auto-generated method stub
		Map m = new HashMap();
		m.put("id", id);
		m.put("seq", seq);
		int i = mobileRecommendInfoDAO.updateSeq(m);
		if(i > 0){
			return true;
		} else {
			return false;
		}
	}



}
