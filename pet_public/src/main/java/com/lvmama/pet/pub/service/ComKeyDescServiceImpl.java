package com.lvmama.pet.pub.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.lvmama.comm.pet.po.pub.ComKeyDesc;
import com.lvmama.comm.pet.service.pub.ComKeyDescService;
import com.lvmama.pet.pub.dao.ComKeyDescDAO;

public class ComKeyDescServiceImpl implements ComKeyDescService {
	@Autowired
	private ComKeyDescDAO comKeyDescDAO;
	
	public void insert(String key, String desc) {
		if (StringUtils.isNotBlank(key) && StringUtils.isNotBlank(desc)) {
			ComKeyDesc ckd = new ComKeyDesc();
			ckd.setKey(key);
			ckd.setDesc(desc);
			//comKeyDescDAO.insert(ckd);
		}
	}
	
	public List<ComKeyDesc> queryAll() {
		return comKeyDescDAO.queryListAll();
	}
	
	public void remove(String key) {
		if (StringUtils.isNotBlank(key)) {
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("key", key);
			comKeyDescDAO.delete("COM_KEY_DESC.delete", param);
		}
	}
	
	public void deleteInValid() {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("valid", "true");
		comKeyDescDAO.delete("COM_KEY_DESC.delete", param);
	}
	
	@Override
	public Long countComKeyDesc() {
		return comKeyDescDAO.getCount();
	}
	
	@Override
	public List<ComKeyDesc> queryComKeyDescByPage(Map<String, Object> param) {
		return comKeyDescDAO.queryComKeyDescByPage(param);
	}
}
