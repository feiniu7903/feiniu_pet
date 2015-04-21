package com.lvmama.operate.service.impl;
/**
 * 用户订阅邮件信息服务实现类
 * @author shangzhengyuan
 * @createDate 2011-09-13
 */
import java.util.List;
import java.util.Map;

import com.lvmama.comm.pet.po.edm.EdmSubscribeInfo;
import com.lvmama.comm.pet.service.edm.EdmSubscribeInfoService;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.operate.dao.EdmSubscribeInfoDAO;

public class EdmSubscribeInfoServiceImpl implements EdmSubscribeInfoService {
     /**
      * 用户订阅邮件信息持久类
      */
     private EdmSubscribeInfoDAO edmSubscribeInfoDAO;
     
     private EdmSubscribeInfoDAO subscribeInfoDAO;
     @Override
     public Long count(Map<String, Object> params) {
          return edmSubscribeInfoDAO.count(params);
     }

     @Override
     public List<EdmSubscribeInfo> query(Map<String, Object> params) {
          return edmSubscribeInfoDAO.query(params);
     }

     @Override
 	public EdmSubscribeInfo insert(EdmSubscribeInfo arg0) {
    	 if(StringUtil.isEmptyString(arg0.getType())){
    		 return arg0;
    	 }
 		return subscribeInfoDAO.insert(arg0);
 	}

 	@Override
 	public List<EdmSubscribeInfo> searchEdmInfo(Map<String, Object> arg0) {
 		return subscribeInfoDAO.searchEdmInfo(arg0);
 	}

 	@Override
 	public int update(EdmSubscribeInfo arg0) {
 		return subscribeInfoDAO.update(arg0);
 	}

	public EdmSubscribeInfoDAO getEdmSubscribeInfoDAO() {
		return edmSubscribeInfoDAO;
	}

	public void setEdmSubscribeInfoDAO(EdmSubscribeInfoDAO edmSubscribeInfoDAO) {
		this.edmSubscribeInfoDAO = edmSubscribeInfoDAO;
	}

	public EdmSubscribeInfoDAO getSubscribeInfoDAO() {
		return subscribeInfoDAO;
	}

	public void setSubscribeInfoDAO(EdmSubscribeInfoDAO subscribeInfoDAO) {
		this.subscribeInfoDAO = subscribeInfoDAO;
	}
}
