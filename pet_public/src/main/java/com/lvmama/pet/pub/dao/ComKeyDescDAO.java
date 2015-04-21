package com.lvmama.pet.pub.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.place.Place;
import com.lvmama.comm.pet.po.pub.ComKeyDesc;

public class ComKeyDescDAO extends BaseIbatisDAO {
	
	/**
	 * 查询所有的key-descript
	 * @return
	 */
	public List<ComKeyDesc> queryListAll() {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("valid", "true");
		
		List<ComKeyDesc> list = super.queryForList("COM_KEY_DESC.selectAll");
		return list;
	}
	
	/**
	 * 插入key-descript
	 * @param comKeyDesc
	 * 插入前会将原本的key删除
	 */
	public void insert(ComKeyDesc comKeyDesc) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("key", comKeyDesc.getKey());
		super.insert("COM_KEY_DESC.insert", comKeyDesc);
	}
	
	public Long getCount(){
		Long totalResultSize = (Long) super.queryForObject("COM_KEY_DESC.count");
		return totalResultSize;
	}
	
	
	public List<ComKeyDesc> queryComKeyDescByPage(Map<String,Object> param){
		if (null == param.get("startRows")) {
			param.put("startRows", 0);
		}
		if (null == param.get("endRows")) {
			param.put("endRows", 15);
		}
		return (List<ComKeyDesc>)super.queryForList("COM_KEY_DESC.queryComKeyDescList",param);
		
	}
}
