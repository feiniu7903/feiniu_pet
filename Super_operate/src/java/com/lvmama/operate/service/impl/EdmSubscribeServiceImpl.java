package com.lvmama.operate.service.impl;
/**
 * 订阅邮件用户信息服务实现类
 * @author shangzhengyuan
 * @createDate 2011-09-13
 */
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.pet.po.edm.EdmSubscribe;
import com.lvmama.comm.pet.po.edm.EdmSubscribeInfo;
import com.lvmama.comm.pet.service.edm.EdmSubscribeService;
import com.lvmama.operate.dao.EdmSubscribeDAO;

public class EdmSubscribeServiceImpl implements EdmSubscribeService {
     /**
      * 订阅用户持久类
      */
     private EdmSubscribeDAO edmSubscribeDAO;
     private EdmSubscribeDAO subscribeDAO;
     
     @Override
     public Long count(Map<String, Object> params) {
          return edmSubscribeDAO.count(params);
     }

     @Override
     public List<EdmSubscribe> query(Map<String, Object> params) {
          return edmSubscribeDAO.query(params);
     }

	@Override
	public EdmSubscribe insert(EdmSubscribe arg0) {
		return subscribeDAO.insert(arg0);
	}

	@Override
	public EdmSubscribe searchEmailIsSubscribe(String arg0) {
		EdmSubscribe edm = subscribeDAO.searchEmailIsSubscribe(arg0);
		getInfoList(edm);
		return edm;
	}

	@Override
	public EdmSubscribe searchSubscribe(Map<String, Object> arg0) {
		EdmSubscribe edm =subscribeDAO.searchSubscribe(arg0);
		getInfoList(edm);
		return edm;
	}

	@Override
	public int update(EdmSubscribe arg0) {
		return subscribeDAO.update(arg0);
	}
	
	private void getInfoList(final EdmSubscribe edm){
		if(null!=edm && null!=edm.getTypeName()){
			List<EdmSubscribeInfo> list = new ArrayList<EdmSubscribeInfo>();
			String[] array = edm.getTypeName().split(",");
			for(int i=0;i<array.length;i++){
				EdmSubscribeInfo info = new EdmSubscribeInfo();
				info.setType(array[i]);
				list.add(info);
			}
			edm.setInfoList(list);
		}
	}
	public EdmSubscribeDAO getEdmSubscribeDAO() {
		return edmSubscribeDAO;
	}

	public void setEdmSubscribeDAO(EdmSubscribeDAO edmSubscribeDAO) {
		this.edmSubscribeDAO = edmSubscribeDAO;
	}

	public EdmSubscribeDAO getSubscribeDAO() {
		return subscribeDAO;
	}

	public void setSubscribeDAO(EdmSubscribeDAO subscribeDAO) {
		this.subscribeDAO = subscribeDAO;
	}

}
